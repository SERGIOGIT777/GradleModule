package entity;

import java.util.List;

public class Application implements Entity {
    public static final Integer STATUS_ALL = -1;
    public static final Integer STATUS_HOT = 0;
    public static final Integer STATUS_NEW = 1;
    public static final Integer STATUS_OLD = 2;

    public static final List<Integer> STATUSES = List.of(STATUS_ALL, STATUS_HOT, STATUS_NEW, STATUS_OLD);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private  Integer id;
    private String firstname;
    private String lastname;
    private String address;
    private Integer age;
    private Integer status;

    public Application() {
    }

    public Application(String[] columns) {
        setData(columns);
    }

    @Override
    public void setData(String[] columns) {
        this.id = Integer.parseInt(columns[0]);
        this.firstname = columns[1];
        this.lastname = columns[2];
        this.age = Integer.parseInt(columns[3]);
        this.address = columns[4];
        this.status = Integer.parseInt(columns[5]);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Application{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", status=" + status +
                '}';
    }
}
