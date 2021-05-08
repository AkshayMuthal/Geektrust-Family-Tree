package com.geektrust.familytree.service;

import com.geektrust.familytree.model.Family;
import com.geektrust.familytree.model.Person;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import static com.geektrust.familytree.constants.PersonConstants.*;

public class InputTextParser {
    private static HashMap<String, Person> familyMembers = Family.getFamilyMembers();


    public void initializeFamilyTree(String fileName){
        try{
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            Scanner scanner = new Scanner(inputStream);

            while(scanner.hasNextLine()){
                String input = scanner.nextLine();
                String personDetails[] = input.split(",");
                parsePersonString(personDetails);
            }
            Family.updateFamilyTree();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Person addPersonToFamily(String name, String gender, String fatherName, String motherName){
        Person person;
        if(familyMembers.containsKey(name)){
            person = familyMembers.get(name);
        }
        else{
            person = new Person(name, gender);
            Family.addMemberToFamily(person);
            setParents(person, fatherName, motherName);
            if(person.getMother()!=null){
                person.getMother().addChildrens(person);
            }
        }
        return person;
    }

    private static void parsePersonString(String[] personDetails){
        String name = personDetails[NAME];
        String gender = personDetails[GENDER];
        String spouseName = personDetails[SPOUSE];
        String fatherName = personDetails[FATHER];
        String motherName = personDetails[MOTHER];
        String spouseGender = ((gender.equalsIgnoreCase(FEMALE)? MALE : FEMALE));

        Person person = addPersonToFamily(name, gender, fatherName, motherName);
        if(!spouseName.equalsIgnoreCase("NONE")) {
            Person spouse = addPersonToFamily(spouseName, spouseGender, "NONE", "NONE");
            person.setPartner(spouse);
        }
    }

    private static void setParents(Person person, String fatherName, String motherName){
        if(!fatherName.equalsIgnoreCase("NONE"))
            person.setFather(familyMembers.get(fatherName));
        if(!motherName.equalsIgnoreCase("NONE"))
            person.setMother(familyMembers.get(motherName));
    }
}
