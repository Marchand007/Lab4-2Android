package com.example.lab3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreboardActivity extends AppCompatActivity {

    String _pXname;
    String _pOname;
    int _pXScore;
    int _pOScore;
    int _nbNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        TextView pXname = findViewById(R.id.pXname);
        TextView pXscore = findViewById(R.id.pXscore);
        TextView pOname = findViewById(R.id.pOname);
        TextView pOscore = findViewById(R.id.pOscore);
        TextView nullscore = findViewById(R.id.nullscore);

        Intent intent = getIntent();

        _pXname = getIntent().getStringExtra("pXname");
        _pOname = intent.getStringExtra("pOname");


        pXname.setText(getIntent().getStringExtra("pXname"));
        pXscore.setText(getIntent().getStringExtra("pXscore"));
        pOname.setText(getIntent().getStringExtra("pOname"));
        pOscore.setText(getIntent().getStringExtra("pOscore"));
        nullscore.setText(getIntent().getStringExtra("nullscore"));

        Button btnretour = findViewById(R.id.btnretour);
        btnretour.setOnClickListener(btnOnClickListener);

    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}