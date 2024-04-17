package commands;

import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Абстрактный класс для реализации команд.
 * @author Ubica228
 */
public abstract class Command {
    private final String name;
    private final String description;
    public Command(String name,String description){
        this.description =description;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getDescription(){
        return description;
    }
    public abstract boolean execute(String[] arguments) throws IOException;


}
