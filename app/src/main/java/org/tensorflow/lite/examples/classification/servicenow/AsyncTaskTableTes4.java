package org.tensorflow.lite.examples.classification.servicenow;

import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.Gson;

import org.tensorflow.lite.examples.classification.model.SensorDataObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

public class AsyncTaskTableTes4 extends AsyncTask<Void, Void, String> {

    private final String USER_AGENT = "Mozilla/5.0";
    //change this as per user
    private final String user = "@psu.edu";
    private final String pwd = "Penn";

    private final String urlString = "https://emplkasperpsu1.service-now.com/api/now/table/u_master_test_table";

    private final SensorDataObject dataObject;

    public AsyncTaskTableTes4(SensorDataObject dataObject){
        this.dataObject = dataObject;
    }

    @Override
    protected String doInBackground(Void... params) {
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection con2 = (HttpURLConnection) url.openConnection();


            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pwd.toCharArray());
                }
            });

            con2.setRequestMethod("POST");
            con2.setRequestProperty("User-Agent", USER_AGENT);
            con2.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con2.setRequestProperty("Content-Type","application/json");
            String postJsonData = new Gson().toJson(dataObject);
            //System.out.println("----"+postJsonData);
            // Send post request
            con2.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con2.getOutputStream());
            wr.writeBytes(postJsonData);
            wr.flush();
            wr.close();

            int responseCode = con2.getResponseCode();
            System.out.println("nSending 'POST' request to URL : " + url);
            System.out.println("Post Data : " + postJsonData);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con2.getInputStream()));
            StringBuffer response = new StringBuffer();

            String output;
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
            con2.disconnect();

            //printing result from response
            System.out.println("--- response from server:"+response.toString());

        } catch (Exception e) {
            Log.e(AsyncTaskTableTes4.class.getSimpleName(), e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

}
