package br.com.meuscontatos.principal.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.activity.CortarFotoActivity;

public class MapFragments extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_bluetooth_fragment, container, false);

        Intent intent = new Intent(getActivity(),MapaFragment.class);
        startActivity(intent);

        return view;
    }
}
