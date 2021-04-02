package com.mathbozzi.bluetothh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button buttonConect;
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket;
    UUID uuidBluetooth = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int BLUETOOTH_ATIVADO = 1;
    private static final int LISTA_PAREADOS = 2;
    private static final int MESSAGE_READ = 3;
    private StringBuilder dadosBluetooth = new StringBuilder();
    private static String MAC;
    boolean conexao = false;
    ConnectedThread connectedThread;
    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonConect = findViewById(R.id.buttonConect);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "nao tem bluetooth", Toast.LENGTH_SHORT).show();
        } else if (!bluetoothAdapter.isEnabled()) {
            Intent ativaBlue = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBlue, BLUETOOTH_ATIVADO);
        }

        buttonConect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conexao) {
                    try {
                        bluetoothSocket.close();
                        conexao = false;
                        buttonConect.setText("Conectar");
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

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == MESSAGE_READ){
                    String recebidos =  (String) msg.obj;
                    dadosBluetooth.append(recebidos);
                    int fimInfo = dadosBluetooth.indexOf("}");

                    if(fimInfo > 0){
                        String dadosCompletos = dadosBluetooth.substring(0,fimInfo);
                        int tamanho = dadosCompletos.length();

                        if(dadosBluetooth.charAt(0) == '{'){
                            String dadosFinais = dadosBluetooth.substring(1,tamanho);
                            Log.d("Recebidos", dadosFinais);
                        }
                        dadosBluetooth.delete(0,dadosBluetooth.length());
                        dadosCompletos = "";
                    }
                }
            }
        };

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
                        connectedThread.enviar("AHUS");
                        buttonConect.setText("Desconectar");

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

    private class ConnectedThread extends Thread {

        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {

            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e("TAG", "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e("TAG", "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {

            mmBuffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(mmBuffer);

                    String dados = new String(mmBuffer,0,numBytes);

                    // Send the obtained bytes to the UI activity.
                    Message readMsg = handler.obtainMessage(MESSAGE_READ, numBytes, -1,dados);
                    readMsg.sendToTarget();
                } catch (IOException e) {
                    Log.d("TAG", "Input stream was disconnected", e);
                    break;
                }
            }

        }

        // Call this from the main activity to send data to the remote device.
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
