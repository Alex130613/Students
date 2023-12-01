package com.example.kursachclient;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class User {
    public static Socket socket;
    public static DataInputStream inStream;
    public static DataOutputStream outStream;
    public static BufferedReader br;
    private HelloController cntr;
    public User(){}
    public Boolean connect() {
        try {
            socket = new Socket("127.0.0.1", 8888);
            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public Pole vhod(String log,String pass){
        String serverMessage;
        try{
        String comand = "VHOD";
        outStream.writeUTF(comand);
        outStream.flush();
            outStream.writeUTF(log);
            outStream.flush();
            outStream.writeUTF(pass);
            outStream.flush();
            serverMessage=inStream.readUTF();
            if(serverMessage=="0")return null;
            else switch (serverMessage){
                case"Администратор": {
                    serverMessage=inStream.readUTF();
                    return new Admin(cntr,serverMessage,log,pass);}
                case"Преподаватель":{
                    serverMessage=inStream.readUTF();
                    return new Prepod(cntr,serverMessage,log,pass);}
                case"Бухгалтер":{
                    serverMessage=inStream.readUTF();
                    return new Bugalter(cntr,serverMessage,log,pass);}
                case"Деканат":{
                    serverMessage=inStream.readUTF();
                    return new Decanat(cntr,serverMessage,log,pass);}
            }
    } catch(Exception e){
        System.out.println(e);
    }
        return null;
    }
}
