package com.view;

import com.bussiness.RegisterPublication;
import com.domain.Publication;
import com.exceptions.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DeleteWindow extends JDialog implements ActionListener {
    private Container container;
    private JPanel filterPane;
    private JLabel filterLabel;
    private JTextField filterText;
    private JButton filterButton;

    private RegisterPublication publicationManager;

    public DeleteWindow(JFrame frame, boolean value){
        super(frame, value);
        this.publicationManager = new RegisterPublication();
        this.setTitle("Deleting Publication");
        this.configurePanel();
        this.pack();
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
        ImageIcon icon = new ImageIcon("src/images/banner4.png");
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
        ImageIcon buttonIcon = new ImageIcon("src/images/searchB.png");
        buttonIcon = resize(buttonIcon, 30, 30);
        this.filterPane = new JPanel(new FlowLayout());
        this.filterPane.setSize(this.getSize());
        this.filterLabel = new JLabel("Publication ISBN to delete");
        this.filterText = new JTextField(30);
        this.filterButton = new JButton();
        this.filterButton.setFocusable(false);
        this.filterButton.setIcon(buttonIcon);
        this.filterButton.addActionListener(this);
        this.filterText.setHorizontalAlignment(this.filterButton.getHorizontalAlignment());

        this.filterPane.add(this.filterLabel);
        this.filterPane.add(this.filterText);
        this.container.add(this.filterPane, BorderLayout.CENTER);
        this.container.add(this.filterButton, BorderLayout.SOUTH);
    }

    ImageIcon resize(ImageIcon icon, int height, int width){
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(height,width, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }

    public void messageWindow(String message, String title, int type, ImageIcon icon){
        if(icon != null)
            JOptionPane.showMessageDialog(this, message, title, type, icon);
        else
            JOptionPane.showMessageDialog(this, message, title, type);
    }

    public Publication deletePublication(Publication p) throws IOException, DataAccessException {
        return this.publicationManager.deletePublication(p);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == this.filterButton){
            ImageIcon trashIcon = new ImageIcon("src/images/trash.png");
            trashIcon = resize(trashIcon, 40, 40);
            String isbn = this.filterText.getText();
            try {
                Publication p = deletePublication(new Publication(isbn));
                messageWindow("Delete Completed, Showing data deleted:\n\n"+p.toString(), "Deleting Publication", JOptionPane.INFORMATION_MESSAGE, trashIcon);
                this.dispose();
            } catch (IOException | DataAccessException | NullPointerException exception) {
                if (exception instanceof NullPointerException)
                        messageWindow("Publication not found", "Deleting Publication", JOptionPane.INFORMATION_MESSAGE, null);
                else
                    messageWindow(exception.getMessage(), "Deleting Publication", JOptionPane.INFORMATION_MESSAGE, null);
            }
        }
    }
}
