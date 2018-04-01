package com.example.louiemain.pripathology.domain;

import com.example.louiemain.pripathology.utils.TimeUtil;

import java.sql.Timestamp;

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
    private int number;
    private String rightAnswer;
    private Timestamp time;
    private String selectAnswer;
    private int target;

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getSelectAnswer() {
        return selectAnswer;
    }

    public void setSelectAnswer(String selectAnswer) {
        this.selectAnswer = selectAnswer;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"number\":" + number +
                ", \"rightAnswer\":\"" + rightAnswer + '\"' +
                ", \"time\":\"" + new TimeUtil().TimeStamp2String(getTime()) + '\"' +
                ", \"selectAnswer\":\"" + selectAnswer + '\"' +
                ", \"target\":" + target +
                '}';
    }
}
