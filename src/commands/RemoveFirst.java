package commands;

import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;
import utilities.HumanBeingDAO;

import java.io.IOException;

/**
 * Команда 'remove_first'. Удаляет первый элемент из коллекции.
 * @author Ubica228
 */
public class RemoveFirst extends Command{

    private UDP udp;
    private JSONObject serverCommands;
    private Asker asker;

    public RemoveFirst(UDP udp, Asker asker, JSONObject serverCommands){
        super("remove_first","Удаляет первого человека из списочка ");
        this.asker = asker;
        this.udp = udp;
        this.serverCommands = serverCommands;
    }
    /**
     * Выполняет команду
     * @return  Успешность выполнения команды
     * */
    @Override
    public boolean execute(String[] arguments) {
        try{
            udp.sendJSONPacket(serverCommands.get(this.getName()),null,null,false);
        }
        catch (IndexOutOfBoundsException e){
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
