package com.view;

import com.exceptions.DataAccessException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws DataAccessException, IOException {
        ConsolaView consolaView = new ConsolaView();
        consolaView.runMenu();
    }
}