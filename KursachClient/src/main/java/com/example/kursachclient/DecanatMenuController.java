package com.example.kursachclient;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class DecanatMenuController {
    Stage stage;
    Decanat dec;
    @FXML
    protected void Exit()throws Exception{
        User.outStream.writeUTF("VYHOD");
        User.outStream.flush();
        HelloApplication hell=new HelloApplication();
        hell.showWindow(stage);
    }
    @FXML
    protected void ChangePass()throws Exception{
        dec.showWindow(2);
    }
    @FXML
    protected void RabGroup()throws Exception{dec.showWindow(3);}
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setPr(Decanat pr){
        this.dec=pr;
    }
}
