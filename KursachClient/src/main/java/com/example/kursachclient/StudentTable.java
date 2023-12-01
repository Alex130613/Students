package com.example.kursachclient;

public class StudentTable {
    String fiostudent;
    int ocenki;
    public StudentTable(String fio,int ocenki){
        this.fiostudent=fio;
        this.ocenki=ocenki;
    }
    public int getOcenki() {
        return ocenki;
    }
    public String getFiostudent() {
        return fiostudent;
    }
    public void setFiostudent(String fiostudent) {
        this.fiostudent = fiostudent;
    }
    public void setOcenki(int ocenki) {
        this.ocenki = ocenki;
    }
}
