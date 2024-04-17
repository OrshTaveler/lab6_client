package initials;

import java.time.ZonedDateTime;

public class HumanBeing {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private Boolean hasToothpick; //Поле может быть null
    private Float impactSpeed; //Максимальное значение поля: 411, Поле не может быть null
    private String soundtrackName; //Поле не может быть null
    private Long minutesOfWaiting; //Поле не может быть null
    private WeaponType weaponType; //Поле не может быть null
    private Car car; //Поле не может быть null

    public HumanBeing(int id,String name,Coordinates coordinates,boolean realHero,Boolean hasToothpick,Float impactSpeed,String soundtrackName,Long minutesOfWaiting,WeaponType weaponType,Car car){
        this.car = car;
        this.coordinates = coordinates;
        this.id = id;
        this.name = name;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.weaponType = weaponType;
        this.minutesOfWaiting = minutesOfWaiting;
        this.creationDate = ZonedDateTime.now();
    }



    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        String strForm;
        strForm= "ID:"+id+" Name:"+name +" X:"+this.coordinates.getX()+" Y:"+this.coordinates.getY()+" realHero:"+this.realHero+" hasToothpick:"+this.hasToothpick+" carName:"+car.getName()+" minutesOfWaiting:"+this.minutesOfWaiting+" weaponType:"+this.weaponType.toString();
        return strForm;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public String getName() {
        return name;
    }

    public Car getCar() {
        return car;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Long getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public Float getImpactSpeed() {
        return impactSpeed;
    }

    public String getSoundtrackName() {
        return soundtrackName;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public boolean getRealHero(){return realHero;}

}
