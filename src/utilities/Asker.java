package utilities;

import customexceptions.IncorrectDataInScript;
import initials.Car;
import initials.Coordinates;
import initials.HumanBeing;
import initials.WeaponType;
import org.json.simple.JSONObject;

import java.util.Scanner;
/**
 * Класс, создающий объекты некоторых классов, получая необходимые для этого данные из указаного потока.
 * @author Ubica228
 */
public class Asker {
    public boolean fromScript;
    InputGetter inputGetter;
    public Asker(boolean fromScript, Scanner scanner){
        this.fromScript = fromScript;
        this.inputGetter = new InputGetter(scanner,fromScript);
    }
    public Asker(Scanner scanner){
        this.fromScript = false;
        this.inputGetter = new InputGetter(scanner,fromScript);
    }
    public void changeMode(){
        this.fromScript = !this.fromScript;
    }
    public void changeScanner(Scanner scanner){
        this.inputGetter = new InputGetter(scanner,fromScript);
    }

    public JSONObject askCar(){
        JSONObject car = new JSONObject();

        if(!fromScript) System.out.print("Название машины:");
        String name = inputGetter.getString();
        car.put("name",name);
        return car;
    }
    public JSONObject askCoordinates() throws IncorrectDataInScript {

        JSONObject coordinates = new JSONObject();
        if(!fromScript) System.out.print("X:");
        double x = inputGetter.getDouble(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        coordinates.put("x",x);

        if(!fromScript) System.out.print("Y:");
        long y   = inputGetter.getLong(-660.0,Double.POSITIVE_INFINITY);
        coordinates.put("y",y);

        return coordinates;
    }
    public JSONObject askHumanBeing() throws IncorrectDataInScript{
        JSONObject humanBeing = new JSONObject();

        if(!fromScript) System.out.print("Имя:");
        String name = inputGetter.getString();
        humanBeing.put("name",name);

        if(!fromScript) System.out.print("Настоящий герой?:");
        boolean realHero = inputGetter.getBoolean();
        humanBeing.put("realHero",realHero);

        if(!fromScript) System.out.print("Есть зубачистка?:");
        boolean hasToothpick = inputGetter.getBoolean();
        humanBeing.put("hasToothpick",hasToothpick);

        if(!fromScript) System.out.print("impactSpeed:");
        float impactSpeed = inputGetter.getFloat(Double.NEGATIVE_INFINITY,411.0);
        humanBeing.put("impactSpeed",impactSpeed);

        if(!fromScript) System.out.print("саундтрек:");
        String soundtrackName = inputGetter.getString();
        humanBeing.put("soundtrackName",soundtrackName);

        if(!fromScript) System.out.print("minutesOfWaiting:");
        long minutesOfWaiting = inputGetter.getLong(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        humanBeing.put("minutesOfWaiting",minutesOfWaiting);

        if(!fromScript) System.out.print("Оружие:");
        WeaponType weaponType = inputGetter.getWeaponType();
        humanBeing.put("weaponType",weaponType);


        humanBeing.put("coordinates",askCoordinates());
        humanBeing.put("car",askCar());

        return humanBeing;
    }

    public JSONObject askAuth(){
        JSONObject userData  = new JSONObject();

        if(!fromScript) System.out.print("Логин:");
        String login = inputGetter.getString();
        userData.put("login",login);

        if(!fromScript) System.out.print("Пароль :");
        String password = inputGetter.getString();
        userData.put("password",password);

        return userData;
    }
    public  boolean askTypeOfConnection(){
        if(!fromScript) System.out.print(">");
        String type = inputGetter.getString();
        if (type.equals("auth")) return false;
        else if (type.equals("reg")) {return true;}
        else {
            if(!fromScript) System.out.println("Введите reg или auth");
            return askTypeOfConnection();
        }
    }


}