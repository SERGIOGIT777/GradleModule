package webServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ApplicationDAO;
import entity.Application;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

public class ApplicationHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var file = new File("./server/public/application.html");
        var response = Files.readString(Paths.get(file.getPath()));
        var builder = new StringBuilder();
        Properties properties = new Properties();
        var dao = new ApplicationDAO(getConnection(properties));
        var method = exchange.getRequestMethod();
        if (method.equals("POST")) {
            var inputStream = exchange.getRequestBody();
            int c;
            var body = new StringBuilder();
            while ((c = inputStream.read()) != -1) {
                body.append((char) c);
            }
            var args = Arrays.stream(body.toString().split("&")).collect(Collectors.toList());
            var fields = new HashMap<String, String>();
            args.forEach(arg -> fields.put(arg.split("=")[0], arg.split("=")[1]));
            var application = new Application();
            application.setFirstname(fields.get("fname"));
            application.setLastname(fields.get("lname"));
            application.setAge(Integer.valueOf(fields.get("age")));
            application.setAddress(fields.get("address"));
            application.setStatus(Integer.valueOf(fields.get("status")));
            dao.save(application);
        }
        var list = dao.findAll();
        list.forEach(x -> {
            builder.append("<tr>");
            builder.append("<th scope=\"row\">" + x.getId() + "</th>");
            builder.append("<td>" + x.getFirstname() + "</td>");
            builder.append("<td>" + x.getLastname() + "</td>");
            builder.append("<td>" + x.getAge() + "</td>");
            builder.append("<td>" + x.getAddress() + "</td>");
            builder.append("<td>" + x.getStatus() + "</td>");
            builder.append("</tr>");
        });
        response = response.replace("{{content}}", builder.toString());
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
    }

    private Connection getConnection(Properties properties) {
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
