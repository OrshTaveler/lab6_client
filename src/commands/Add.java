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

    private Asker asker;
    private UDP udp;
    private JSONObject serverCommands;

    public Add(UDP udp, Asker asker,JSONObject serverCommands){
        super("add","Добавляет людей в списочек");
        this.asker = asker;
        this.udp = udp;
        this.serverCommands = serverCommands;
    }
    /**
     * Выполняет команду
     * @return  Json
     * */
    @Override
    public boolean execute(String[] arguments) {
        JSONObject jsonHuman;
        try{
            jsonHuman = asker.askHumanBeing();
            udp.sendJSONPacket(serverCommands.get(this.getName()),null,jsonHuman,false);
            return true;
        }
         catch (IncorrectDataInScript e){
             System.out.println("При добавлении нового HumanBeing произошла ошибка с комментарием - "+e.getMessage()+", проверьте скрипт");

         } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
