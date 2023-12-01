package com.example.kursachclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController{
    //private HelloApplication helloApplication;
    @FXML
    private Label Oshibka;
    @FXML
    private TextField Login;
    @FXML
    private PasswordField Password;
    private Pole obj;
    private User user;

    @FXML
    protected void Vhod()throws Exception {
        obj=user.vhod(Login.getText(),Password.getText());
        if(obj!=null){obj.setStage(HelloApplication.stg);
            obj.showWindow(1);}
        else Oshibka.setText("Неверный логин или пароль");
    }
    public boolean Connection() throws IOException{user=new User();
        if(!user.connect()){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("OshibkaPodkl.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 288, 166);
            HelloApplication.stg.setTitle("Ошибка");
            HelloApplication.stg.setScene(scene);
            HelloApplication.stg.show();
    return false;
        }
        return true;
    }
}