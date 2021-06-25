package com.data;

import com.domain.Publication;
import com.exceptions.DataAccessException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListData implements DataAccess, Serializable {

    private List<Publication> publicationList;

    public ListData() {
        this.publicationList = new LinkedList<>();
    }

    @Override
    public void addPublication(Publication publication) throws DataAccessException {
        if(publication == null){
            throw new DataAccessException("You can not add a null publication");
        }
        if(publication.getIsbn() == null){
            throw new DataAccessException("You can not add a publication without ISBN");
        }
        this.publicationList.add(publication);
    }

    @Override
    public List<Publication> readPublications() throws DataAccessException {
        if(this.publicationList.size() == 0){
            throw new DataAccessException("There's no publications registered");
        }
        return this.publicationList;
    }

    @Override
    public Publication searchPublication(Publication publication) throws DataAccessException {
        if(publication == null){
            throw new DataAccessException("You can not search a null publication");
        }
        if(publication.getIsbn() == null){
            throw new DataAccessException("You can not search a publication without ISBN");
        }
        Publication publicationS = null;
        if(this.publicationList.size() != 0) {
            for (Publication value : this.publicationList) {
                if (value.getIsbn().equalsIgnoreCase(publication.getIsbn())) {
                    publicationS = value;
                    break;
                }
            }
        }
        return publicationS;
    }

    @Override
    public List<Publication> searchFilter(String filter) {
        List<Publication> publications = new ArrayList<>();
        for(Publication publication: this.publicationList){
            if(publication.getIsbn().contains(filter) || publication.getAuthor().contains(filter) ||
            publication.getTitle().contains(filter)){
                publications.add(publication);
            }
        }
        return publications;
    }

    @Override
    public Publication deletePublication(Publication publication) throws DataAccessException {
        if(this.publicationList.size() == 0){
            throw new DataAccessException("There's no publications registered");
        }
        if(publication == null){
            throw new DataAccessException("You can not delete a null publication");
        }
        if(publication.getIsbn() == null || publication.getIsbn().isEmpty()){
            throw new DataAccessException("You can not delete a publication without ISBN");
        }
        Publication publicationD = null;

        for (Publication value : this.publicationList) {
            if (value.getIsbn().equalsIgnoreCase(publication.getIsbn())) {
                publicationD = value;
                break;
            }
        }
        if(publicationD!=null)
            this.publicationList.remove(publicationD);
        return publicationD;
    }
}
