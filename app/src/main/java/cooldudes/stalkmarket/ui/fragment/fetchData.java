package cooldudes.stalkmarket.ui.fragment;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cooldudes.stalkmarket.R;
import cooldudes.stalkmarket.ui.activity.MainActivity;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    private TextView crying;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.twelvedata.com/ad?symbol=AAPL&interval=1min&apikey=02fae515b0b34baabaaa5f4c0f343f7a");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));
            String line = "";
            while(line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA = new JSONArray(data);
            JSONArray JAA = (JSONArray) JA.get(1);
            JSONObject JO = (JSONObject) JAA.get(1);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        GardenFragment.data.setText(this.dataParsed);
    }
}
