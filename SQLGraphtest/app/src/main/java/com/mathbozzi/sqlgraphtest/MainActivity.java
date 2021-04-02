package com.mathbozzi.sqlgraphtest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    private Button addPonto;
    private EditText xInput, yInput;
    private GraphView graphView;
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);
    private MyHelper myHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addPonto = findViewById(R.id.btnAddPt);
        xInput = findViewById(R.id.numX);
        yInput = findViewById(R.id.numY);
        graphView = findViewById(R.id.GraphView);

        myHelper = new MyHelper(this);
        sqLiteDatabase = myHelper.getWritableDatabase();

        inicializarGrafico();
        adicionarPonto();
        graphView.addSeries(series);
    }

    private void inicializarGrafico() {

       /* graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);
        graphView.setCursorMode(true);
        series.setDrawBackground(true);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setBackgroundColor(Color.GRAY);


        */
    }

    private void adicionarPonto() {
        addPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!xInput.getText().toString().equals("") && !yInput.getText().toString().equals("") ){
                    int x = Integer.parseInt(xInput.getText().toString());
                    int y = Integer.parseInt(yInput.getText().toString());

                    myHelper.insertData(x,y);

                    series.resetData(getPontos());
                    //series = new LineGraphSeries<>(getPontos());

                    xInput.setText("");
                    yInput.setText("");
                }else {
                    Toast.makeText(MainActivity.this, "You must fill out both fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private DataPoint[] getPontos() {
        String[] colums = {"xValues","yValues"};
        Cursor cursor = sqLiteDatabase.query("MYBD", colums,null,null,null,null,null);

        DataPoint[] dataPoints = new DataPoint[cursor.getCount()];

        for(int i=0; i<cursor.getCount();i++){
            cursor.moveToNext();
            dataPoints[i] = new DataPoint(cursor.getInt(0),cursor.getInt(1));
        }

        return dataPoints;
    }
}
