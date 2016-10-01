package br.com.meuscontatos.principal.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.fragment.ListaContatosFragment;
import br.com.meuscontatos.principal.fragment.TabContatosPrincipalFragment;


public class MainActivity extends ActionBarActivity {

    private Toolbar tbHead;
    private Toolbar tbBottom;


//    //Bluetooth
//    private Button btnConnect;
//    private BluetoothAdapter myBluetoothAdapter = null;
//    private final int REQUEST_ENABLE_BT = 1; //identificador para a solicitação de ativação de BT
//    private final int REQUEST_CONN_BT = 2; //identificador para a solicitação conexão
//    private boolean conn = false;
//    private static String MAC = null;
//    BluetoothDevice deviceToConnect = null;
//    BluetoothSocket myBluetoothSocket = null;
//    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //default para comunicações inseguras e sem autenticação.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        setupBluetooth(); //Ativando o Bluetooth

//        btnConnect = (Button) findViewById(R.id.btnConnect);

//        btnConnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(conn){
//                    //desconectar
//                    try{
//                        myBluetoothSocket.close();
//                        conn = false;
//                        btnConnect.setText("Conectar um dispositivo");
//                        Toast.makeText(getApplicationContext(), "Bluetooth desconectado com sucesso", Toast.LENGTH_SHORT).show();
//                    } catch (IOException e){
//                        Toast.makeText(getApplicationContext(), "Falha ao tentar desconectar: "+e, Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    //conectar
//                    Intent openListDevices = new Intent(MainActivity.this, ListaDispositivosActivity.class);
//                    startActivityForResult(openListDevices, REQUEST_CONN_BT);
//                }
//            }
//        });



        //**************************************
        tbHead   = (Toolbar) findViewById(R.id.toolbar);



        //setSupportActionBar(tbHead);
      //  tbBottom = (Toolbar) findViewById(R.id.tb_bottom);

        TabContatosPrincipalFragment listaContatos = (TabContatosPrincipalFragment) getSupportFragmentManager().findFragmentByTag("fragListaContatos");
        if(listaContatos==null){
            listaContatos=new TabContatosPrincipalFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,listaContatos,"fragListaContatos");
            ft.commit();
        }

        //btnConnect.setOnClickListener();


    }




//    protected void setupBluetooth(){
//        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if(myBluetoothAdapter == null){
//            Toast.makeText(getApplicationContext(), "Seu dispositivo não possui bluetooth", Toast.LENGTH_LONG).show();
//
//        } else if(!myBluetoothAdapter.isEnabled()){
//            Intent activateBtfIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(activateBtfIntent, REQUEST_ENABLE_BT);
//        }
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        switch (requestCode){ //Parâmetro de intent
//
//            case REQUEST_ENABLE_BT:
//                if(resultCode == Activity.RESULT_OK){
//                    Toast.makeText(getApplicationContext(), "O Bluetooth foi ativado", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "O Bluetooth não foi ativado, o app será encerrado", Toast.LENGTH_LONG).show();
//                    finish();
//                }
//                break;
//            case REQUEST_CONN_BT:
//                if(resultCode == Activity.RESULT_OK){
//                    MAC = data.getExtras().getString(ListaDispositivosActivity.MAC_ADDRESS);
//                    Toast.makeText(getApplicationContext(), "MAC: " + MAC, Toast.LENGTH_LONG).show();
//                    deviceToConnect = myBluetoothAdapter.getRemoteDevice(MAC);
//                    try {
//                        myBluetoothSocket = deviceToConnect.createRfcommSocketToServiceRecord(MY_UUID);
//                        myBluetoothSocket.connect();
//                        conn = true;
//                        btnConnect.setText("Desconectar um dispositivo");
//                        Toast.makeText(getApplicationContext(), "Conexão com "+MAC+" realizada com sucesso", Toast.LENGTH_LONG).show();
//
//                    } catch (IOException e) {
//                        conn = false;
//                        Toast.makeText(getApplicationContext(), "Erro ao se conectar a "+MAC, Toast.LENGTH_LONG).show();
//                    }
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Falha ao obter o MAC", Toast.LENGTH_LONG).show();
//                }
//        }
//    }



}
