package com.canerkaya.currency_convert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView gbpText;
    TextView usdText;
    TextView euroText;
    TextView jpyText;
    EditText edit;
    float turkishlira;
    float usd;
    float jpy;
    float euro;
    float gbp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gbpText =findViewById(R.id.gbpText);
        usdText=findViewById(R.id.usdText);
        euroText =findViewById(R.id.euroText);
        jpyText=findViewById(R.id.jpyText);
        edit=findViewById(R.id.editText);
    }
    public void getRates(View view){
        DownloadData downloadData=new DownloadData();

        try {
            String url="http://data.fixer.io/api/latest?access_key=d3ca6ebcfdecbb2f0014cfefb22a99a5&format=1";
            downloadData.execute(url);
        }catch (Exception e){

        }
    }
    public void change(View view){
        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        finish();
        startActivity(intent);
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
                float times=Float.parseFloat(edit.getText().toString());
                if (edit.getText().toString()==""){
                    times=1;
                }
                turkishlira=Float.parseFloat(jsonObject1.getString("TRY"));
                euro=Float.parseFloat(jsonObject1.getString("EUR"))/turkishlira;
                jpy=Float.parseFloat(jsonObject1.getString("JPY"))/turkishlira;
                usd=Float.parseFloat(jsonObject1.getString("USD"))/turkishlira;
                gbp=Float.parseFloat(jsonObject1.getString("GBP"))/turkishlira;

                usdText.setText("USD (Amerikan Doları) : "+(usd*times));
                jpyText.setText("Japon Yeni : "+(jpy*times));
                euroText.setText("EURO : "+(euro*times));
                gbpText.setText("İngiliz Sterlini : "+(gbp*times));


            }catch (Exception e){

            }
        }
    }
}
