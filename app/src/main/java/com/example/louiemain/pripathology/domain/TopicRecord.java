package com.example.louiemain.pripathology.domain;

/**
 * @Program: PriPathology
 * @Type: Class
 * @Description:
 * @Author: louiemain
 * @Created: 2018-03-29 16:10
 **/
public class TopicRecord {
    private int id;
    private String name;
    private String number;
    private String rightAnswer;
    private String time;
    private String selectAnswer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSelectAnswer() {
        return selectAnswer;
    }

    public void setSelectAnswer(String selectAnswer) {
        this.selectAnswer = selectAnswer;
    }
}
