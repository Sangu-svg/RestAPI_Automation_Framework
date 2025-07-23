package com.jkTech.configManager;

import com.jkTech.utilities.readData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class pojo {

    private String baseURI;
    private String login_API;
    private String bearer_Token;
    private String id;
    private String username;
    private String password;
    private String name;
    private String author;
    private String published_year;
    private String book_summary;
    private HashMap<String, String> book_details = new HashMap<String, String>();
    private static pojo dataLoad = null;
    private static String token;
    private static String create_Books;
    ArrayList<Integer> bookIds = new ArrayList<>();


    public pojo() {
    }

    public static pojo getInstance() {
        if (dataLoad == null) {
            dataLoad = new pojo();
        }
        return dataLoad;
    }

    public String getBaseURI() throws IOException {
        return this.baseURI = readData.getProperty("base_url");

    }

    public String getUserName() throws IOException {
        return this.username = readData.getProperty("username");
    }

    public String getPassword() throws IOException {
        return this.password = readData.getProperty("password");
    }

    public String getID() throws IOException {
        return readData.getProperty("id");
    }

    public String getLoginAPI() throws IOException {
        return readData.getProperty("login");
    }

    public String getCreateBookAPI() throws IOException {
        return this.create_Books = readData.getProperty("create_Books");
    }

    public String getbearer_Token() throws IOException {
        return readData.getProperty("bearer_token");
    }

    public void setBearerToken(String token) {
        this.token = token;
    }

    public String getBearerToken() {
        return this.token;
    }

    public void store_Created_book_details(HashMap<String, String> bookDetails) {
        book_details.putAll(bookDetails);
    }

    public HashMap get_Created_book_details() {
        return book_details;
    }

    public void store_Created_book_ids(ArrayList bookIds) {
        bookIds.addAll(bookIds);
    }

    public void setBookId(String id) {
        this.id = id;
    }

    public String getGetBookId() {
        return this.id;
    }

    public void setBookName(String name) {
        this.name = name;
    }

    public String getBookname() {
        return this.name;
    }

    public void setBookauthor(String author) {
        this.author = author;
    }

    public String getBookauthor() {
        return this.author;
    }

}



