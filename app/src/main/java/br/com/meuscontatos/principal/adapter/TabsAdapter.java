package br.com.meuscontatos.principal.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.com.meuscontatos.principal.fragment.ConversarFragment;
import br.com.meuscontatos.principal.fragment.ListaBluetoothFragment;
import br.com.meuscontatos.principal.fragment.ListaContatosFragment;
import br.com.meuscontatos.principal.fragment.MapFragments;
import br.com.meuscontatos.principal.fragment.MapaFragment;

public class TabsAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles = {"CONVERSAS", "CONTATOS", "BLUETOOTH", "MAPA"};
    //private int[] icons = new int[]{R.drawable.account, R.drawable.almoco_, R.drawable.bell_ring, R.drawable.account, R.drawable.account};
    //private int heightIcon;

    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        mContext = c;
        double scale = c.getResources().getDisplayMetrics().density;
        //heightIcon = (int)( 24 * scale + 0.5f );
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0: frag    = new ConversarFragment(); break;
            case 1: frag    = new ListaContatosFragment(); break;
            case 2: frag    = new ListaBluetoothFragment(); break;
            case 3: frag    = new MapFragments(); break;

        }

        Bundle b = new Bundle();
        b.putInt("position", position);
        frag.setArguments(b);
        return frag;
    }



    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        /*Drawable d = mContext.getResources().getDrawable( icons[position] );
        d.setBounds(0, 0, heightIcon, heightIcon);
        ImageSpan is = new ImageSpan( d );
        SpannableString sp = new SpannableString(" ");
        sp.setSpan( is, 0, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        return ( sp );*/
        return ( titles[position] );
    }
}