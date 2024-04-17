package controllers;

import commands.Command;
import utilities.Asker;
import utilities.InputGetter;

import java.util.Map;
import java.util.Scanner;
/**
 * Класс предназначенный для управления программой в зависимости от полученого ввода
 * @author Ubica228
 */
public abstract class ControlLoop {
    protected Map<String, Command> commands;
    protected InputGetter inputGetter;
    protected Asker asker;

    protected Scanner scanner;
    public ControlLoop(Map<String,Command> commands, Scanner scanner,Asker asker,InputGetter inputGetter){
        this.inputGetter = inputGetter;
        this.commands = commands;
        this.asker = asker;
        this.scanner = scanner;
    }

    public abstract boolean execute();
}
