package br.com.meuscontatos.principal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.activity.EditarContatosActivity;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import br.com.meuscontatos.principal.vo.ContatoVO;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class BluetoothRecyclerViewAdapter extends RecyclerView.Adapter<BluetoothRecyclerViewAdapter.ViewHolder>{

    private List<String> listaBluetooth;
    private LayoutInflater layoutInflater;
    private Context context;

    public BluetoothRecyclerViewAdapter(Context context, List<String> listaBluetooth) {
        this.context=context;
        this.listaBluetooth=listaBluetooth;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    public Long getIdContatoVO(int position){
//        ContatoVO contatoVO =  contatosVO.get(position);
//        return contatoVO.getId();
//    }
//
//    public String getNomeContatoVO(int position){
//        ContatoVO contatoVO =  contatosVO.get(position);
//        return contatoVO.getNome();
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_bluetooth,viewGroup,false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {

        viewHolder.labelBluetooth.setText(listaBluetooth.get(position));
        YoYo.with(Techniques.FadeIn).duration(800).playOn(viewHolder.itemView);


        viewHolder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //TODO ação ao clicar em um contatoVO na listagem Bluetooth
//                Intent intent = new Intent(v.getContext(),EditarContatosActivity.class);
//                intent.putExtra("idContato",getIdContatoVO(position));
//                v.getContext().startActivity(intent);
            }
        });

        viewHolder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { //TODO ação ao fazer clique longo em um contatoVO na listagem Bluetooth
//                final Realm realm = Service.getInstace().getRealm(v.getContext());
//                AlertDialog builder = new AlertDialog.Builder(v.getContext())
//                        .setTitle("Exclusão")
//                        .setMessage("Deseja excluir "+getNomeContato(position)+" ? ")
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                realm.beginTransaction();
//                                realm.where(Contato.class).equalTo("id",getIdContato(position)).findFirst().deleteFromRealm();
//                                realm.commitTransaction();
//                                notifyItemRemoved(position);
//                            }
//                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).setIcon(android.R.drawable.ic_dialog_alert).create();
//                builder.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaBluetooth.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View v;
        public TextView labelBluetooth;


        public ViewHolder(View view){
            super(view);
            v=view;

            labelBluetooth = (TextView) itemView.findViewById(R.id. labelBluetooth);
            //nome     = (TextView)  itemView.findViewById(R.id.nome);
            //email    = (TextView)  itemView.findViewById(R.id.email);
            //telefone = (TextView)  itemView.findViewById(R.id.telefone);
        }
    }
}
