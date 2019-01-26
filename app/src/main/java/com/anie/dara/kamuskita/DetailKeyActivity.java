package com.anie.dara.kamuskita;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.anie.dara.kamuskita.db.KamusHelper;

import java.util.ArrayList;

public class    DetailKeyActivity extends AppCompatActivity {

    TextView key, arti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_key);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        key = findViewById(R.id.Keyword_detail);
        arti = findViewById(R.id.detail_arti);

        Intent detailIntent = getIntent();
        if(null != detailIntent) {
            kamus kamusItem = detailIntent.getParcelableExtra("data_kamus");
            key.setText(kamusItem.getKeyword());
            arti.setText(kamusItem.getArti());
       }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
