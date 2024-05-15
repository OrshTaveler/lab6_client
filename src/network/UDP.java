package network;

import commands.Authorize;
import commands.Command;
import commands.Register;
import org.json.simple.JSONObject;
import utilities.Asker;
import utilities.Serialization;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;

import static utilities.Serialization.SerializeObject;


public class UDP {
    private int port;
    DatagramSocket socket;

    public static  String LOGIN;
    public static  String PASSWORD;
    public static  long TIMESTAMP;
    public static String TOKEN = " ";
    private static final int MAX_CONNECTION_RETRY = 5;
    InetAddress address;
    Asker asker;
    int size = 60000;
    public UDP(int port,String host,Asker asker) throws SocketException, UnknownHostException {
        this.port = port;
        socket = new DatagramSocket();
        socket.setSoTimeout(10000);
        address = InetAddress.getByName(host);
        this.asker = asker;
    }

    private void sendPacket(byte[] message) throws IOException {
        DatagramPacket packet = new DatagramPacket(message,message.length, address, port);
        socket.send(packet);
    }
    public void sendJSONPacket(Object command,String atribute ,JSONObject additionalData,boolean initialConnect) throws IOException {
        JSONObject jsonPacket = new JSONObject();
        additionalData.put("login",LOGIN);
        additionalData.put("password",PASSWORD);
        additionalData.put("timestamp",TIMESTAMP);
        additionalData.put("token",TOKEN);
        jsonPacket.put("additionalData",additionalData);
        jsonPacket.put("atribute",atribute);
        jsonPacket.put("command",command);
        jsonPacket.put("initialConnect",initialConnect);

        this.sendPacket(SerializeObject(jsonPacket));
    }

    public DatagramPacket recivePacket() throws IOException,SocketTimeoutException  {

            byte[] buf = new  byte[size];
            DatagramPacket packet = new DatagramPacket(buf,buf.length, address, port);
            socket.receive(packet);
            return packet;


    }
    public JSONObject initialConnect(int count,UDP udp) throws IOException, ClassNotFoundException {
        try{

         System.out.println("Подключаемся к серверу (попытка "+ (count+1)+")");
         this.sendJSONPacket(null,null,new JSONObject(),true);
         DatagramPacket packet = this.recivePacket();
         JSONObject initialConnectData = Serialization.DeserializeObject(packet.getData());
         System.out.println("Подключились");
         System.out.println("Для регистрации введите reg\nДля авторизации введите auth");

         JSONObject authData;
         boolean typeOfConnection = asker.askTypeOfConnection();
         while (true) {
            if (typeOfConnection) {
                Command reg = new Register(udp, asker, (JSONObject) initialConnectData.get("data"));
                reg.execute(null);
            } else {
                Command auth = new Authorize(udp, asker, (JSONObject) initialConnectData.get("data"));
                auth.execute(null);
            }
            DatagramPacket authPacket = this.recivePacket();
            authData = Serialization.DeserializeObject(authPacket.getData());
            if ((Boolean) authData.get("status")) break;
             if (typeOfConnection) {
                 System.out.println("Не зарегисрировали попробуйте ещё раз!");
             } else {
                System.out.println("Не авторизовались попробуйте ещё раз!");
             }

        }
        JSONObject tokenData = (JSONObject) authData.get("data");
         TOKEN  = (String) tokenData.get("token");
         System.out.println(authData.get("responseText"));
         return initialConnectData;
     }
        catch (SocketTimeoutException e){
            count+=1;
            if (count >= MAX_CONNECTION_RETRY ) throw new RuntimeException(e);
            return initialConnect(count,udp);
        }
    }
}
