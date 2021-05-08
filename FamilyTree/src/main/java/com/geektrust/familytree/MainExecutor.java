package com.geektrust.familytree;

import com.geektrust.familytree.exception.ChildAdditionFailureException;
import com.geektrust.familytree.exception.PersonNotFoundException;
import com.geektrust.familytree.model.Family;
import com.geektrust.familytree.model.Person;
import com.geektrust.familytree.constants.FamilyConstants;
import com.geektrust.familytree.service.InputTextParser;

import java.io.*;
import java.util.List;

public class MainExecutor {
    public static void main(String args[]){
        try{
            init();
            processInput(args[0]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void init(){
        InputTextParser inputParser = new InputTextParser();

        String inputFileName = "family.txt";
        inputParser.initializeFamilyTree(inputFileName);
    }

    private static void getFamilyRelationship(Family family, String input){
        try {
            List<Person> relatives = family.getRelationship(input);
            for (Person person: relatives){
                System.out.print(person.getName()+" ");
            }
            if(relatives.size() == 0){
                System.out.print("NONE");
            }
            System.out.println("");
        }
        catch (PersonNotFoundException e){
            System.out.println(e.toString());
        }
    }

    private static void addChildInFamily(Family family, String input){
        try{
            family.addChildInTree(input);
            System.out.println(FamilyConstants.CHILD_ADDITION_SUCCEEDED);
        } catch (PersonNotFoundException e){
            System.out.println(e.toString());
        } catch (ChildAdditionFailureException e){
            System.out.println(e.toString());
        }
    }

    private static void processInput(String filepath){
        File file = new File(filepath);
        Family family = new Family();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String input;
            while ((input = bufferedReader.readLine())!=null){
                if(input.contains(FamilyConstants.GET_RELATIONSHIP)){
                    getFamilyRelationship(family, input);
                }
                else if(input.contains(FamilyConstants.ADD_CHILD)){
                    addChildInFamily(family, input);
                }
            }
        } catch (FileNotFoundException exception){
            exception.printStackTrace();
        } catch (IOException exception){
            exception.printStackTrace();
        }

    }
}
