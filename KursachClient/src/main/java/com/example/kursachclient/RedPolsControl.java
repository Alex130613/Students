package com.example.kursachclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RedPolsControl {
    Stage stage;
    Admin ad;
    private ObservableList<UserTable> user= FXCollections.observableArrayList();
    private ObservableList<String> roles= FXCollections.observableArrayList("Администратор","Преподаватель","Бухгалтер","Деканат");
    @FXML private TableView<UserTable> Users;
    @FXML private TableColumn<UserTable,String> LogCol;
    @FXML private TableColumn<UserTable,String> FIOCol;
    @FXML private TableColumn<UserTable,String> PassCol;
    @FXML private TableColumn<UserTable,String> RoleCol;
    @FXML private TextField Login;
    @FXML private TextField FIO;
    @FXML private TextField Password;
    @FXML private ComboBox<String> Role;
    @FXML private Label OshLog;
    @FXML private Label OshPass;
    @FXML private Label OshRole;
    @FXML private Label Osh;
    @FXML private Label Osh1;
    @FXML public void initialize(){
        Role.setItems(roles);
    boolean resh=true;
    try{
        String comand = "USER_TABLE";
        User.outStream.writeUTF(comand);
        User.outStream.flush();
        while(resh){
        String log=User.inStream.readUTF();
        if (log.equals("Null")){resh=false;break;}
        String FIO=User.inStream.readUTF();
        String pass=User.inStream.readUTF();
        String role=User.inStream.readUTF();
        user.add(new UserTable(log,FIO,pass,role));
        }
        LogCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("log"));
        FIOCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("FIO"));
        PassCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("pass"));
        RoleCol.setCellValueFactory(new PropertyValueFactory<UserTable, String>("role"));
        Users.setItems(user);
    } catch(Exception e){
        System.out.println(e);
    }
}
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setAd(Admin ad){
        this.ad=ad;
    }
    @FXML
    protected void Delpols(){
        Osh.setText("");
        Osh1.setText("");
        OshLog.setText("");
        OshPass.setText("");
        OshRole.setText("");
        UserTable use=Users.getSelectionModel().getSelectedItem();
        if (use.getLog().equals(ad.login)){Osh.setText("Невозможно удалить себя");}
        else{
        try{
        String comand = "DEL_POLS";
        User.outStream.writeUTF(comand);
        User.outStream.flush();
            if(use!=null){
                user.remove(use);
                Users.setItems(user);
            User.outStream.writeUTF(use.getLog());
            User.outStream.flush();}
        } catch(Exception e){
        System.out.println(e);
    }}
    }
    @FXML protected void Addpols(){
        Osh.setText("");
        Osh1.setText("");
        OshLog.setText("");
        OshPass.setText("");
        OshRole.setText("");
        if(Login.getText().length()==0)OshLog.setText("Логин является обязательным полем");
        if(Password.getText().length()==0)OshPass.setText("Пароль является обязательным полем");
        if(Role.getValue()==null)OshRole.setText("Poль является обязательным полем");
        if (Login.getText().length()!=0&&Password.getText().length()!=0&&Role.getValue()!=null){
            try {
            String comand = "ADD_POLS";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(Login.getText());
            User.outStream.flush();
            if(FIO.getText().length()==0){
                User.outStream.writeUTF("NULL");
                User.outStream.flush();}
            else{
            User.outStream.writeUTF(FIO.getText());
            User.outStream.flush();}
            User.outStream.writeUTF(Password.getText());
            User.outStream.flush();
            User.outStream.writeUTF(Role.getValue());
            User.outStream.flush();
            if(User.inStream.readUTF().equals("FALSE")){Osh.setText("Пользователь с таким логином");
                Osh1.setText("уже существует");}
            else{
                user.add(new UserTable(Login.getText(),FIO.getText(),Password.getText(),Role.getValue()));
                Users.setItems(user);
                Login.setText("");FIO.setText("");Password.setText("");Role.setValue("");
            }
        }catch(Exception e){
            System.out.println(e);
        }}
    }
    @FXML
    protected void Menu()throws Exception{
        ad.showWindow(1);
    }
}
