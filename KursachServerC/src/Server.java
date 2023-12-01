import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        try{
            ServerSocket server=new ServerSocket(8888);
            System.out.print("Сервер запущен..\n");
            int counter=0;
            while(true){
                counter++;
                Socket serverClient=server.accept();
                System.out.print(counter+"-Клиент подключился\n");// сервер принимает запрос на подключение клиента
                ServerClientThread sct = new ServerClientThread(serverClient,counter); // отправляем запрос в отдельный поток
                sct.start();
            }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
}