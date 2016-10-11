package br.com.meuscontatos.principal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.melnykov.fab.FloatingActionButton;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.activity.BluetoothDashboardActivity;

public class MapaFragment extends SupportMapFragment {


    private MapaFragment mapFragment;

    public MapaFragment (){

    }

    public static MapaFragment newInstance(){
        return new MapaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = super.onCreateView(inflater, container, savedInstanceState);
        return root;

    }

}