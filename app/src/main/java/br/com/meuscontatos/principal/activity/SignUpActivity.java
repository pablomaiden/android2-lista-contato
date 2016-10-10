package br.com.meuscontatos.principal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Usuario;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.R.attr.password;
import static com.google.android.gms.R.id.email;

public class SignUpActivity extends Activity {

    private EditText et_nome;
    private EditText et_email;
    private EditText et_senha;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        et_nome  = (EditText) findViewById(R.id.et_nome);
        et_email = (EditText) findViewById(R.id.et_email);
        et_senha = (EditText) findViewById(R.id.et_senha);
        mAuth = FirebaseAuth.getInstance();
    }

    public void salvar(View view){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsuario(et_nome.getText().toString());
        usuario.setUsuario(et_email.getText().toString());
        usuario.setSenha(et_senha.getText().toString());

        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        realm.beginTransaction();
        realm.insertOrUpdate(usuario);
        realm.commitTransaction();

        mAuth.createUserWithEmailAndPassword(et_email.getText().toString(), et_senha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            //Toast.makeText(SignUpActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
