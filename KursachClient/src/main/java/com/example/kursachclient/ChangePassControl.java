package com.example.kursachclient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ChangePassControl {
    @FXML
    PasswordField OldPass,NewPass,RePass;
    @FXML
    Label Oshibka;
    Stage stage;
    Pole role;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setAd(Pole ad){
        this.role=ad;
    }
    @FXML
    protected void Menu()throws Exception{
        role.showWindow(1);
    }
    @FXML
    protected void Change() throws Exception{
        if(!role.isPass(OldPass.getText()))Oshibka.setText("Старый пароль набран не верно");
        else {
            if(!NewPass.getText().equals(RePass.getText()))Oshibka.setText("Пароль повторён неверно");
            else {
                role.Change(NewPass.getText());
                role.showWindow(1);
            }
        }
    }
}
