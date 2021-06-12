package com.data;

import com.domain.AudioBook;
import com.domain.Book;
import com.domain.Publication;
import com.exceptions.DataAccessException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileText implements DataAccess {
    private File file;
    private FileWriter writer;
    private Scanner reader;

    public FileText() {
        this.file = new File("data.dat");
    }

    public FileText(String name) {
        this.file = new File(name);
    }

    @Override
    public void addPublication(Publication publication) throws DataAccessException, IOException {
        PrintWriter printer = null;
        try {
            this.writer = new FileWriter(this.file, true);
            printer = new PrintWriter(this.writer);
            if (publication instanceof Book) {
                Book book = (Book) publication;
                printer.println(book);
            } else {
                AudioBook audioBook = (AudioBook) publication;
                printer.println(audioBook);
            }
        } finally {
            if (printer != null)
                printer.close();
            this.writer.close();
        }
    }

    public Publication createPublication(String line) {
        String[] data = line.split(",");
        Publication publication = null;
        if (data.length == 7) {
            Book book = new Book();
            book.setIsbn(data[0]);
            book.setTitle(data[1]);
            book.setAuthor(data[2]);
            book.setYear(Integer.parseInt(data[3]));
            book.setCost(Double.parseDouble(data[4]));
            book.setPages(Integer.parseInt(data[5]));
            book.setEdition(Integer.parseInt(data[6]));
            return publication = (Publication) book;
        } else {
            AudioBook audioBook = new AudioBook();
            audioBook.setIsbn(data[0]);
            audioBook.setTitle(data[1]);
            audioBook.setAuthor(data[2]);
            audioBook.setYear(Integer.parseInt(data[3]));
            audioBook.setCost(Double.parseDouble(data[4]));
            audioBook.setDuration(Double.parseDouble(data[5]));
            audioBook.setWeight(Double.parseDouble(data[6]));
            audioBook.setFormat(data[7]);
            return publication = (Publication) audioBook;
        }
    }

    @Override
    public List<Publication> readPublications() throws DataAccessException, IOException {
        List<Publication> publicationList = new ArrayList<>();
        try {
            this.reader = new Scanner(this.file);
            while (this.reader.hasNext()) {
                String line = this.reader.nextLine();
                Publication publication = createPublication(line);
                publicationList.add(publication);
            }
            return publicationList;
        } finally {
            if (this.reader != null)
                this.reader.close();
        }
    }

    @Override
    public Publication searchPublication(Publication publication) throws DataAccessException, IOException {
        if (publication == null){
            throw new DataAccessException("You can not search a null publication");
        }
        if (publication.getIsbn() == null){
            throw new DataAccessException("The ISBN of the publication is null");
        }
        Publication publicationS = null;
        try {
            this.reader = new Scanner(this.file);
            if(!this.reader.hasNext()){
                return null;
            }else{
                while (this.reader.hasNext()) {
                    String line = this.reader.nextLine();
                    Publication searching = createPublication(line);
                    if (searching.getIsbn().equalsIgnoreCase(publication.getIsbn())) {
                        publicationS = searching;
                        break;
                    }
                }
            }
            return publicationS;
        } finally {
            if (this.reader != null) {
                this.reader.close();
            }
        }
    }

    private void renameFile(File newFile) throws IOException{
        // if the file don't exists will be created
        if(!newFile.exists())
            newFile.createNewFile();

        //deletes the original file
        if(!this.file.delete()){
            throw new IOException("Can not delete the original file");
        }

        //renames the temporal file
        if(!newFile.renameTo(this.file)){
            throw new IOException("Can not rename the temporal file");
        }
    }

    @Override
    public Publication deletePublication(Publication publication) throws DataAccessException, IOException {
        if (publication == null){
            throw new DataAccessException("You can not delete a null publication");
        }
        if (publication.getIsbn() == null){
            throw new DataAccessException("The ISBN of the publication is null");
        }
        Publication deletePub = null;
        try {
            this.reader = new Scanner(this.file);
            FileText temporalFile = new FileText("temporal.dat");
            while (this.reader.hasNext()){
                String line = this.reader.nextLine();
                Publication pub = createPublication(line);
                if(pub.getIsbn().equalsIgnoreCase(publication.getIsbn())){
                    deletePub = pub;
                }else{
                    temporalFile.addPublication(pub);
                }
            }
            this.reader.close();
            this.renameFile(temporalFile.file);
            return deletePub;
        }finally {
            this.reader.close();
        }
    }
}
