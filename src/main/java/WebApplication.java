import com.sun.net.httpserver.HttpServer;
import webServer.*;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

public class WebApplication {
    public static void main(String[] args) {
        HttpServer server = null;
        URI oURL = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8081), 0);
            server.createContext("/application", new ApplicationHandler());
            server.createContext("/phonebook", new PhonebookHandler());
            server.createContext("/save" , new SaveHandler());
            server.createContext("/savebootstrap", new SaveBootstrapHandler());
            server.createContext("/index", new IndexHandler());
            server.createContext("/", new HomeHandler());
            oURL = new URI("http://localhost:8081/");
            Desktop.getDesktop().browse(oURL);
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }
        server.setExecutor(null); // creates a default executor

        System.out.println("Starting HTTP service on :8080...");
        server.start();
    }
}
