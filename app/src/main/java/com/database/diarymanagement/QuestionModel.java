package com.database.diarymanagement;

public class QuestionModel {
    private int id;
    private String ques;
    private String ans;

    public QuestionModel(int id, String ques, String ans) {
        this.id = id;
        this.ques = ques;
        this.ans = ans;
    }

    public QuestionModel(String ques, String ans) {
        this.ques = ques;
        this.ans = ans;
    }

    public QuestionModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
