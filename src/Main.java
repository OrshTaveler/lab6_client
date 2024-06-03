import GUI.AuthorizationForm;
import GUI.HumanForm;
import GUI.MainForm;
import GUI.Visualization;
import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;
import commands.*;
import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {



            Scanner scanner = new Scanner(System.in);



            JSONObject serverCommands = null;
            Asker asker = new Asker(scanner);
            UDP udp = new UDP(4555,"localhost",asker);
            Map<String, Command> commands  = new LinkedHashMap<>();



            MainForm mainForm = null;
            AuthorizationForm authorizationForm = new AuthorizationForm(commands,udp,serverCommands,mainForm);








    }
}
