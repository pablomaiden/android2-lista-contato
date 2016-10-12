package br.com.meuscontatos.principal.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import br.com.meuscontatos.principal.activity.ChatActivity;
import br.com.meuscontatos.principal.activity.ConversaActivity;
import br.com.meuscontatos.principal.adapter.ConversasRecyclerViewAdapter;
import br.com.meuscontatos.principal.domain.Conversa;
import br.com.meuscontatos.principal.interfaces.RecyclerViewOnClickListenerHack;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;
import io.realm.RealmResults;


public class ListaConversasFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    static final int CHAT_REQUEST_FOR_RESULT = 35;
    ConversasRecyclerViewAdapter conversasAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_conversas_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_lista_conversas);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(lm);

        Realm realm = Service.getInstace().getRealm(getActivity());
        RealmResults<Conversa> listaConversas = realm.where(Conversa.class).findAll();

        conversasAdapter = new ConversasRecyclerViewAdapter(getActivity(),listaConversas);
        //conversasAdapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(conversasAdapter);





        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabConversas);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                // 1 - listar os Contatos disponíveis
                // 2 - Ao selecionar o Contato desejado, iniciar uma conversa
                // 3 - Abrir um chat
                //int position = 0; //Remover quando mover esse código para um clique em um item da lista, e utilizar o position do item clicado
                //Intent intent = new Intent(getActivity(),ChatActivity.class);
                Intent intent = new Intent(getActivity(),ConversaActivity.class);
                //if(position > 0){
                //    intent.putExtra("idConversa",conversasRecyclerViewAdapter.getIdConversa(position));
                //}
                startActivityForResult(intent,CHAT_REQUEST_FOR_RESULT);
                conversasAdapter.notifyDataSetChanged();
            }});
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHAT_REQUEST_FOR_RESULT) {
            Realm realm = Service.getInstace().getRealm(getActivity());
            int total = (int) realm.where(Conversa.class).count();
            conversasAdapter.notifyItemInserted(total);
        }
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(view.getContext(),ChatActivity.class);
        intent.putExtra("idConversa",conversasAdapter.getIdConversa(position));
        startActivityForResult(intent,CHAT_REQUEST_FOR_RESULT);
        conversasAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClickListener(View view, final int position) {
        AlertDialog builder = new AlertDialog.Builder(view.getContext())
                .setTitle("Exclusão")
                .setMessage("Deseja excluir a conversa ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Realm realm = Service.getInstace().getRealm(getActivity());
                        realm.beginTransaction();
                        realm.where(Conversa.class).equalTo("id",conversasAdapter.getIdConversa(position)).findFirst().deleteFromRealm();
                        realm.commitTransaction();
                        conversasAdapter.notifyItemRemoved(position);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).create();
        builder.show();
    }

}
