package com.example.recyclerview;

public class Query {
    //Query type  , Product name  , Picture  , Price , Desc , Seller name , Seller's contact

    //private int query_type;
    private String query_name;
    private int query_price;
    private String query_desc;
    private String query_creator;
    private String query_contact;
    private String query_image_id;

    public Query(String query_name , int query_price , String query_desc , String query_creator , String query_contact ,String query_image_id) {
        this.query_name = query_name;
        this.setQuery_price(query_price);
        this.query_desc = query_desc;
        this.query_creator = query_creator;
        this.query_contact = query_contact;
        this.query_image_id = query_image_id;
    }

    public  Query()
    {

    }
    public Query(Query query)
    {
        this.query_name = query.getQuery_name();
        this.query_price = query.getQuery_price();
        this.query_desc = query.getQuery_desc();
        this.query_image_id = query.getQuery_image_id();
        this.query_contact = query.getQuery_contact();
        this.query_creator = query.getQuery_contact();
    }

    public String getQuery_image_id() {
        return query_image_id;
    }

    public void setQuery_image_id(String query_image_id) {
        this.query_image_id = query_image_id;
    }



    /*
    public int getQuery_type() {
        return query_type;
    }

    public void setQuery_type(int query_type) {
        this.query_type = query_type;
    }
    */

    public String getQuery_name() {
        return query_name;
    }

    public void setQuery_name(String query_name) {
        this.query_name = query_name;
    }

    public int getQuery_price() {
        return query_price;
    }

    public void setQuery_price(int query_price) {
        this.query_price = query_price;
    }

    public String getQuery_desc() {
        return query_desc;
    }

    public void setQuery_desc(String query_desc) {
        this.query_desc = query_desc;
    }

    public String getQuery_creator() {
        return query_creator;
    }

    public void setQuery_creator(String query_creator) {
        this.query_creator = query_creator;
    }

    public String getQuery_contact() {
        return query_contact;
    }

    public void setQuery_contact(String query_contact) {
        this.query_contact = query_contact;
    }
}
