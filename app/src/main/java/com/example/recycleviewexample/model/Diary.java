package com.example.recycleviewexample.model;

public class Diary {
    private String title;
    private String content;
    private String username;

    public Diary(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
