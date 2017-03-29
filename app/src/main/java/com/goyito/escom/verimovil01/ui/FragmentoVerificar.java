package com.goyito.escom.verimovil01.ui;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;



import com.goyito.escom.verimovil01.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by goyito on 18/02/17.
 */

public class FragmentoVerificar extends Fragment {
    // GUI Components
    private TextView mBluetoothStatus;
    private static TextView mReadBuffer;
    private TextView datos;
    private Button detectarBtn;
    private Button encenderBtn;
    private Button verificarBtn;
    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private Set<BluetoothDevice> mDevices;// = mBTAdapter.getBondedDevices();
    private ArrayAdapter<String> mBTArrayAdapter;
    private ListView mDevicesListView;

    private int estado = 1;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private BluetoothDevice dispositivo = null;

    private Handler mHandler; // Our main handler that will receive callback notifications
    private ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    private static final String NOMBRE_DISPOSITIVO = "HC-05";

    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status

    //private String readMessage = null;
    private int contador=0;

    public FragmentoVerificar(){}

    public static FragmentoVerificar newInstance(Bundle arguments){
        FragmentoVerificar fragmentoVerificar = new FragmentoVerificar();
        if(arguments != null) {
            fragmentoVerificar.setArguments(arguments);
        }
        return fragmentoVerificar;
    }

    //El fragment se ha adjuntado al Activity
    //@Override
    public void onAttach(ActividadPrincipal activity) {
        super.onAttach(activity);
    }

