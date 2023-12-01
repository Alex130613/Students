package com.example.kursachclient;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class BugalterMenuController {
    Stage stage;
    Bugalter bug;
    @FXML
    protected void Exit()throws Exception{
        User.outStream.writeUTF("VYHOD");
        User.outStream.flush();
        HelloApplication hell=new HelloApplication();
        hell.showWindow(stage);
    }
    @FXML
    protected void ChangePass()throws Exception{
        bug.showWindow(2);
    }
    @FXML
    protected void Otchet()throws Exception{
        bug.showWindow(3);
    }
    @FXML protected void Grafic()throws Exception{
        bug.showWindow(4);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setAd(Bugalter bug){
        this.bug=bug;
    }
}
