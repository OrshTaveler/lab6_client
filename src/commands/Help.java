package commands;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Map;
/**
 * Команда 'help'. Выводит описание всех команд.
 * @author Ubica228
 */
public class Help extends  Command{
    private Map<String, Command> commands;
    public Help(Map<String, Command> commands){
        super("help","Помогает людям");
        this.commands = commands;
    }
    /**
     * Выполняет команду
     * @return  Успешность выполнения команды
     * */
    @Override
    public boolean execute(String[] arguments) {
       for(String commandKey: commands.keySet()){
           System.out.println(this.commands.get(commandKey).getName() + ": "+this.commands.get(commandKey).getDescription());
           System.out.print(">");
       }
       return true;
    }

    @Override
    public boolean execute(JSONObject arguments) throws IOException {
        return false;
    }
}
