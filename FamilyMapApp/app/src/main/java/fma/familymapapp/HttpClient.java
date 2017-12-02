package fma.familymapapp;

import android.util.Log;

import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static android.R.id.message;
import static java.net.HttpURLConnection.HTTP_OK;

public class HttpClient {

    public String getUrl(String urlString) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HTTP_OK) {

                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    out.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = out.toString();
                return responseBodyData;

            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }

        return null;

    }

    public String get(String urlAddress, String authToken)
    {
        //Response response = new Response();
        String response;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HttpURLConnection conn;
        try {
            URL url = new URL(urlAddress);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(false);
            conn.setRequestProperty("Authorization", authToken);
            conn.connect();
            response = getResponseBody(conn);
        }catch (Exception e) {
            e.printStackTrace();
            response = "Login Failed";
        }

        return response;
    }

    public Response post(String urlAddress, String reqData)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Response response = new Response();
        try {
            System.out.println(urlAddress);
            URL url = new URL(urlAddress);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();

            OutputStream reqBody = conn.getOutputStream();

            OutputStreamWriter sw = new OutputStreamWriter(reqBody);
            sw.write(reqData);
            sw.flush();

            reqBody.close();

            if(conn.getResponseCode() == HTTP_OK)
            {
                System.out.println("Success.");
            }
            else
            {
                System.out.println("ERROR: " + conn.getResponseMessage());
            }

            //final InputStream in = conn.getInputStream();
            Reader read = new InputStreamReader(conn.getInputStream());
            //String httpResponse = read(in);
            response = gson.fromJson(read, Response.class);
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Login Failed");
        }
        return response;
    }

    private static String read(final InputStream in) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
        return out.toString();
    }


    String getResponseBody(HttpURLConnection connection) throws Exception {

        String response = "";


        int status = connection.getResponseCode();
        if (status == HTTP_OK) {

//            System.out.println("response body:");
            Scanner in = new Scanner(connection.getInputStream());
            while (in.hasNextLine()) {
                String line = in.nextLine();
                response += line + "\n";
//                System.out.println(line);
            }
            in.close();

        }

//        System.out.println();

        return response;

    }
}