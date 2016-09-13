package br.com.meuscontatos.principal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Usuario;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;
import android.content.Intent;

public class SignUpActivity extends Activity {

    private EditText et_nome;
    private EditText et_email;
    private EditText et_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        et_nome  = (EditText) findViewById(R.id.et_nome);
        et_email = (EditText) findViewById(R.id.et_email);
        et_senha = (EditText) findViewById(R.id.et_senha);

    }

    public void salvar(View view){
        Usuario usuario = new Usuario();
        usuario.setUsuario(et_email.getText().toString());
        usuario.setSenha(et_senha.getText().toString());

        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        realm.beginTransaction();
        realm.insertOrUpdate(usuario);
        realm.commitTransaction();
    }





}
