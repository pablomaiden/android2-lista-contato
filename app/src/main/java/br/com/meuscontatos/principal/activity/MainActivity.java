package br.com.meuscontatos.principal.activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Usuario;
import br.com.meuscontatos.principal.fragment.TabContatosPrincipalFragment;
import br.com.meuscontatos.principal.service.Service;
import br.com.meuscontatos.principal.util.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;


public class MainActivity extends ActionBarActivity {

    private Toolbar tbHead;

    private CircleImageView foto_perfil_autenticacao;
    private TextView tv_nome_usuario_firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        Usuario user = realm.where(Usuario.class).findFirst();
        foto_perfil_autenticacao = (CircleImageView) findViewById(R.id.foto_perfil_autenticacao);
        tv_nome_usuario_firebase = (TextView) findViewById(R.id.tv_nome_usuario_firebase);

        if(user!=null && user.getUrlFotoFireBase()!=null){
            //Method: Google
            int loader = R.drawable.bubble2;
            String image_url = user.getUrlFotoFireBase();
            ImageLoader imgLoader = new ImageLoader(getApplicationContext());
            imgLoader.DisplayImage(image_url, loader, foto_perfil_autenticacao);
            tv_nome_usuario_firebase.setText(user.getNameUserFireBase());
        }

        tbHead   = (Toolbar) findViewById(R.id.toolbar);
        TabContatosPrincipalFragment listaContatos = (TabContatosPrincipalFragment) getSupportFragmentManager().findFragmentByTag("fragListaContatos");
        if(listaContatos==null){
            listaContatos=new TabContatosPrincipalFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,listaContatos,"fragListaContatos");
            ft.commit();
        }
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            //Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }

}
