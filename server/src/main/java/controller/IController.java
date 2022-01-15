package controller;

import java.sql.Connection;
import java.util.List;

public interface IController {
    void setDatabaseConnection(Connection connection);

    void process(List<String> arguments);
}
