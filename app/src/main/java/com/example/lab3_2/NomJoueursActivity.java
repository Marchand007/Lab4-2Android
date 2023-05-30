package com.example.lab3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NomJoueursActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.joueurs_layout);

        Button btnEnregistrer = findViewById(R.id.btnenregistrer);
        btnEnregistrer.setOnClickListener(btnOnClickListener);
        EditText pX = findViewById(R.id.inputpXname);
        EditText pO = findViewById(R.id.inputpOname);
        pX.setText(getIntent().getStringExtra("pXname"));
        pO.setText(getIntent().getStringExtra("pOname"));
    }


    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;

            EditText pX = findViewById(R.id.inputpXname);
            EditText pO = findViewById(R.id.inputpOname);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("pXname", pX.getText().toString());
            resultIntent.putExtra("pOname", pO.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    };
}
