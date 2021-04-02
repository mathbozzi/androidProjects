package com.cursoandroid.matheus.classemetodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private TextView resultado;
    private TextInputEditText email;
    private CheckBox cor_verde, cor_branco, cor_vermelho;
    private RadioButton sexomasculino, sexofeminino;
    private RadioGroup opcaoSexo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.nome = findViewById(R.id.nome);
        this.email = findViewById(R.id.email);
        this.resultado = findViewById(R.id.resultado);
        this.cor_branco = findViewById(R.id.branco);
        this.cor_verde = findViewById(R.id.verde);
        this.cor_vermelho = findViewById(R.id.vermelho);
        this.sexomasculino = findViewById(R.id.masculino);
        this.sexofeminino = findViewById(R.id.feminino);
        this.opcaoSexo = findViewById(R.id.sexo_group);
        radiobutton();
    }

    public void enviar(View view) {

        //  String nome2 = this.nome.getText().toString();
        //  String email2 = this.email.getText().toString();
        //  resultado.setText("nome: " + nome2 + " email: " + email2 + "\n");

        //checkbox();

      //  radiobutton();

    }

    public void radiobutton(){

        opcaoSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.masculino){
                    resultado.setText("O usuario é Masculino");
                }else if(checkedId == R.id.feminino){
                    resultado.setText("O usuario é Feminino");
                }

            }
        });

    }

    public void checkbox(){

        String text = "";

        if(cor_verde.isChecked()){
            text = "verde Selecionado - ";
        }
        if(cor_branco.isChecked()){
            text = text +"Branco Selecionado - ";
        }
        if(cor_vermelho.isChecked()){
            text = text +  "Vermelho Selecionado - ";
        }

        this.resultado.setText(text);
    }

    public void limpar(View view) {
        this.nome.setText("");
        this.email.setText("");
        this.resultado.setText("");
    }

}
