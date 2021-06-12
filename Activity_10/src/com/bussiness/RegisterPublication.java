package com.bussiness;

import com.data.*;
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
        this.data.addPublication(p);
    }
    public List<Publication> readPublications() throws DataAccessException, IOException {
        return this.data.readPublications();
    }
    public Publication searchPublication(Publication p) throws DataAccessException, IOException{
        return this.data.searchPublication(p);
    }
    public Publication deletePublication(Publication p) throws DataAccessException, IOException{
        return this.data.deletePublication(p);
    }


}
