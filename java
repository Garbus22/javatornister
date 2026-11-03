package com.example.maciek;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editTextDiceNumber;
    private Button drawButton;
    private Button buttonUp;
    private Button buttonDown;
    private GridLayout gridDices;
    private TextView textViewResults;

    private final List<Integer> wyniki = new ArrayList<>();
    private final Random losowanieRzutu = new Random();

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

        editTextDiceNumber = findViewById(R.id.editTextDiceNumber);
        drawButton = findViewById(R.id.drawButton);
        buttonUp = findViewById(R.id.buttonUp);
        buttonDown = findViewById(R.id.buttonDown);
        gridDices = findViewById(R.id.gridDices);
        textViewResults = findViewById(R.id.textViewResults);

        drawButton.setOnClickListener(v -> losuj());

        buttonUp.setOnClickListener(v -> {
            if (wyniki.isEmpty()) {
                Toast.makeText(this, "Najpierw wylosuj kostki", Toast.LENGTH_SHORT).show();
            } else {
                sortujRosnaco();
                odswiezWidok();
            }
        });

        buttonDown.setOnClickListener(v -> {
            if (wyniki.isEmpty()) {
                Toast.makeText(this, "Najpierw wylosuj kostki", Toast.LENGTH_SHORT).show();
            } else {
                sortujMalejaco();
                odswiezWidok();
            }
        });
    }

    private void losuj() {
        int wpisanaLiczba;

        try {
            wpisanaLiczba = Integer.parseInt(editTextDiceNumber.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Podaj poprawną liczbę kostek", Toast.LENGTH_SHORT).show();
            return;
        }

        if (wpisanaLiczba <= 0) {
            Toast.makeText(this, "Podaj poprawną liczbę kostek", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (wpisanaLiczba > 100) {
            Toast.makeText(this, "Maksymalnie 100 kostek", Toast.LENGTH_SHORT).show();
            return;
        }

        wyniki.clear();

        for (int i = 0; i < wpisanaLiczba; i++) {
            wyniki.add(losowanieRzutu.nextInt(6) + 1);
        }

        odswiezWidok();
    }

    private void sortujRosnaco() {
        int rozmiarListy = wyniki.size();
        for (int i = 0; i < rozmiarListy - 1; i++) {
            for (int j = 0; j < rozmiarListy - 1 - i; j++) {
                if (wyniki.get(j) > wyniki.get(j + 1)) {
                    int bufor = wyniki.get(j);
                    wyniki.set(j, wyniki.get(j + 1));
                    wyniki.set(j + 1, bufor);
                }
            }
        }
    }

    private void sortujMalejaco() {
        int rozmiarListy = wyniki.size();
        for (int i = 0; i < rozmiarListy - 1; i++) {
            for (int j = 0; j < rozmiarListy - 1 - i; j++) {
                if (wyniki.get(j) < wyniki.get(j + 1)) {
                    int bufor = wyniki.get(j);
                    wyniki.set(j, wyniki.get(j + 1));
                    wyniki.set(j + 1, bufor);
                }
            }
        }
    }

    private void odswiezWidok() {
        StringBuilder tekstWynikow = new StringBuilder();

        for (int i = 0; i < wyniki.size(); i++) {
            if (i > 0) {
                tekstWynikow.append(", ");
            }
            tekstWynikow.append(wyniki.get(i));
        }
        textViewResults.setText(tekstWynikow.toString());

        gridDices.removeAllViews();
        gridDices.setColumnCount(5);

        int szerokoscPiksele = (int) (56 * getResources().getDisplayMetrics().density);

        for (int oczka : wyniki) {
            TextView pojedynczaKostka = new TextView(this);

            pojedynczaKostka.setText(String.valueOf(oczka));
            pojedynczaKostka.setTextSize(24f);
            pojedynczaKostka.setGravity(Gravity.CENTER);
            pojedynczaKostka.setTextColor(0xFF000000);
            pojedynczaKostka.setBackgroundColor(0xFFE0E0E0);

            GridLayout.LayoutParams parametry = new GridLayout.LayoutParams();
            parametry.width = szerokoscPiksele;
            parametry.height = szerokoscPiksele;

            pojedynczaKostka.setLayoutParams(parametry);
            gridDices.addView(pojedynczaKostka);
        }
    }
}
