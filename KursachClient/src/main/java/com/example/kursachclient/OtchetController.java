package com.example.kursachclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OtchetController {
    Stage stage;
    Bugalter bug;
    private ObservableList<StipTable> StipList= FXCollections.observableArrayList();
    private ObservableList<String> Groups= FXCollections.observableArrayList();
    @FXML private ComboBox<String>Group;
    @FXML private TableView<StipTable> StipView;
    @FXML private TableColumn<StipTable,String> FIOColumn;
    @FXML private TableColumn<StipTable,Double> StipColumn;
    @FXML public void initialize(){
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
        }
        FIOColumn.setCellValueFactory(new PropertyValueFactory<StipTable,String>("fio"));
    StipColumn.setCellValueFactory(new PropertyValueFactory<StipTable,Double>("stip"));
    }
    @FXML protected void Update(){
        StipList.clear();
        try {
            String comand = "SPIS_STIP";
            User.outStream.writeUTF(comand);
            User.outStream.flush();
            User.outStream.writeUTF(Group.getValue());
            User.outStream.flush();
            boolean resh=true;
            int i=1;
            while(resh){
                String fio=User.inStream.readUTF();
                if (fio.equals("NULL")){resh=false;break;}
                String stip=User.inStream.readUTF();
                StipList.add(new StipTable(fio,Double.parseDouble(stip)));
                ++i;
            }
            StipView.setItems(StipList);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    @FXML protected void OtchetFile(){
        String name="D:\\"+Group.getValue()+".txt";
        String fio,stip;
        try(FileWriter writer=new FileWriter(name,false)){
            for(int i=0;i<StipList.size();++i){
            fio=StipList.get(i).getFio();
            stip=Double.toString(StipList.get(i).getStip());
            writer.write(fio+"----------"+stip);
            writer.append(System.lineSeparator());
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML protected void Menu()throws Exception{
        bug.showWindow(1);}

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setBug(Bugalter bug) {
        this.bug = bug;
    }
}
