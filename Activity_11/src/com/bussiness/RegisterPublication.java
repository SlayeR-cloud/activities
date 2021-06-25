package com.bussiness;

import com.data.*;
import com.domain.AudioBook;
import com.domain.Book;
import com.domain.Publication;
import com.exceptions.DataAccessException;

import java.io.IOException;
import java.util.List;

public class RegisterPublication {

    private DataAccess data;

    public RegisterPublication() {
        data = new FileObject();
    }
    public void addPublication(Publication p) throws DataAccessException, IOException{
        if (p == null)
            throw new DataAccessException("Publication null");
        if(p.getIsbn() == null || p.getIsbn().isEmpty())
            throw new DataAccessException("Publication doesn't have ISBN number");
        if(p.getAuthor() == null || p.getAuthor().isEmpty())
            throw new DataAccessException("Publication doesn't have an author");
        if(p.getTitle() == null || p.getTitle().isEmpty())
            throw new DataAccessException("Publication doesn't have a title");
        if(p.getYear() <= 0)
            throw new DataAccessException("Publication year is not valid");
        if(p.getCost() <= 0)
            throw new DataAccessException("Publication cost is not valid");
        if(p instanceof AudioBook){
            if( ((AudioBook) p).getDuration() <= 0)
                throw new DataAccessException("Audiobook publication has a not valid duration");

            if( ((AudioBook) p).getWeight() <= 0)
                throw new DataAccessException("Audiobook publication has a not valid weight");

            if( ((AudioBook) p).getFormat() == null || ((AudioBook) p).getFormat().isEmpty())
                throw new DataAccessException("Audiobook publication has a not valid format");

        }else{
            if(((Book) p).getPages() <= 0)
                throw new DataAccessException("Book publication has not a valid number of pages");
            if(((Book) p).getEdition() <= 0)
                throw new DataAccessException("Book publication has not a valid edition");
        }
        if(this.searchPublication(p) != null)
            throw new DataAccessException("Publication previous registered");
        this.data.addPublication(p);
    }

    public List<Publication> readPublications() throws DataAccessException, IOException {
        return this.data.readPublications();
    }
    public Publication searchPublication(Publication p) throws DataAccessException, IOException{
        return this.data.searchPublication(p);
    }
    public List<Publication> searchFilter (String filter) throws DataAccessException, IOException{
        return this.data.searchFilter(filter);
    }
    public Publication deletePublication(Publication p) throws DataAccessException, IOException{
        return this.data.deletePublication(p);
    }

}
