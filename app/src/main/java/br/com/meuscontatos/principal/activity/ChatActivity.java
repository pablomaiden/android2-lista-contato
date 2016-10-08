package br.com.meuscontatos.principal.activity;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;


public class ChatActivity extends AppCompatActivity {

    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbubble);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);

        recuperarContato();
        getSupportActionBar().setTitle(contato.getNome());
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

}
