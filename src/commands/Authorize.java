package commands;

import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;

import java.io.IOException;
import java.util.Calendar;

public class Authorize extends  Command{
    private Asker asker;
    private UDP udp;
    private JSONObject serverCommands;
    public Authorize(UDP udp, Asker asker,JSONObject serverCommands) {
        super("auth", "Авторизует юзеров");
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
}
