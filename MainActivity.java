package com.example.maciekpro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText imie;
    private EditText nazwisko;
    private EditText email;
    private EditText haslo;
    private Button przeslij;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imie = findViewById(R.id.imie);
        nazwisko = findViewById(R.id.nazwisko);
        email = findViewById(R.id.mail);
        haslo = findViewById(R.id.haslo);
        przeslij = findViewById(R.id.rejestracja);

        przeslij.setOnClickListener(v -> {
            String imie_tekst = imie.getText().toString();
            String nazwisko_tekst = nazwisko.getText().toString();
            String email_tekst = email.getText().toString();
            String haslo_tekst = haslo.getText().toString();
            if(sprawdzPustosc(imie_tekst) && sprawdzPustosc(nazwisko_tekst) && sprawdzEmail(email_tekst) && sprawdzHaslo(haslo_tekst)) {
                Toast.makeText(this, "utworzno konto", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "blad", Toast.LENGTH_LONG).show();
            }
        });

    }
    public boolean sprawdzPustosc(String element) {
        int dlugosc = element.length();
        if (dlugosc > 0) {
            if (element.equals("") == false) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public boolean sprawdzEmail(String element) {
        boolean malpa = false;
        boolean kropka = false;
        if (element.contains("@")) {
            malpa = true;
        }
        if (element.contains(".")) {
            kropka = true;
        }
        if (malpa == true && kropka == true) {
            return true;
        } else {
            return false;
        }
    }
    public boolean sprawdzHaslo(String element) {
        boolean dlugoscOk = false;
        if (element.length() >= 8) {
            dlugoscOk = true;
        }
        boolean malaLitera = false;
        if (element.matches(".*[a-z].*") == true) {
            malaLitera = true;
        }
        boolean duzaLitera = false;
        if (element.matches(".*[A-Z].*") == true) {
            duzaLitera = true;
        }
        boolean znakSpecjalny = false;
        if (element.matches(".*[^a-zA-Z0-9].*") == true) {
            znakSpecjalny = true;
        }
        if (dlugoscOk == true) {
            if (malaLitera == true) {
                if (duzaLitera == true) {
                    if (znakSpecjalny == true) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
