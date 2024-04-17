package controllers;

import commands.Command;
import network.UDP;
import org.json.simple.JSONObject;
import utilities.*;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Класс наследник ControlLoop. Получает команды из файла скрипта.
 * @author Ubica228
 */
public class ScriptLoop extends ControlLoop {
    String currentPath;
    UDP udp;

    ArrayList<String> scriptHistory;

    public ScriptLoop(Map<String,Command> commands, Scanner scanner, Asker asker, ArrayList<String> scriptHistory,UDP udp ){
        super(commands,scanner,asker,new InputGetter(scanner,true));
        this.currentPath = scriptHistory.get(scriptHistory.size()-1);
        this.scriptHistory = scriptHistory;
        this.udp = udp;
    }
    @Override
    public boolean execute() {
        boolean work = true;
        String[] commandInput;
        String   commandName;
        while (work){
            try {
                commandInput = this.inputGetter.getInputLine();
                commandName = commandInput[0];
                if (commandName.isEmpty()) {continue;}
            }
            catch (NoSuchElementException e){
                return work;
            }
            try {
                switch (commandName) {
                    case ("exit") -> {
                        return false;
                    }
                    case ("execute_script") -> {
                        if (!commandInput[1].equals(this.currentPath) & !scriptHistory.contains(commandInput[1])) {
                                Scanner scriptScanner =  FileManager.getFileScanner(commandInput[1]);
                                asker.changeScanner(scriptScanner);

                                this.scriptHistory.add(commandInput[1]);
                                ScriptLoop scriptLoop = new ScriptLoop(commands, scriptScanner,asker,scriptHistory,udp);
                                work = scriptLoop.execute();

                                asker.changeScanner(scanner);
                        }

                    }
                    default -> {
                        this.commands.get(commandName).execute(commandInput);
                        JSONObject response =  Serialization.DeserializeObject(udp.recivePacket().getData());
                        System.out.println(response.get("responseText"));
                    }
                }

            }
            catch (NullPointerException e){
                System.out.println("Не существующая команда - "+ commandName+" "+e.getMessage());
                return true;
            }
            catch (FileNotFoundException e){
                System.out.println("Файл скрипта не найден!");
                return true;
            }
            catch (Exception e){
                System.out.println("Ошибка "+ e +" в скрипте - "+ Arrays.toString(commandInput));
                return true;
            }
        }
        return work;
    }
}
