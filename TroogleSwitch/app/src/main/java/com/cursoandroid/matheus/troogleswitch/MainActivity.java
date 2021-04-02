package com.cursoandroid.matheus.troogleswitch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.UiAutomation;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton ligaDesliga;
    private Switch interruptor;
    private TextView result, resultEscala;
    private ProgressBar progressBar, progressBarCirc;
    private int progresso;

    private SeekBar escala;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.ligaDesliga = findViewById(R.id.ligadesliga);
        this.interruptor = findViewById(R.id.interruptor);
        this.result = findViewById(R.id.resultado);
        this.progressBar = findViewById(R.id.progressBar);
        this.progressBarCirc = findViewById(R.id.progressBarCircular);
        this.progressBarCirc.setVisibility(View.GONE);
        this.escala = findViewById(R.id.seekBar);
        this.resultEscala = findViewById(R.id.result_escala);
        ouvinte();
        seekbarOuvinte();

    }

    public void seekbarOuvinte(){
        escala.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resultEscala.setText("Progresso: "+ progress +"/"+ escala.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void ouvinte() {
        ligaDesliga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    result.setText("ligado");
                } else
                    result.setText("Desligado");

            }
        });
    }

    public void enviar(View view) {

        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(android.R.drawable.alert_dark_frame);

        TextView textView = new TextView(getApplicationContext());
        textView.setText("hsuahushau");

        Toast t = new Toast(getApplicationContext());
        t.setDuration(Toast.LENGTH_LONG);
        t.setView(imageView);
        //t.setView(textView);
        t.show();


        /*
        Toast.makeText(getApplicationContext(),"Clicou em enviar",Toast.LENGTH_SHORT).show();

         */
    }

    public void abrirAlertDialog(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Titulo da Dialog");
        alert.setMessage("mensagem dialog");
        alert.setIcon(android.R.drawable.ic_dialog_alert);

        alert.setCancelable(false);


        alert.setNegativeButton("nao", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "exec ao clicar em nao", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "exec ao clicar em sim", Toast.LENGTH_SHORT).show();
            }
        });

        alert.create();
        alert.show();
    }

    public void carregarProgressBar(View view) {

        progressBarCirc.setVisibility(View.VISIBLE);
        this.progresso = this.progresso + 1;
        progressBar.setProgress(this.progresso);

        if(this.progresso == 10) {
            progressBarCirc.setVisibility(View.GONE);
            this.progresso = 0;
        }
    }


    public void recuperaProcessoSeekBar(View view){

        resultEscala.setText("Escolhido: " + escala.getProgress());

    }
}

