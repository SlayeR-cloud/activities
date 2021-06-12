package com.view;

import com.bussiness.RegisterPublication;
import com.domain.AudioBook;
import com.domain.Book;
import com.domain.Publication;
import com.exceptions.DataAccessException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConsolaView {
    private final String[] titles = {"1. Register a post", "2. View publications",
            "3. Search for a publication", "4. Delete a publication", "0. Exit"};
    private int option;
    private Scanner input;
    private RegisterPublication register;

    public ConsolaView() {
        this.input = new Scanner(System.in);
        this.register = new RegisterPublication();
    }
    public void runMenu() throws DataAccessException, IOException {
        do{
            this.printTitles();
            this.readOption();
            this.executeOption();
        }while(this.option!=0);
    }
    public void printTitles(){
        System.out.println("\nMenu");
        for (String title : this.titles) {
            System.out.println(title);
        }
    }
    public void readOption(){
        boolean exception = true;
        do{
            try{
                System.out.print("Option: ");
                this.option = this.input.nextInt();
                exception = false;
            }catch (java.util.InputMismatchException e){
                System.out.println("Integer value is required, try again");
                exception = true;
                this.input.nextLine();
            }
        }while (exception);
    }

    public String readString(){
        boolean exception = true;
        String line = null;
        do{
            try{
                line = this.input.next();
                exception = false;
            }catch (java.util.InputMismatchException e){
                System.out.println("String value is required, try again");
                exception = true;
                this.input.nextLine();
            }
        }while (exception);
        return line;
    }

    public int readInt(){
        boolean exception = true;
        int integer = 0;
        do{
            try{
                integer = this.input.nextInt();
                exception = false;
            }catch (java.util.InputMismatchException e){
                System.out.println("Integer value is required, try again");
                exception = true;
                this.input.nextLine();
            }
        }while (exception);
        return integer;
    }

    public char readChar(){
        boolean exception = true;
        char charValue = 'N';
        do{
            try{
                charValue = this.input.next().charAt(0);
                exception = false;
            }catch (java.util.InputMismatchException e){
                System.out.println("Char value is required, try again");
                exception = true;
                this.input.nextLine();
            }
        }while (exception);
        return charValue;
    }

    public double readDouble(){
        boolean exception = true;
        double doubleValue = 0;
        do{
            try{
                doubleValue = this.input.nextDouble();
                exception = false;
            }catch (java.util.InputMismatchException e){
                System.out.println("Double value is required, try again");
                exception = true;
                this.input.nextLine();
            }
        }while (exception);
        return doubleValue;
    }

    public void executeOption() throws DataAccessException, IOException {
        switch (this.option){
            case 1:addPublication();
                break;
            case 2:readPublications();
                break;
            case 3:searchPublication();
                break;
            case 4:deletePublication();
                break;
            case 0:System.out.println("You are out");
                break;
            default:
                System.out.println("Please try to write a valid option");
        }
    }
    public void addPublication() throws DataAccessException , IOException {
        System.out.println(this.titles[this.option-1]);
        System.out.println("Registering a publication...");
        System.out.print("Please add the ISBN: ");
        String isbn = readString();
        System.out.print("Please add the title: ");
        String title = readString();
        System.out.print("Please add the author: ");
        String author = readString();
        System.out.print("Please add the year: ");
        int year = readInt();
        System.out.print("Please add the cost: ");
        double cost = readDouble();
        System.out.println("Is a Book or is an Audiobook? B/A");
        char type = readChar();
        while (type != 'B' && type != 'A') {
            System.out.print("\nYou sent a wrong information, please send a character B/A: ");
            type = readChar();
        }
        Publication publication = null;
        if(type == 'B'){
            System.out.print("Please add the number of pages: ");
            int pages = readInt();
            System.out.print("Please add the edition: ");
            int edition = readInt();
            publication = new Book(isbn, title, author, year, cost, pages, year);
        }else{
            System.out.print("Please add the duration: ");
            double duration = readDouble();
            System.out.print("Please add the weight: ");
            double weight = readDouble();
            System.out.print("Please add the format: ");
            String format = readString();
            publication = new AudioBook(isbn, title, author, year, cost, duration, weight, format);
        }
        this.register.addPublication(publication);
        System.out.print("Added Successful");
    }
    public void readPublications() throws DataAccessException, IOException{
        System.out.println(this.titles[this.option-1]);
        System.out.println("Showing posts...");
        List<Publication> publications = this.register.readPublications();
        for (int i = 0; i < publications.size(); i++){
            System.out.printf("Publication %d \n",i+1);
            System.out.println(publications.get(i).toString());
        }
    }
    public void searchPublication() throws DataAccessException, IOException{
        System.out.println(this.titles[this.option-1]);
        System.out.println("Searching post...");
        System.out.print("Please add the isbn you're looking for: ");
        String isbn = readString();
        Publication publication = new Publication();
        publication.setIsbn(isbn);
        Publication publicationR = this.register.searchPublication(publication);
        if(publicationR==null){
            System.out.println("The publication is not registered");
        }else{
            publication = publicationR;
            System.out.println("Publication founded, showing...");
            if(publication instanceof Book){
                Book pub = (Book) publication;
                System.out.println(pub);
            }else{
                AudioBook pub = (AudioBook) publication;
                System.out.println(pub);
            }
        }
    }
    public void deletePublication() throws DataAccessException, IOException{
        System.out.println(this.titles[this.option-1]);
        System.out.println("Searching post...");
        System.out.print("Please add the isbn you're looking for delete: ");
        String isbn = readString();
        Publication publication = new Publication();
        publication.setIsbn(isbn);
        Publication publicationR = this.register.deletePublication(publication);
        if(publicationR==null){
            System.out.println("The publication is not registered");
        }else{
            publication = publicationR;
            System.out.println("Publication founded, deleted... showing data deleted");
            if(publication instanceof Book){
                Book pub = (Book) publication;
                System.out.println(pub);
            }else{
                AudioBook pub = (AudioBook) publication;
                System.out.println(pub);
            }
        }
    }
}
