package com.domain;

public class AudioBook extends Publication{

    private double duration, weight;
    private String format;

    public AudioBook() {
    }

    public AudioBook(String isbn, String title, String author, int year, double cost, double duration, double weight, String format) {
        super(isbn, title, author, year, cost);
        this.duration = duration;
        this.weight = weight;
        this.format = format;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return super.toString() + ","+duration+","+weight
                +","+format;
    }
}