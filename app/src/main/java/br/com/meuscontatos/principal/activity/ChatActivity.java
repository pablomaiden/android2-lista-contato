package br.com.meuscontatos.principal.activity;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import br.com.meuscontatos.principal.util.Util;
import io.realm.Realm;


public class ChatActivity extends AppCompatActivity {

    private EditText et_send;
    private TextView et_chat;
    private ImageView btn_send;
    private Contato contato;
    private ScrollView sc_view_chat;
    private TextView tv_hora;

    //Bluetooth
    private BluetoothAdapter btfAdapter = null;
    private final int REQUEST_ENABLE_BT = 1478;
    private final int REQUEST_CONN_BT = 2048;
    private static String MAC = null;
    static String MAC_ADDRESS = null;
    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private boolean conn = false;
    BluetoothDevice deviceToConnect = null;
    BluetoothServerSocket btfServerSocket = null;
    BluetoothSocket myBluetoothSocket = null;
    BluetoothSocket btfSocket =  null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbubble);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        recuperarContato();
        getSupportActionBar().setTitle(contato.getNome());

        et_send      = (EditText)   findViewById(R.id.et_send);
        et_chat      = (TextView)   findViewById(R.id.et_chat);
        btn_send     = (ImageView)  findViewById(R.id.btn_send);
        sc_view_chat = (ScrollView) findViewById(R.id.sc_view_chat);

        et_chat.setMovementMethod(new ScrollingMovementMethod());
        btn_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                et_chat.setText(et_chat.getText()+"\n"+et_send.getText()+"\n"+Util.getHora());
                et_send.setText("");
                sc_view_chat.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    public void recuperarContato(){
        Long idContato = (Long) getIntent().getExtras().get("idContato");
        if(idContato!=null) {
           contato = getContato(idContato);
        }
    }

    public Contato getContato(Long id){
        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        return realm.where(Contato.class).equalTo("id",id).findFirst();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bluetooth:
                ativarBluetooth();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void ativarBluetooth(){
        this.btfAdapter = BluetoothAdapter.getDefaultAdapter();
        if(this.btfAdapter == null){
            Toast.makeText(getApplicationContext(), "Seu dispositivo não possui suporte a Bluetooth", Toast.LENGTH_LONG).show();

        } else if(!this.btfAdapter.isEnabled()){
            Intent activateBtfIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(activateBtfIntent, REQUEST_ENABLE_BT);
        }
    }

    public void ficarVisivel(){
        Intent visibilityIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        visibilityIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(visibilityIntent);
    }

    public void iniciarServidor() {
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

}
