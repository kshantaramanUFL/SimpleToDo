package com.ebay.kshantaraman.simpletodo;

/**
 * Created by kshantaraman on 11/15/15.
 */
public class Item {

    private long id;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return description;
    }
}
