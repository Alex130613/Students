package com.example.kursachclient;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class NetGroupController {

    private Stage stage1=new Stage();
    RabGroupController rab;
    @FXML
    protected void Yes()throws IOException {
        rab.AddGroup();
        stage1.close();
    }
    @FXML protected void No(){
        stage1.close();
    }
    public void setRab(RabGroupController rab) {
        this.rab = rab;
    }

    public void setStage1(Stage stage1) {
        this.stage1 = stage1;
    }
}
