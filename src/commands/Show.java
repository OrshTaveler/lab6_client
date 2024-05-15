package commands;

import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;

import java.io.IOException;

public class Show extends Command{
    private Asker asker;
    private UDP udp;
    private JSONObject serverCommands;

    public Show(UDP udp, Asker asker,JSONObject serverCommands){
        super("show","Выводит людей из списка");
        this.asker = asker;
        this.udp = udp;
        this.serverCommands = serverCommands;
    }
    @Override
    public boolean execute(String[] arguments) throws IOException {
        udp.sendJSONPacket(serverCommands.get(this.getName()),null,new JSONObject(),false);
        return true;
    }
}
