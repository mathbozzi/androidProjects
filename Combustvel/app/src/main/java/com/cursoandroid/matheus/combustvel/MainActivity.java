package com.cursoandroid.matheus.combustvel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText precoGasolina, precoAlcool;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.precoAlcool = findViewById(R.id.alcool);
        this.precoGasolina = findViewById(R.id.gasolina);
        this.result = findViewById(R.id.resultado);

    }

    public void calcular(View view) {

        String precoAlcool1 = precoAlcool.getText().toString();
        String precogasosa = precoGasolina.getText().toString();

        if (validaCampos(precoAlcool1, precogasosa)) {
            calculaMelhorPreco(precoAlcool1, precogasosa);
        } else {
            result.setText("Preencha todos os campos!");
        }


    }

    public Boolean validaCampos(String pAlcool, String pGasolina) {
        Boolean campos = true;

        if (pAlcool == null || pAlcool.equals("")) {
            campos = false;
        }
        if (pGasolina == null || pGasolina.equals("")) {
            campos = false;
        }

        return campos;
    }


    public void calculaMelhorPreco(String pa, String pg) {
        Double precoA = Double.parseDouble(pa);
        Double precoG = Double.parseDouble(pg);


        if ((precoA / precoG) >= 0.7) {
            result.setText("É melhor utilizar a gasolina");
        } else {
            result.setText("É melhor utilizar a Alcool");
        }

    }
}