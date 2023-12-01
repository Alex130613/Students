package com.example.kursachclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Collections;

public class RabotaOcenkiController {
    private ObservableList<String> GroupList= FXCollections.observableArrayList();
    private ObservableList<String> PredmetList= FXCollections.observableArrayList();
    private ObservableList<StudentTable> student= FXCollections.observableArrayList();
    private ObservableList<Integer> ocenki= FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10);
    @FXML TableView<StudentTable>StudentView;
    @FXML TableColumn<StudentTable,String>FIOStudent;
    @FXML TableColumn<StudentTable,Integer>OcenkaCol;
    @FXML ComboBox<String>Group;
    @FXML ComboBox<String>Predmet;
    @FXML ComboBox<Integer>Ocenka;
    Stage stage;
    Prepod pr;
    @FXML public void initialize(){
        FIOStudent.setCellValueFactory(new PropertyValueFactory<StudentTable, String>("fiostudent"));
        OcenkaCol.setCellValueFactory(new PropertyValueFactory<StudentTable, Integer>("ocenki"));
        Ocenka.setItems(ocenki);
        boolean resh=true;
        try{
            String comand = "GROUP_LIST";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(Prepod.login);
            User.outStream.flush();
            while(resh){
                String group=User.inStream.readUTF();
                if (group.equals("NULL")){resh=false;break;}
                if(!GroupList.contains(group))GroupList.add(group);
            }
            Group.setItems(GroupList);
        } catch(Exception e){
            System.out.println(e);
        }
    }
    @FXML protected void GroupChange(){
        student.clear();
        PredmetList.clear();
        boolean resh=true;
        try{
            String comand = "PREDMET_LIST";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(Prepod.login);
            User.outStream.flush();
            String group=Group.getValue();
            User.outStream.writeUTF(group);
            User.outStream.flush();
            while(resh){
                String pred=User.inStream.readUTF();
                if (pred.equals("NULL")){resh=false;break;}
                if(!PredmetList.contains(pred))PredmetList.add(pred);
            }
            Predmet.setItems(PredmetList);
        } catch(Exception e){
            System.out.println(e);
        }
    }
    @FXML protected void PredmetChange(){
        student.clear();
        boolean resh=true;
        if(Predmet.getValue()!=null){
            try{
            String comand = "OCENKI_TABLE";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(Prepod.login);
            User.outStream.flush();
            String group=Group.getValue();
            User.outStream.writeUTF(group);
            User.outStream.flush();
            String pred=Predmet.getValue();
            User.outStream.writeUTF(pred);
            User.outStream.flush();
            while(resh){
                String fio=User.inStream.readUTF();
                if (fio.equals("NULL")){resh=false;break;}
                String ocenki=User.inStream.readUTF();
                student.add(new StudentTable(fio,Integer.parseInt(ocenki)));
            }
            StudentView.setItems(student);
        } catch(Exception e){
            System.out.println(e);
        }}
    }
    @FXML protected void Vystav(){
        StudentTable stud=StudentView.getSelectionModel().getSelectedItem();
        stud.setOcenki(Ocenka.getSelectionModel().getSelectedItem());
        student.set(StudentView.getSelectionModel().getSelectedIndex(),stud);
        try{
            String comand = "VYSTAV_OCENKI";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(stud.getFiostudent());
            User.outStream.flush();
            User.outStream.writeUTF(Integer.toString(stud.getOcenki()));
            User.outStream.flush();
            User.outStream.writeUTF(Group.getValue());
            User.outStream.flush();
            User.outStream.writeUTF(Predmet.getValue());
            User.outStream.flush();
        } catch(Exception e){
            System.out.println(e);
        }
    }
    @FXML protected void Exit()throws Exception{
        pr.showWindow(1);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setPr(Prepod pr){
        this.pr=pr;
    }

}
