package commands;

import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;
import utilities.HumanBeingDAO;


import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по полю id класса HumanBeing.
 * @author Ubica228
 */
public class RemoveById extends Command{

    private UDP udp;
    private JSONObject serverCommands;


    public RemoveById(UDP udp, JSONObject serverCommands){
        super("remove_by_id","Удаляет людей по ID");
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
            int id = Integer.parseInt(arguments[1]);
            System.out.println(arguments[1]);
            udp.sendJSONPacket(serverCommands.get(this.getName()),arguments[1],new JSONObject(),false);
            return true;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Вы не ввели ID человека");
            return false;
        }
        catch (NumberFormatException e){
            System.out.println("ID представляет собой число.");
            return false;
        }
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean execute(JSONObject arguments) throws IOException {
        return false;
    }
}
