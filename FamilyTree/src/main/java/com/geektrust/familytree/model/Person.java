package com.geektrust.familytree.model;

import com.geektrust.familytree.constants.PersonConstants;

import java.util.ArrayList;
import java.util.List;


public class Person {
    private String name;
    private String gender;
    private Person spouse;
    private Person father;
    private Person mother;

    private List<Person> brothers = new ArrayList<Person>();
    private List<Person> sisters = new ArrayList<Person>();
    private List<Person> sons = new ArrayList<Person>();
    private List<Person> daughters = new ArrayList<Person>();

    public Person(String name, String gender){
        this.name = name;
        this.gender = gender;
        this.father = null;
        this.mother = null;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public Person getSpouse() {
        return spouse;
    }

    private void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public void setPartner(Person spouse) {
        this.setSpouse(spouse);
        spouse.setSpouse(this);
    }

    public List<Person> getChildrens() {
        List<Person> childrens = new ArrayList<Person>(sons);
        childrens.addAll(daughters);
        return childrens;
    }

    public List<Person> getSons() {
        return sons;
    }

    public List<Person> getDaughters() {
        return daughters;
    }

    private void addSons(Person person){
        this.sons.add(person);
    }

    private void addDaughter(Person person){
        this.daughters.add(person);
    }

    public void addChildrens(Person person){
        Person father = this.getSpouse();
        person.setFather(father);
        person.setMother(this);

        if(person.getGender().equalsIgnoreCase(PersonConstants.FEMALE)){
            this.addDaughter(person);
            father.addDaughter(person);
        }
        else {
            this.addSons(person);
            father.addSons(person);
        }
    }

    public List<Person> getSiblings() {
        List<Person> siblings = new ArrayList<Person>(brothers);
        siblings.addAll(sisters);
        return siblings;
    }

    public List<Person> getBrothers() {
        return brothers;
    }

    public List<Person> getSisters() {
        return sisters;
    }

    public void setBrothers(List<Person> brothers) {
        this.brothers = brothers;
    }

    public void setSisters(List<Person> sisters) {
        this.sisters = sisters;
    }

    public void addSiblings(){
        List<Person> brothers = new ArrayList<Person>();
        List<Person> sisters = new ArrayList<Person>();
        Person father = this.getFather();

        if(father!=null && father.getChildrens()!=null){
            List<Person> children = father.getChildrens();
            for(int i=0;i<children.size();i++){
                Person child = children.get(i);
                if(!child.getName().equals(this.getName())){
                    if(child.getGender().equalsIgnoreCase(PersonConstants.FEMALE))
                        sisters.add(child);
                    else
                        brothers.add(child);
                }
            }
            this.setBrothers(brothers);
            this.setSisters(sisters);
        }
    }

    public List<Person> getPaternalUncle(){
        List<Person> paternalUncles = new ArrayList<Person>();
        Person father = this.getFather();
        if(father!=null && father.getBrothers()!=null) {
            paternalUncles.addAll(father.getBrothers());
        }
        return paternalUncles;
    }

    public List<Person> getMaternalUncle(){
        List<Person> maternalUncles = new ArrayList<Person>();
        Person mother = this.getMother();
        if(mother!=null && mother.getBrothers()!=null) {
            maternalUncles.addAll(mother.getBrothers());
        }
        return maternalUncles;
    }

    public List<Person> getPaternalAunt(){
        List<Person> paternalAunt = new ArrayList<Person>();
        Person father = this.getFather();
        if(father!=null && father.getSisters()!=null) {
            paternalAunt.addAll(father.getSisters());
        }
        return paternalAunt;
    }

    public List<Person> getMaternalAunt(){
        List<Person> maternalAunt = new ArrayList<Person>();
        Person mother = this.getMother();
        if(mother!=null && mother.getSisters()!=null) {
            maternalAunt.addAll(mother.getSisters());
        }
        return maternalAunt;
    }

    public List<Person> getSisterInLaw(){
        List<Person> sisterInLaw = new ArrayList<Person>();

        Person spouse = this.getSpouse();
        if(spouse!=null && spouse.getSisters()!=null){
            sisterInLaw.addAll(spouse.getSisters());
        }

        List<Person> siblings = this.getBrothers();
        for(int i=0;i<siblings.size();i++){
            sisterInLaw.add(siblings.get(i).getSpouse());
        }
        return sisterInLaw;
    }

    public List<Person> getBrotherInLaw(){
        List<Person> brotherInLaw = new ArrayList<Person>();

        Person spouse = this.getSpouse();
        if(spouse!=null && spouse.getBrothers()!=null){
            brotherInLaw.addAll(spouse.getBrothers());
        }

        List<Person> siblings = this.getSisters();
        for(int i=0;i<siblings.size();i++){
            brotherInLaw.add(siblings.get(i).getSpouse());
        }
        return brotherInLaw;
    }
}
