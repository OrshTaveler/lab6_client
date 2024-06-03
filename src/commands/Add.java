package commands;

import customexceptions.IncorrectDataInScript;
import network.UDP;
import utilities.Asker;
import utilities.HumanBeingDAO;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 * @author Ubica228
 */

public class Add extends Command{

    private UDP udp;
    private JSONObject serverCommands;

    public Add(UDP udp,JSONObject serverCommands){
        super("add","Добавляет людей в списочек");
        this.udp = udp;
        this.serverCommands = serverCommands;
    }
    /**
     * Выполняет команду
     * @return  Json
     * */
    @Override
    public boolean execute(String[] arguments) {
        return false;
    }

    @Override
    public boolean execute(JSONObject jsonHuman) throws IOException {
        try{
            udp.sendJSONPacket(serverCommands.get(this.getName()),null,jsonHuman,false);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
