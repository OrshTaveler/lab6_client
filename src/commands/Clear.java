package commands;

import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;
import utilities.HumanBeingDAO;

import java.io.IOException;

/**
 * Команда 'clear'. Очищает коллекцию.
 * @author Ubica228
 */
public class Clear extends Command{
    private Asker asker;
    private UDP udp;
    private JSONObject serverCommands;
    public Clear(UDP udp, Asker asker, JSONObject serverCommands){
        super("clear","Удаляет всех людей");
        this.asker = asker;
        this.udp = udp;
        this.serverCommands = serverCommands;
    }
    /**
     * Выполняет команду
     * @return  Успешность выполнения команды
     * */
    @Override
    public boolean execute(String[] arguments) throws IOException {
        udp.sendJSONPacket(serverCommands.get(this.getName()),null,null,false);
        return true;
    }
}
