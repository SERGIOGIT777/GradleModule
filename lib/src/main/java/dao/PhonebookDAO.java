package dao;


import entity.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhonebookDAO {
    private final Connection connection;

    public PhonebookDAO(Connection connection) {
        this.connection = connection;
    }

//    public List<Person> findBy(Predicate<Person> predicate) {
//        List<Person> result = new ArrayList<>();
//        var personList = this.findAll();
//        personList.forEach(person -> {
//            if (predicate.test(person)) {
//                result.add(person);
//            }
//        });
//        return result;
//    }
//
//
//    private void saveAll(List<Person> people) {
//        this.deleteFile();
//        people.forEach(this::save);
//    }
//
//    private void deleteFile() {
//        new File("./phonebook.txt").delete();
//    }
//
//    public Person findByLastname(String lastname) {
//        try {
//            var ois = new ObjectInputStream(new FileInputStream("./phonebook.txt"));
//            Person person;
//            while (true){
//                try{
//                    person =(Person) ois.readObject();
//
//                    if(person.getLastname().equals(lastname)){
//                        return person;
//                    }
//                }catch (EOFException e){
//                    break;
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
//        return null;
//    }
//
//    public Person find(Integer id) {
//        var storage = this.storages.get(0);
//        var people = storage.findAll();
//        return people.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
//    }
//
//    public void delete(int id) {
//        var people = this.storages.get(0).findAll();
//        people.removeIf(person -> person.getId().equals(id));
//        this.saveAll(people);
//    }
//
//    public void save(Person person) {
//        this.storages.stream().forEach(s -> s.save(person));
//
//
//    }
//
//    public void saveIndex(){
//
//        var personList = this.findAll();
//        var map = new HashMap<Integer, String>();
//        personList.stream().forEach(person -> map.put(person.getId(), person.getLastname()));
//
//
//        ObjectOutputStream ous = null;
//        try {
//            ous = new ObjectOutputStream(new FileOutputStream("./Index.txt", true));
//            ous.writeObject(map);
//            ous.flush();
//            ous.close();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
//
//    }
//
//    public List<Person> findAll() {
//        return this.storages.get(0).findAll();
//
//    }
//
//    public String findByIndex(int id) {
//        ObjectInputStream ois = null;
//        HashMap<Integer, String> personMap;
//        try {
//            ois = new ObjectInputStream(new FileInputStream("./Index.txt"));
//            personMap =(HashMap<Integer, String>) ois.readObject();
//            ois.close();
//        } catch (IOException | ClassNotFoundException exception) {
//            exception.printStackTrace();
//            return null;
//        }
//        return personMap.get(id);
//    }

    public Person findByLastname(String lastname) {
        try {
            var stmt = connection.prepareStatement("SELECT * FROM Phonebook WHERE lastname LIKE CONCAT('%', ?, '%')");
            stmt.setString(1, lastname);

            var rs = stmt.executeQuery();
            while (rs.next()) {
                var person = new Person(rs.getInt("id"));
                person.setLastname(rs.getString("lastname"));
                person.setFirstname(rs.getString("firstname"));
                person.setAge(rs.getInt("age"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                return person;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public List<Person> findAll() {
        try {
            var stmt = connection.prepareStatement("SELECT * FROM phonebook");

            var people = new ArrayList<Person>();
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var person = new Person(rs.getInt("id"));
                person.setLastname(rs.getString("lastname"));
                person.setFirstname(rs.getString("firstname"));
                person.setAge(rs.getInt("age"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                people.add(person);
            }
            return people;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Collections.emptyList();
    }

    public Person find(Integer id) {
        try {
            var stmt = connection.prepareStatement("SELECT * FROM Phonebook WHERE id = ?");
            stmt.setInt(1, id);

            var rs = stmt.executeQuery();
            while (rs.next()) {
                var person = new Person(rs.getInt("id"));
                person.setLastname(rs.getString("lastname"));
                person.setFirstname(rs.getString("firstname"));
                person.setAge(rs.getInt("age"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                return person;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public void delete(int id) {
        try {
            var stmt = connection.prepareStatement("DELETE FROM Phonebook WHERE Id = ?");
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void save(Person person) {
        try {
            if (person.getId() != null) {
                var stmt = this.connection.prepareStatement(
                        "UPDATE Phonebook SET age = ?, firstname = ?, lastname = ?, address = ?, phone_number = ? WHERE id = ?"
                );
                stmt.setInt(1, person.getAge());
                stmt.setString(2, person.getFirstname());
                stmt.setString(3, person.getLastname());
                stmt.setString(4, person.getAddress());
                stmt.setString(5, person.getPhoneNumber());
                stmt.setInt(6, person.getId());
                stmt.execute();
            } else {
                var stmt = this.connection.prepareStatement(
                        "INSERT INTO Phonebook (firstname, lastname, age, phone_number, address) VALUES (?, ?, ?, ?, ?)"
                );
                stmt.setString(1, person.getFirstname());
                stmt.setString(2, person.getLastname());
                stmt.setInt(3, person.getAge());
                stmt.setString(4, person.getPhoneNumber());
                stmt.setString(5, person.getAddress());
                stmt.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
