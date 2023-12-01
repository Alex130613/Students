package com.example.kursachclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class RabGroupController {
    private ObservableList<GroupStudent> StudentTable= FXCollections.observableArrayList();
    private ObservableList<GroupPredmet> PredmetTable= FXCollections.observableArrayList();
    private ObservableList<String> Groups= FXCollections.observableArrayList();
    private ObservableList<String> Prepods= FXCollections.observableArrayList();
    @FXML private TableView <GroupStudent> StudentView1;
    @FXML private TableColumn <GroupStudent, Integer>NColumn1;
    @FXML private TableColumn <GroupStudent, String>FIOColumn;
    @FXML private TableColumn <GroupStudent, String>FormaObuchColumn;
    @FXML private TextField Familia1;
    @FXML private TextField Name1;
    @FXML private TextField Otchestvo1;
    @FXML private ToggleGroup FormaObuch=new ToggleGroup();
    @FXML private ComboBox<String> Group1;
    @FXML private TableView <GroupPredmet>PredmetView2;
    @FXML private TableColumn <GroupPredmet, Integer>NColumn2;
    @FXML private TableColumn <GroupPredmet, String>PredmetColumn2;
    @FXML private TableColumn <GroupPredmet, String>FIOColumn2;
    @FXML private ComboBox <String> FIO2;
    @FXML private TextField Pred2;
    @FXML private ComboBox <String>Group2;
    @FXML private RadioButton Platnoe;
    @FXML private RadioButton Bydzhet;
    @FXML private Button RedButton1;
    @FXML private Button RedButton2;
    private Stage stage1=new Stage();
    private Decanat dec;
    int i=0;
    @FXML public void initialize(){
        try {
            String comand = "SPIS_GROUP";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            boolean resh=true;
            while(resh){
                String group=User.inStream.readUTF();
                if (group.equals("NULL")){resh=false;break;}
                Groups.add(group);
            }
            Group1.setItems(Groups);
            Group2.setItems(Groups);
            comand = "SPIS_PREPODOV";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            resh=true;
            while(resh){
                String prepod=User.inStream.readUTF();
                if (prepod.equals("NULL")){resh=false;break;}
                Prepods.add(prepod);
            }
            FIO2.setItems(Prepods);
        }catch(Exception e){
            System.out.println(e);
        }
        NColumn1.setCellValueFactory(new PropertyValueFactory<GroupStudent, Integer>("n"));
        FIOColumn.setCellValueFactory(new PropertyValueFactory<GroupStudent, String>("fio"));
        FormaObuchColumn.setCellValueFactory(new PropertyValueFactory<GroupStudent, String>("formobuch"));
        NColumn2.setCellValueFactory(new PropertyValueFactory<GroupPredmet, Integer>("n"));
        FIOColumn2.setCellValueFactory(new PropertyValueFactory<GroupPredmet, String>("fioPrepod"));
        PredmetColumn2.setCellValueFactory(new PropertyValueFactory<GroupPredmet, String>("pred"));
        Platnoe.setToggleGroup(FormaObuch);
        Bydzhet.setToggleGroup(FormaObuch);
    }
    public void setDec(Decanat dec) {
        this.dec = dec;
    }
    @FXML public void Add1()throws IOException {
        i=1;
        String group=Group1.getValue();
        String fio=Familia1.getText()+" "+Name1.getText()+" "+Otchestvo1.getText();
        Toggle form1=FormaObuch.getSelectedToggle();
        String form;
        if(form1==Platnoe)form="Платное";
        else form="Бюджет";
        if(!Groups.contains(group)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NetGroup.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 251, 139);
            NetGroupController net=fxmlLoader.getController();
            net.setRab(this);
            net.setStage1(stage1);
                stage1.setTitle("Нет группы");
                stage1.setScene(scene);
                stage1.show();}
        else {
            try {
                String comand = "ADD_STUDENT";
                User.outStream.writeUTF(comand);
                User.outStream.flush();
                User.outStream.writeUTF(group);
                User.outStream.flush();
                User.outStream.writeUTF(fio);
                User.outStream.flush();
                User.outStream.writeUTF(form);
                User.outStream.flush();
                String nal=User.inStream.readUTF();
                if(nal.equals("TRUE")){
                    StudentTable.add(new GroupStudent(StudentTable.size()+1,fio,form));
                    StudentView1.setItems(StudentTable);}
            }catch(Exception e){
                System.out.println(e);
            }
            Familia1.setText("");
            Name1.setText("");
            Otchestvo1.setText("");
            FormaObuch.selectToggle(null);
        }
    }
    @FXML protected void Del1(){
        GroupStudent stud=StudentView1.getSelectionModel().getSelectedItem();
        try {
            String comand = "DEL_STUDENT";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(Group1.getValue());
            User.outStream.flush();
            User.outStream.writeUTF(stud.getFio());
            User.outStream.flush();
        }catch(Exception e){
            System.out.println(e);
        }
        StudentTable.remove(stud);
        int n=StudentTable.size();
        for(int i=0;i<n;++i){
            stud=StudentTable.get(i);
            stud.setN(i+1);
            StudentTable.set(i,stud);
        }
        StudentView1.setItems(StudentTable);
    }
    @FXML protected void Red1(){  GroupStudent stud=StudentView1.getSelectionModel().getSelectedItem();
        if (RedButton1.getText().equals("Редактировать")){
            RedButton1.setText("Сохранить");
            String FIO,fio;
            fio="";
            FIO=stud.getFio();
            int n=FIO.length();
            int i=0;
            for(int j=0;j<n;++j){
                if(FIO.charAt(j)==' '){
                    if(i==0){Familia1.setText(fio);
                        i=1;
                        fio="";}
                    else{Name1.setText(fio);
                        i=2;
                        fio="";}
                }
                else{
                    fio+=FIO.charAt(j);
                }
            }
            if(i==1)Name1.setText(fio);
            if (i==2)Otchestvo1.setText(fio);
            if(stud.getFormobuch().equals("Платное"))FormaObuch.selectToggle(Platnoe);
            else FormaObuch.selectToggle(Bydzhet);
        }
        else{
            String group=Group1.getValue();
            String fio=Familia1.getText()+" "+Name1.getText()+" "+Otchestvo1.getText();
            Toggle form1=FormaObuch.getSelectedToggle();
            String form;
            if(form1==Platnoe)form="Платное";
            else form="Бюджет";
                try {
                    String comand = "RED_STUDENT";
                    User.outStream.writeUTF(comand);
                    User.outStream.flush();
                    User.outStream.writeUTF(group);
                    User.outStream.flush();
                    User.outStream.writeUTF(stud.getFio());
                    User.outStream.flush();
                    User.outStream.writeUTF(fio);
                    User.outStream.flush();
                    User.outStream.writeUTF(form);
                    User.outStream.flush();
                    String nal=User.inStream.readUTF();
                    int i=StudentTable.indexOf(stud);
                    stud.setFio(fio);
                    stud.setFormobuch(form);
                    if(nal.equals("TRUE")){
                        StudentTable.set(i,stud);
                        StudentView1.setItems(StudentTable);
                        RedButton1.setText("Редактировать");
                        Familia1.setText("");
                        Name1.setText("");
                        Otchestvo1.setText("");
                        FormaObuch.selectToggle(null);
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
        }   }
    @FXML protected void Update1(){
        if (RedButton1.getText().equals("Сохранить")){
            RedButton1.setText("Редактировать");
            Familia1.setText("");
            Name1.setText("");
            Otchestvo1.setText("");
            FormaObuch.selectToggle(null);}
        StudentTable.clear();
        try {
            String comand = "SPIS_STUDENT";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(Group1.getValue());
            User.outStream.flush();
            boolean resh=true;
            int i=1;
            while(resh){
                String fio=User.inStream.readUTF();
                if (fio.equals("NULL")){resh=false;break;}
                String form=User.inStream.readUTF();
                StudentTable.add(new GroupStudent(i,fio,form));
                ++i;
            }
            StudentView1.setItems(StudentTable);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    @FXML protected void Add2()throws IOException{
        i=2;
        String group=Group2.getValue();
        String fio=FIO2.getValue();
        String pred=Pred2.getText();
        if(!Groups.contains(group)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NetGroup.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 251, 139);
            NetGroupController net=fxmlLoader.getController();
            net.setRab(this);
            net.setStage1(stage1);
            stage1.setTitle("Нет группы");
            stage1.setScene(scene);
            stage1.show();}
        else {
            try {
                String comand = "ADD_PREDMET";
                User.outStream.writeUTF(comand);
                User.outStream.flush();
                User.outStream.writeUTF(group);
                User.outStream.flush();
                User.outStream.writeUTF(fio);
                User.outStream.flush();
                User.outStream.writeUTF(pred);
                User.outStream.flush();
                String nal=User.inStream.readUTF();
                if(nal.equals("TRUE")){
                    PredmetTable.add(new GroupPredmet(PredmetTable.size()+1,fio,pred));
                    PredmetView2.setItems(PredmetTable);
                    FIO2.setValue("");
                    Pred2.setText("");}
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    @FXML protected void Del2(){
        GroupPredmet pred=PredmetView2.getSelectionModel().getSelectedItem();
        try {
            String comand = "DEL_PREDMET";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(Group2.getValue());
            User.outStream.flush();
            User.outStream.writeUTF(pred.getPred());
            User.outStream.flush();
        }catch(Exception e){
            System.out.println(e);
        }
        PredmetTable.remove(pred);
        int n=PredmetTable.size();
        for(int i=0;i<n;++i){
            GroupPredmet pred1=PredmetTable.get(i);
            pred1.setN(i+1);
            PredmetTable.set(i,pred1);
        }
        PredmetView2.setItems(PredmetTable);}
    @FXML protected void Red2(){
        GroupPredmet pred=PredmetView2.getSelectionModel().getSelectedItem();
        if (RedButton2.getText().equals("Редактировать")){
            RedButton2.setText("Сохранить");
            FIO2.setValue(pred.getFioPrepod());
            Pred2.setText(pred.getPred());
        }
        else{
            String group=Group2.getValue();
            String fio=FIO2.getValue();
            String predm=Pred2.getText();
            try {
                String comand = "RED_PREDMET";
                User.outStream.writeUTF(comand);
                User.outStream.flush();
                User.outStream.writeUTF(group);
                User.outStream.flush();
                User.outStream.writeUTF(pred.getPred());
                User.outStream.flush();
                User.outStream.writeUTF(fio);
                User.outStream.flush();
                User.outStream.writeUTF(predm);
                User.outStream.flush();
                String nal=User.inStream.readUTF();
                int i=PredmetTable.indexOf(pred);
                if(nal.equals("TRUE")){
                    pred.setFioPrepod(fio);
                    pred.setPred(predm);
                    PredmetTable.set(i,pred);
                    PredmetView2.setItems(PredmetTable);
                    RedButton2.setText("Редактировать");
                    FIO2.setValue("");
                    Pred2.setText("");}
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    @FXML protected void Update2(){
        PredmetTable.clear();
        try {
            String comand = "SPIS_PREDMETOV";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(Group2.getValue());
            User.outStream.flush();
            boolean resh=true;
            int i=1;
            while(resh){
                String fio=User.inStream.readUTF();
                if (fio.equals("NULL")){resh=false;break;}
                String pred=User.inStream.readUTF();
                PredmetTable.add(new GroupPredmet(i,fio,pred));
                ++i;
            }
            if (RedButton2.getText().equals("Сохранить")){
                RedButton2.setText("Редактировать");
                FIO2.setValue("");
                Pred2.setText("");}
            PredmetView2.setItems(PredmetTable);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    @FXML protected void Update22(){
        if (RedButton2.getText().equals("Сохранить")){
            RedButton2.setText("Редактировать");
            FIO2.setValue("");
            Pred2.setText("");}
    }
    @FXML protected void Update12(){
        if (RedButton1.getText().equals("Сохранить")){
        RedButton1.setText("Редактировать");
        Familia1.setText("");
        Name1.setText("");
        Otchestvo1.setText("");
            FormaObuch.selectToggle(null);}}
    @FXML protected void Menu()throws Exception{
        dec.showWindow(1);}
    public void AddGroup() throws IOException{
        String group=null;
        if(i==1)group=Group1.getValue();
        else group=Group2.getValue();
        Groups.add(group);
        Group1.setItems(Groups);
        Group2.setItems(Groups);
        if(i==1)Group1.setValue(group);
        else Group2.setValue(group);
        try {
            String comand = "ADD_GROUP";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(group);
            User.outStream.flush();

        }catch(Exception e){
            System.out.println(e);}
        if(i==1)Add1();
        else Add2();
    }
}
