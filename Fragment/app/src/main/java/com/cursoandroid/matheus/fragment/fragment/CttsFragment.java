package com.cursoandroid.matheus.fragment.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cursoandroid.matheus.fragment.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CttsFragment extends Fragment {

    private Button buttonAbrir;
    private TextView textContatos;

    public CttsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ctts, container, false);

        buttonAbrir = view.findViewById(R.id.buttonAbrir);
        textContatos = view.findViewById(R.id.contatos);
        //textContatos.setText("Contatos");

        System.out.println(buttonAbrir.getText().toString());
        if(buttonAbrir.getText().toString().equals("Abrir")) {
            buttonAbrir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Snackbar
                            .make(v, "desfazer", Snackbar.LENGTH_LONG)
                            .setAction("confirmar", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    buttonAbrir.setText("Abrir");
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .show();
                    System.out.println(buttonAbrir.getText().toString());
                    buttonAbrir.setText("Fechar");
                    System.out.println(buttonAbrir.getText().toString());

                }
            });
        }else {
            buttonAbrir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonAbrir.setText("Abrir");
                }
            });
            System.out.println("entrei aqui ");
        }
        System.out.println(buttonAbrir.getText().toString());


        return view;

    }

}
