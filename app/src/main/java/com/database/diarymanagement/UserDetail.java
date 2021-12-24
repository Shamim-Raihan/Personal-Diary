package com.database.diarymanagement;

import android.accessibilityservice.GestureDescription;

class UserDetail {

    private String name;
    private String password;
    private String secret_word;
    private String ans_1;
    private String ans_2;
    private String ans_3;
    private String ques_4;
    private String ans_4;


    public UserDetail() {
    }

    public UserDetail(String name, String password, String secret_word) {
        this.name = name;
        this.password = password;
        this.secret_word = secret_word;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret_word() {
        return secret_word;
    }

    public void setSecret_word(String secret_word) {
        this.secret_word = secret_word;
    }
}
