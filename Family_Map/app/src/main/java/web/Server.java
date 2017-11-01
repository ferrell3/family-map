package web;

import service.service;

import com.sun.net.httpserver.*;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;

import java.io.IOException;
import java.net.*;
import com.sun.net.httpserver.HttpExchange;



public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    public void init(String portNumber){
        service s = new service();

        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        }catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");
        server.createContext("/clear", new clearHandler());
    }

    class rootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange e) throws IOException {
            try {
                String absolutePath = "/users/ugrad/f/ferrell3/AndroidStudioProjects/Family_Map";
                String relativePath = e.getRequestURI().toString();
                String filePathStr = null;
                if(relativePath.equals("/"))
                {
                    filePathStr = absolutePath + "/index.html";
                }
                else
                {
                    filePathStr = absolutePath + relativePath;
                }
                e.sendResponseHeaders(HTTP_OK, 0);
                Path filePath = FileSystems.getDefault().getPath(filePathStr);
                Files.copy(filePath, e.getResponseBody());

                e.getResponseBody().close();
            }catch (Exception error)
            {
                error.printStackTrace();
                e.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0);
            }
        }
    }


    class registerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

        }
    }


    class loginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

        }
    }


    class clearHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

        }

    }

    class fillHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

        }

    }


    class loadHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

        }
    }

    class personHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

        }

    }

    class peopleHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

        }

    }

    class eventHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

        }

    }

    class allEventsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

        }

    }

}
