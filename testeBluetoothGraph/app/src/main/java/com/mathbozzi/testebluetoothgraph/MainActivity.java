package com.mathbozzi.testebluetoothgraph;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class MainActivity extends Activity implements View.OnClickListener{

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket;
    UUID uuidBluetooth = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int BLUETOOTH_ATIVADO = 1;
    private static final int LISTA_PAREADOS = 2;
    private static final int MESSAGE_READ = 3;
    private static String MAC;
    boolean conexao = false;
    static ConnectedThread connectedThread;

    public static void disconnect(){
        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (connectedThread != null) {
            connectedThread.enviar("Q");}//Stop streaming
        super.onBackPressed();
    }

    //toggle Button
    static boolean Lock;//whether lock the x-axis to 0-5
    static boolean AutoScrollX;//auto scroll to the last x value
    static boolean Stream;//Start or stop streaming
    //Button init
    Button bXminus;
    Button bXplus;
    ToggleButton tbLock;
    ToggleButton tbScroll;
    ToggleButton tbStream;
    //GraphView init
    static LinearLayout GraphView;
    static GraphView graphView;
    static GraphViewSeries Series;
    //graph value
    private static double graph2LastXValue = 0;
    private static int Xview=10;
    Button bConnect, bDisconnect;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch(msg.what){
                case MESSAGE_READ:

                    byte[] readBuf = (byte[]) msg.obj;
                    String strIncom = new String(readBuf, 0, 5);                 // create string from bytes array

                    Log.d("strIncom", strIncom);
                    if (strIncom.indexOf('.')==2 && strIncom.indexOf('s')==0){
                        strIncom = strIncom.replace("s", "");
                        if (isFloatNumber(strIncom)){
                            Series.appendData(new GraphView.GraphViewData(graph2LastXValue,Double.parseDouble(strIncom)),AutoScrollX);

                            //X-axis control
                            if (graph2LastXValue >= Xview && Lock == true){
                                Series.resetData(new GraphView.GraphViewData[] {});
                                graph2LastXValue = 0;
                            }else graph2LastXValue += 0.1;

                            if(Lock == true)
                                graphView.setViewPort(0, Xview);
                            else
                                graphView.setViewPort(graph2LastXValue-Xview, Xview);

                            //refresh
                            GraphView.removeView(graphView);
                            GraphView.addView(graphView);

                        }
                    }

                    break;
            }
        }

        public boolean isFloatNumber(String num){
            //Log.d("checkfloatNum", num);
            try{
                Double.parseDouble(num);
            } catch(NumberFormatException nfe) {
                return false;
            }
            return true;
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Hide title
        this.getWindow().setFlags(WindowManager.LayoutParams.
                FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//Hide Status bar
        setContentView(R.layout.activity_main);
        //set background color
        LinearLayout background = (LinearLayout)findViewById(R.id.bg);
        background.setBackgroundColor(Color.BLACK);
        init();
        ButtonInit();

        bConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conexao) {
                    try {
                        bluetoothSocket.close();
                        conexao = false;
                        Toast.makeText(MainActivity.this, "Bluetooth Desconectado", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, ListaDispositivos.class);
                    startActivityForResult(intent, LISTA_PAREADOS);
                }
            }
        });
    }

    void init(){

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(MainActivity.this, "nao tem bluetooth", Toast.LENGTH_SHORT).show();
        } else if (!bluetoothAdapter.isEnabled()) {
            Intent ativaBlue = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBlue, BLUETOOTH_ATIVADO);
        }
        //init graphview
        GraphView = findViewById(R.id.Graph);
        Series = new GraphViewSeries("Signal",
                new GraphViewSeries.GraphViewStyle(Color.YELLOW, 2),//color and thickness of the line
                new GraphView.GraphViewData[] {new GraphView.GraphViewData(0, 0)});
        graphView = new LineGraphView(
                this // context
                , "Graph" // heading
        );
        graphView.setViewPort(0, Xview);
        graphView.setScrollable(true);
        graphView.setScalable(true);
        graphView.setShowLegend(true);
        graphView.setLegendAlign(com.jjoe64.graphview.GraphView.LegendAlign.BOTTOM);
        graphView.setManualYAxis(true);
        graphView.setManualYAxisBounds(15, 0);
        graphView.addSeries(Series); // data
        GraphView.addView(graphView);
    }

    void ButtonInit(){
        bConnect = findViewById(R.id.bConnect);
        bConnect.setOnClickListener(this);
        bDisconnect = findViewById(R.id.bDisconnect);
        bDisconnect.setOnClickListener(this);
        //X-axis control button
        bXminus = findViewById(R.id.bXminus);
        bXminus.setOnClickListener(this);
        bXplus = findViewById(R.id.bXplus);
        bXplus.setOnClickListener(this);
        //
        tbLock = findViewById(R.id.tbLock);
        tbLock.setOnClickListener(this);
        tbScroll = findViewById(R.id.tbScroll);
        tbScroll.setOnClickListener(this);
        tbStream = findViewById(R.id.tbStream);
        tbStream.setOnClickListener(this);
        //init toggleButton
        Lock=true;
        AutoScrollX=true;
        Stream=true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case BLUETOOTH_ATIVADO:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth Conectado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Bluetooth não Conectado", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case LISTA_PAREADOS:
                if (resultCode == Activity.RESULT_OK) {
                    MAC = data.getExtras().getString(ListaDispositivos.enderecoMAC);
                    bluetoothDevice = bluetoothAdapter.getRemoteDevice(MAC);

                    try {
                        bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuidBluetooth);
                        bluetoothSocket.connect();
                        conexao = true;
                        connectedThread = new ConnectedThread(bluetoothSocket);
                        connectedThread.start();

                    } catch (IOException e) {
                        conexao = false;
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Bluetooth Falhou", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()){
            case R.id.bDisconnect:
                disconnect();
                break;
            case R.id.bXminus:
                if (Xview>1) Xview--;
                break;
            case R.id.bXplus:
                if (Xview<30) Xview++;
                break;
            case R.id.tbLock:
                if (tbLock.isChecked()){
                    Lock = true;
                }else{
                    Lock = false;
                }
                break;
            case R.id.tbScroll:
                if (tbScroll.isChecked()){
                    AutoScrollX = true;
                }else{
                    AutoScrollX = false;
                }
                break;
            case R.id.tbStream:
                if (tbStream.isChecked()){
                    if (connectedThread != null)
                        connectedThread.enviar("E");
                }else{
                    if (connectedThread != null)
                        connectedThread.enviar("Q");
                }
                break;
        }
    }

    private class ConnectedThread extends Thread {

        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        StringBuffer sbb = new StringBuffer();
        public void run() {

            byte[] buffer;
            int bytes;

            while (true) {
                try {
                    try {
                        sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    buffer = new byte[1024];
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        public void cancel() {
            try {
                bluetoothSocket.close();
            } catch (IOException e) { }
        }

        public void enviar(String enviar) {

            byte[] bufferMsg = enviar.getBytes();
            try {
                mmOutStream.write(bufferMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}