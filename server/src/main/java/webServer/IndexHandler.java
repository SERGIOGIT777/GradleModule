package webServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.PhonebookDAO;
import entity.Person;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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

public class IndexHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {

        System.out.println("Handling: " + t.getRequestURI());

        var file = new File("./server/public/index.html");
//        var response = Files.readAllBytes(Paths.get(file.getPath()));
        var response1 = Files.readString(Paths.get(file.getPath()));
        var builder = new StringBuilder();
        var properties = new Properties();
        var dao = new PhonebookDAO(getConnection(properties));
        var method = t.getRequestMethod();
        if (method.equals("POST")) {
            var inputStream = t.getRequestBody();
            int c;
            var body = new StringBuilder();
            while ((c = inputStream.read()) != -1) {
                body.append((char) c);
            }
            var args = Arrays.stream(body.toString().split("&")).collect(Collectors.toList());
            var fields = new HashMap<String, String>();
            args.forEach(arg -> fields.put(arg.split("=")[0], arg.split("=")[1]));
            var person = new Person();
            person.setFirstname(fields.get("fname"));
            person.setLastname(fields.get("lname"));
            person.setAge(Integer.parseInt(fields.get("age")));
            person.setPhoneNumber(fields.get("mob"));
            person.setAddress(fields.get("address"));
            dao.save(person);
        }
        var person = dao.findAll();
        person.forEach(x -> {
            builder.append("<tr class=\"table-dark\">");
            builder.append("<th scope=\"row\" class=\"bg-primary\">" + x.getId() + "</th>");
            builder.append("<td class=\"bg-primary\">" + x.getFirstname() + "</td>");
            builder.append("<td class=\"bg-primary\">" + x.getLastname() + "</td>");
            builder.append("<td class=\"bg-primary\">" + x.getAge() + "</td>");
            builder.append("<td class=\"bg-primary\">" + x.getPhoneNumber() + "</td>");
            builder.append("<td class=\"bg-primary\">" + x.getAddress() + "</td>");
            builder.append("</tr>");
        });
        builder.append("</table>");
        var response = (response1.replace("{{content}}", builder.toString())).getBytes(StandardCharsets.UTF_8);
        t.sendResponseHeaders(200, response.length);
        OutputStream os = t.getResponseBody();
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
