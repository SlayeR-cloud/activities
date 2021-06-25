package com.view;

import com.bussiness.RegisterPublication;
import com.domain.Publication;
import com.exceptions.DataAccessException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class MainWindow extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JPanel westPanel;
    private JPanel centrePanel;

    private JButton addBook;
    private JButton viewBooks;
    private JButton searchBook;
    private JButton deleteBook;

    private final Image logo;
    private RegisterPublication publicationManager;

    public MainWindow(){
        logo = new ImageIcon("src/images/logo.png").getImage();
        this.setSize(850, 500);
        this.publicationManager = new RegisterPublication();
        this.innitComponents();
        this.setTitle("Virtual Library 1.0");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setIconImage(logo);
        this.setResizable(false);
    }

    public void messageWindow(String message, String title, int type, ImageIcon icon){
        if(icon != null)
            JOptionPane.showMessageDialog(this, message, title, type, icon);
        else
            JOptionPane.showMessageDialog(this, message, title, type);
    }

    public void setWestPanel(){
        this.westPanel = new JPanel();
        this.westPanel.setLayout(new BorderLayout());
        ImageIcon labelImage = new ImageIcon("src/images/library.png");
        labelImage = resize(labelImage, 300, 300);
        JLabel title = new JLabel("Virtual Library");
        title.setIcon(labelImage);
        title.setVerticalTextPosition(JLabel.TOP);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setIconTextGap(30);
        this.westPanel.setBackground(new Color(880015));
        this.westPanel.add(title, BorderLayout.CENTER);
        Border border = BorderFactory.createLineBorder(this.westPanel.getBackground(),30);
        this.westPanel.setBorder(border);
    }

    public void setCentrePanel(){
        this.centrePanel = new JPanel();
        JPanel optionPanel = new JPanel();
        JPanel westEmpty = new JPanel();
        westEmpty.setBackground(Color.CYAN);

        optionPanel.setSize(500,500);
        optionPanel.setLayout(new GridLayout(4,2, 5, 40));

        this.addBook = new JButton("Add a publication");
        this.addBook.setFocusable(false);
        this.addBook.addActionListener(this);
        ImageIcon addIcon = new ImageIcon("src/images/add.png");
        addIcon = resize(addIcon, 50,50);
        JLabel emptyLabel = new JLabel();
        emptyLabel.setIcon(addIcon);
        this.viewBooks = new JButton("View all publications");
        this.viewBooks.setFocusable(false);
        this.viewBooks.addActionListener(this);
        ImageIcon viewIcon = new ImageIcon("src/images/read.png");
        viewIcon = resize(viewIcon, 50,50);
        JLabel emptyLabel2 = new JLabel();
        emptyLabel2.setIcon(viewIcon);
        this.searchBook = new JButton("Search a publication");
        this.searchBook.setFocusable(false);
        this.searchBook.addActionListener(this);
        ImageIcon searchIcon = new ImageIcon("src/images/search.png");
        searchIcon = resize(searchIcon, 50,50);
        JLabel emptyLabel3 = new JLabel();
        emptyLabel3.setIcon(searchIcon);
        this.deleteBook = new JButton("Delete a publication");
        this.deleteBook.setFocusable(false);
        this.deleteBook.addActionListener(this);
        ImageIcon deleteIcon = new ImageIcon("src/images/delete.png");
        deleteIcon = resize(deleteIcon, 50,50);
        JLabel emptyLabel4 = new JLabel();
        emptyLabel4.setIcon(deleteIcon);

        optionPanel.add(addBook);
        optionPanel.add(emptyLabel);
        optionPanel.add(viewBooks);
        optionPanel.add(emptyLabel2);
        optionPanel.add(searchBook);
        optionPanel.add(emptyLabel3);
        optionPanel.add(deleteBook);
        optionPanel.add(emptyLabel4);

        Border border = BorderFactory.createLineBorder(this.centrePanel.getBackground(),50);
        optionPanel.setBorder(border);
        this.centrePanel.setLayout(new BorderLayout());
        this.centrePanel.add(optionPanel, BorderLayout.CENTER);
        this.centrePanel.add(westEmpty, BorderLayout.WEST);
    }

    public void innitComponents(){
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        this.setWestPanel();
        this.setCentrePanel();
        this.mainPanel.setSize(this.getSize());
        this.mainPanel.add(this.westPanel, BorderLayout.WEST);
        this.mainPanel.add(this.centrePanel, BorderLayout.CENTER);
        this.add(mainPanel);
    }

    ImageIcon resize(ImageIcon icon, int height, int width){
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(height,width, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ImageIcon errorIcon = new ImageIcon("src/images/warning.png");
        errorIcon = resize(errorIcon, 40, 40);
        if(actionEvent.getSource() == this.addBook){
            new AddWindow(this, true);
        }
        if(actionEvent.getSource() == this.viewBooks){
            try {
                this.publicationManager.readPublications();
                new ViewWindow(this, true);
            } catch (DataAccessException | IOException e) {
                messageWindow(e.getMessage(), "Warning Advice", JOptionPane.ERROR_MESSAGE, errorIcon);
            }
        }
        if(actionEvent.getSource() == this.searchBook){
            try {
                this.publicationManager.readPublications();
                new SearchWindow(this, true);
            } catch (DataAccessException | IOException e) {
                messageWindow(e.getMessage(), "Warning Advice", JOptionPane.ERROR_MESSAGE, errorIcon);
            }
        }
        if(actionEvent.getSource() == this.deleteBook){
            try {
                this.publicationManager.readPublications();
                new DeleteWindow(this, true);
            } catch (DataAccessException | IOException e) {
                messageWindow(e.getMessage(), "Warning Advice", JOptionPane.ERROR_MESSAGE, errorIcon);
            }
        }
    }
}