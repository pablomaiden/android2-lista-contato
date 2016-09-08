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

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.activity.CadastrarContatosActivity;
import br.com.meuscontatos.principal.adapter.ContatoRecyclerViewAdapter;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;
import io.realm.RealmResults;

public class ListaContatosFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_contato_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_lista_contatos);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(lm);

        Realm realm = Service.getInstace().getRealm(getActivity());
        RealmResults<Contato> listaContatos = realm.where(Contato.class).findAll();
        ContatoRecyclerViewAdapter contatosAdapter = new ContatoRecyclerViewAdapter(getActivity(),listaContatos);
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
