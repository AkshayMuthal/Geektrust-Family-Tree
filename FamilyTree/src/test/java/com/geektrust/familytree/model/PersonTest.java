package com.geektrust.familytree.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PersonTest {

    @Mock
    Person person;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        setMockPerson();
    }

    @Test
    public void testAddChildrens(){
        Person child = new Person("Param", "male");
        person.addChildrens(child);
        Assert.assertEquals(person.getChildrens().size(), 3);
    }

    @Test
    public void testGetSiblings(){
        List<Person> list = new ArrayList<Person>();
        list.add(new Person("Lakshman", "male"));
        list.add(new Person("Bharat", "male"));
        Assert.assertEquals(person.getSiblings().size(), list.size());
    }

    @Test
    public void testGetPersonObject(){
        Person sister = new Person("Shanta", "female");
        Person father = this.person.getFather();
        father.addChildrens(sister);
        this.person.addSiblings();
        Assert.assertEquals(father.getChildrens().size(), 4);
    }


    @Test
    public void testGetPaternalUncle(){
        Person child = this.person.getSons().get(0);
        Assert.assertEquals(child.getPaternalUncle().size(), 2);
    }

    @Test
    public void testGetMaternalUncle(){
        Person child = this.person.getSons().get(0);
        Assert.assertEquals(child.getMaternalUncle().size(), 0);
    }

    @Test
    public void testGetPaternalAunt(){
        Person child = this.person.getSons().get(0);
        Assert.assertEquals(child.getPaternalAunt().size(), 0);
    }

    @Test
    public void testGetMaternalAunt(){
        Person child = this.person.getSons().get(0);
        Assert.assertEquals(child.getMaternalAunt().size(), 0);
    }

    @Test
    public void testGetSisterInLaw(){
        Person child = this.person.getSons().get(0);
        Assert.assertEquals(child.getSisterInLaw().size(), 0);
    }

    @Test
    public void testGetBrotherInLaw(){
        Person child = this.person.getSons().get(0);
        Assert.assertEquals(child.getBrotherInLaw().size(), 0);
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
