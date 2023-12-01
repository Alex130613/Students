package com.example.kursachclient;

public class UserTable {
private String log;
private String FIO;
private String pass;
private String role;
public UserTable(){}
public UserTable(String log,String FIO,String pass,String role){
    this.FIO=FIO;
    this.log=log;
    this.role=role;
    this.pass=pass;
}
    public void setFIO(String FIO) {
        this.FIO = FIO;
    }
    public void setLog(String log) {
        this.log = log;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getFIO() {
        return FIO;
    }
    public String getPass() {
        return pass;
    }
    public String getLog() {
        return log;
    }
    public String getRole() {
        return role;
    }
}
