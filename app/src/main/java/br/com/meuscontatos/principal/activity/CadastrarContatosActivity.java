package br.com.meuscontatos.principal.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;

public class CadastrarContatosActivity extends AppCompatActivity {

    private EditText et_nome;
    private EditText et_email;
    private EditText telefone;
    public ImageView foto;
    private Uri mCropImageUri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_contatos_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        getSupportActionBar().setTitle("Cadastro Contato");

        et_nome = (EditText) findViewById(R.id.cad_nome);
        et_email = (EditText) findViewById(R.id.cad_email);
        telefone = (EditText) findViewById(R.id.cad_telefone);

        telefone.addTextChangedListener(Mask.insert("(##)#####-####", telefone));
        foto = (ImageView) findViewById(R.id.foto);

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                String cropped = (String) getIntent().getExtras().get("foto");

                if (cropped != null) {
                    foto.setImageURI(Uri.parse("file://" + cropped));
                    foto.setBackground(null);
                }
            }
        }
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
                validar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void validar() {
        boolean nome_preenchido = Validator.validateNotNull(et_nome, "Preencha o campo nome");
        boolean telefone_preenchido = Validator.validateNotNull(telefone, "Preencha o campo telefone");
        boolean email_preenchido = Validator.validateNotNull(et_email, "Preencha o campo email");
        boolean email_valido = Validator.validateEmail(et_email.getText().toString());

        if (nome_preenchido) {
            if (email_preenchido) {
                if (email_preenchido) {
                    if (email_valido) {
                        salvar();
                        finish();
                    } else {
                        if (!email_valido) {
                            et_email.setError("Email inválido");
                            et_email.setFocusable(true);
                            et_email.requestFocus();
                        }
                    }
                }
            }
        }
    }


    public void salvar() {
        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        Contato contato = new Contato();
        contato.setId(autoIncremento());
        contato.setNome(et_nome.getText().toString());
        contato.setEmail(et_email.getText().toString());
        contato.setTelefone(telefone.getText().toString());

        realm.beginTransaction();
        realm.insertOrUpdate(contato);
        realm.commitTransaction();
    }

    public Long autoIncremento() {
        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        Long isContem = realm.where(Contato.class).count();
        if (isContem == 0) {
        } else {
            return realm.where(Contato.class).max("id").longValue() + 1;
        }
        return 0L;
    }

    public void capturarFoto(View view) {
        Intent intent = new Intent(this, CortarFotoActivity.class);
        startActivity(intent);
    }
}
