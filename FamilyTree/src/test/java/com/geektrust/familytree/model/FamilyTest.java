package com.geektrust.familytree.model;


import com.geektrust.familytree.exception.ChildAdditionFailureException;
import com.geektrust.familytree.exception.PersonNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class FamilyTest {
    @InjectMocks
    Family family;

    @Mock
    Person person;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        setMockPerson();
    }

    @Test
    public void testGetRelationship(){
        Assert.assertEquals(family.getRelationship("GET_RELATIONSHIP Ram Son").size(), 2);
    }

    @Test(expected = PersonNotFoundException.class)
    public void testGetRelationshipTestPersonNotFoundException(){
        family.getRelationship("GET_RELATIONSHIP Vishal Son");
    }

    @Test(expected = ChildAdditionFailureException.class)
    public void testGetRelationshipTestChildAdditionFailureException(){
        family.addChildInTree("ADD_CHILD Ram Ketu Male");
    }

    @Test
    public void testAddChildInFamily(){
        family.addChildInTree("ADD_CHILD Sita Ketu Male");
        Assert.assertNotNull(family.getRelationship("GET_RELATIONSHIP Sita Son"));
    }

    @Test
    public void testGetRelationshipForNoRelatives(){
        Assert.assertEquals(family.getRelationship("GET_RELATIONSHIP Lav Son")
                , new ArrayList<Person>());
    }

    public void setMockPerson(){
        Person father = new Person("Dashrath", "male");
        Person mother = new Person("Kausalya", "female");
        Person person = new Person("Ram", "male");
        Person spouse = new Person("Sita", "female");
        Person child1 = new Person("Lav", "male");
        Person child2 = new Person("Kush", "male");

        father.setPartner(mother);
        mother.addChildrens(new Person("Lakshman", "male"));
        mother.addChildrens(new Person("Bharat", "male"));

        person.setFather(father);
        person.setMother(mother);

        person.setPartner(spouse);
        spouse.addChildrens(child1);
        spouse.addChildrens(child2);
        father.addChildrens(person);
        person.addSiblings();
        this.person = person;

        Family.addMemberToFamily(father);
        Family.addMemberToFamily(mother);
        Family.addMemberToFamily(new Person("Lakshman", "male"));
        Family.addMemberToFamily(new Person("Bharat", "male"));
        Family.addMemberToFamily(person);
        Family.addMemberToFamily(spouse);
        Family.addMemberToFamily(child1);
        Family.addMemberToFamily(child2);
    }
}
