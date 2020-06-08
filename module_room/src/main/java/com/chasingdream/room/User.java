package com.chasingdream.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {
 
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;
    private String name;
    private int age;
 
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    @Ignore
    public User() {
    }
 
    //这里的getter/setter方法是必须的
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}