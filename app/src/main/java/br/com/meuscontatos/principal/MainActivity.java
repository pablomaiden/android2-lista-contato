package br.com.meuscontatos.principal;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import br.com.meuscontatos.principal.fragment.ListaContatosFragment;
import br.com.meuscontatos.principal.fragment.TabContatosPrincipalFragment;


public class MainActivity extends ActionBarActivity {

    private Toolbar tbHead;
    private Toolbar tbBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        tbHead   = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(tbHead);
        tbBottom = (Toolbar) findViewById(R.id.tb_bottom);

        TabContatosPrincipalFragment listaContatos = (TabContatosPrincipalFragment) getSupportFragmentManager().findFragmentByTag("fragListaContatos");
        if(listaContatos==null){
            listaContatos=new TabContatosPrincipalFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,listaContatos,"fragListaContatos");
            ft.commit();
        }
    }

}
