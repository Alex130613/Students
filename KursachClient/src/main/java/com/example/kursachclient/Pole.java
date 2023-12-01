package com.example.kursachclient;

import javafx.stage.Stage;

public interface Pole {
    public void setStage(Stage stage);
    public void showWindow(int i)throws Exception;
    public boolean isPass(String p);
    public void Change(String p);
}
