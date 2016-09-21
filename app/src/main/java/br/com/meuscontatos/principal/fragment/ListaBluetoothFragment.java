package br.com.meuscontatos.principal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.activity.CadastrarContatosActivity;
import br.com.meuscontatos.principal.adapter.BluetoothRecyclerViewAdapter;
import br.com.meuscontatos.principal.adapter.ContatoRecyclerViewAdapter;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import br.com.meuscontatos.principal.vo.ContatoVO;
import io.realm.Realm;
import io.realm.RealmResults;

public class ListaBluetoothFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_bluetooth_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_lista_bluetooth);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(lm);

        List<ContatoVO> listaBluetooth = new ArrayList<ContatoVO>();

        ContatoVO c1 = new ContatoVO("nometeste1", "sobrenometeste1", "111111111", "nometeste1@email.com", null, "BLUETOOTH1");
        ContatoVO c2 = new ContatoVO("nometeste2", "sobrenometeste2", "222222222", "nometeste2@email.com", null, "BLUETOOTH2");
        ContatoVO c3 = new ContatoVO("nometeste3", "sobrenometeste3", "333333333", "nometeste3@email.com", null, "BLUETOOTH3");

        listaBluetooth.add(c1);
        listaBluetooth.add(c2);
        listaBluetooth.add(c3);

        //TODO
        //Create a list of bluetooth contacts

        BluetoothRecyclerViewAdapter contatosAdapter = new BluetoothRecyclerViewAdapter(getActivity(),listaBluetooth);

        mRecyclerView.setAdapter(contatosAdapter);

        //TODO ação de clicar no floating button - ficar visível e buscar por outros dispositivos par adicioná-os a lista de pareados.
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CadastrarContatosActivity.class);
                startActivity(intent);
            }});

        return view;
    }

}
