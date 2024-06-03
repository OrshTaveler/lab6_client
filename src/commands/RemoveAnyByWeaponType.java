package commands;

import initials.WeaponType;
import network.UDP;
import org.json.simple.JSONObject;
import utilities.Asker;
import utilities.HumanBeingDAO;


import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Команда 'remove_any_by_weapon_type'. Удаляет первый элемент коллекции у которого поле weaponType совпадает с заданым.
 * @author Ubica228
 */
public class RemoveAnyByWeaponType extends Command{
    private Asker asker;

    private UDP udp;
    private JSONObject serverCommands;


    public RemoveAnyByWeaponType(UDP udp, Asker asker,JSONObject serverCommands){
        super("remove_any_by_weapon_type","Удаляет людей по типу оружия");
        this.asker = asker;
        this.udp = udp;
        this.serverCommands = serverCommands;
    }
    /**
     * Выполняет команду
     * @return  Успешность выполнения команды
     * */
    @Override
    public boolean execute(String[] arguments) {
        try{
            String weaponType = arguments[1];
            WeaponType weaponType1 = WeaponType.valueOf(weaponType);
            udp.sendJSONPacket(serverCommands.get(this.getName()),arguments[1],new JSONObject(),false);
            return true;
        }
        catch (IllegalArgumentException e){
            System.out.println("Такого типа оружия нет");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Вы не ввели тип оружия человека");
            return false;
        }
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    @Override
    public boolean execute(JSONObject arguments) throws IOException {
        return false;
    }
}
