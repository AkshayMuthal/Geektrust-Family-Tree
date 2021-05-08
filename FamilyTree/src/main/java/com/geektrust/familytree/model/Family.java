package com.geektrust.familytree.model;

import com.geektrust.familytree.constants.RelationshipName;
import com.geektrust.familytree.exception.ChildAdditionFailureException;
import com.geektrust.familytree.exception.PersonNotFoundException;

import java.util.*;

import static com.geektrust.familytree.constants.PersonConstants.MALE;

public class Family {

    private static HashMap<String, Person> familyMembers = new HashMap<String, Person>();

    public static HashMap<String, Person> getFamilyMembers() {
        return familyMembers;
    }

    public static void addMemberToFamily(Person person){
        Family.familyMembers.put(person.getName(), person);
    }

    public static void updateFamilyTree(){
        HashMap<String, Person> familyMembers = Family.getFamilyMembers();

        for(Map.Entry personElement: familyMembers.entrySet()){
            Person person = (Person) personElement.getValue();
            person.addSiblings();
        }
    }

    public List<Person> getRelationship(String input){
        String inputArray[] = input.split(" ");
        String personName = inputArray[1];
        RelationshipName relation = getRelationName(inputArray[2]);

        Person person = findFamilyMember(personName);
        return getRelations(person, relation);
    }

    private List<Person> getRelations(Person person, RelationshipName relationshipName){
        switch (relationshipName){
            case SON:
                return person.getSons();
            case DAUGHTER:
                return person.getDaughters();
            case SIBLINGS:
                return person.getSiblings();
            case PATERNAL_UNCLE:
                return person.getPaternalUncle();
            case MATERNAL_UNCLE:
                return person.getMaternalUncle();
            case PATERNAL_AUNT:
                return person.getPaternalAunt();
            case MATERNAL_AUNT:
                return person.getMaternalAunt();
            case SISTER_IN_LAW:
                return person.getSisterInLaw();
            case BROTHER_IN_LAW:
                return person.getBrotherInLaw();
        }
        return new ArrayList<Person>();
    }

    private RelationshipName getRelationName(String relation){
        relation = relation.toUpperCase().replace("-","_");
        return RelationshipName.valueOf(relation);
    }

    public Person findFamilyMember(String personName){
        if(familyMembers.containsKey(personName))
            return familyMembers.get(personName);
        else
            throw new PersonNotFoundException();
    }

    public void addChildInTree(String input){
        String inputParam[] = input.split(" ");
        String motherName = inputParam[1];
        String childName = inputParam[2];
        String childGender = inputParam[3];

        Person mother = findFamilyMember(motherName);
        if(mother.getGender().equalsIgnoreCase(MALE)){
            throw new ChildAdditionFailureException();
        }

        Person child = new Person(childName, childGender);
        mother.addChildrens(child);
        Family.addMemberToFamily(child);
        Family.updateFamilyTree();
    }
}
