package com.example.licenseebe.repository.bookCategory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class BookCategoryRepositoryImpl implements BookCategoryRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    public List<String> getAllBookCategories() {

        String query = "SELECT bc.name FROM BookCategory bc";
        try {
            return entityManager
                    .createQuery(query, String.class)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>(0);
        }
    }
}
