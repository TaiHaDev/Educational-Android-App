package com.example.ga_23s1_comp2100_6442.testDatabaseConnection;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
class Person {
    String name;
    int age;
    Skill skill;

    public Person() {
    }

    public Person(String name, int age, Skill skill) {
        this.name = name;
        this.age = age;
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", skill=" + skill +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Skill getSkill() {
        return skill;
    }
}