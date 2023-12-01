package com.example.kursachclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Prepod implements Pole{
    private Stage stage;
    int width,hight;
    String FIO,pass;
    public static String login;
    String title,res;
    private HelloController cntr;
    Prepod (HelloController cntr,String FIO,String log,String pass){
        this.cntr=cntr;
        this.FIO=FIO;
        this.login=log;
        this.pass=pass;
    }
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(res));
        Scene scene = new Scene(fxmlLoader.load(), width, hight);
        switch(res){
            case"MenuPrepod.fxml":PrepodMenuController cont =fxmlLoader.getController();
                cont.setPr(this);
                cont.setStage(stage);
                break;
            case"ChangePass.fxml":
                ChangePassControl contr1 =fxmlLoader.getController();
                contr1.setAd(this);
                contr1.setStage(stage);
                break;
            case"PabotaOcenki.fxml":
                RabotaOcenkiController contr2 =fxmlLoader.getController();
                contr2.setPr(this);
                contr2.setStage(stage);
                break;
        }
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    public void showWindow(int i)throws Exception{
        switch(i){
            case 1:
                this.hight=307;
                this.width=529;
                this.res="MenuPrepod.fxml";
                this.title="Преподаватель";
                break;
            case 2:
                this.hight=254;
                this.width=215;
                this.res="ChangePass.fxml";
                this.title="Смена пароля";
                break;
            case 3:
                this.hight=326;
                this.width=541;
                this.res="PabotaOcenki.fxml";
                this.title="Работа с оценками";
                break;
        }
        start(stage);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @Override
    public boolean isPass(String p) {
        return pass.equals(p);
    }
    @Override
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