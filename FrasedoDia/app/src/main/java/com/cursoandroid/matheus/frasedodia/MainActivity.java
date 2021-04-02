package com.cursoandroid.matheus.frasedodia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gerarNovaFrase(View view){

        String[] frases = {"Minha fofinha","Liiiiiiiiiiiiiiiiiiiiinda",
                "EU TE AMO","Cabritinha do pé preto"};

        int x = new Random().nextInt(4);
        TextView frase = findViewById(R.id.fraseDoDia);
        frase.setText(frases[x]);
    }
}
