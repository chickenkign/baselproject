package com.example.baselproject;

public class Data {
    private String username ;
    private String password ;
    private String compassword;

    public Data(String username, String password, String compassword) {
        this.username = username;
        this.password = password;
        this.compassword = compassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompassword() {
        return compassword;
    }

    public void setCompassword(String compassword) {
        this.compassword = compassword;
    }

    @Override
    public String toString() {
        return "Data{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", compassword='" + compassword + '\'' +
                '}';
    }
}
