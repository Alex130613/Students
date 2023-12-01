package com.example.kursachclient;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.lang.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Admin implements Pole{
    private Stage stage;
    int width,hight;
    String FIO,login,pass;
    String title,res;
    private HelloController cntr;
    Admin (HelloController cntr,String FIO,String log,String pass){
        this.cntr=cntr;
        this.FIO=FIO;
        this.login=log;
        this.pass=pass;
    }
    public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(res));
            Scene scene = new Scene(fxmlLoader.<Parent>load(), width, hight);
            switch(res){
                case"MenuAdmin.fxml":AdminMenuController cont =fxmlLoader.getController();
            cont.setAd(this);
            cont.setStage(stage);
            break;
                case"RedPols.fxml":
                    RedPolsControl contr =fxmlLoader.getController();
                    contr.setAd(this);
                    contr.setStage(stage);
                    break;
                case"ChangePass.fxml":
                    ChangePassControl contr1 =fxmlLoader.getController();
                    contr1.setAd(this);
                    contr1.setStage(stage);
                    break;
            }
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
    }
    public void showWindow(int i)throws Exception{
        switch(i){
            case 1:
                this.hight=346;
                this.width=600;
                this.res="MenuAdmin.fxml";
                this.title="Администратор";
                break;
            case 2:
                this.hight=254;
                this.width=215;
                this.res="ChangePass.fxml";
                this.title="Смена пароля";
                break;

            case 3:
                this.hight=400;
                this.width=640;
                this.res="RedPols.fxml";
                this.title="Редактирование пользоваетей";
                break;
        }
        start(stage);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public boolean isPass(String p) {
       return pass.equals(p);
    }
    public void Change(String p) {
        String serverMessage;
        pass=p;
        try{
            String comand = "CHANGE_PASS";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(login);
            User.outStream.flush();
            User.outStream.writeUTF(pass);
            User.outStream.flush();
        } catch(Exception e){
            System.out.println(e);
        }
    }
}
