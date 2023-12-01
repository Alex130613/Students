package com.example.kursachclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Bugalter implements Pole {private Stage stage;
    int width,hight;
    String FIO,login,pass;
    String title,res;
    private HelloController cntr;
    Bugalter (HelloController cntr,String FIO,String log,String pass){
        this.cntr=cntr;
        this.FIO=FIO;
        this.login=log;
        this.pass=pass;
    }
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(res));
        Scene scene = new Scene(fxmlLoader.load(), width, hight);
        switch(res){
            case"MenuBugalter.fxml":BugalterMenuController cont =fxmlLoader.getController();
                cont.setAd(this);
                cont.setStage(stage);
                break;
            case"Otchet.fxml":
                OtchetController contr =fxmlLoader.getController();
                contr.setBug(this);
                contr.setStage(stage);
                break;
            case"ChangePass.fxml":
                ChangePassControl contr1 =fxmlLoader.getController();
                contr1.setAd(this);
                contr1.setStage(stage);
                break;
            case"Grafic.fxml":
                GraficController contr2 =fxmlLoader.getController();
                contr2.setBug(this);
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
                this.hight=315;
                this.width=514;
                this.res="MenuBugalter.fxml";
                this.title="Бугалтер";
                break;
            case 2:
                this.hight=254;
                this.width=215;
                this.res="ChangePass.fxml";
                this.title="Смена пароля";
                break;

            case 3:
                this.hight=346;
                this.width=600;
                this.res="Otchet.fxml";
                this.title="Отчёт";
                break;
            case 4:
                this.hight=347;
                this.width=600;
                this.res="Grafic.fxml";
                this.title="График";
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
