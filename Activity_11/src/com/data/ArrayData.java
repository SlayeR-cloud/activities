package com.data;

import com.domain.Publication;
import com.exceptions.DataAccessException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArrayData implements DataAccess, Serializable {

    private Publication[] publications;
    private int quantity;


    public ArrayData(int size){
        this.publications = new Publication[size];
    }


    @Override
    public void addPublication(Publication publication) throws DataAccessException, IOException {
        if(this.quantity>=this.publications.length){
            throw new DataAccessException("Array limit exceeded");
        }
        if(publication == null){
            throw new DataAccessException("You can not add a null publication");
        }
        if(publication.getIsbn() == null){
            throw new DataAccessException("You can not add a publication without ISBN");
        }
        this.publications[this.quantity] = publication;
        this.quantity++;
    }

    @Override
    public List<Publication> readPublications() throws DataAccessException, IOException{
        if(this.quantity == 0){
            throw new DataAccessException ("There's no publications registered");
        }
        List<Publication> result = new ArrayList<>();
        for (int i = 0; i < this.quantity; i++){
            result.add(this.publications[i]);
        }
        return result;
    }

    @Override
    public Publication searchPublication(Publication publication) throws DataAccessException, IOException{
        if (publication == null){
            throw new DataAccessException("You can not search a null publication");
        }
        if (publication.getIsbn() == null){
            throw new DataAccessException("The ISBN of the publication is null");
        }
        Publication searchedPublication = null;
        if (this.quantity != 0) {
            for (int i = 0; i < this.quantity; i++) {
                Publication publicationI = this.publications[i];
                if (publicationI.getIsbn().equalsIgnoreCase(publication.getIsbn())) {
                    searchedPublication = publicationI;
                    break;
                }
            }
        }
        return searchedPublication;
    }

    @Override
    public List<Publication> searchFilter(String filter) throws DataAccessException, IOException {
        if(filter == null || filter.isEmpty())
            return this.readPublications();
        else{
            List<Publication> publicationList = new ArrayList<>();
            for(int i = 0; i<this.quantity; i++){
                Publication publication = this.publications[i];
                if(publication.getIsbn().contains(filter) || publication.getAuthor().contains(filter) ||
                        publication.getTitle().contains(filter)){
                    publicationList.add(publication);
                }
            }
            return publicationList;
        }
    }

    @Override
    public Publication deletePublication(Publication publication) throws DataAccessException, IOException{
        if (this.quantity == 0){
            throw new DataAccessException("There's no publications registered");
        }
        if (publication == null){
            throw new DataAccessException("You can not delete a null publication");
        }
        if (publication.getIsbn() == null){
            throw new DataAccessException("The ISBN of the publication is null");
        }
        Publication[] publications = new Publication[this.publications.length];
        int index = 0;
        for(int i = 0; i < this.quantity; i++){
            Publication p = this.publications[i];
            if(p.getIsbn().equalsIgnoreCase(publication.getIsbn())){
                break;
            }else{
                index++;
            }
        }
        Publication deletedPublication = null;
        if(index!=this.quantity){
            for(int i = 0; i < index; i++){
                publications[i] = this.publications[i];
            }
            for(int i = index; i<this.quantity;i++){
                publications[i] = this.publications[i+1];
            }
            this.quantity--;
            deletedPublication = this.publications[index];
            this.publications = publications;
        }
        return deletedPublication;
    }
}
