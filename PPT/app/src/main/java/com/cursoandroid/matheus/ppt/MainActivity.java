package com.cursoandroid.matheus.ppt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selecionadoPedra(View view) {
        this.opcaoSelecionada("pedra");
    }

    public void selecionadoPapel(View view) {
        this.opcaoSelecionada("papel");
    }

    public void selecionadoTesoura(View view) {
        this.opcaoSelecionada("tesoura");
    }

    public void opcaoSelecionada(String opcaoSelecionada) {

        int x = new Random().nextInt(3);//0 = Pedra , 1 = Papel, 2 = Tesoura;
        TextView result = findViewById(R.id.resultado);
        ImageView imagem = findViewById(R.id.imagemResultado);

        if(opcaoSelecionada.equals("pedra")){
            if(x == 0){
                imagem.setImageResource(R.drawable.pedra);
                result.setText("Empate!");
            }
            else if(x == 1){
                imagem.setImageResource(R.drawable.papel);
                result.setText("Perdeu!");

            }else{
                imagem.setImageResource(R.drawable.tesoura);
                result.setText("Parabéns, você ganhou!");
            }
        }
        else if(opcaoSelecionada.equals("papel")) {
            if (x == 0) {
                imagem.setImageResource(R.drawable.pedra);
                result.setText("Parabéns, você ganhou!");
            } else if (x == 1) {
                imagem.setImageResource(R.drawable.papel);
                result.setText("Empate!");
            } else {
                imagem.setImageResource(R.drawable.tesoura);
                result.setText("Perdeu!");
            }
        }
        else if(opcaoSelecionada.equals("tesoura")) {
            if (x == 0) {
                imagem.setImageResource(R.drawable.pedra);
                result.setText("Perdeu!");
            } else if (x == 1) {
                imagem.setImageResource(R.drawable.papel);
                result.setText("Parabéns, você ganhou!");
            } else {
                imagem.setImageResource(R.drawable.tesoura);
                result.setText("Empate!");
            }
        }
    }

}
