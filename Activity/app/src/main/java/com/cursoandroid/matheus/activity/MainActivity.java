package com.cursoandroid.matheus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button botaoEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoEnviar = findViewById(R.id.botaoEnviar);
        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);

                Usuario u1 = new Usuario("matheus","matheus.bozzi@hotmail.com");


                intent.putExtra("nome","cu de dia");
                intent.putExtra("idade", 23);
                intent.putExtra("usuario", u1);

                startActivity(intent);
            }
        });
    }
}
