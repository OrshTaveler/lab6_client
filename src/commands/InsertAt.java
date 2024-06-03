package commands;

import customexceptions.IncorrectDataInScript;
import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;
import utilities.HumanBeingDAO;

import java.io.IOException;

/**
 * Команда 'insert_at'. Добавляет элемент в коллекцию по заданому индексу.
 * @author Ubica228
 */
public class InsertAt extends Command{

    private Asker asker;

    private UDP udp;
    private JSONObject serverCommands;

    public InsertAt(UDP udp, Asker asker,JSONObject serverCommands){
        super("insert_at","Добавляет людей в списочек по индексу");
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
            int index = Integer.parseInt(arguments[1]);
            JSONObject jsonHuman = asker.askHumanBeing();
            udp.sendJSONPacket(serverCommands.get(this.getName()),arguments[1],jsonHuman,false);

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
        } catch (IncorrectDataInScript e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean execute(JSONObject arguments) throws IOException {
        return false;
    }
}
