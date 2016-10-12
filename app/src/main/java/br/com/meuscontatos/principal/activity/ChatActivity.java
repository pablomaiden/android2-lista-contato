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
import br.com.meuscontatos.principal.domain.Conversa;
import br.com.meuscontatos.principal.service.Service;
import br.com.meuscontatos.principal.util.Util;
import io.realm.Realm;


public class ChatActivity extends AppCompatActivity {

    private EditText et_send;
    private TextView et_chat;
    private ImageView btn_send;
    private Conversa conversa;
    private ScrollView sc_view_chat;
    private TextView tv_hora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbubble);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        //recuperarConversa();

        if(conversa!=null)
           getSupportActionBar().setTitle(conversa.getName());

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

//    public void recuperarConversa(){
//        if(getIntent().getExtras()!=null){
//            Long idConversa = (Long) getIntent().getExtras().get("idConversa");
//            if(idConversa!=null) {
//                conversa = getConversa(idConversa);
//            }
//        }
//    }
//
//    public Conversa getConversa(Long id){
//        Realm realm = Service.getInstace().getRealm(getApplicationContext());
//        return realm.where(Conversa.class).equalTo("id",id).findFirst();
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_chat, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.sendLocation:
//                //TODO Enviar um marker para o mapa com a posição do usuário e postar no chat.
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }








}
