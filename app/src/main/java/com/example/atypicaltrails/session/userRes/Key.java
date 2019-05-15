package com.example.atypicaltrails.session.userRes;

public class Key {
    private String kind;
    private String id;
    private String name;

    public Key(String kind,String id, String name){
        this.kind= kind;
        this.id=id;
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
