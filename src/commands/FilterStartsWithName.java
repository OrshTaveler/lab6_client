package commands;

import customexceptions.IncorrectDataInScript;
import initials.HumanBeing;
import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;
import utilities.HumanBeingDAO;


import java.io.IOException;

/**
 * Команда 'filter_starts_with_name'. Выводит элементы коллекции у которых поле name начинается с заданой подстроки.
 * @author Ubica228
 */
public class FilterStartsWithName extends Command {
    private Asker asker;
    private UDP udp;
    private JSONObject serverCommands;
    public FilterStartsWithName(UDP udp, Asker asker,JSONObject serverCommands){
        super("filter_starts_with_name","Находит людей у которых имя начинается на");
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
        JSONObject jsonHuman;
        try{
            udp.sendJSONPacket(serverCommands.get(this.getName()),arguments[1],new JSONObject(),false);
            return true;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Вы не ввели подстроку");
            return false;
        }


    }
}
