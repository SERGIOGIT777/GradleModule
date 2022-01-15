package dao;


import entity.Application;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationDAO {
    private Connection connection;

    public ApplicationDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Application> findAll() {
        try {
            var applications = new ArrayList<Application>();
            var stmt = this.connection.createStatement();
            var rs = stmt.executeQuery("SELECT * FROM application");
            while (rs.next()) {
                var application = new Application();
                application.setId(rs.getInt("id"));
                application.setAge(rs.getInt("age"));
                application.setFirstname(rs.getString("firstname"));
                application.setLastname(rs.getString("lastname"));
                application.setAddress(rs.getString("address"));
                application.setStatus(rs.getInt("status"));
                applications.add(application);
            }
            return applications;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void save(Application application) {
        try {
            if (application.getId() != null) {
                var stmt = this.connection.prepareStatement(
                        "UPDATE Application SET firstname = ?, lastname = ?, age = ?, address = ?, status = ? WHERE id = ?"
                );
                stmt.setString(1, application.getFirstname());
                stmt.setString(2, application.getLastname());
                stmt.setInt(3, application.getAge());
                stmt.setString(4, application.getAddress());
                stmt.setInt(5, application.getStatus());
                stmt.setInt(6, application.getId());
                stmt.execute();
            } else {
                var stmt = this.connection.prepareStatement(
                        "INSERT INTO Application (firstname, lastname, age, address, status) VALUES (?, ?, ?, ?, ?)"
                );
                stmt.setString(1, application.getFirstname());
                stmt.setString(2, application.getLastname());
                stmt.setInt(3, application.getAge());
                stmt.setString(4, application.getAddress());
                stmt.setInt(5, application.getStatus());
                stmt.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteFile() {
        new File("./applications.txt").delete();
    }
}
