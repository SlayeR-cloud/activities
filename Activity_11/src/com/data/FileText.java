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
        if(publication == null || publication.getIsbn() == null || publication.getIsbn().isEmpty())
            throw new DataAccessException("Publication has empty or null value");
        PrintWriter printer = null;
        try {
            this.writer = new FileWriter(this.file, true);
            printer = new PrintWriter(this.writer);
            String pubData = publication.getIsbn()+","+publication.getTitle()+","+publication.getAuthor()+","+
                    publication.getCost()+","+publication.getYear()+",";
            if (publication instanceof Book) {
                Book book = (Book) publication;
                pubData = pubData+book.getPages()+","+book.getEdition();
            } else {
                AudioBook audioBook = (AudioBook) publication;
                pubData = pubData+audioBook.getDuration()+","+audioBook.getWeight()+","+audioBook.getFormat();
            }
            printer.println(pubData);
        } finally {
            if (printer != null)
                printer.close();
            this.writer.close();
        }
    }

    public Publication createPublication(String line) {
        String[] data = line.split(",");
        if (data.length == 7) {
            Book book = new Book();
            book.setIsbn(data[0]);
            book.setTitle(data[1]);
            book.setAuthor(data[2]);
            book.setCost(Double.parseDouble(data[3]));
            book.setYear(Integer.parseInt(data[4]));
            book.setPages(Integer.parseInt(data[5]));
            book.setEdition(Integer.parseInt(data[6]));
            return book;
        } else {
            AudioBook audioBook = new AudioBook();
            audioBook.setIsbn(data[0]);
            audioBook.setTitle(data[1]);
            audioBook.setAuthor(data[2]);
            audioBook.setCost(Double.parseDouble(data[3]));
            audioBook.setYear(Integer.parseInt(data[4]));
            audioBook.setDuration(Double.parseDouble(data[5]));
            audioBook.setWeight(Double.parseDouble(data[6]));
            audioBook.setFormat(data[7]);
            return audioBook;
        }
    }

    @Override
    public List<Publication> readPublications() throws IOException, DataAccessException {
        if(this.file.exists()){
            List<Publication> publicationList = new ArrayList<>();
            try {
                this.reader = new Scanner(this.file);
                if (this.reader.hasNext()) {
                    while (this.reader.hasNext()) {
                        String line = this.reader.nextLine();
                        Publication publication = createPublication(line);
                        publicationList.add(publication);
                    }
                    return publicationList;
                }else {
                    throw new DataAccessException("There's no publications registered");
                }
            } finally {
                if (this.reader != null)
                    this.reader.close();
            }
        }else{
            throw new DataAccessException("There's no publications registered");
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
        if (this.file.exists()){
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
        }else {
            return null;
        }
    }

    @Override
    public List<Publication> searchFilter(String filter) throws DataAccessException, IOException {
        if(filter == null || filter.isEmpty())
            return this.readPublications();
        List<Publication> publicationsR = new ArrayList<>();
        try {
            this.reader = new Scanner(this.file);
            if(!this.reader.hasNext()){
                return null;
            }else{
                while (this.reader.hasNext()) {
                    String line = this.reader.nextLine();
                    Publication searching = createPublication(line);
                    if(searching.getIsbn().contains(filter) || searching.getAuthor().contains(filter) ||
                            searching.getTitle().contains(filter)){
                        publicationsR.add(searching);
                    }
                }
            }
            return publicationsR;
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
