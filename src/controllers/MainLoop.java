package controllers;

import commands.Command;
import network.UDP;
import org.json.simple.JSONObject;
import utilities.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс наследник ControlLoop. Получает команды из стандартного потока ввода
 * @author Ubica228
 */
public class MainLoop extends ControlLoop{

    UDP udp;
    public MainLoop(Map<String,Command> commands, Scanner scanner, Asker asker, UDP udp){
        super(commands,scanner,asker,new InputGetter(scanner,false));
        this.udp = udp;
        printIndication();
    }
    public void printIndication(){
        System.out.print(">");
    }

    public boolean execute(){

        boolean work = true;
        while (work){
            String[] commandInput = this.inputGetter.getInputLine();
            String commandName = commandInput[0];
            try {
                if (commandName.equals("execute_script")) {
                    Scanner scriptScanner = FileManager.getFileScanner(commandInput[1]);
                    asker.changeMode();
                    asker.changeScanner(scriptScanner);

                    ArrayList<String> scriptHistory = new ArrayList<>();
                    scriptHistory.add(commandInput[1]);
                    ScriptLoop scriptLoop = new ScriptLoop(commands, scriptScanner, asker,scriptHistory,udp);
                    work = scriptLoop.execute();

                    asker.changeMode();
                    asker.changeScanner(scanner);

                    printIndication();
                } else if (commandName.equals("help")) {
                    this.commands.get(commandName).execute(commandInput);
                } else {
                    try {
                     this.commands.get(commandName).execute(commandInput);
                     JSONObject response =  Serialization.DeserializeObject(udp.recivePacket().getData());
                     System.out.println(response.get("responseText"));
                     printIndication();
                    }
                    catch (SocketTimeoutException e){
                        System.out.print("Сервер не отвечает!");
                        printIndication();
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Не указан аргумент для команды "+commandName);
            }
            catch (NullPointerException e){
                System.out.println(e.getMessage());
                if(!commandName.isEmpty()) System.out.println("Введена не существующая команда. help чтобы узнать команды");
                printIndication();
            }
            catch (FileNotFoundException e) {
                System.out.println("Файл скрипта не найден!");
                printIndication();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        return work;
    }
}