package entity;

import java.io.Serializable;

public class Person implements Entity, Serializable {
    private Integer id;
    private String firstname;
    private String lastname;
    private String address;
    private Integer age;
    private String phoneNumber;

    public Person() {
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Person(String id, String[] columns) {
        this.id = Integer.parseInt(id);
        this.firstname = columns[1];
        this.lastname = columns[2];
        this.age = Integer.parseInt(columns[3]);
        this.address = columns[4];
    }

    @Override
    public void setData(String[] columns) {
        this.id = Integer.parseInt(columns[0]);
        this.firstname = columns[1];
        this.lastname = columns[2];
        this.age = Integer.parseInt(columns[3]);
        this.phoneNumber = columns[4];
        this.address = columns[5];
    }

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (firstname.length() > 30) {
            throw new IllegalArgumentException();
        }
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        if (age < 0) {
            throw new IllegalArgumentException("Wrong age: " + age);
        }
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
