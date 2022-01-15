package webServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ApplicationDAO;
import entity.Application;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

public class SaveBootstrapHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var inputStream = exchange.getRequestBody();
        int c;
        var body = new StringBuilder();
        while ((c = inputStream.read()) != -1) {
            body.append((char) c);
        }
        var args = Arrays.stream(body.toString().split("&")).collect(Collectors.toList());
        var fields = new HashMap<String, String>();
        args.forEach(arg -> fields.put(arg.split("=")[0], arg.split("=")[1]));

        var properties = new Properties();
        Connection connection = getConnection(properties);

        var dao = new ApplicationDAO(connection);
        args.add(0, "");
        args.forEach(System.out::println);
        var application = new Application();
        application.setFirstname(fields.get("fname"));
        application.setLastname(fields.get("lname"));
        application.setAge(Integer.valueOf(fields.get("age")));
        application.setAddress(fields.get("address"));
        application.setStatus(Integer.valueOf(fields.get("status")));
        dao.save(application);
        var file = new File("./server/public/saveBootstrap.html");
        var response = Files.readAllBytes(Paths.get(file.getPath()));
        exchange.sendResponseHeaders(200, response.length);
        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    private static Connection getConnection(Properties properties) {
        Connection connection = null;
        try (var in = Files.newInputStream(Paths.get("server/src/main/resources/database.properties"))) {
            properties.load(in);

            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));


        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();

        }
        return connection;
    }
}