    //El Fragment ha sido creado
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //El Fragment va a cargar su layout, el cual debemos especificar
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_verificar, container, false);

        mBluetoothStatus = (TextView)view.findViewById(R.id.bluetoothStatus);
        mReadBuffer = (TextView) view.findViewById(R.id.readBuffer);
        encenderBtn = (Button) view.findViewById(R.id.encender);
        detectarBtn = (Button)view.findViewById(R.id.detectar);
        verificarBtn = (Button) view.findViewById(R.id.btnverificar);
        //mListPairedDevicesBtn = (Button)view.findViewById(R.id.PairedBtn);
        datos = (TextView) view.findViewById(R.id.datos);


        mBTArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio

        mDevicesListView = (ListView)view.findViewById(R.id.devicesListView);
        mDevicesListView.setAdapter(mBTArrayAdapter); // assign model to view
        mDevicesListView.setOnItemClickListener(mDeviceClickListener);

        mHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == MESSAGE_READ){
                    //String readMessage = "";
                    //StringBuilder recDataString = new StringBuilder();
                    //try {
                        //int i=0;
                        //readMessage = (String) msg.obj.toString().;
                        //readMessage = new String((byte[]) msg.obj, "UTF-8");
                        //readMessage.concat(new String((byte[]) msg.obj, "UTF-8"));
                        //while(msg.obj == null){
                            String readMessage = (String) msg.obj;
                            recDataString.append(readMessage);
                        //}
                    //} catch (Exception e) {
                       // e.printStackTrace();
                    ///
                    //mReadBuffer.setText(readMessage);
                    //readMessage = (String) (msg.obj);

                    //StringBuilder recDataString = new StringBuilder();
                    //recDataString.append(readMessage);

                    mReadBuffer.setText(recDataString.toString());

                    //mReadBuffer.setText(readMessage);
                    //recDataString.delete(0, recDataString.length());
                }

                if(msg.what == CONNECTING_STATUS){
                    if(msg.arg1 == 1)
                        mBluetoothStatus.setText("Connected to Device: " + (String)(msg.obj));
                    else
                        mBluetoothStatus.setText("Connection Failed");
                }
            }

        };

        if (mBTArrayAdapter == null) {
            // Device does not support Bluetooth
            mBluetoothStatus.setText("Status: Bluetooth not found");
            Toast.makeText(getContext(),"Bluetooth device not found!",Toast.LENGTH_SHORT).show();
        }
        //else {

            /*mLED1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(mConnectedThread != null) //First check to make sure thread created
                        mConnectedThread.write("1");
                }
            }); */

            encenderBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    power(v);
                }
            });

            /*mListPairedDevicesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    listPairedDevices(v);
                }
            });*/

            detectarBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    discover(v);
                }
            });
        //}

        verificarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(v);
            }
        });

        return view;
    }

    //La vista de layout ha sido creada y ya está disponible
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //La vista ha sido creada y cualquier configuración guardada está cargada
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    //El Activity que contiene el Fragment ha terminado su creación
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    @Override
    public void onDetach() {
        super.onDetach();
    }

    //metodos para bluetooth
    private void bluetoothOn(View view){
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            mBluetoothStatus.setText("Bluetooth enabled");
            Toast.makeText(getContext(),"Bluetooth turned on",Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getContext(),"Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }

    // Enter here after user selects "yes" or "no" to enabling radio
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent Data){
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                mBluetoothStatus.setText("Enabled");
            }
            else
                mBluetoothStatus.setText("Disabled");

        }
    }

    private void power(View view){
        if(estado==0){
            mBTAdapter.disable();
            mBluetoothStatus.setText("Bluetooth disabled");
            Toast.makeText(getContext(),"Bluetooth turned Off", Toast.LENGTH_SHORT).show();
            estado = 1;
        } else
        if(estado==1) {
            if (!mBTAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                mBluetoothStatus.setText("Bluetooth enabled");
                Toast.makeText(getContext(),"Bluetooth turned on",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(),"Bluetooth is already on", Toast.LENGTH_SHORT).show();
            }
            estado = 0;
        }
    }

    private void verificar(View view) {
        mBTArrayAdapter.clear();
        //contador = 0;
        Thread hilo = new Thread(new Runnable()
        {
            public void run() {
                while(contador < 3) {
                    if(mConnectedThread != null) { //First check to make sure thread created
                         mConnectedThread.write("1");
                    }
                    String dat = mReadBuffer.getText().toString();
                    datos.setText(dat);
                    recDataString.delete(0, recDataString.length());
                    SystemClock.sleep(1000);
                    contador++;
                }
            }
        });
        hilo.start();

        /*//for(int j=0; j<3; j++){
            if(mConnectedThread != null) { //First check to make sure thread created
                mConnectedThread.write("1");
            }
            String dat = mReadBuffer.getText().toString();
            datos.setText(dat);
            recDataString.delete(0, recDataString.length());
            //SystemClock.sleep(1000);
        //}*/

        //recDataString = "";
        //recDataString.delete(0, recDataString.length());
        //if(mConnectedThread != null) { //First check to make sure thread created
          //  mConnectedThread.write("1");
        //}
        /*String dat = mReadBuffer.getText().toString();
        datos.setText(dat);
        recDataString.delete(0, recDataString.length());*/
    }

    private void discover(View view){
        // comprueba si el dispositivo ya esta buscando
        if(mBTAdapter.isDiscovering()){
            mBTAdapter.cancelDiscovery();
            Toast.makeText(getContext(),"Discovery stopped",Toast.LENGTH_SHORT).show();
        }

        else{
            if(mBTAdapter.isEnabled()) {
                mBTArrayAdapter.clear(); // limpia el items
                mBTAdapter.startDiscovery();
                Toast.makeText(getContext(), "Discovery started", Toast.LENGTH_SHORT).show();
                getContext().registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
            else{
                Toast.makeText(getContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
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

    final BroadcastReceiver blReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name to the list
                //mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                //mBTArrayAdapter.notifyDataSetChanged();
                dispositivo = device;
            }
        }
    };

    /*private void listPairedDevices(View view){
        mPairedDevices = mBTAdapter.getBondedDevices();
        mBTArrayAdapter.clear(); // limpia el items
        if(mBTAdapter.isEnabled()) {
            // put it's one to the adapter
            for (BluetoothDevice device : mPairedDevices)
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());

            Toast.makeText(getContext(), "Show Paired Devices", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
    }*/

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            if(!mBTAdapter.isEnabled()) {
                Toast.makeText(getContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
                return;
            }

            mBluetoothStatus.setText("Connecting...");
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            final String address = info.substring(info.length() - 17);
            final String name = info.substring(0,info.length() - 17);

            // Spawn a new thread to avoid blocking the GUI one
            new Thread()
            {
                public void run() {
                    boolean fail = false;

                    BluetoothDevice device = mBTAdapter.getRemoteDevice(address);

                    try {
                        mBTSocket = createBluetoothSocket(device);
                    } catch (IOException e) {
                        fail = true;
                        Toast.makeText(getContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                    // Establish the Bluetooth socket connection.
                    try {
                        mBTSocket.connect();
                        //mBluetoothStatus.setText("Conectado");
                        //miHandle();
                    } catch (IOException e) {
                        try {
                            fail = true;
                            mBTSocket.close();
                            mHandler.obtainMessage(CONNECTING_STATUS, -1, -1).sendToTarget();
                        } catch (IOException e2) {
                            //insert code to deal with this
                            Toast.makeText(getContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(fail == false) {
                        mConnectedThread = new ConnectedThread(mBTSocket);
                        mConnectedThread.start();

                        mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name).sendToTarget();
                    }
                }
            }.start();
        }
    };

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connection with BT device using UUID
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                 try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    String read = new String(buffer, 0, bytes);

                    //if(bytes != 0) {
                        //SystemClock.sleep(100);
                        //mmInStream.read(buffer);
                    //}
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, read).sendToTarget();
                    //mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }




    /*@Override //metodo modificado cuando el usuario eliga no prender el bluetooth
    public void onActivityResult(int requestCode, int resultCode, Intent Data){
        // Check which request we're responding to
        if (requestCode == REQUEST_BLUETOOTH) {
            // Make sure the request was successful
            if (resultCode == RESULT_CANCELED) {
              ToggleButton b = (ToggleButton) getView().findViewById(R.id.toggle_boton);
                b.toggle();
            }
            //else {}
        }
    } */

    public void miHandle(){
        final Handler mHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == MESSAGE_READ){
                    //String readMessage = "";
                    //StringBuilder recDataString = new StringBuilder();
                    //try {
                        //int i=0;
                        //readMessage = (String) msg.obj.toString().;
                        //readMessage = new String((byte[]) msg.obj, "UTF-8");
                        //readMessage.concat(new String((byte[]) msg.obj, "UTF-8"));
                        //while(msg.obj == null){
                            String readMessage = (String) msg.obj;
                            recDataString.append(readMessage);
                        //}
                    //} catch (Exception e) {
                       // e.printStackTrace();
                    ///
                    //mReadBuffer.setText(readMessage);
                    //readMessage = (String) (msg.obj);

                    //StringBuilder recDataString = new StringBuilder();
                    //recDataString.append(readMessage);

                    mReadBuffer.setText(recDataString.toString());

                    //mReadBuffer.setText(readMessage);
                    //recDataString.delete(0, recDataString.length());
                }

                if(msg.what == CONNECTING_STATUS){
                    if(msg.arg1 == 1)
                        mBluetoothStatus.setText("Connected to Device: " + (String)(msg.obj));
                    else
                        mBluetoothStatus.setText("Connection Failed");
                }
            }

        };
    }

}
