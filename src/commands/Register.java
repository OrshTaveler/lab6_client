package commands;

import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;

import java.io.IOException;
import java.util.Calendar;

public class Register extends  Command{
    private Asker asker;
    private UDP udp;
    private JSONObject serverCommands;
    public Register(UDP udp, Asker asker,JSONObject serverCommands) {
        super("reg", "Регистрирует юзеров");
        this.asker = asker;
        this.udp = udp;
        this.serverCommands = serverCommands;
    }

    @Override
    public boolean execute(String[] arguments) throws IOException {
        JSONObject authData = asker.askAuth();
        UDP.LOGIN = (String) authData.get("login");
        UDP.PASSWORD = (String) authData.get("password");
        UDP.TIMESTAMP = Calendar.getInstance().getTimeInMillis();
        udp.sendJSONPacket(serverCommands.get(this.getName()),null,authData,false);
        return true;
    }

    @Override
    public boolean execute(JSONObject arguments) throws IOException {
        return false;
    }
}
