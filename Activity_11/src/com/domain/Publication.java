package com.domain;

import java.io.Serializable;

public class Publication implements Serializable {

    private String isbn, title, author;
    private int year;
    private double cost;

    public Publication() {
    }

    public Publication(String isbn) {
        this.isbn = isbn;
    }

    public Publication(String isbn, String title, String author, int year, double cost) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.cost = cost;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "ISBN: "+isbn + "\nTITLE: " + title + "\nAUTHOR: " + author
                + "\nYEAR: " + year + "\nCOST: " + cost;
    }
}