package com.data;

import com.domain.Publication;
import com.exceptions.DataAccessException;

import java.io.IOException;
import java.util.List;

public interface DataAccess {

    void addPublication(Publication publication) throws DataAccessException, IOException;
    List<Publication> readPublications () throws DataAccessException, IOException;
    Publication searchPublication(Publication publication) throws DataAccessException, IOException;
    Publication deletePublication(Publication publication) throws DataAccessException, IOException;
}
