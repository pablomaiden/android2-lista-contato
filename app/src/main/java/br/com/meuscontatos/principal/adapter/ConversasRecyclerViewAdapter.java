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
import br.com.meuscontatos.principal.activity.ChatActivity;
import br.com.meuscontatos.principal.domain.Conversa;
import br.com.meuscontatos.principal.interfaces.*;
import br.com.meuscontatos.principal.service.Service;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class ConversasRecyclerViewAdapter extends RecyclerView.Adapter<ConversasRecyclerViewAdapter.ViewHolder>{

    private List<Conversa> conversas;
    private LayoutInflater layoutInflater;
    private Context context;
    static final int CHAT_REQUEST_FOR_RESULT = 36;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public ConversasRecyclerViewAdapter(Context context, List<Conversa> conversas) {
        this.context=context;
        this.conversas=conversas;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public Long getIdConversa(int position){
        Conversa conversa =  conversas.get(position);
        return conversa.getId();
    }

    public String getNomeConversa(int position){
        Conversa conversa =  conversas.get(position);
        return conversa.getNomeDest();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_conversas,viewGroup,false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        if(conversas.get(position).getUrlFotoDest()!=null && !conversas.get(position).getUrlFotoDest().isEmpty()){
           viewHolder.urlFotoDest.setImageURI(Uri.parse(conversas.get(position).getUrlFotoDest()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.urlFotoDest.setBackground(null);
            }
        }else {
           viewHolder.urlFotoDest.setImageResource(R.drawable.sem_foto);
        }
        viewHolder.nomeDest.setText(conversas.get(position).getNomeDest());
        viewHolder.lastMsg.setText(conversas.get(position).getLastMsg());
        YoYo.with(Techniques.FadeIn).duration(800).playOn(viewHolder.itemView);

        viewHolder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("idConversa", getIdConversa(position));
                //v.getContext().startActivityForResult(intent,CHAT_REQUEST_FOR_RESULT);
                v.getContext().startActivity(intent);
                notifyDataSetChanged();
            }
        });

        viewHolder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog builder = new AlertDialog.Builder(v.getContext())
                        .setTitle("Exclusão")
                        .setMessage("Deseja excluir a conversa "+getNomeConversa(position)+" ? ")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Realm realm = Service.getInstace().getRealm(context);
                                realm.beginTransaction();
                                realm.where(Conversa.class).equalTo("id",getIdConversa(position)).findFirst().deleteFromRealm();
                                realm.commitTransaction();
                                notifyItemRemoved(position);
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).create();
                builder.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return conversas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View v;
        public CircleImageView urlFotoDest;
        public TextView nomeDest;
        public TextView lastMsg;

        public ViewHolder(View view){
            super(view);
            v=view;
            urlFotoDest     = (CircleImageView) itemView.findViewById(R.id.urlFotoDest);
            nomeDest     = (TextView)  itemView.findViewById(R.id.nomeDest);
            lastMsg    = (TextView)  itemView.findViewById(R.id.lastMsg);
        }
    }
}
