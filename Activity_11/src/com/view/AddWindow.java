package com.view;


import com.bussiness.RegisterPublication;
import com.domain.AudioBook;
import com.domain.Book;
import com.domain.Publication;
import com.exceptions.DataAccessException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddWindow extends JDialog {

    private Container container;
    private JPanel mainPanel;
    private JTextField isbnTextField;
    private JTextField titleTextField;
    private JTextField authorTextField;
    private JTextField numberTextField;
    private JTextField editionTextField;
    private JTextField durationTextField;
    private JTextField weightTextField;
    private JTextField pagesTextField;
    private JTextField costTextField;
    private JComboBox format;
    private JComboBox yearBox;
    private JRadioButton bookButton;
    private JRadioButton audioButton;
    private ButtonGroup buttonGroup;

    private JButton saveButton;
    private JButton restartButton;
    private JButton continueButton;
    private JButton cleanButton;
    private JFrame main;

    private RegisterPublication publicationManager;

    public AddWindow(JFrame frame, boolean modalMode){
        super(frame, modalMode);
        this.main = frame;
        this.configurePanel();
        this.pack();
        this.publicationManager = new RegisterPublication();
        this.setTitle("Adding Publication");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(500, 700);
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
        ImageIcon icon = new ImageIcon("src/images/banner.png");
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
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayout(13, 2, 3, 10));
        this.mainPanel.setBackground(Color.WHITE);
        this.mainPanel.setSize(500, 900);
        Border border = BorderFactory.createLineBorder(this.mainPanel.getBackground(),10);
        this.mainPanel.setBorder(border);
        JLabel isbnLabel = new JLabel("ISBN");
        this.isbnTextField = new JTextField(null);
        JLabel titleLabel = new JLabel("TITLE");
        this.titleTextField = new JTextField(null);
        JLabel authorLabel = new JLabel("AUTHOR");
        this.authorTextField = new JTextField(null);
        JLabel yearLabel = new JLabel("YEAR");
        this.yearBox = new JComboBox();
        for (int i = 1000; i<9999; i++){
            yearBox.addItem(i);
        }
        yearBox.setSelectedItem(2021);
        JLabel costLabel = new JLabel("COST");
        this.costTextField = new JTextField(null);
        JLabel typeLabel = new JLabel("TYPE");
        this.bookButton = new JRadioButton("BOOK");
        bookButton.setSelected(true);
        this.audioButton = new JRadioButton("AUDIOBOOK");
        this.buttonGroup = new ButtonGroup();
        buttonGroup.add(bookButton);
        buttonGroup.add(audioButton);
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioPanel.add(bookButton);
        radioPanel.add(audioButton);
        this.mainPanel.add(isbnLabel);
        this.mainPanel.add(isbnTextField);
        this.mainPanel.add(titleLabel);
        this.mainPanel.add(titleTextField);
        this.mainPanel.add(authorLabel);
        this.mainPanel.add(authorTextField);
        this.mainPanel.add(yearLabel);
        this.mainPanel.add(yearBox);
        this.mainPanel.add(costLabel);
        this.mainPanel.add(costTextField);
        this.mainPanel.add(typeLabel);
        this.mainPanel.add(radioPanel);
        this.continueButton = new JButton("Continue");
        this.continueButton.addActionListener(new ContinueListener());
        this.cleanButton = new JButton("Clean");
        this.cleanButton.addActionListener(new ClearListener());
        this.mainPanel.add(this.continueButton);
        this.mainPanel.add(this.cleanButton);
        this.container.add(this.mainPanel, BorderLayout.CENTER);
    }
    public void innitAudioBookPANE(){
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridLayout(4, 2, 3, 10));
        newPanel.setBackground(this.mainPanel.getBackground());
        newPanel.setSize(this.mainPanel.getSize());
        newPanel.setBorder(this.mainPanel.getBorder());
        JLabel durationLabel = new JLabel("DURATION");
        JLabel weightLabel = new JLabel("WEIGHT");
        JLabel formatLabel = new JLabel("FORMAT");
        this.durationTextField = new JTextField(null);
        this.weightTextField = new JTextField(null);
        this.format = new JComboBox();
        format.addItem("MP3");
        format.addItem("WAV");
        format.addItem("OPUS");
        format.addItem("WMA");
        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(new saveListener());
        this.restartButton = new JButton("Restart");
        this.restartButton.addActionListener(new RestartListener());
        newPanel.add(durationLabel);
        newPanel.add(this.durationTextField);
        newPanel.add(weightLabel);
        newPanel.add(this.weightTextField);
        newPanel.add(formatLabel);
        newPanel.add(this.format);
        newPanel.add(this.saveButton);
        newPanel.add(this.restartButton);
        this.container.add(newPanel, BorderLayout.SOUTH);
        this.notEnabledMethod();
    }
    ImageIcon resize(ImageIcon icon, int height, int width){
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(height,width, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }
    public void innitBookPANE(){
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridLayout(3, 2, 3, 10));
        newPanel.setBackground(this.mainPanel.getBackground());
        newPanel.setSize(this.mainPanel.getSize());
        newPanel.setBorder(this.mainPanel.getBorder());
        JLabel pagesLabel = new JLabel("NUMBER OF PAGES");
        JLabel editionLabel = new JLabel("EDITION");
        this.pagesTextField = new JTextField(null);
        this.editionTextField = new JTextField(null);
        this.weightTextField = new JTextField(null);
        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(new saveListener());
        this.restartButton = new JButton("Restart");
        this.restartButton.addActionListener(new RestartListener());
        newPanel.add(pagesLabel);
        newPanel.add(this.pagesTextField);
        newPanel.add(editionLabel);
        newPanel.add(this.editionTextField);
        newPanel.add(this.saveButton);
        newPanel.add(this.restartButton);
        this.container.add(newPanel, BorderLayout.SOUTH);
        this.notEnabledMethod();
    }

    public Publication readPublication(){
        String isbn = this.isbnTextField.getText();
        String title = this.titleTextField.getText();
        String author = this.authorTextField.getText();
        int year = Integer.parseInt(this.yearBox.getSelectedItem().toString());
        double cost = Double.parseDouble(this.costTextField.getText());
        Publication publication;
        if(this.audioButton.isSelected()){
            double duration = Double.parseDouble(this.durationTextField.getText());
            double weight = Double.parseDouble(this.weightTextField.getText());
            String format = this.format.getSelectedItem().toString();
            publication = new AudioBook(isbn, title, author, year, cost, duration, weight, format);
        }else{
            int pages = Integer.parseInt(this.pagesTextField.getText());
            int edition = Integer.parseInt(this.editionTextField.getText());
            publication = new Book(isbn, title, author, year, cost, pages, edition);
        }
        return publication;
    }


    public void notEnabledMethod(){
        for(Component component: this.mainPanel.getComponents()){
            component.setEnabled(false);
            if(component instanceof JPanel){
                for(Component component1: ((JPanel) component).getComponents()){
                    component1.setEnabled(false);
                }
            }
        }
    }

    public void messageWindow(String message, String title, int type, ImageIcon icon){
        if(icon != null)
            JOptionPane.showMessageDialog(this, message, title, type, icon);
        else
            JOptionPane.showMessageDialog(this, message, title, type);
    }

    public void close(){
        this.dispose();
    }

    public void savePublication() throws IOException, DataAccessException {
        Publication publication = readPublication();
        this.publicationManager.addPublication(publication);
    }

    public void clear(JPanel jPanel){
        for (Component component: jPanel.getComponents()){
            if(component instanceof JTextField){
                ((JTextField) component).setText(null);
            }
        }
    }

    public void restart(){
        this.close();
        new AddWindow(this.main, true);
    }

    class ContinueListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(getAudioButton().isSelected()){
                innitAudioBookPANE();
            }else{
               innitBookPANE();
            }
            container.validate();
            container.repaint();
        }
    }
    class ClearListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            clear(mainPanel);
        }
    }
    class saveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ImageIcon errorIcon = new ImageIcon("src/images/warning.png");
            errorIcon = resize(errorIcon, 40, 40);
            ImageIcon saveIcon = new ImageIcon("src/images/success.png");
            saveIcon = resize(saveIcon, 50, 40);
            try {                savePublication();
                messageWindow("Save Successfully", "Publication Registering", JOptionPane.INFORMATION_MESSAGE, saveIcon);
                close();
            } catch (IOException | NumberFormatException | NullPointerException | DataAccessException exception) {
                messageWindow(exception.getMessage(), "Warning Advice", JOptionPane.WARNING_MESSAGE, errorIcon);
            }
        }
    }
    class RestartListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            restart();
        }
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JTextField getIsbnTextField() {
        return isbnTextField;
    }

    public void setIsbnTextField(JTextField isbnTextField) {
        this.isbnTextField = isbnTextField;
    }

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public void setTitleTextField(JTextField titleTextField) {
        this.titleTextField = titleTextField;
    }

    public JTextField getAuthorTextField() {
        return authorTextField;
    }

    public void setAuthorTextField(JTextField authorTextField) {
        this.authorTextField = authorTextField;
    }

    public JTextField getNumberTextField() {
        return numberTextField;
    }

    public void setNumberTextField(JTextField numberTextField) {
        this.numberTextField = numberTextField;
    }

    public JTextField getEditionTextField() {
        return editionTextField;
    }

    public void setEditionTextField(JTextField editionTextField) {
        this.editionTextField = editionTextField;
    }

    public JTextField getDurationTextField() {
        return durationTextField;
    }

    public void setDurationTextField(JTextField durationTextField) {
        this.durationTextField = durationTextField;
    }

    public JTextField getWeightTextField() {
        return weightTextField;
    }

    public void setWeightTextField(JTextField weightTextField) {
        this.weightTextField = weightTextField;
    }

    public JTextField getPagesTextField() {
        return pagesTextField;
    }

    public void setPagesTextField(JTextField pagesTextField) {
        this.pagesTextField = pagesTextField;
    }

    public JTextField getCostTextField() {
        return costTextField;
    }

    public void setCostTextField(JTextField costTextField) {
        this.costTextField = costTextField;
    }

    public JComboBox getFormat() {
        return format;
    }

    public void setFormat(JComboBox format) {
        this.format = format;
    }

    public JComboBox getYearBox() {
        return yearBox;
    }

    public void setYearBox(JComboBox yearBox) {
        this.yearBox = yearBox;
    }

    public JRadioButton getBookButton() {
        return bookButton;
    }

    public void setBookButton(JRadioButton bookButton) {
        this.bookButton = bookButton;
    }

    public JRadioButton getAudioButton() {
        return audioButton;
    }

    public void setAudioButton(JRadioButton audioButton) {
        this.audioButton = audioButton;
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public void setButtonGroup(ButtonGroup buttonGroup) {
        this.buttonGroup = buttonGroup;
    }

    public JButton getContinueButton() {
        return continueButton;
    }

    public void setContinueButton(JButton continueButton) {
        this.continueButton = continueButton;
    }

    public JButton getCleanButton() {
        return cleanButton;
    }

    public void setCleanButton(JButton cleanButton) {
        this.cleanButton = cleanButton;
    }
}