package com.example.songthrush;

public class Rooms {
    String name,desc,color;

    public Rooms(String name, String desc, String color) {
        this.name = name;
        this.desc = desc;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getColor() {
        return color;
    }
}
