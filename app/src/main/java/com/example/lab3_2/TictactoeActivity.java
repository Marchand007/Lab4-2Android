package com.example.lab3_2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

public class TictactoeActivity extends AppCompatActivity {

    String _pXname;
    String _pOname;
    int _pXScore;
    int _pOScore;
    int _nbNull;
    String _tour = "";
    String _tourname = "";
    Boolean gameEnCours = true;
    Vector<Button> _lstButtonsJeu;

    ActivityResultLauncher<Intent> joueursGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    TextView status = findViewById(R.id.status);
                    Intent data = result.getData();
                    _pXname = data.getStringExtra("pXname");
                    _pOname = data.getStringExtra("pOname");
                    if (gameEnCours) {
                        if (_tour == "X") {
                            _tourname = _pXname;
                            status.setText("Cest au tour de " + _tourname + " a jouer");
                        } else {
                            _tourname = _pOname;
                            status.setText("Cest au tour de " + _tourname + " a jouer");
                        }
                    } else {
                        if (_tour == "X") {
                            _tourname = _pXname;
                            status.setText(_tourname + " a gagner");
                        } else {
                            _tourname = _pOname;
                            status.setText(_tourname + " a gagner");
                        }
                    }
                }
            });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater monMenu = getMenuInflater();
        monMenu.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public void MenuClick(MenuItem menu) {
        int itemId = menu.getItemId();
        if (itemId == R.id.menuChangePlayers) {
            Intent intent = new Intent(TictactoeActivity.this, NomJoueursActivity.class);
            intent.putExtra("pXname", _pXname);
            intent.putExtra("pOname", _pOname);
            joueursGetContent.launch(intent);
        } else if (itemId == R.id.menuScoreboard) {
            Intent intent = new Intent(TictactoeActivity.this, ScoreboardActivity.class);
            intent.putExtra("pXname", _pXname);
            intent.putExtra("pOname", _pOname);
            intent.putExtra("pXscore", Integer.toString(_pXScore));
            intent.putExtra("pOscore", Integer.toString(_pOScore));
            intent.putExtra("nullscore", Integer.toString(_nbNull));
            startActivity(intent);
        } else if (itemId == R.id.menuRecommencer) {
            recommencer();
        } else if (itemId == R.id.menuQuitter) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_layout);
        _pXname = "Joueur X";
        _pOname = "Joueur O";
        _pXScore = 0;
        _pOScore = 0;
        _nbNull = 0;
        _tour = "X";
        _tourname = _pXname;
        _lstButtonsJeu = new Vector<Button>();
        TextView status = findViewById(R.id.status);
        status.setText("Cest au tour de " + _tourname + " a jouer");
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        _lstButtonsJeu.add(btn1);
        _lstButtonsJeu.add(btn2);
        _lstButtonsJeu.add(btn3);
        _lstButtonsJeu.add(btn4);
        _lstButtonsJeu.add(btn5);
        _lstButtonsJeu.add(btn6);
        _lstButtonsJeu.add(btn7);
        _lstButtonsJeu.add(btn8);
        _lstButtonsJeu.add(btn9);
        for (Button b : _lstButtonsJeu) {
            b.setOnClickListener(btnOnClickListener);
        }

    }

    public void recommencer() {
        resetAllButtons();
        changeColorAllButtons(R.color.lightgrey);
        gameEnCours = true;
        _tour = "X";
        _tourname = _pXname;
    }

    ;

    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            TextView status = findViewById(R.id.status);

            btn.setText(_tour);

            if (verifierGagnant()) {
                status.setText(_tourname + " a gagner");
                Toast.makeText(getApplicationContext(), _tour + " a gagner",
                        Toast.LENGTH_LONG).show();
                desactiveAllButtons();
                if (_tour.equals("X")) {
                    _pXScore++;
                } else {
                    _pOScore++;
                }
                final MediaPlayer mp = MediaPlayer.create(TictactoeActivity.this, R.raw.winner);
                mp.start();
                gameEnCours = false;
            }
            if (gameEnCours && verifierNull()) {
                Toast.makeText(getApplicationContext(), "MATCH NUL",
                        Toast.LENGTH_LONG).show();
                status.setText("Match Nul");
                changeColorAllButtons(R.color.red);
                desactiveAllButtons();
                gameEnCours = false;
            }
            if (gameEnCours && _tour == "X") {
                _tour = "O";
                _tourname = _pOname;
                btn.setEnabled(false);
                status.setText("Cest au tour de " + _tourname + " a jouer");
                final MediaPlayer mp = MediaPlayer.create(TictactoeActivity.this, R.raw.checked);
                mp.start();

            } else if (gameEnCours && _tour == "O") {
                _tour = "X";
                _tourname = _pXname;
                btn.setEnabled(false);
                status.setText("Cest au tour de " + _tourname + " a jouer");
                final MediaPlayer mp = MediaPlayer.create(TictactoeActivity.this, R.raw.checked);
                mp.start();
            }
        }
    };

    void desactiveAllButtons() {
        for (Button b : _lstButtonsJeu) {
            b.setEnabled(false);
        }

    }

    void resetAllButtons() {
        for (Button b : _lstButtonsJeu) {
            b.setText("");
            b.setEnabled(true);
        }
    }

    void changeColorAllButtons(int color) {
        for (Button b : _lstButtonsJeu) {
            b.setBackgroundColor(getColor(color));
        }
    }

    Boolean verifierGagnant() {
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        if (btn1.getText().equals(btn2.getText()) && btn2.getText().equals(btn3.getText()) && btn1.getText() == _tour) {
            btn1.setBackgroundColor(getColor(R.color.green));
            btn2.setBackgroundColor(getColor(R.color.green));
            btn3.setBackgroundColor(getColor(R.color.green));
            return true;
        }
        if (btn4.getText() == btn5.getText() && btn5.getText() == btn6.getText() && btn4.getText() == _tour) {
            btn4.setBackgroundColor(getColor(R.color.green));
            btn5.setBackgroundColor(getColor(R.color.green));
            btn6.setBackgroundColor(getColor(R.color.green));
            return true;
        }
        if (btn7.getText() == btn8.getText() && btn8.getText() == btn9.getText() && btn7.getText() == _tour) {
            btn7.setBackgroundColor(getColor(R.color.green));
            btn8.setBackgroundColor(getColor(R.color.green));
            btn9.setBackgroundColor(getColor(R.color.green));
            return true;
        }
        if (btn1.getText() == btn4.getText() && btn4.getText() == btn7.getText() && btn1.getText() == _tour) {
            btn1.setBackgroundColor(getColor(R.color.green));
            btn4.setBackgroundColor(getColor(R.color.green));
            btn7.setBackgroundColor(getColor(R.color.green));
            return true;
        }
        if (btn2.getText() == btn5.getText() && btn5.getText() == btn8.getText() && btn2.getText() == _tour) {
            btn2.setBackgroundColor(getColor(R.color.green));
            btn5.setBackgroundColor(getColor(R.color.green));
            btn8.setBackgroundColor(getColor(R.color.green));
            return true;
        }
        if (btn3.getText() == btn6.getText() && btn6.getText() == btn9.getText() && btn3.getText() == _tour) {
            btn3.setBackgroundColor(getColor(R.color.green));
            btn6.setBackgroundColor(getColor(R.color.green));
            btn9.setBackgroundColor(getColor(R.color.green));
            return true;
        }
        if (btn1.getText() == btn5.getText() && btn5.getText() == btn9.getText() && btn1.getText() == _tour) {
            btn1.setBackgroundColor(getColor(R.color.green));
            btn5.setBackgroundColor(getColor(R.color.green));
            btn9.setBackgroundColor(getColor(R.color.green));
            return true;
        }
        if (btn3.getText() == btn5.getText() && btn5.getText() == btn7.getText() && btn3.getText() == _tour) {
            btn3.setBackgroundColor(getColor(R.color.green));
            btn5.setBackgroundColor(getColor(R.color.green));
            btn7.setBackgroundColor(getColor(R.color.green));
            return true;
        }
        return false;
    }

    Boolean verifierNull() {
        for (Button b : _lstButtonsJeu) {
            if (b.getText().equals("")) {
                return false;
            }
        }
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.nullgame);
        mp.start();
        _nbNull++;
        return true;
    }
}