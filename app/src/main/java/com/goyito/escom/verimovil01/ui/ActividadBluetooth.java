package com.goyito.escom.verimovil01.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goyito.escom.verimovil01.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static java.security.AccessController.getContext;

/**
 * Created by goyito on 22/03/17.
 */

public class ActividadBluetooth extends AppCompatActivity {
    private BluetoothAdapter mBTAdapter;
    private ArrayAdapter<String> mBTArrayAdapter;
    private ListView mDevicesListView;

    private int estado = 1;

    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names

    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//Serial Port Service ID
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Button prender, conectar, verificar, imprimir;
    private TextView mBluetoothStatus;
    private TextView datos;
    private TextView readbuffer;
    byte buffer[];
    boolean stopThread;

    private static String cadena ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_bluetooth);
        /*startButton = (Button) findViewById(R.id.buttonStart);
        sendButton = (Button) findViewById(R.id.buttonSend);
        clearButton = (Button) findViewById(R.id.buttonClear);
        stopButton = (Button) findViewById(R.id.buttonStop);*/
        //editText = (EditText) findViewById(R.id.editText);
        mBluetoothStatus = (TextView) findViewById(R.id.bluetoothStatus);
        datos = (TextView) findViewById(R.id.datos);
        readbuffer = (TextView) findViewById(R.id.readBuffer);
        prender = (Button) findViewById(R.id.prender);
        conectar = (Button) findViewById(R.id.conectar);
        verificar = (Button) findViewById(R.id.verificar);
        imprimir = (Button) findViewById(R.id.imprimir);

        mBTArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio

        mDevicesListView = (ListView) findViewById(R.id.devicesListView);
        mDevicesListView.setAdapter(mBTArrayAdapter); // assign model to view
        mDevicesListView.setOnItemClickListener(mDeviceClickListener);

        prender.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                power();
            }
        });
        conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                conectar();
            }
        });
        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //try {
                    verificar();
                //} catch (InterruptedException e) {
                  //  e.printStackTrace();
                //}
            }
        });
        imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });

        //setUiEnabled(false);

    }

    private void power(){
        if(estado==0){
            mBTAdapter.disable();
            mBluetoothStatus.setText("Bluetooth disabled");
            Toast.makeText(this,"Bluetooth turned Off", Toast.LENGTH_SHORT).show();
            estado = 1;
        } else
        if(estado==1) {
            mBTAdapter = BluetoothAdapter.getDefaultAdapter();
            if(!mBTAdapter.isEnabled())
            {
                Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableAdapter, REQUEST_ENABLE_BT);
                mBluetoothStatus.setText("Bluetooth enabled");
            } else{
                Toast.makeText(this,"Bluetooth is already on", Toast.LENGTH_SHORT).show();
            }
            estado = 0;
        }
    }

    private void conectar() {
        // comprueba si el dispositivo ya esta buscando
        if(mBTAdapter.isDiscovering()){
            mBTAdapter.cancelDiscovery();
            Toast.makeText(this,"Discovery stopped",Toast.LENGTH_SHORT).show();
        }
        else{
            if(mBTAdapter.isEnabled()) {
                mBTArrayAdapter.clear(); // limpia el items
                mBTAdapter.startDiscovery();
                Toast.makeText(this, "Discovery started", Toast.LENGTH_SHORT).show();
                this.registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
            else{
                Toast.makeText(this, "Bluetooth not on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name to the list
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                mBTArrayAdapter.notifyDataSetChanged();
            }
        }
    };


    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            if(!mBTAdapter.isEnabled()) {
                //Toast.makeText(this, "Bluetooth not on", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
                return;
            }
            mBTAdapter.cancelDiscovery(); //cancela la busqueda

            mBluetoothStatus.setText("Connecting...");
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            final String address = info.substring(info.length() - 17);
            final String name = info.substring(0,info.length() - 17);

            boolean connected = true;
            //mDevices = mBTAdapter.getBondedDevices();

            BluetoothDevice device = mBTAdapter.getRemoteDevice(address);

                try {
                    socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
                    socket.connect();
                    outputStream=socket.getOutputStream();
                    inputStream=socket.getInputStream();

                    mBTArrayAdapter.clear();
                    //mBluetoothStatus.setText("Connecting..."+device.getName());
                    //beginListenForData();
                } catch (IOException e) {
                    e.printStackTrace();
                    //connected=false;
                }
            mBluetoothStatus.setText("Connecting..."+device.getName());
            beginListenForData();
            }
    };

    public void verificar() {
        final String string = "1";
        final String string2 = "2";
        readbuffer.setText("");
        cadena="";
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            int contador=0; //numero de lecturas que pedira al sensor de CO
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (contador < 10) {
                            try {
                                outputStream.write(string.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            contador++;
                            readbuffer.setText("");
                        } else
                        if(contador>=10 && contador <20) {
                            try {
                                outputStream.write(string2.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            contador++;
                            readbuffer.setText("");
                        } else {
                            t.cancel();
                            t.purge();
                            readbuffer.setText("Finalizado");
                            calcular();
                        }
                    }
                });
            }
        }, 0, 1000); //delay(del timer), cada cuando se vuelve a ejecutar
    }

    public void calcular() {
        String [] c = cadena.split(",");
        double valores = 0.0;
        for(int i=0; i<(c.length/2); i++){
          valores += Double.parseDouble(c[i]);
        }
        datos.setText(cadena);
        datos.append("\nResultado CO: "+(valores/(c.length/2)));
    }

    public void beginListenForData() {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
                        int byteCount = inputStream.available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String c = new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run()
                                {
                                    cadena += c;
                                    readbuffer.append(c);
                                }
                            });
                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }
}
