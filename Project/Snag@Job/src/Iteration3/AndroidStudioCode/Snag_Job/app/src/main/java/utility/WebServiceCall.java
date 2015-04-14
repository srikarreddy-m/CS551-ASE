package utility;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by SrikarReddy on 3/14/2015.
 */
public class WebServiceCall {
    public String readWCFServiceData(String URL) {
        System.out.println("Inside readJSON 1");
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            System.out.println("Inside TRY readJSON 2");
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Inside TRY WHILE readJSON 3");
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                System.out.println("status code not 200"+statusCode);
                Log.d("JSON", "status code not 200");
            }
        } catch (Exception e) {
            System.out.println("Failed to download file"+e);
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }
}
