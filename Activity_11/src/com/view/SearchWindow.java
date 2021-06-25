package com.view;

import com.bussiness.RegisterPublication;
import com.domain.AudioBook;
import com.domain.Book;
import com.domain.Publication;
import com.exceptions.DataAccessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class SearchWindow extends JDialog implements ActionListener, MouseListener {
    private JTableHeader header;
    private Container container;
    private JPanel filterPane;
    private JScrollPane resultsPane;
    private JLabel filterLabel;
    private JTextField filterText;
    private JTable table;
    private JButton viewButton;
    // For manage the table, we need a table model
    private DefaultTableModel model;
    private String[] titles = {"ISBN", "TITLE", "AUTHOR", "YEAR",
            "COST", "TYPE", "VIEW"};
    private JButton cleanButton;
    private RegisterPublication publicationManager;

    public SearchWindow(JFrame frame, boolean modalMode){
        super(frame, modalMode);
        this.publicationManager = new RegisterPublication();
        this.configurePanel();
        this.pack();
        this.setTitle("Searching Publication");
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
        ImageIcon icon = new ImageIcon("src/images/banner3.png");
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
        this.innitFilterPane();
        this.innitResultsPane();
        this.showTotal();
        this.table.setEnabled(false);
    }

    public void innitResultsPane(){

        this.resultsPane = new JScrollPane();
        this.table = new JTable();
        this.table.addMouseListener(this);
        this.table.setDefaultRenderer(Object.class, new Render());
        this.header = this.table.getTableHeader();
        this.header.setDefaultRenderer(new HeadRender());
        this.table.setTableHeader(this.header);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setResizingAllowed(false);
        // The defaultTableModel gets an object where take the information
        // and also takes the titles, we give it null because we don't have
        // any object yet
        this.model = new DefaultTableModel(null, this.titles);
        this.table.setModel(model);
        this.resultsPane.setViewportView(this.table);
        this.container.add(this.resultsPane, BorderLayout.CENTER);
    }

    public void messageWindow(String message, String title, int type, ImageIcon icon){
        if(icon != null)
            JOptionPane.showMessageDialog(this, message, title, type, icon);
        else
            JOptionPane.showMessageDialog(this, message, title, type);
    }


    public void showTotal(){
        try{
            List<Publication> list = this.publicationManager.readPublications();
            this.tableUpdateII(list);

        }catch(IOException | DataAccessException ioe){
            this.messageWindow("Error", ioe.getMessage(), JOptionPane.ERROR_MESSAGE, null);
        }

    }

    public void tableUpdateII(List<Publication> list){

        this.model.setNumRows(0);
        ImageIcon viewIcon = new ImageIcon("src/images/view.png");
        viewIcon = resize(viewIcon, 30, 30);
        this.viewButton = new JButton();
        this.viewButton.setIcon(viewIcon);
        String type;
        for(Publication p : list){
            if (p instanceof AudioBook){
                type = "Audiobook";
            }else{
                type = "Book";
            }
            Object[] line ={p.getIsbn(), p.getTitle(), p.getAuthor(),
                    ""+p.getYear(), ""+p.getCost(), type, this.viewButton};
            this.model.addRow(line);
        }
        this.table.setRowHeight(20);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(150);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(150);
    }

    ImageIcon resize(ImageIcon icon, int height, int width){
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(height,width, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }

    public void innitFilterPane(){
        this.filterPane = new JPanel();
        this.cleanButton = new JButton("Clean Filter");
        this.cleanButton.setSize(30,20);
        this.cleanButton.addActionListener(this);
        // Moves the left the elements
        this.filterPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.filterLabel = new JLabel("Search Filter: ");
        this.filterText = new JTextField(10);
        this.filterText.addKeyListener(new KeyFilterEvent());

        this.filterPane.add(this.filterLabel);
        this.filterPane.add(this.filterText);
        this.filterPane.add(this.cleanButton);
        this.container.add(this.filterPane, BorderLayout.SOUTH);

    }

    public void clear(){
        this.filterText.setText(null);
        this.tableFilter();
    }

    public void tableFilter(){
        String text = this.filterText.getText();
        try{
            List<Publication> filterList = this.publicationManager.searchFilter(text);
            this.tableUpdateII(filterList);

        }catch(IOException | DataAccessException ioe){
            this.messageWindow("Error", ioe.getMessage(), JOptionPane.ERROR_MESSAGE, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == this.cleanButton){
            clear();
        }
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
            this.messageWindow(data, "Viewing Publications", JOptionPane.INFORMATION_MESSAGE, icon);
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

    class KeyFilterEvent extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent ke) {
            tableFilter();
        }
    }

}
