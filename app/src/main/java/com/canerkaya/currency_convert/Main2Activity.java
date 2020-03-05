package com.canerkaya.currency_convert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {
    TextView usdText;
    TextView euroText;
    TextView jpyText;
    TextView gbpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        usdText=findViewById(R.id.textView2);
        euroText=findViewById(R.id.textView3);
        jpyText=findViewById(R.id.textView4);
        gbpText=findViewById(R.id.textView5);
        DownloadData downloadData=new DownloadData();
        try {
            String url="http://data.fixer.io/api/latest?access_key=d3ca6ebcfdecbb2f0014cfefb22a99a5&format=1";
            downloadData.execute(url);
        }catch (Exception e){

        }
    }
    public void change(View view){
        Intent intent=new Intent(Main2Activity.this,MainActivity.class);
        finish();
        startActivity(intent);
    }
    private class DownloadData extends AsyncTask<String,Void,String> {

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
                float euro=Float.parseFloat(jsonObject1.getString("TRY"));
                float usd=euro/Float.parseFloat(jsonObject1.getString("USD"));
                float jpy=euro/Float.parseFloat(jsonObject1.getString("JPY"));
                float gbp=euro/Float.parseFloat(jsonObject1.getString("GBP"));

                usdText.setText(usd+" Amerikan Doları");
                euroText.setText(euro+" Euro");
                jpyText.setText(jpy+" Japon Yeni");
                gbpText.setText(gbp+" İngiliz Sterlini");




            }catch (Exception e){

            }
        }
    }
}
