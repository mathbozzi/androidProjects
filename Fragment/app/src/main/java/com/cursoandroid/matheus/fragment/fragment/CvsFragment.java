package com.cursoandroid.matheus.fragment.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cursoandroid.matheus.fragment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CvsFragment extends Fragment {


    public CvsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cvs, container, false);
    }

}
