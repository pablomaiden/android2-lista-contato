package br.com.meuscontatos.principal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;

public class CadastrarContatosActivity extends AppCompatActivity {

    private EditText et_nome;
    private EditText et_email;
    private EditText et_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_contatos_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        getSupportActionBar().setTitle("Cadastro Contato");

        et_nome  = (EditText) findViewById(R.id.et_nome);
        et_email = (EditText) findViewById(R.id.et_email);
        et_senha = (EditText) findViewById(R.id.et_senha);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_contato, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvar:
                salvar();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
        public void salvar(){
            Realm realm = Service.getInstace().getRealm(getApplicationContext());
            Contato contato=new Contato();
            contato.setId(autoIncremento());
            contato.setNome(et_nome.getText().toString());
            contato.setEmail(et_email.getText().toString());
            contato.setEmail(et_senha.getText().toString());

            realm.beginTransaction();
            realm.insertOrUpdate(contato);
            realm.commitTransaction();
    }

    public Long autoIncremento(){
        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        Long isContem = realm.where(Contato.class).count();
        if(isContem==0){
        }else{
            return realm.where(Contato.class).max("id").longValue()+1;
        }
        return 0L;
    }

}
