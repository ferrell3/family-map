package fma.familymapapp;

import android.util.Log;

import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.id.message;

public class HttpClient {

    public String getUrl(String urlString) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

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

    public void post(String urlAdress, Login login) //change to String gson instead of login so I can post anything
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            System.out.println(urlAdress);
            URL url = new URL(urlAdress);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();

            String reqData = gson.toJson(login);

            OutputStream reqBody = conn.getOutputStream();

            OutputStreamWriter sw = new OutputStreamWriter(reqBody);
            sw.write(reqData);
            sw.flush();

            reqBody.close();

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                System.out.println("Success.");
            }
            else
            {
                System.out.println("ERROR: " + conn.getResponseMessage());
            }

//            JSONObject jsonParam = new JSONObject();
//            jsonParam.put("username", login.getUsername());
//            jsonParam.put("password", login.getPassword());
//
//            Log.i("JSON", jsonParam.toString());
//            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//            os.writeBytes(jsonParam.toString());
//
//            os.flush();
//            os.close();

//            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
//            Log.i("MSG" , conn.getResponseMessage());

//            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}