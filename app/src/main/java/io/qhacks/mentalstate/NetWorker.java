package io.qhacks.mentalstate;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class NetWorker extends AsyncTask<String, Void, Void> {

    private static final String url = "http://www.brainassist.org/sendstate.php";

    private int type;

    public void sendResponse(int type){
        this.type = type;
        this.execute(url);
    }

    @Override
    protected Void doInBackground(String[] params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            //see http://stackoverflow.com/a/4206094/1896516
            String postDataStr = "user=0&type=" + type;
            byte[] postData = postDataStr.getBytes(StandardCharsets.UTF_8);
            con.setRequestProperty( "Content-Length", Integer.toString( postData.length ));

            try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
                wr.write( postData );
            }

            con.connect();
            int response = con.getResponseCode();
            Log.d("Net", Integer.toString(response));
            if(response >= 300){
                con.disconnect();
            }
            InputStreamReader is = new InputStreamReader(con.getInputStream());
            BufferedReader reader = new BufferedReader(is);
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null; ) {
                builder.append(line);
            }
            con.disconnect();
            reader.close();
            is.close();

            Log.d("Net", builder.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
