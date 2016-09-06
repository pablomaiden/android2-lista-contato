package br.com.meuscontatos.principal;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


public class MainActivity extends ActionBarActivity {

    private Toolbar tbHead;
    private Toolbar tbBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        tbHead   = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbHead);
        tbBottom = (Toolbar) findViewById(R.id.tb_bottom);


    }

}
