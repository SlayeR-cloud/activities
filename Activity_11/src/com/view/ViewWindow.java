package com.view;

import com.bussiness.RegisterPublication;
import com.domain.*;
import com.exceptions.DataAccessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

public class ViewWindow extends JDialog implements MouseListener {
    private Container container;
    private JButton viewButton;
    private JScrollPane resultsPane;
    private JTable table;
    private JTableHeader header;
    // For manage the table, we need a table model
    private DefaultTableModel model;
    private final String[] titles = {"ISBN", "TITLE", "AUTHOR", "YEAR",
            "COST", "TYPE", "VIEW"};

    private RegisterPublication publicationManager;

    public ViewWindow(JFrame frame, boolean modalMode){
        super(frame, modalMode);
        this.publicationManager = new RegisterPublication();
        this.configurePanel();
        this.pack();
        this.setTitle("Viewing Publications");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    public void configurePanel(){
        this.container = getContentPane();
        this.container.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setBackground(new Color(880015));
        JLabel emptyLabel = new JLabel();
        northPanel.setLayout(new FlowLayout());
        ImageIcon icon = new ImageIcon("src/images/banner2.png");
        icon = resize(icon, 400, 150);
        emptyLabel.setIcon(icon);
        northPanel.add(emptyLabel);
        JPanel westEmpty = new JPanel();
        JPanel eastEmpty = new JPanel();
        westEmpty.setBackground(Color.CYAN);
        eastEmpty.setBackground(Color.CYAN);
        this.container.add(northPanel, BorderLayout.NORTH);
        this.container.add(westEmpty, BorderLayout.WEST);
        this.container.add(eastEmpty, BorderLayout.EAST);
        this.innitComponents();
    }
    public void innitComponents(){
        this.innitResultsPane();
        this.tableUpdate();
        this.table.setEnabled(false);
    }

    public void innitResultsPane(){
        this.resultsPane = new JScrollPane();
        this.table = new JTable();
        this.header = this.table.getTableHeader();
        this.header.setDefaultRenderer(new HeadRender());
        this.table.setTableHeader(this.header);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setResizingAllowed(false);
        this.table.setDefaultRenderer(Object.class, new Render());
        // The defaultTableModel gets an object where take the information
        // and also takes the titles, we give it null because we don't have
        // any object yet
        this.model = new DefaultTableModel(null, this.titles);
        this.table.setModel(model);
        this.resultsPane.setViewportView(this.table);
        //this.firstPanelConfiguration();
        this.container.add(this.resultsPane, BorderLayout.CENTER);
    }

    public void messageWindow(String message, String title, int type, ImageIcon icon){
        if(icon != null)
            JOptionPane.showMessageDialog(this, message, title, type, icon);
        else
            JOptionPane.showMessageDialog(this, message, title, type);
    }

    public void tableUpdate() {
        List<Publication> publicationList;
        ImageIcon errorIcon = new ImageIcon("src/images/warning.png");
        errorIcon = resize(errorIcon, 40, 40);
        try{
            publicationList = this.publicationManager.readPublications();
            this.model.setNumRows(0);
            ImageIcon viewIcon = new ImageIcon("src/images/view.png");
            viewIcon = resize(viewIcon, 30, 30);
            this.viewButton = new JButton();
            this.table.addMouseListener(this);
            viewButton.setIcon(viewIcon);
            for(Publication publication: publicationList){
                String type;
                if (publication instanceof AudioBook){
                    type = "Audiobook";
                }else{
                    type = "Book";
                }
                Object[] line = { publication.getIsbn(), publication.getTitle(), publication.getAuthor(),
                        ""+publication.getYear(), ""+publication.getCost(), type, viewButton};
                this.model.addRow(line);
            }
            this.table.setRowHeight(20);
            this.table.getColumnModel().getColumn(1).setPreferredWidth(150);
            this.table.getColumnModel().getColumn(2).setPreferredWidth(150);
        } catch (IOException | DataAccessException exception){
            messageWindow(exception.getMessage(), "Warning Advice", JOptionPane.ERROR_MESSAGE, errorIcon);
        }
    }

    ImageIcon resize(ImageIcon icon, int height, int width){
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(height,width, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int row = this.table.rowAtPoint(mouseEvent.getPoint());
        int column = this.table.columnAtPoint(mouseEvent.getPoint());

        if (column == (this.titles.length-1)) {
            ImageIcon icon = new ImageIcon("src/images/blue.png");
            icon = resize(icon, 40, 40);
            Publication p = new Publication();
            String isbn = this.table.getValueAt(row, 0).toString();
            String data = null;
            p.setIsbn(isbn);
            try {
                p = this.publicationManager.searchPublication(p);
                if(p instanceof AudioBook){
                    AudioBook audioBook = (AudioBook) p;
                    data = audioBook.toString();
                }else{
                    Book book = (Book) p;
                    data = book.toString();
                }
            } catch (DataAccessException | IOException e) {
                e.printStackTrace();
            }
            messageWindow(data, "Viewing Publications", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}