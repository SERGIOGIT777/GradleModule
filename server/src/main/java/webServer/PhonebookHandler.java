package webServer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.PhonebookController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class PhonebookHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        var phonebookController = new PhonebookController();
        var list = t.getRequestBody();
        List<String> args = mapper.readValue(list, new TypeReference<List<String>>(){});
//        var list = new LinkedList<String>();
//        list.add("generate");
//        list.add("vasia");
//        list.add("pupkin");
//        list.add("20");
//        list.add("2020");
//        list.add("Minsk");


        var output = new ByteArrayOutputStream();
        mapper.writeValue(output, null);

        System.out.println("Handling: " + t.getRequestURI());

        t.sendResponseHeaders(200, output.size());
        OutputStream os = t.getResponseBody();
        os.write(output.toByteArray());
        os.close();
    }
}
