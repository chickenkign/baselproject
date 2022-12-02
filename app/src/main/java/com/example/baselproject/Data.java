package com.example.baselproject;

import android.widget.EditText;

public class Data {
    private EditText email ;
    private EditText password ;
    private EditText username ;
    private EditText phone ;

    public Data(EditText email, EditText password, EditText username, EditText phone) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
    }

    public EditText getEmail() {
        return email;
    }

    public void setEmail(EditText email) {
        this.email = email;
    }

    public EditText getPassword() {
        return password;
    }

    public void setPassword(EditText password) {
        this.password = password;
    }

    public EditText getUsername() {
        return username;
    }

    public void setUsername(EditText username) {
        this.username = username;
    }

    public EditText getPhone() {
        return phone;
    }

    public void setPhone(EditText phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Data{" +
                "email=" + email +
                ", password=" + password +
                ", username=" + username +
                ", phone=" + phone +
                '}';
    }
}
