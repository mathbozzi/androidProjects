package com.cursoandroid.matheus.fragment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cursoandroid.matheus.fragment.R;
import com.cursoandroid.matheus.fragment.fragment.CttsFragment;
import com.cursoandroid.matheus.fragment.fragment.CvsFragment;

public class MainActivity extends AppCompatActivity {

    private Button buttoncvs, buttonctt;
    private CvsFragment cvsFragment;
    private CttsFragment cttsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        buttonctt = findViewById(R.id.buttonctt);
        buttoncvs = findViewById(R.id.buttoncvs);

        buttonctt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cttsFragment = new CttsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameconteudo, cttsFragment);
                transaction.commit();
            }
        });

        buttoncvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvsFragment = new CvsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameconteudo, cvsFragment);
                transaction.commit();
            }
        });
    }


}
