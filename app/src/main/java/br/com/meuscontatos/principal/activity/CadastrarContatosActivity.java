package br.com.meuscontatos.principal.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;

public class CadastrarContatosActivity extends AppCompatActivity {

    private EditText et_nome;
    private EditText et_email;
    private EditText telefone;
    public ImageView foto;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_contatos_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        getSupportActionBar().setTitle("Cadastro Contato");

        et_nome  = (EditText)  findViewById(R.id.cad_nome);
        et_email = (EditText)  findViewById(R.id.cad_email);
        telefone = (EditText)  findViewById(R.id.cad_telefone);
        foto     = (ImageView) findViewById(R.id.foto);
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
            contato.setTelefone(telefone.getText().toString());

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

    public void acionarCamera(View view) throws IOException {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
        createImageFile();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                foto.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
