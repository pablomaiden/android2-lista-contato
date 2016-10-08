package br.com.meuscontatos.principal.activity;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;


public class ChatActivity extends AppCompatActivity {

    private EditText et_send;
    private EditText et_chat;
    private Button btn_send;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbubble);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        recuperarContato();
        getSupportActionBar().setTitle(contato.getNome());

        et_send  = (EditText) findViewById(R.id.et_send);
        et_chat  = (EditText) findViewById(R.id.et_chat);
        btn_send = (Button)   findViewById(R.id.btn_send);

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

}
