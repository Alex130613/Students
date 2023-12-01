package com.example.kursachclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OshibkaPodklController {
    @FXML protected void Yes()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Avtoriz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 485, 287);
        HelloController contr=fxmlLoader.getController();
        if(contr.Connection()){
        HelloApplication.stg.setTitle("Вход");
        HelloApplication.stg.setScene(scene);
        HelloApplication.stg.show();}
    }
    @FXML protected void No(){
        HelloApplication.stg.close();
    }
}
