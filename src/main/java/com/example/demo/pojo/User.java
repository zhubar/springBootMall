package com.example.demo.pojo;

public class User {
    private String password;
    private String name;

    private String receiver;
    private String address;
    private String mobile;
    private int id;

    public int getId() {
        return id;
    }
    public String getReceiver() { return receiver;}

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReceiver(String password) {
        this.receiver = password;
    }
    public String getAddress() {
        return address;
    }

    public void setMobile(String password) {
        this.mobile = password;
    }
    public String getMobile() {
        return mobile;
    }

    public void setAddress(String password) {
        this.address = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnonymousName() {
        if (null == name)
            return null;

        if (name.length() <= 1)
            return "*";

        if (name.length() == 2)
            return name.substring(0, 1) + "*";

        char[] categories = name.toCharArray();
        for (int i = 1; i < categories.length - 1; i++) {
            categories[i] = '*';
        }
        return new String(categories);


    }

}
