package com.domain;

public class Book extends Publication {

    private int pages, edition;

    public Book() {
    }

    public Book(String isbn, String title, String author, int year, double cost, int pages, int edition) {
        super(isbn, title, author, year, cost);
        this.pages = pages;
        this.edition = edition;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPAGES: " + pages +
                "\nEDITION: " + edition;
    }
}

