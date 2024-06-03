package commands;

import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;

import java.io.IOException;

public class Show extends Command{

    private UDP udp;
    private JSONObject serverCommands;

    public Show(UDP udp,JSONObject serverCommands){
        super("show","Выводит людей из списка");
        this.udp = udp;
        this.serverCommands = serverCommands;
    }
    @Override
    public boolean execute(String[] arguments) throws IOException {
        udp.sendJSONPacket(serverCommands.get("show"),null,new JSONObject(),false);
        return true;
    }

    @Override
    public boolean execute(JSONObject arguments) throws IOException {
        return false;
    }
}
