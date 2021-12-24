package com.database.diarymanagement;

class SaveDiary {

    private String subject;
    private  String date;
    private String description;
    private String keyWord;

    public SaveDiary() {

    }

    public SaveDiary(String subject, String date, String description) {
        this.subject = subject;
        this.date = date;
        this.description = description;
    }


    public SaveDiary(String subject, String date, String description, String keyWord) {
        this.subject = subject;
        this.date = date;
        this.description = description;
        this.keyWord = keyWord;
    }

    public String getSubject() {
        return subject;
    }


    public SaveDiary(String subject, String date) {
        this.subject = subject;
        this.date = date;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
