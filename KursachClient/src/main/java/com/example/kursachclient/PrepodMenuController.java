package com.example.kursachclient;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PrepodMenuController {
    Stage stage;
    Prepod pr;
    @FXML
    protected void Exit()throws Exception{
        User.outStream.writeUTF("VYHOD");
        User.outStream.flush();
        HelloApplication hell=new HelloApplication();
        hell.showWindow(stage);
    }
    @FXML
    protected void Change()throws Exception{
        pr.showWindow(2);
    }
    @FXML
    protected void RabOcenki()throws Exception{pr.showWindow(3);}
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setPr(Prepod pr){
        this.pr=pr;
    }
}
