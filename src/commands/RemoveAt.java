package commands;

import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;
import utilities.HumanBeingDAO;

import java.io.IOException;


/**
 * Команда 'remove_at'. Удаляет элемент с введённым.
 * @author Ubica228
 */
public class RemoveAt extends Command{
    private Asker asker;
    private UDP udp;
    private JSONObject serverCommands;


    public RemoveAt(UDP udp, Asker asker,JSONObject serverCommands){
        super("remove_at","Удаляет людей из списочка по индексу");

    }
    /**
     * Выполняет команду
     * @return  Успешность выполнения команды
     * */
    @Override
    public boolean execute(String[] arguments) {
        try{
            int index = Integer.parseInt(arguments[1]);
            udp.sendJSONPacket(serverCommands.get(this.getName()),arguments[1],new JSONObject(),false);
            return true;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Вы не ввели индекс");
            return false;
        }
        catch (NumberFormatException e){
            System.out.println("Индекс представляет собой число");
            return false;
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Индекс выходит за пределы списка");
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }
}
