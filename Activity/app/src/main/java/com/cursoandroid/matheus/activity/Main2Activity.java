package com.cursoandroid.matheus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView textNome, textIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textNome = findViewById(R.id.nome);
        textIdade = findViewById(R.id.idade);

        Bundle dados = getIntent().getExtras();

        Usuario user = (Usuario) dados.getSerializable("usuario");  //passar classe

        /*
        String nome = dados.getString("nome");
        int idade = dados.getInt("idade");

        textIdade.setText( String.valueOf(idade));
        textNome.setText( nome );

         */
        textIdade.setText( user.getEmail());
        textNome.setText(user.getNome() );

    }
}
