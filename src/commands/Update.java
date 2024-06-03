package commands;

import customexceptions.IncorrectDataInScript;
import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;
import utilities.Serialization;

import java.io.IOException;

public class Update extends Command {
    private Asker asker;
    private UDP udp;
    private JSONObject serverCommands;

    public Update(UDP udp, JSONObject serverCommands) {
        super("update", "Обнавляет списочек");
        this.udp = udp;
        this.serverCommands = serverCommands;
    }

    @Override
    public boolean execute(String[] arguments) throws IOException {

        return false;
    }

    @Override
    public boolean execute(JSONObject jsonHuman) throws IOException {
        try {
            udp.sendJSONPacket(serverCommands.get("owner"), (String) jsonHuman.get("id"), new JSONObject(), false);
            JSONObject response = Serialization.DeserializeObject(udp.recivePacket().getData());
            boolean res = (boolean) response.get("status");
            if (!res) {
                System.out.println("У вас нет прав к этому объекту!");
                return false;
            }
            udp.sendJSONPacket(serverCommands.get(this.getName()), (String) jsonHuman.get("id"), jsonHuman, false);
            return true;
        }  catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Вы не ввели ID человека");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("ID представляет собой число.");
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
