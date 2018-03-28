package com.example.louiemain.pripathology.domain;/**
 * @description
 * @author&date Created by louiemain on 2018/3/28 18:28
 */

import java.io.Serializable;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 题目实体
 * @Author: louiemain
 * @Created: 2018/3/28 18:28
 **/
public class Topic implements Serializable {
    private String number;
    private String name;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String commons;
    private String anser;
    private String analysis;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getCommons() {
        return commons;
    }

    public void setCommons(String commons) {
        this.commons = commons;
    }

    public String getAnser() {
        return anser;
    }

    public void setAnser(String anser) {
        this.anser = anser;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
