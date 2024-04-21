package edu.ntudp.fit.hnoievets.java_lab.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String query;

    public SearchHistory() {
    }

    public SearchHistory(String query) {
        this.query = query;
    }

    public Integer getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }
}
