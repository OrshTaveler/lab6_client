package commands;

import customexceptions.IncorrectDataInScript;
import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;

import java.io.IOException;

public class Update extends Command{
    private Asker asker;
    private UDP udp;
    private JSONObject serverCommands;
    public Update(UDP udp, Asker asker,JSONObject serverCommands) {
        super("update", "Обнавляет списочек");
        this.asker = asker;
        this.udp = udp;
        this.serverCommands = serverCommands;
    }

    @Override
    public boolean execute(String[] arguments) throws IOException {
        JSONObject jsonHuman;
        try{
            int id = Integer.parseInt(arguments[1]);
            jsonHuman = asker.askHumanBeing();
            udp.sendJSONPacket(serverCommands.get(this.getName()),arguments[1],jsonHuman,false);
            return true;
        }
        catch (IncorrectDataInScript e){
            System.out.println("При добавлении нового HumanBeing произошла ошибка с комментарием - "+e.getMessage()+", проверьте скрипт");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Вы не ввели ID человека");
            return false;
        }
        catch (NumberFormatException e){
            System.out.println("ID представляет собой число.");
            return false;
        }
        return false;
    }
}
