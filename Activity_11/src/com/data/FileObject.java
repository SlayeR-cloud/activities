package com.data;

import com.domain.Publication;
import com.exceptions.DataAccessException;

import java.io.*;
import java.util.List;

public class FileObject implements DataAccess{

    private File file;
    private FileInputStream reader;
    private FileOutputStream writer;

    public FileObject(String name) {
        this.file = new File(name);
    }

    public FileObject() {
        this("data.obj");
    }

    public void save(DataAccess dataAccess) throws IOException {
        ObjectOutputStream outputStream = null;
        try {
            this.writer = new FileOutputStream(this.file);
            outputStream = new ObjectOutputStream(this.writer);
            outputStream.writeObject(dataAccess);
        }finally {
            if(outputStream!=null){
                outputStream.close();
            }
            if(this.writer!=null){
                this.writer.close();
            }
        }
    }

    private DataAccess read() throws IOException, DataAccessException {
        DataAccess dataAccess;
        ObjectInputStream objectInputStream = null;
        if(this.file.exists()){
            try{
                this.reader = new FileInputStream(this.file);
                objectInputStream = new ObjectInputStream(this.reader);
                dataAccess = (ArrayData) objectInputStream.readObject();
                return dataAccess;
            } catch (ClassNotFoundException e) {
                throw new DataAccessException("Class don't exists");
            } finally {
                if(objectInputStream!=null){
                    objectInputStream.close();
                }
                if(this.reader!=null){
                    this.reader.close();
                }
            }
        }else {
            dataAccess = new ArrayData(1000);
            save(dataAccess);
            return dataAccess;
        }
    }

    @Override
    public void addPublication(Publication publication) throws DataAccessException, IOException {
        DataAccess dataAccess = this.read();
        dataAccess.addPublication(publication);
        save(dataAccess);
    }

    @Override
    public List<Publication> readPublications() throws DataAccessException, IOException {
        DataAccess dataAccess = this.read();
        return dataAccess.readPublications();
    }

    @Override
    public Publication searchPublication(Publication publication) throws DataAccessException, IOException {
        DataAccess dataAccess = this.read();
        return dataAccess.searchPublication(publication);
    }

    @Override
    public List<Publication> searchFilter(String filter) throws DataAccessException, IOException {
        DataAccess publicationList = this.read();
        return publicationList.searchFilter(filter);
    }

    @Override
    public Publication deletePublication(Publication publication) throws DataAccessException, IOException {
        DataAccess dataAccess = this.read();
        Publication publicationDeleted = dataAccess.deletePublication(publication);
        save(dataAccess);
        return publicationDeleted;
    }
}
