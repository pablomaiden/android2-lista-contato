package br.com.meuscontatos.principal.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Usuario;
import br.com.meuscontatos.principal.fragment.ListaContatosFragment;
import br.com.meuscontatos.principal.fragment.TabContatosPrincipalFragment;
import br.com.meuscontatos.principal.service.Service;
import br.com.meuscontatos.principal.util.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;


public class MainActivity extends ActionBarActivity {

    private Toolbar tbHead;
    private Toolbar tbBottom;
    private CircleImageView foto_perfil_autenticacao;
    private TextView tv_nome_usuario_firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        Usuario user = realm.where(Usuario.class).findFirst();
        foto_perfil_autenticacao = (CircleImageView) findViewById(R.id.foto_perfil_autenticacao);
        tv_nome_usuario_firebase = (TextView) findViewById(R.id.tv_nome_usuario_firebase);

        if(user!=null && user.getUrlFotoFireBase()!=null){
            int loader = R.drawable.bubble2;
            String image_url = user.getUrlFotoFireBase();
            ImageLoader imgLoader = new ImageLoader(getApplicationContext());
            imgLoader.DisplayImage(image_url, loader, foto_perfil_autenticacao);
            tv_nome_usuario_firebase.setText(user.getNameUserFireBase());
        }

        tbHead   = (Toolbar) findViewById(R.id.toolbar);
        TabContatosPrincipalFragment listaContatos = (TabContatosPrincipalFragment) getSupportFragmentManager().findFragmentByTag("fragListaContatos");
        if(listaContatos==null){
            listaContatos=new TabContatosPrincipalFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,listaContatos,"fragListaContatos");
            ft.commit();
        }
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            //Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
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
