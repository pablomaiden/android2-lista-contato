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
import br.com.meuscontatos.principal.adapter.ContatoRecyclerViewAdapter;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;
import io.realm.RealmResults;

// TODO: 08/09/2016  - Atualizar a lista depois da inclusão do contato

public class ListaNetworkFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_network_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_lista_network);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(lm);

//        Realm realm = Service.getInstace().getRealm(getActivity());
//        RealmResults<Contato> listaNetwork = realm.where(Contato.class).findAll();
        List<Contato> listaNetwork = new ArrayList<Contato>();

        Contato c1 = new Contato("nometeste1", "sobrenometeste1", "111111111", "nometeste1@email.com", null);
        Contato c2 = new Contato("nometeste2", "sobrenometeste2", "222222222", "nometeste2@email.com", null);
        Contato c3 = new Contato("nometeste3", "sobrenometeste3", "333333333", "nometeste3@email.com", null);

        listaNetwork.add(c1);
        listaNetwork.add(c2);
        listaNetwork.add(c3);

        //TODO
        //Create a list of bluetooth contacts/network

        ContatoRecyclerViewAdapter contatosAdapter = new ContatoRecyclerViewAdapter(getActivity(),listaNetwork);
        mRecyclerView.setAdapter(contatosAdapter);

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
