package com.example.kursachclient;

public class GroupPredmet {
    private int n;
    private String fioPrepod;
    private String pred;
    public GroupPredmet(int n,String fioPrepod,String pred){
        this.n=n;
        this.fioPrepod=fioPrepod;
        this.pred=pred;
    }
    public void setN(int n) {
        this.n = n;
    }
    public int getN() {
        return n;
    }
    public String getFioPrepod() {
        return fioPrepod;
    }
    public void setFioPrepod(String fioPrepod) {
        this.fioPrepod = fioPrepod;
    }
    public String getPred() {
        return pred;
    }
    public void setPred(String pred) {
        this.pred = pred;
    }
}
