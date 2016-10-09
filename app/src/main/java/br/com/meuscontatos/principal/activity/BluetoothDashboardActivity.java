package br.com.meuscontatos.principal.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.adapter.BluetoothRecyclerViewAdapter;
import br.com.meuscontatos.principal.domain.Usuario;
import br.com.meuscontatos.principal.service.Service;


/**
 * Created by patricknasc on 03/10/16.
 */

public class BluetoothDashboardActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private Button btnAtivarBluetooth;
    private Button btnBuscarDevices;
    private Button btnFicarVisivel;
    private Button btnIniciarServidor;


    //Bluetooth setup
    private BluetoothAdapter btfAdapter = null;
    private final int REQUEST_ENABLE_BT = 1; //identificador para a solicitação de ativação de BT
    private final int REQUEST_CONN_BT = 2; //identificador para a solicitação conexão
    private boolean conn = false;
    private static String MAC = null;
    BluetoothDevice deviceToConnect = null;
    BluetoothSocket myBluetoothSocket = null;
    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //default para comunicações inseguras e sem autenticação.
    static String MAC_ADDRESS = null;
    Set<BluetoothDevice> bluetoothDevices = null;
    BluetoothServerSocket btfServerSocket = null;
    BluetoothSocket btfSocket =  null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_dashboard_activity);


        ativarBluetooth();

        //Registrando o receiver para receber as mensagens de dispositivos pareados
        this.getApplicationContext().registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        //Registrando o receiver para receber a mensagem do final da busca por devices
        this.getApplicationContext().registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
        //Fim funcionalidade

    }



    public void ativarBluetooth(){
        setupBluetooth();

    }


    public void ativarBluetooth(View view){
        setupBluetooth();

    }

    protected void setupBluetooth(){
        this.btfAdapter = BluetoothAdapter.getDefaultAdapter();
        if(this.btfAdapter == null){
            Toast.makeText(getApplicationContext(), "Seu dispositivo não possui suporte a Bluetooth", Toast.LENGTH_LONG).show();

        } else if(!this.btfAdapter.isEnabled()){
            Intent activateBtfIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(activateBtfIntent, REQUEST_ENABLE_BT); //Solicitação para habilitar o bluetooth
        }
    }




    public void buscarDevices(View view){
        setupBluetooth();
        //Iniciando a lista com os dispositivos previamente pareados
        bluetoothDevices = btfAdapter.getBondedDevices();

        //Garante que não existe outra busca sendo realizada.
        if(btfAdapter.isDiscovering()){
            btfAdapter.cancelDiscovery();
        }
        //Dispara uma nova busca
        btfAdapter.startDiscovery();
        dialog = ProgressDialog.show(getApplicationContext(), "Bluetooth", "Buscando dispositivos Bluetooth...", false, true);

    }





    public void ficarVisivel(View view){
        Intent visibilityIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        visibilityIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(visibilityIntent);
    }





    public void iniciarServidor(View view) {
        //Abre um servidor socket de forma não bloqueante
        new Thread(){

            @Override
            public void run(){

                try {
                    btfServerSocket = btfAdapter.listenUsingRfcommWithServiceRecord("Meus Contatos Chat", MY_UUID);
                    btfSocket = btfServerSocket.accept();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }




    //Receiver para receber os broadcasts do Bluetooth
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        //Quantidade de dispositivos encontrados
        private int count;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //Se um device foi encontrado
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                //Recupera o device da intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //Se não está pareado ainda, insere na lista de devices não pareados
                if(device.getBondState() != BluetoothDevice.BOND_BONDED){
                    bluetoothDevices.add(device);
                    Toast.makeText(context, "Encontrou: "+device.getName()+":"+
                            device.getAddress(), Toast.LENGTH_SHORT).show();
                    count++;
                }
            } else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                //Busca por dispositivos iniciada
                count = 0;
                Toast.makeText(context, "Busca iniciada", Toast.LENGTH_LONG).show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                //Busca por dispositivos finalizada
                Toast.makeText(context, "Busca finalizada. "+count + " devices encontrados", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                //Atualiza a listagem para conter tanto os devices pareados quanto os mais novos que foram encontrados
                updateDevicesList();
            } else if(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION.equals(action)){
                Toast.makeText(context, "Seu dispositivo estará visível por 300 segundos", Toast.LENGTH_LONG).show();
            }

        }
    };





    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){ //Parâmetro de intent

            case REQUEST_ENABLE_BT: //Solicitação para habilitar o bluetooth
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "O Bluetooth foi ativado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "O Bluetooth não pode ser ativado", Toast.LENGTH_LONG).show();
                    //finish();
                }
                break;
            case REQUEST_CONN_BT: //Solicitação para se conectar a um dispositivo
                if(resultCode == Activity.RESULT_OK) {
                    MAC = data.getExtras().getString(MAC_ADDRESS);
                    Toast.makeText(getApplicationContext(), "MAC: " + MAC, Toast.LENGTH_LONG).show();
                    deviceToConnect = btfAdapter.getRemoteDevice(MAC);
                    try {
                        myBluetoothSocket = deviceToConnect.createRfcommSocketToServiceRecord(MY_UUID);
                        myBluetoothSocket.connect();
                        conn = true;
                        Toast.makeText(getApplicationContext(), "Conexão com "+MAC+" realizada com sucesso", Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        conn = false;
                        Toast.makeText(getApplicationContext(), "Erro ao se conectar a "+MAC, Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Falha ao obter o MAC", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Garante que a busca é cancelada ao sair
        if(btfAdapter != null){
            btfAdapter.cancelDiscovery();
        }

        //Cancela o registro do receiver
        this.getApplicationContext().unregisterReceiver(mReceiver);
    }


    private void updateDevicesList() {
        //Criando o array com o nome de cada device
        //Iniciando a lista com os dispositivos previamente pareados

        bluetoothDevices = btfAdapter.getBondedDevices();

        List<String> listaBluetooth = new ArrayList<>();
        for(BluetoothDevice device : bluetoothDevices){
            //Neste momento a lista contem ainda apenas devices pareados, o que resultará sempre em true
            boolean paired = device.getBondState() == BluetoothDevice.BOND_BONDED;
            listaBluetooth.add(device.getName() + " - " + device.getAddress() + (paired ? " [pareado]" : ""));

            //TODO retornar essa lista pra a ListaBluetoothFragment
            finish();

//            BluetoothRecyclerViewAdapter btfViewAdapter = new BluetoothRecyclerViewAdapter(getActivity(),listaBluetooth);
//
//            mRecyclerView.setAdapter(btfViewAdapter);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        //Garante que não existe outra busca sendo realizada.
        if(btfAdapter.isDiscovering()){
            btfAdapter.cancelDiscovery();
        }
//        //Dispara uma nova busca
//        btfAdapter.startDiscovery();
//        dialog = ProgressDialog.show(getActivity(), "Bluetooth", "Buscando dispositivos bluetooth...", false, true);
    }



}
