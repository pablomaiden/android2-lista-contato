package br.com.meuscontatos.principal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import br.com.meuscontatos.principal.interfaces.RecyclerViewOnClickListenerHack;

public class ContatoRecyclerViewAdapter extends RecyclerView.Adapter<ContatoRecyclerViewAdapter.ViewHolder>{

    private List<Contato> contatos;
    private LayoutInflater layoutInflater;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public ContatoRecyclerViewAdapter(Context context, List<Contato> contatos) {
        this.context=context;
        this.contatos=contatos;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Long getIdContato(int position){
        Contato contato =  contatos.get(position);
        return contato.getId();
    }

    public String getNomeContato(int position){
        Contato contato =  contatos.get(position);
        return contato.getNome();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_contatos,viewGroup,false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        if(contatos.get(position).getUrlFoto()!=null && !contatos.get(position).getUrlFoto().isEmpty()){
           viewHolder.foto.setImageURI(Uri.parse(contatos.get(position).getUrlFoto()));
           //viewHolder.foto.setImageResource(R.drawable.sem_foto);
        }
        viewHolder.nome.setText(contatos.get(position).getNome());
        viewHolder.email.setText(contatos.get(position).getEmail());
        viewHolder.telefone.setText(contatos.get(position).getTelefone());
        YoYo.with(Techniques.FadeIn).duration(800).playOn(viewHolder.itemView);

        viewHolder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EditarContatosActivity.class);
                intent.putExtra("idContato",getIdContato(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public View v;
        public CircleImageView foto;
        public TextView nome;
        public TextView email;
        public TextView telefone;

        public ViewHolder(View view){
            super(view);
            v=view;
            foto     = (CircleImageView) itemView.findViewById(R.id.foto);
            nome     = (TextView)  itemView.findViewById(R.id.nome);
            email    = (TextView)  itemView.findViewById(R.id.email);
            telefone = (TextView)  itemView.findViewById(R.id.telefone);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onLongClickListener(v, getPosition());
                return true;
            }
            return false;
        }
    }
}
