import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpexchange) throws IOException {
        //Code for what happens at the server, here we simply print
        //the request message
        InputStream inStream = httpexchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String data = scanner.nextLine();
        System.out.println(data);
    }
}
