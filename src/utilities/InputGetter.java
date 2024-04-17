package utilities;
import customexceptions.IncorrectDataInScript;
import initials.WeaponType;

import java.util.Scanner;

/**
 * Класс предназначенный для получения строковых данных из указаного потока ввода и последующего вычленения типов из полученых строк
 * @author Ubica228
 */
public class InputGetter {
    Scanner scanner;
    boolean fromScript;
    public InputGetter(Scanner scanner, boolean fromScript){
        this.scanner = scanner;
        this.fromScript = fromScript;
    }

    public String[] getInputLine(){
        return this.scanner.nextLine().trim().split(" ");

    }

    public String getString(){
        return this.scanner.nextLine().trim();
    }
    public int getInt(Double min, Double max) throws IncorrectDataInScript { // Ждём int на входе и переспрашиваем, если не получили int
        try {
            String newinput = scanner.nextLine().trim();
            int res = Integer.parseInt(newinput);
            if (res >= min && res <= max){
                return res;
            }
            else {
                  if (!fromScript) {
                      System.out.print("Введите число в диапазоне от " + min + " до " + max.toString());
                      return getInt(min, max);
                  }
                  else {throw new IncorrectDataInScript("Число должно быть в диапазоне от " + min + " до " + max);}
            }
        }
        catch (NumberFormatException e) {
            if (!fromScript) {
                System.out.print("Введите целое число:");
                return getInt(min, max);
            }
            else {throw new IncorrectDataInScript("Получено не целое число");}
        }
    }
    public double getDouble(Double min, Double max) throws IncorrectDataInScript{ // Ждём double на входе и переспрашиваем, если не получили double
        try {
            String newinput = scanner.nextLine().trim();
            double res = Double.parseDouble(newinput);
            if (res >= min && res <= max){
                return res;
            }
            else {
                if(!fromScript){
                 System.out.print("Введите число в диапазоне от "+ min + " до "+ max);
                 return getDouble(min,max);
                }
                else {
                    throw new IncorrectDataInScript("Число должно быть в диапазоне от " + min + " до " + max);
                }
            }

        }
        catch (NumberFormatException e){
            if(!fromScript){
             System.out.print("Введите число с точкой:");
             return getDouble(min,max);
            }
            else {throw new IncorrectDataInScript("Не корректная форма числа");}
        }
    }
    public  float getFloat(Double min, Double max) throws IncorrectDataInScript{ // Ждём float на входе и переспрашиваем, если не получили float
        try {
            String newinput = scanner.nextLine().trim();
            float res = Float.parseFloat(newinput);
            if (res >= min && res <= max){
                return res;
            }
            else {
                if(!fromScript) {
                    System.out.print("Введите число в диапазоне от " + min + " до " + max);
                    return getFloat(min, max);
                }
                else {throw new IncorrectDataInScript("Число должно быть в диапазоне от " + min + " до " + max);}
            }
        }
        catch (NumberFormatException e){
            if(!fromScript){
             System.out.print("Введите число с точкой:");
             return getFloat(min,max);
            }
            else {throw new IncorrectDataInScript("Не корректная форма числа");}
        }
    }
    public long getLong(Double min, Double max) throws IncorrectDataInScript{ // Ждём double на входе и переспрашиваем, если не получили double
        try {
            String newinput = scanner.nextLine().trim();
            long res = Long.parseLong(newinput);
            if (res >= min && res <= max){
                return res;
            }
            else {
                if(!fromScript){
                 System.out.print("Введите число в диапазоне от "+ min + " до "+ max+":");
                 return getLong(min,max);
                }
                else {throw new IncorrectDataInScript("Число должно быть в диапазоне от " + min + " до " + max);}
            }
        }
        catch (NumberFormatException e){
            if(!fromScript){
             System.out.print("Введите число:");
             return getLong(min,max);
            }
            else{throw new IncorrectDataInScript("Не корректная форма числа");}
        }
    }
    public  boolean getBoolean() throws IncorrectDataInScript{ // Ждём YES/NO на входе и переспрашиваем, если не получили YES/NO
        String newinput = scanner.nextLine().trim();
        if (newinput.equals("YES")) {return true;}
        else if (newinput.equals("NO")) {
            return false;
        }
        else{
            if(!fromScript){
             System.out.print("Введите YES ли NO:");
             return getBoolean();
            }
            else {throw new IncorrectDataInScript("Введено что-то кроме YES/NO");}
        }
    }
    public  WeaponType getWeaponType() throws IncorrectDataInScript{
        String newinput = scanner.nextLine().trim();
        try {
            return WeaponType.valueOf(newinput);
        }
        catch (IllegalArgumentException e){
            if(!fromScript){
             System.out.print("Такого типа оружия нет, введите заново:");
             return getWeaponType();
           }
            else{throw new IncorrectDataInScript("Введен не существующий вид оружия");}
        }
    }
}
