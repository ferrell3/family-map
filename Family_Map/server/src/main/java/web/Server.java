package web;

import service.service;
import dto.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.*;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sun.net.httpserver.HttpExchange;



public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;
    private service s;

    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().init(portNumber);
    }

    public void init(String portNumber){
        s = new service();

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
        server.createContext("/event", new eventHandler());
        server.createContext("/fill", new fillHandler());
        server.createContext("/load", new loadHandler());
        server.createContext("/user/login", new loginHandler());
        server.createContext("/person", new personHandler());
        server.createContext("/user/register", new registerHandler());
        server.createContext("/", new rootHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");
    }

    class rootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange e) throws IOException {
            try {
                String absolutePath = "/users/ugrad/f/ferrell3/AndroidStudioProjects/Family_Map/web";
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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            response regResponse = new response();
            if(httpExchange.getRequestMethod().toLowerCase().equals("post"))
            {
                Reader read = new InputStreamReader(httpExchange.getRequestBody());
                registerRequest regReq = gson.fromJson(read, registerRequest.class);
                regResponse = s.register(regReq);
                read.close();
                if(regResponse.getMessage() == null)
                {
                    httpExchange.sendResponseHeaders(HTTP_OK, 0);
                }
                else
                {
                    httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                }
            }
            String gsonResp = gson.toJson(regResponse);
            PrintWriter pw = new PrintWriter(httpExchange.getResponseBody());
            pw.write(gsonResp);
            pw.close();
        }
    }


    class loginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Reader read = new InputStreamReader(httpExchange.getRequestBody());
            loginRequest logReq = gson.fromJson(read, loginRequest.class);
            response rsp = s.login(logReq);
            read.close();
            if(rsp.getMessage() == null)
            {
                httpExchange.sendResponseHeaders(HTTP_OK, 0);
            }
            else
            {
                httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            }
            String gsonResp = gson.toJson(rsp);
            PrintWriter pw = new PrintWriter(httpExchange.getResponseBody());
            pw.write(gsonResp);
            pw.close();
        }
    }


    class clearHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            response clrRsp = new response(s.clear());
            if(clrRsp.getMessage().equals("Successfully cleared the database.")) //success
            {
                httpExchange.sendResponseHeaders(HTTP_OK, 0);
            }
            else //error
            {
                httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            }
            String gsonResponse = gson.toJson(clrRsp);
            PrintWriter pw = new PrintWriter(httpExchange.getResponseBody());
            pw.write(gsonResponse);
            pw.close();
        }

    }

    class fillHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            response rsp;
            String uri = httpExchange.getRequestURI().toString();
            String[] split = uri.split("/");
            if(split.length == 3)
            {
                rsp = new response(s.fill(split[2]));
            }
            else if(split.length == 4)
            {
                rsp = new response(s.fill(split[2], Integer.parseInt(split[3])));
            }
            else
            {
                rsp = new response("Fill request failed: Invalid request");
            }


            if(rsp.getMessage().contains("failed"))
            {
                httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            }
            else
            {
                httpExchange.sendResponseHeaders(HTTP_OK, 0);
            }

            String gsonResponse = gson.toJson(rsp);
            PrintWriter pw = new PrintWriter(httpExchange.getResponseBody());
            pw.write(gsonResponse);
            pw.close();
        }

    }


    class loadHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Reader read = new InputStreamReader(httpExchange.getRequestBody());
            loadRequest req = gson.fromJson(read, loadRequest.class);
            response rsp = new response(s.load(req)); //set response message to the String returned by load service
            read.close();
            if(rsp.getMessage() == null)
            {
                httpExchange.sendResponseHeaders(HTTP_OK, 0);
            }
            else
            {
                httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            }
            String gsonResp = gson.toJson(rsp);
            PrintWriter pw = new PrintWriter(httpExchange.getResponseBody());
            pw.write(gsonResp);
            pw.close();
        }
    }

    class personHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            //services check authToken, personID, and user
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            personResponse rsp = new personResponse();

            String uri = httpExchange.getRequestURI().toString();
            String[] split = uri.split("/");
            Headers header = httpExchange.getRequestHeaders();

            if (header.containsKey("Authorization"))
            {
                if(split.length == 2) //all people
                {
                    peopleHandler pplH = new peopleHandler();
                    pplH.handle(httpExchange);
                }
                else if(split.length == 3) //single person
                {
                    String authToken = header.getFirst("Authorization");
                    rsp = s.personService(split[2], authToken);
                    if(rsp.getMessage() == null)
                    {
                        httpExchange.sendResponseHeaders(HTTP_OK, 0);
                    }
                    else
                    {
                        httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                    }
                    String gsonResp = gson.toJson(rsp);
                    //if I do combine with ppl, I can do these three outside
                    PrintWriter pw = new PrintWriter(httpExchange.getResponseBody());
                    pw.write(gsonResp);
                    pw.close();
                }
                else
                {
                    httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                    rsp.setMessage("Person request failed: Invalid request.");
                }
            }
            else
            {
                httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                rsp.setMessage("Person request failed: Invalid authorization.");
            }
        }
    }

    class peopleHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Headers header = httpExchange.getRequestHeaders();
            String authToken = header.getFirst("Authorization");
            peopleResponse rsp = s.peopleService(authToken);
            if(rsp.getMessage() == null)
            {
                httpExchange.sendResponseHeaders(HTTP_OK, 0);
            }
            else
            {
                httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            }
            String gsonResp = gson.toJson(rsp);
            PrintWriter pw = new PrintWriter(httpExchange.getResponseBody());
            pw.write(gsonResp);
            pw.close();
        }
    }

    class eventHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            //services check authToken, personID, and user
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            eventResponse rsp = new eventResponse();

            String uri = httpExchange.getRequestURI().toString();
            String[] split = uri.split("/");
            Headers header = httpExchange.getRequestHeaders();

            if (header.containsKey("Authorization"))
            {
                if(split.length == 2) //all events
                {
                    allEventsHandler allEH = new allEventsHandler();
                    allEH.handle(httpExchange);
                }
                else if(split.length == 3) //single person
                {
                    String authToken = header.getFirst("Authorization");
                    rsp = s.eventService(split[2], authToken);
                    if(rsp.getMessage() == null)
                    {
                        httpExchange.sendResponseHeaders(HTTP_OK, 0);
                    }
                    else
                    {
                        httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                    }
                    String gsonResp = gson.toJson(rsp);
                    //if I do combine with allEvents, I can do these three outside
                    PrintWriter pw = new PrintWriter(httpExchange.getResponseBody());
                    pw.write(gsonResp);
                    pw.close();
                }
                else
                {
                    httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                    rsp.setMessage("Event request failed: Invalid request.");
                }
            }
            else
            {
                httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                rsp.setMessage("Event request failed: Invalid authorization.");
            }
        }
    }

    class allEventsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Headers header = httpExchange.getRequestHeaders();
            String authToken = header.getFirst("Authorization");
            allEventsResponse rsp = s.allEventsService(authToken);
            if(rsp.getMessage() == null)
            {
                httpExchange.sendResponseHeaders(HTTP_OK, 0);
            }
            else
            {
                httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            }
            String gsonResp = gson.toJson(rsp);
            PrintWriter pw = new PrintWriter(httpExchange.getResponseBody());
            pw.write(gsonResp);
            pw.close();
        }

    }

}
