package com.automation.pojos;

public class Character {
    private String __id;
    private String name;
    private  String role;
    private String house;
    private String school;
    private int __v;
    private boolean ministryOfMagic;
    private boolean orderOfThePhoenix;
    private boolean dumbledoresArmy;
    private boolean deathEater;
    private String species;

    public Character() {
    }

    public Character(String name, String role, String house, String school, int __v, boolean ministryOfMagic, boolean orderOfThePhoenix, boolean dumbledoresArmy, boolean deathEater, String species) {
        this.name = name;
        this.role = role;
        this.house = house;
        this.school = school;
        this.__v = __v;
        this.ministryOfMagic = ministryOfMagic;
        this.orderOfThePhoenix = orderOfThePhoenix;
        this.dumbledoresArmy = dumbledoresArmy;
        this.deathEater = deathEater;
        this.species = species;
    }

    public String get__id() {
        return __id;
    }

    public void set__id(String __id) {
        this.__id = __id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public boolean isMinistryOfMagic() {
        return ministryOfMagic;
    }

    public void setMinistryOfMagic(boolean ministryOfMagic) {
        this.ministryOfMagic = ministryOfMagic;
    }

    public boolean isOrderOfThePhoenix() {
        return orderOfThePhoenix;
    }

    public void setOrderOfThePhoenix(boolean orderOfThePhoenix) {
        this.orderOfThePhoenix = orderOfThePhoenix;
    }

    public boolean isDumbledoresArmy() {
        return dumbledoresArmy;
    }

    public void setDumbledoresArmy(boolean dumbledoresArmy) {
        this.dumbledoresArmy = dumbledoresArmy;
    }

    public boolean isDeathEater() {
        return deathEater;
    }

    public void setDeathEater(boolean deathEater) {
        this.deathEater = deathEater;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public String toString() {
        return "Character{" +
                "__id='" + __id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", house='" + house + '\'' +
                ", school='" + school + '\'' +
                ", __v=" + __v +
                ", ministryOfMagic=" + ministryOfMagic +
                ", orderOfThePhoenix=" + orderOfThePhoenix +
                ", dumbledoresArmy=" + dumbledoresArmy +
                ", deathEater=" + deathEater +
                ", species='" + species + '\'' +
                '}';
    }
}
