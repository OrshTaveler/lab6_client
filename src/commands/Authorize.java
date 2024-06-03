package commands;

import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;

import java.io.IOException;
import java.util.Calendar;

public class Authorize extends  Command{
    private UDP udp;
    private JSONObject serverCommands;
    public Authorize(UDP udp,JSONObject serverCommands) {
        super("auth", "Авторизует юзеров");
        this.udp = udp;
        this.serverCommands = serverCommands;
    }

    @Override
    public boolean execute(String[] arguments) throws IOException {
        JSONObject authData = new JSONObject();
        UDP.LOGIN = arguments[0];
        UDP.PASSWORD = arguments[1];
        UDP.TIMESTAMP = Calendar.getInstance().getTimeInMillis();

        authData.put("login",arguments[0]);
        authData.put("password",arguments[1]);


        udp.sendJSONPacket(serverCommands.get(this.getName()),null,authData,false);

        return true;
    }

    @Override
    public boolean execute(JSONObject arguments) throws IOException {
        return false;
    }
}
