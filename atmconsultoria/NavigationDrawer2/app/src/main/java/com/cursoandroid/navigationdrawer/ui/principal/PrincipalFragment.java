package com.cursoandroid.navigationdrawer.ui.principal;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.cursoandroid.navigationdrawer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalFragment extends Fragment{

    private SeekBar midiaVolume;
    private AudioManager audioManager;


    // Inflate the layout for this fragment
    public PrincipalFragment() {
        // Required empty public constructor
    }

    public void inicializaMidiaVolume(){
        midiaVolume = findViewById(R.id.volume_midia);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        midiaVolume.setMax(volumeMaximo);
        midiaVolume.setProgress(volumeAtual);

        midiaVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inicializaMidiaVolume();
        return inflater.inflate(R.layout.fragment_principal, container, false);
    }

}
