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
import br.com.meuscontatos.principal.activity.CadastrarContatosActivity;
import br.com.meuscontatos.principal.activity.EditarContatosActivity;
import br.com.meuscontatos.principal.adapter.ContatoRecyclerViewAdapter;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.interfaces.RecyclerViewOnClickListenerHack;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;
import io.realm.RealmResults;


public class ListaContatosFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    static final int FOTO_REQUEST_FOR_RESULT = 10;
    static final int EDIT_REQUEST_FOR_RESULT = 15;
    ContatoRecyclerViewAdapter contatosAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_contato_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_lista_contatos);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(lm);

        Realm realm = Service.getInstace().getRealm(getActivity());
        RealmResults<Contato> listaContatos = realm.where(Contato.class).findAllSorted("nome");
        contatosAdapter = new ContatoRecyclerViewAdapter(getActivity(),listaContatos);
        contatosAdapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(contatosAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CadastrarContatosActivity.class);
                startActivityForResult(intent,FOTO_REQUEST_FOR_RESULT);
            }});
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Realm realm = Service.getInstace().getRealm(getActivity());
        int total = (int) realm.where(Contato.class).count();
        contatosAdapter.notifyItemInserted(total);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FOTO_REQUEST_FOR_RESULT) {
            Realm realm = Service.getInstace().getRealm(getActivity());
            int total = (int) realm.where(Contato.class).count();
            contatosAdapter.notifyItemInserted(total);
        }
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(view.getContext(),EditarContatosActivity.class);
        intent.putExtra("idContato",contatosAdapter.getIdContato(position));
        startActivityForResult(intent,EDIT_REQUEST_FOR_RESULT);
        contatosAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClickListener(View view, final int position) {
        AlertDialog builder = new AlertDialog.Builder(view.getContext())
                .setTitle("Exclusão")
                .setMessage("Deseja excluir ? ")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Realm realm = Service.getInstace().getRealm(getActivity());
                        realm.beginTransaction();
                        realm.where(Contato.class).equalTo("id",contatosAdapter.getIdContato(position)).findFirst().deleteFromRealm();
                        realm.commitTransaction();
                        contatosAdapter.notifyItemRemoved(position);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).create();
        builder.show();
    }
}
