package com.example.kursachclient;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AdminMenuController {
    Stage stage;
    Admin ad;
    @FXML
    protected void Exit()throws Exception{
        User.outStream.writeUTF("VYHOD");
        User.outStream.flush();
        HelloApplication hell=new HelloApplication();
        hell.showWindow(stage);
    }
    @FXML
    protected void Change()throws Exception{
        ad.showWindow(2);
    }
    @FXML
    protected void RedPols()throws Exception{
        ad.showWindow(3);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setAd(Admin ad){
        this.ad=ad;
    }
}
