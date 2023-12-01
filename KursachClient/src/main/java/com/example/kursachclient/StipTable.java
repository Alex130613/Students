package com.example.kursachclient;

public class StipTable {
    String fio;
    Double stip;
    public StipTable(String fio,double stip){
        this.fio=fio;
        this.stip=stip;
    }
    public void setFio(String fio) {
        this.fio = fio;
    }
    public String getFio() {
        return fio;
    }
    public Double getStip() {
        return stip;
    }
    public void setStip(Double stip) {
        this.stip = stip;
    }
}
