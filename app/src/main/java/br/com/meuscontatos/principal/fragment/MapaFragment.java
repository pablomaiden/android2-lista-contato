package br.com.meuscontatos.principal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

import br.com.meuscontatos.principal.activity.CadastrarContatosActivity;
import br.com.meuscontatos.principal.activity.MapActivity;


public class MapaFragment extends SupportMapFragment {

    public MapaFragment() {
    }

    public static MapaFragment newInstance() {
        return new MapaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Intent intent = new Intent(getActivity(), MapActivity.class);
        MapaFragment.this.startActivity(intent);
        return view;
    }

}