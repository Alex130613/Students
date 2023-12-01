import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.*;
import java.io.*;
class ServerClientThread extends Thread {
    private DataInputStream inStream;
    private DataOutputStream outStream;
    private Connection connection;
    private Socket serverClient;
    int clientNo;
    private Statement stmt;
    private Statement stmt1;
    private Statement stmt2;
    ServerClientThread(Socket inSocket,int counter){
        serverClient = inSocket;
        clientNo=counter;
    }
    public int getClientNo() {
        return clientNo;
    }
    public Socket getServerClient() {
        return serverClient;
    }

    public void run(){
        try {
            // Название драйвера
            String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

            Class.forName(driverName);

            // Create a connection to the database
            String url =  "jdbc:sqlserver://127.0.0.1:1433;databaseName=Стипендия";
            String username = "admin";
            String password = "12345678";

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Сервер установил соединение с базой данных для клиента\nСоединение: " + connection);
            stmt = connection.createStatement();
            stmt1 = connection.createStatement();
            stmt2 = connection.createStatement();

        } // end try
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Could not find the database driver
        } catch (SQLException e) {
            e.printStackTrace();
            // Could not connect to the database
        }
        try{
            inStream = new DataInputStream(serverClient.getInputStream());
            outStream = new DataOutputStream(serverClient.getOutputStream());
            String clientMessage="", serverMessage="";
               while (!clientMessage.equals("VYHOD")){
                   clientMessage=inStream.readUTF();
                   System.out.println(clientMessage);
                switch(clientMessage){
                    case "VHOD":{
                        String log,pass;
                        log=inStream.readUTF();
                        pass=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * FROM Пользователь where Логин='"+log+"' and Пароль='"+pass+"'");
                       if(!rs.next()){
                            outStream.writeUTF("0");
                        outStream.flush();}
                        else{
                            clientMessage="";
                            clientMessage=rs.getString(4);
                        outStream.writeUTF(clientMessage);
                        outStream.flush();
                        clientMessage="";
                        clientMessage=rs.getString(2);
                        if(clientMessage!=null){
                        outStream.writeUTF(clientMessage);
                        outStream.flush();}
                        else {outStream.writeUTF("Пусто");
                               outStream.flush();
                            clientMessage="";
                        }}
                        break;
                    }
                    case "CHANGE_PASS":{
                        String log=inStream.readUTF();
                        String pass=inStream.readUTF();
                        int rs = stmt.executeUpdate("update Пользователь set Пароль='"+pass+"' where Логин='"+log+"'");
                        break;
                    }
                    case "USER_TABLE":{
                        ResultSet rs = stmt.executeQuery("select * FROM Пользователь");
                        while(rs.next()){
                            for(int i=1;i<=4;++i){
                                clientMessage=rs.getString(i);
                                if(clientMessage==null){outStream.writeUTF("Пусто");
                                   outStream.flush();}
                                else{outStream.writeUTF(clientMessage);
                                outStream.flush();}
                            }
                        }
                        outStream.writeUTF("Null");
                        outStream.flush();
                        clientMessage="";
                        break;
                    }
                    case "DEL_POLS":{
                        String log=inStream.readUTF();
                        ResultSet rs=stmt1.executeQuery("select * FROM Пользователь where Логин='"+log+"'");
                        rs.next();
                        if (rs.getString(4).equals("Преподаватель")){
                            System.out.print(rs.getString(4));
                            rs=stmt1.executeQuery("select * FROM Предмет where Логин='"+log+"'");
                            while(rs.next()) stmt.executeUpdate("delete FROM Оценки where ID_Предмета='"+rs.getString(1)+"'");
                        stmt.executeUpdate("delete FROM Предмет where Логин='"+log+"'");
                        stmt.executeUpdate("delete FROM Пользователь where Логин='"+log+"'");}
                        else stmt.executeUpdate("delete FROM Пользователь where Логин='"+log+"'");
                        break;
                    }
                    case "ADD_POLS":{
                        String log=inStream.readUTF();
                        String FIO=inStream.readUTF();
                        String pass=inStream.readUTF();
                        String role=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * FROM Пользователь where Логин='"+log+"'");
                        if(!rs.next()){
                            outStream.writeUTF("TRUE");
                            outStream.flush();
                        stmt.executeUpdate("insert into Пользователь values('"+log+"','"+FIO+"','"+pass+"','"+role+"')");
                    }
                        else{
                            outStream.writeUTF("FALSE");
                            outStream.flush();}
                        clientMessage="";
                        break;
                    }
                    case "GROUP_LIST":{
                        clientMessage=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * FROM Предмет where Логин='"+clientMessage+"'");
                        while (rs.next()){
                            outStream.writeUTF(rs.getString(3));
                            outStream.flush();
                        }
                        outStream.writeUTF("NULL");
                        outStream.flush();
                        break;
                    }
                    case "PREDMET_LIST":{
                        String log=inStream.readUTF();
                        String group=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * FROM Предмет where Логин='"+log+"'and Группа='"+group+"'");
                        while (rs.next()){
                            outStream.writeUTF(rs.getString(2));
                            outStream.flush();
                        }
                        outStream.writeUTF("NULL");
                        outStream.flush();
                        break;}
                    case "OCENKI_TABLE":{
                        String log=inStream.readUTF();
                        String group=inStream.readUTF();
                        String pred=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * FROM Предмет where Логин='"+log+"'and Группа='"+group+"' and Предмет='"+pred+"'");
                        rs.next();
                        ResultSet rs1 = stmt.executeQuery("select * FROM Оценки where ID_Предмета='"+rs.getString(1)+"'");
                        while(rs1.next()){
                            ResultSet rs2 = stmt1.executeQuery("select * FROM Студент where ID_Студента='"+rs1.getString(2)+"'");
                            rs2.next();
                            outStream.writeUTF(rs2.getString(2));
                            outStream.flush();
                            outStream.writeUTF(Integer.toString(rs1.getInt(4)));
                            outStream.flush();
                        }
                        outStream.writeUTF("NULL");
                        outStream.flush();
                        clientMessage="";
                        break;
                    }
                    case "VYSTAV_OCENKI":{
                        String fio=inStream.readUTF();
                        String ocenk=inStream.readUTF();
                        String group=inStream.readUTF();
                        String pred=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * FROM Предмет where Группа='"+group+"' and Предмет='"+pred+"'");
                        rs.next();
                        ResultSet rs1=stmt1.executeQuery("select * FROM Студент where ФИО='"+fio+"' and Группа='"+group+"'");
                        rs1.next();
                        stmt2.executeUpdate("update Оценки set Оценка="+Integer.parseInt(ocenk)+" where ID_Предмета='"+rs.getString(1)+"' and ID_Студента='"+rs1.getString(1)+"'");
                        rs=stmt.executeQuery("select Оценка from Оценки where ID_Студента='"+rs1.getString(1)+"'");
                        int i=0;
                        double bal=0;
                        boolean stip=true;
                        boolean stip1=true;
                        if(rs1.getObject(5)==null)stip=false;
                        while(rs.next()){
                            ++i;
                            if(rs.getInt("Оценка")<4)stip1=false;
                            bal+= rs.getInt("Оценка");
                        }
                        if(stip) {
                            int id=0;
                            double srbal=bal/i;
                            if(stip1){if(srbal<5)id=0;
                            if(srbal>=5&&srbal<6)id=5;
                            if(srbal>=6&&srbal<8)id=6;
                            if(srbal>=8&&srbal<9)id=8;
                            if(srbal>=9&&srbal<=10)id=9;}
                            ResultSet rs2= stmt.executeQuery("select * from Стипендия where ID_Стипендии="+id);
                            rs2.next();
                            stmt2.executeUpdate("update Студент set СреднийБал=" + bal / i + ", ID_Стипендии="+id+", РазмерСтипендии="+rs2.getDouble(3)+" where ID_Студента='" + rs1.getString(1) + "'");
                        }
                        stmt2.executeUpdate("update Студент set СреднийБал=" + bal / i +" where ID_Студента='" + rs1.getString(1) + "'");
                        break;}
                    case "SPIS_GROUP":{
                        ResultSet rs = stmt.executeQuery("select * FROM ВсеГруппы");
                        while (rs.next()){
                            outStream.writeUTF(rs.getString(1));
                            outStream.flush();
                        }
                        outStream.writeUTF("NULL");
                        outStream.flush();
                        break;
                    }
                    case "SPIS_PREPODOV":{
                        ResultSet rs = stmt.executeQuery("select * FROM Пользователь where Роль='Преподаватель'");
                        while (rs.next()){
                            outStream.writeUTF(rs.getString(2));
                            outStream.flush();
                        }
                        outStream.writeUTF("NULL");
                        outStream.flush();
                        break;}
                    case "SPIS_STUDENT":{
                        String group=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * FROM Студент where Группа='"+group+"'");
                        while (rs.next()){
                            outStream.writeUTF(rs.getString(2));
                            outStream.flush();
                            if (rs.getObject(5)==null){
                                outStream.writeUTF("Платное");
                                outStream.flush();}
                            else {outStream.writeUTF("Бюджет");
                                outStream.flush();}
                        }
                        outStream.writeUTF("NULL");
                        outStream.flush();
                        break;
                    }
                    case "SPIS_PREDMETOV":{String group=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * FROM Предмет where Группа='"+group+"'");
                        while (rs.next()){
                            ResultSet rs1 = stmt1.executeQuery("select * FROM Пользователь where Логин='"+rs.getString(4)+"'");
                            rs1.next();
                            outStream.writeUTF(rs1.getString(2));
                            outStream.flush();
                            outStream.writeUTF(rs.getString(2));
                            outStream.flush();
                        }
                        outStream.writeUTF("NULL");
                        outStream.flush();
                        break;
                    }
                    case "ADD_GROUP":{
                        String group=inStream.readUTF();
                        stmt.executeUpdate("insert into ВсеГруппы values('"+group+"')");
                                break;
                    }
                    case "ADD_STUDENT":{
                        String group=inStream.readUTF();
                        String fio=inStream.readUTF();
                        String form=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * from Студент where ФИО='" + fio+ "'");
                        if(rs.next()){outStream.writeUTF("FALSE");
                            outStream.flush();}
                        else {
                            outStream.writeUTF("TRUE");
                            outStream.flush();
                        int i=0;
                       rs=null;
                        do {
                            ++i;
                            rs = stmt.executeQuery("select * from Студент where ID_Студента='" + group +Integer.toString(i)+ "'");
                        }while (rs.next());
                        if(form.equals("Платное")) stmt.executeUpdate("insert into Студент(ID_Студента, ФИО, Группа) values('"+ group +Integer.toString(i)+"','"+fio+"','"+group+"')");
                    else stmt.executeUpdate("insert into Студент(ID_Студента, ФИО, Группа,ID_Стипендии) values('"+ group +Integer.toString(i)+"','"+fio+"','"+group+"',0)");
                    rs=stmt.executeQuery("select * from Предмет where Группа='" + group+ "'");
                    int j=0;
                    ResultSet rs1=null;
                    while (rs.next()){
                        do {
                            ++j;
                            rs1 = stmt1.executeQuery("select * from Оценки where ID_Оценки='" + group +Integer.toString(j)+ "'");
                        }while (rs1.next());
                        stmt2.executeUpdate("insert into Оценки(ID_Оценки, ID_Студента, ID_Предмета,Оценка) values('"+ group +Integer.toString(j)+"','"+group +Integer.toString(i)+"','"+rs.getString(1)+"',0)");
                    }}
                    break;
                    }
                    case"ADD_PREDMET":{
                        String group=inStream.readUTF();
                        String fio=inStream.readUTF();
                        String pred=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * from Предмет where Предмет='" + pred+ "'and Группа='"+group+"'");
                        if(rs.next()){outStream.writeUTF("FALSE");
                            outStream.flush();}
                        else {
                            outStream.writeUTF("TRUE");
                            outStream.flush();
                            int i=0;
                            rs=null;
                        do {
                            ++i;
                            rs = stmt.executeQuery("select * from Предмет where ID_Предмета='" + group +Integer.toString(i)+ "'");
                        }while (rs.next());
                        rs=stmt.executeQuery("select * from Пользователь where ФИО='" + fio+ "'");
                        rs.next();
                        stmt1.executeUpdate("insert into Предмет(ID_Предмета, Предмет, Группа,Логин) values('"+ group +Integer.toString(i)+"','"+pred+"','"+group+"','"+rs.getString(1)+"')");
                        rs=stmt.executeQuery("select * from Студент where Группа='" + group+ "'");
                        int j=0;
                        ResultSet rs1=null;
                        while (rs.next()){
                            do {
                                ++j;
                                rs1 = stmt1.executeQuery("select * from Оценки where ID_Оценки='" + group +Integer.toString(j)+ "'");
                            }while (rs1.next());
                            stmt2.executeUpdate("insert into Оценки(ID_Оценки, ID_Студента, ID_Предмета,Оценка) values('"+ group +Integer.toString(j)+"','"+rs.getString(1)+"','"+group +Integer.toString(i)+"',0)");
                        }}
                        break;
                    }
                    case"DEL_STUDENT":{
                        String group=inStream.readUTF();
                        String fio=inStream.readUTF();
                        ResultSet rs=stmt.executeQuery("select * from Студент where ФИО='"+fio+"'and Группа='"+group+"'");
                        rs.next();
                        String id=rs.getString(1);
                        stmt.executeUpdate("delete FROM Оценки where ID_Студента='"+id+"'");
                        stmt.executeUpdate("delete FROM Студент where ID_Студента='"+id+"'");
                        break;
                    }
                    case "DEL_PREDMET":{
                        String group=inStream.readUTF();
                        String pred=inStream.readUTF();
                        ResultSet rs=stmt.executeQuery("select * from Предмет where Предмет='"+pred+"'and Группа='"+group+"'");
                        rs.next();
                        String id=rs.getString(1);
                        stmt.executeUpdate("delete FROM Оценки where ID_Предмета='"+id+"'");
                        stmt.executeUpdate("delete FROM Предмет where ID_Предмета='"+id+"'");
                        break;}
                    case "RED_PREDMET":{
                        String group=inStream.readUTF();
                        String pred1=inStream.readUTF();
                        String fio2=inStream.readUTF();
                        String pred2=inStream.readUTF();
                        boolean resh=true;
                        if(!pred1.equals(pred2)){
                        ResultSet rs = stmt.executeQuery("select * from Предмет where Предмет='" + pred2+ "'and Группа='"+group+"'");
                        if(rs.next()){outStream.writeUTF("FALSE");
                            outStream.flush();
                        resh=false;}}
                        if(resh) {
                            outStream.writeUTF("TRUE");
                            outStream.flush();
                            ResultSet rs = stmt.executeQuery("select * from Предмет where Предмет='" + pred1+ "'and Группа='"+group+"'");
                            rs.next();
                            ResultSet rs1=stmt2.executeQuery("select * from Пользователь where ФИО='" + fio2+ "'");
                            rs1.next();
                            stmt1.executeUpdate("update Предмет set Предмет='"+pred2+"', Логин='"+rs1.getString(1)+"' where ID_Предмета='" +rs.getString(1)+ "'");
                        }
                        break;
                    }
                    case"RED_STUDENT":{
                        String group=inStream.readUTF();
                        String fio1=inStream.readUTF();
                        String fio2=inStream.readUTF();
                        String form=inStream.readUTF();
                        boolean resh=true;
                        if(!fio1.equals(fio2)){ResultSet rs = stmt.executeQuery("select * from Студент where ФИО='" + fio2+ "'");
                        if(rs.next()){outStream.writeUTF("FALSE");
                            outStream.flush();
                        resh=false;}}
                        if(resh){
                            outStream.writeUTF("TRUE");
                            outStream.flush();
                            int i=0;
                            ResultSet rs=stmt1.executeQuery("select * from Студент where Группа='" + group+ "' and ФИО='"+fio1+"'");
                            rs.next();
                            if(form.equals("Платное")) stmt.executeUpdate("update Студент set ФИО='"+fio2+"',ID_Стипендии=NULL, РазмерСтипндии=NULL where ID_Студента='"+rs.getString(1)+"'");
                            else {
                                stmt.executeUpdate("update Студент set ФИО='" + fio2 + "',ID_Стипендии=0 where ID_Студента='" + rs.getString(1) + "'");
                            }}
                        break;
                    }
                    case"SPIS_STIP":{
                        String group=inStream.readUTF();
                        ResultSet rs = stmt.executeQuery("select * FROM Студент where Группа='"+group+"'");
                        while (rs.next()){
                            outStream.writeUTF(rs.getString(2));
                            outStream.flush();
                            if(rs.getObject(6)==null){
                                outStream.writeUTF("0");
                            outStream.flush();}
                            else{
                            outStream.writeUTF(Double.toString(rs.getDouble(6)));
                            outStream.flush();}
                        }
                        outStream.writeUTF("NULL");
                        outStream.flush();
                        break;
                    }
                    case"UPDATE_STIP":{
                        ResultSet rs = stmt.executeQuery("select * FROM Студент");
                        while (rs.next()){
                            ResultSet rs1=stmt1.executeQuery("select Оценка from Оценки where ID_Студента='"+rs.getString(1)+"'");
                            int i=0;
                            double bal=0;
                            boolean stip=true;
                            boolean stip1=true;
                            if(rs.getObject(5)==null)stip=false;
                            while(rs1.next()){
                                ++i;
                                if(rs1.getInt("Оценка")<4)stip1=false;
                                bal+= rs1.getInt("Оценка");
                            }
                            if(stip) {
                                int id=0;
                                double srbal=bal/i;
                                if(stip1){if(srbal<5)id=0;
                                    if(srbal>=5&&srbal<6)id=5;
                                    if(srbal>=6&&srbal<8)id=6;
                                    if(srbal>=8&&srbal<9)id=8;
                                    if(srbal>=9&&srbal<=10)id=9;}
                                ResultSet rs2= stmt1.executeQuery("select * from Стипендия where ID_Стипендии="+id);
                                rs2.next();
                                stmt2.executeUpdate("update Студент set ID_Стипендии="+id+", РазмерСтипендии="+rs2.getDouble(3)+" where ID_Студента='" + rs.getString(1) + "'");
                            }
                        }
                        break;
                    }
                    case"KOL_STIP":{
                        String group=inStream.readUTF();
                        if(group.equals("Все")){
                            ResultSet rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=0");
                            int i=0;
                            while(rs.next())++i;
                            rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=NULL");
                            while(rs.next())++i;
                            outStream.writeUTF(Integer.toString(i));
                            outStream.flush();
                            rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=5");
                            i=0;
                            while(rs.next())++i;
                            outStream.writeUTF(Integer.toString(i));
                            outStream.flush();rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=6");
                            i=0;
                            while(rs.next())++i;
                            outStream.writeUTF(Integer.toString(i));
                            outStream.flush();rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=8");
                            i=0;
                            while(rs.next())++i;
                            outStream.writeUTF(Integer.toString(i));
                            outStream.flush();rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=9");
                            i=0;
                            while(rs.next())++i;
                            outStream.writeUTF(Integer.toString(i));
                            outStream.flush();
                        }
                        else{
                            ResultSet rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=0 and Группа='"+group+"'");
                            int i=0;
                            while(rs.next())++i;
                            rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=NULL and Группа='"+group+"'");
                            while(rs.next())++i;
                            outStream.writeUTF(Integer.toString(i));
                            outStream.flush();
                            rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=5 and Группа='"+group+"'");
                            i=0;
                            while(rs.next())++i;
                            outStream.writeUTF(Integer.toString(i));
                            outStream.flush();rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=6 and Группа='"+group+"'");
                            i=0;
                            while(rs.next())++i;
                            outStream.writeUTF(Integer.toString(i));
                            outStream.flush();rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=8 and Группа='"+group+"'");
                            i=0;
                            while(rs.next())++i;
                            outStream.writeUTF(Integer.toString(i));
                            outStream.flush();rs = stmt.executeQuery("select * FROM Студент where ID_Стипендии=9 and Группа='"+group+"'");
                            i=0;
                            while(rs.next())++i;
                            outStream.writeUTF(Integer.toString(i));
                            outStream.flush();}
                        break;
                    }
                }
               }
            inStream.close();
            outStream.close();
            serverClient.close();
            connection.close();
        }catch(Exception ex){
            System.out.println(ex);
        }finally{
            System.out.println("Client -" + clientNo + " exit!! ");
        }
    }
}