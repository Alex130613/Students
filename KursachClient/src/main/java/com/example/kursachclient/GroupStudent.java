package com.example.kursachclient;

public class GroupStudent {
    private int n;
    private String fio;
    private String formobuch;
    public GroupStudent (int n,String fio, String formobuch){
        this.n=n;
        this.fio=fio;
        this.formobuch=formobuch;
    }
    public int getN() {
        return n;
    }
    public String getFio() {
        return fio;
    }
    public String getFormobuch() {
        return formobuch;
    }
    public void setFio(String fio) {
        this.fio = fio;
    }
    public void setFormobuch(String formobuch) {
        this.formobuch = formobuch;
    }
    public void setN(int n) {
        this.n = n;
    }
}
