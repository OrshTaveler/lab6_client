package network;

import commands.Command;
import org.json.simple.JSONObject;
import utilities.Serialization;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;

import static utilities.Serialization.SerializeObject;


public class UDP {
    private int port;
    DatagramSocket socket;
    private static final int MAX_CONNECTION_RETRY = 5;
    InetAddress address;
    int size = 60000;
    public UDP(int port,String host) throws SocketException, UnknownHostException {
        this.port = port;
        socket = new DatagramSocket();
        socket.setSoTimeout(10000);
        address = InetAddress.getByName(host);

    }

    private void sendPacket(byte[] message) throws IOException {
        DatagramPacket packet = new DatagramPacket(message,message.length, address, port);
        socket.send(packet);
    }
    public void sendJSONPacket(Object command,String atribute ,JSONObject additionalData,boolean initialConnect) throws IOException {
        JSONObject jsonPacket = new JSONObject();
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
    public JSONObject initialConnect(int count) throws IOException, ClassNotFoundException {
        try{
         System.out.println("Подключаемся к серверу (попытка "+ (count+1)+")");
         this.sendJSONPacket(null,null,null,true);
         DatagramPacket packet = this.recivePacket();
         JSONObject initialConnectData = Serialization.DeserializeObject(packet.getData());
         System.out.println("Подключились");
         return initialConnectData;
     }
        catch (SocketTimeoutException e){
            count+=1;
            if (count >= MAX_CONNECTION_RETRY ) throw new RuntimeException(e);
            return initialConnect(count);
        }
    }
}
