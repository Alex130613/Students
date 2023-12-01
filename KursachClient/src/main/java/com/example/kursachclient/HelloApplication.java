package com.example.kursachclient;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stg;
    @Override
    public void start(Stage stage) throws IOException {
        this.stg=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Avtoriz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 485, 287);
        HelloController contr=fxmlLoader.getController();
        if(contr.Connection()){
        stage.setTitle("Вход");
        stage.setScene(scene);
        stage.show();}
    }

    public static void main(String[] args) {
        launch();
    }
    public void showWindow(Stage stage)throws Exception{
        start(stage);
    }
}