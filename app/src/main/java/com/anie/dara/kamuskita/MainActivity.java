package com.anie.dara.kamuskita;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anie.dara.kamuskita.db.DoengHelper;
import com.anie.dara.kamuskita.db.KamusHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        load = findViewById(R.id.loading);
        LoadData load = new LoadData();
        load.execute();
    }
    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        DoengHelper doengHelper;
        int status =0;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(MainActivity.this);
            doengHelper = new DoengHelper(MainActivity.this);
        }


        @Override
        protected Void doInBackground(Void... params) {
             ArrayList<kamus> kamusItem = preLoadEngdo();
            ArrayList<kamus> kamusItem2 = preLoadDoeng();


            //data Doeng
            status=2;
            doengHelper.open();
            progress = 10;
            publishProgress((int) progress);
            Double progressMaxInsert2 = 80.0;
            Double progressDiff2 = (progressMaxInsert2 - progress) / kamusItem.size();
            doengHelper.beginTransaction();

            try {
                for (kamus item : kamusItem2) {
                    boolean tfr = doengHelper.insertdata(item.getKeyword(),item.getArti());
                    progress += progressDiff2;
                    publishProgress((int) progress);
                }
                doengHelper.setTransactionSuccess();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            doengHelper.endTransaction();
            doengHelper.close();

            //data Engdo
            status = 1;
            kamusHelper.open();
            progress = 10;
            publishProgress((int) progress);
            Double progressMaxInsert = 80.0;
            Double progressDiff = (progressMaxInsert - progress) / kamusItem.size();
            kamusHelper.beginTransaction();

            try {
                for (kamus model : kamusItem) {
                    boolean trt = kamusHelper.insertdata(model.getKeyword(),model.getArti());
                    progress += progressDiff;
                    publishProgress((int) progress);
                }
                kamusHelper.setTransactionSuccess();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            kamusHelper.endTransaction();
            kamusHelper.close();




            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            publishProgress((int) maxprogress);
            Intent i = new Intent(MainActivity.this, KamusActivity.class);
            startActivity(i);
            finish();
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            String loadingDoeng = String.format(getString(R.string.loadingIndoEng));
            String loaddingEngdo = String.format(getString(R.string.loadingEngDo));
           if(status == 1){
               load.setText(loaddingEngdo);
           }else{
               load.setText(loadingDoeng);
           }


            progressBar.setProgress(values[0]);
        }
    }


    public ArrayList<kamus> preLoadEngdo() {
        ArrayList<kamus> kamusList = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;

            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                String kata2="";
                for(int i = 1; i<splitstr.length;i++){
                    kata2 = kata2+splitstr[i];
                }
                kamus kamus = new kamus(splitstr[0], kata2);
                kamusList.add(kamus);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusList;
    }

    public ArrayList<kamus> preLoadDoeng() {
        ArrayList<kamus> kamusList = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.indonesia_english);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;

            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                String kata2="";
                for(int i = 1; i<splitstr.length;i++){
                    kata2 = kata2+splitstr[i];
                }
                kamus kamus = new kamus(splitstr[0], kata2);
                kamusList.add(kamus);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusList;
    }


}
