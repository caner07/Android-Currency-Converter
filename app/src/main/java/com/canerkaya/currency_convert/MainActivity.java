package com.canerkaya.currency_convert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView cadText;
    TextView chfText;
    TextView usdText;
    TextView tryText;
    TextView jpyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cadText=findViewById(R.id.cadText);
        chfText=findViewById(R.id.chfText);
        usdText=findViewById(R.id.usdText);
        tryText=findViewById(R.id.tryText);
        jpyText=findViewById(R.id.jpyText);
    }
    public void getRates(View view){
        DownloadData downloadData=new DownloadData();

        try {
            String url="http://data.fixer.io/api/latest?access_key=d3ca6ebcfdecbb2f0014cfefb22a99a5&format=1";
            downloadData.execute(url);
        }catch (Exception e){

        }
    }

    private class DownloadData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String result="";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url=new URL(strings[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                int data=inputStreamReader.read();
                while (data>0){
                    char character=(char)data;
                    result+=character;
                    data=inputStreamReader.read();
                }

            }catch (Exception e){
                return null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                String rates=jsonObject.getString("rates");
                JSONObject jsonObject1=new JSONObject(rates);
                String turkishLira= jsonObject1.getString("TRY");
                String cad=jsonObject1.getString("CAD");
                String chf=jsonObject1.getString("CHF");
                String usd=jsonObject1.getString("USD");
                String jpy=jsonObject1.getString("JPY");
                tryText.setText("TRY : " + turkishLira);
                cadText.setText("CAD : "+cad);
                chfText.setText("CHF : "+chf);
                usdText.setText("USD : "+usd);
                jpyText.setText("JPY : "+jpy);


            }catch (Exception e){

            }
        }
    }
}
