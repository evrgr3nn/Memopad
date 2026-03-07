package com.example.memopad;

public class Memo {
    private int id;
    private String text;

    public Memo(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
