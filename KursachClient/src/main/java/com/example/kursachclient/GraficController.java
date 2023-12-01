package com.example.kursachclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GraficController {
    private ObservableList<String> Groups= FXCollections.observableArrayList();
    @FXML private ComboBox<String> Group;
    @FXML private PieChart GrafStip;
    PieChart.Data slice1;
    PieChart.Data slice2;
    PieChart.Data slice3;
    PieChart.Data slice4;
    PieChart.Data slice5;
    private Stage stage;
    private Bugalter bug;
    @FXML public void initialize(){
        Groups.add("Все");
        try {
            String comand = "UPDATE_STIP";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            comand = "SPIS_GROUP";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            boolean resh=true;
            while(resh){
                String group=User.inStream.readUTF();
                if (group.equals("NULL")){resh=false;break;}
                Groups.add(group);
            }
            Group.setItems(Groups);
        }catch(Exception e){
            System.out.println(e);
        }}
    @FXML protected void Menu()throws Exception {
        bug.showWindow(1);
    }
    @FXML protected void Change(){
        GrafStip.getData().clear();
        try {
            String comand = "KOL_STIP";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(Group.getValue());
            User.outStream.flush();
            String kol=User.inStream.readUTF();
            slice1=new PieChart.Data("0",Integer.parseInt(kol));
            kol=User.inStream.readUTF();
            slice2=new PieChart.Data("96,73",Integer.parseInt(kol));
            kol=User.inStream.readUTF();
            slice3=new PieChart.Data("115,64",Integer.parseInt(kol));
            kol=User.inStream.readUTF();
            slice4=new PieChart.Data("134,91",Integer.parseInt(kol));
            kol=User.inStream.readUTF();
            slice5=new PieChart.Data("154,19",Integer.parseInt(kol));
        }catch(Exception e){
            System.out.println(e);
        }
        GrafStip.getData().add(slice1);
        GrafStip.getData().add(slice2);
        GrafStip.getData().add(slice3);
        GrafStip.getData().add(slice4);
        GrafStip.getData().add(slice5);
        GrafStip.setLegendSide(Side.LEFT);
    }
    public void setBug(Bugalter bug) {
        this.bug = bug;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
