package br.com.meuscontatos.principal.activity;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
        tv_hora      = (TextView)   findViewById(R.id.tv_hora);
        btn_send     = (ImageView)  findViewById(R.id.btn_send);
        sc_view_chat = (ScrollView) findViewById(R.id.sc_view_chat);

        et_chat.setMovementMethod(new ScrollingMovementMethod());
        btn_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                et_chat.setText(et_chat.getText()+"\n"+et_send.getText()+"\n"+Util.getHora());
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

}
