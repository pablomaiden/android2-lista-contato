package br.com.meuscontatos.principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import br.com.meuscontatos.principal.domain.Usuario;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;

public class SignUpActivity extends AppCompatActivity {

    private EditText et_login;
    private EditText et_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        et_login  = (EditText) findViewById(R.id.et_login);
        et_senha  = (EditText) findViewById(R.id.et_senha);
    }

    public void registrar(View view){
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }

    public void autenticar(View view){
        Usuario usuario = new Usuario();
        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        Usuario usuario_= realm.where(Usuario.class).equalTo("usuario",et_login.getText().toString()).equalTo("senha",et_senha.getText().toString()).findFirst();

        if(usuario_==null){

        }else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    }

}
