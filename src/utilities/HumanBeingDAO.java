package utilities;

import initials.HumanBeing;
import initials.WeaponType;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HumanBeingDAO {
    ArrayList<HumanBeing> humanBeings;
    public int idPointer;
    public HumanBeingDAO(){
        humanBeings = new ArrayList<>();
        idPointer = 0;
    }
    public void add(HumanBeing humanBeing){
        humanBeings.add(humanBeing);
        idPointer++;
    }
    public void add(int index,HumanBeing humanBeing) throws IndexOutOfBoundsException{
        humanBeings.add(index,humanBeing);
        idPointer++;
    }

    public HumanBeing getById(int id) throws NoSuchElementException{
        for (HumanBeing humanBeing: humanBeings){
            if(humanBeing.getId() == id){
                return humanBeing;
            }
        }
        throw new NoSuchElementException("С таким id людей нет");
    }
    public HumanBeing getByWeaponType(WeaponType weaponType){
        for (HumanBeing humanBeing: humanBeings){
            if(humanBeing.getWeaponType() == weaponType){
                return humanBeing;
            }
        }
        throw new NoSuchElementException("С таким оружием людей нет");
    }
    public int getIndexById(int id){
        for (int i = 0; i <= humanBeings.size(); i++){
            if(humanBeings.get(i).getId() == id){
                return i;
            }
        }
        throw new NoSuchElementException("С таким id людей нет");
    }
    public HumanBeing getByIndex(int index) throws IndexOutOfBoundsException{
        return humanBeings.get(index);
    }
    public void remove(HumanBeing humanBeing){
        this.humanBeings.remove(humanBeing);
    }


    public void clear(){
        this.humanBeings.clear();
    }
    public List<HumanBeing> startsWithName(String start){
        List<HumanBeing> res = new ArrayList<>();
        for(HumanBeing humanBeing: humanBeings){
            if(humanBeing.getName().startsWith(start)){
                res.add(humanBeing);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        String res = "";
        for(HumanBeing humanBeing: humanBeings){
            res += humanBeing.toString()+'\n';
        }
        return res;
    }

    public ArrayList<HumanBeing> getHumanBeings() {
        return humanBeings;
    }
}
