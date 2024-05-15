import network.UDP;
import org.json.simple.JSONObject;
import utilities.FileManager;
import utilities.HumanBeingDAO;
import utilities.Asker;
import commands.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Map;
import controllers.MainLoop;
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

         Scanner scanner = new Scanner(System.in);

            JSONObject serverCommands;
            Asker asker = new Asker(scanner);
            UDP udp = new UDP(4555,"localhost",asker);
            Map<String, Command> commands  = new LinkedHashMap<>();

            JSONObject initialResponse  = udp.initialConnect(0,udp);
            serverCommands = (JSONObject) initialResponse.get("data");


            commands.put("add", new Add(udp,asker,serverCommands));
            commands.put("show", new Show(udp,asker,serverCommands));
            commands.put("clear", new Clear(udp,asker,serverCommands));
            commands.put("update", new Update(udp,asker,serverCommands));
            commands.put("remove_by_id", new RemoveById(udp,asker,serverCommands));
            commands.put("insert_at", new InsertAt(udp,asker,serverCommands));
            commands.put("remove_at", new RemoveAt(udp,asker,serverCommands));
            commands.put("remove_first", new RemoveFirst(udp,asker,serverCommands));
            commands.put("remove_any_by_weapon_type", new RemoveAnyByWeaponType(udp,asker,serverCommands));
            commands.put("filter_starts_with_name", new FilterStartsWithName(udp,asker,serverCommands));
            commands.put("reg",new Register(udp,asker,serverCommands));
            commands.put("auth",new Authorize(udp,asker,serverCommands));
            commands.put("help",new Help(commands));


            MainLoop controlLoop = new MainLoop(commands,scanner,asker,udp);
            controlLoop.execute();


    }
}
