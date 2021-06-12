package com.data;

import com.domain.Publication;
import com.exceptions.DataAccessException;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ListData implements DataAccess, Serializable {

    private List<Publication> publicationList;

    public ListData() {
        this.publicationList = new LinkedList<>();
    }

    @Override
    public void addPublication(Publication publication) throws DataAccessException , IOException{
        if(publication == null){
            throw new DataAccessException("You can not add a null publication");
        }
        if(publication.getIsbn() == null){
            throw new DataAccessException("You can not add a publication without ISBN");
        }
        this.publicationList.add(publication);
    }

    @Override
    public List<Publication> readPublications() throws DataAccessException , IOException{
        if(this.publicationList.size() == 0){
            throw new DataAccessException("There's no publications registered");
        }
        return this.publicationList;
    }

    @Override
    public Publication searchPublication(Publication publication) throws DataAccessException , IOException {
        if(this.publicationList.size() == 0){
            throw new DataAccessException("There's no publications registered");
        }
        if(publication == null){
            throw new DataAccessException("You can not search a null publication");
        }
        if(publication.getIsbn() == null){
            throw new DataAccessException("You can not search a publication without ISBN");
        }
        Publication publicationS = null;
        for (int i = 0; i < this.publicationList.size(); i++){
            publicationS = this.publicationList.get(i);
            if(publicationS.getIsbn().equalsIgnoreCase(publication.getIsbn())){
                break;
            }
        }
        return publicationS;
    }

    @Override
    public Publication deletePublication(Publication publication) throws DataAccessException, IOException {
        if(this.publicationList.size() == 0){
            throw new DataAccessException("There's no publications registered");
        }
        if(publication == null){
            throw new DataAccessException("You can not delete a null publication");
        }
        if(publication.getIsbn() == null){
            throw new DataAccessException("You can not delete a publication without ISBN");
        }
        Publication publicationD = null;

        for (int i = 0; i < this.publicationList.size(); i++){
            publicationD = this.publicationList.get(i);
            if(publicationD.getIsbn().equalsIgnoreCase(publication.getIsbn())){
                break;
            }
        }
        if(publicationD!=null)
            this.publicationList.remove(publicationD);
        return publicationD;
    }
}
