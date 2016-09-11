package br.com.meuscontatos.principal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import java.util.List;
import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;

public class ContatoRecyclerViewAdapter extends RecyclerView.Adapter<ContatoRecyclerViewAdapter.ViewHolder>{

    private List<Contato> contatos;
    private LayoutInflater layoutInflater;
    private Context context;

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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_contatos,viewGroup,false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        //Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.my_image);
        viewHolder.foto.setImageResource(R.drawable.sem_foto);
        viewHolder.nome.setText(contatos.get(position).getNome());
        viewHolder.email.setText(contatos.get(position).getEmail());
        viewHolder.telefone.setText(contatos.get(position).getTelefone());
        YoYo.with(Techniques.FadeIn).duration(800).playOn(viewHolder.itemView);

        viewHolder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"On click",Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Realm realm = Service.getInstace().getRealm(v.getContext());
                AlertDialog builder = new AlertDialog.Builder(v.getContext())
                        .setTitle("Exclusão")
                        .setMessage("Deseja excluir "+getNomeContato(position)+" ? ")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                realm.beginTransaction();
                                realm.where(Contato.class).equalTo("id",getIdContato(position)).findFirst().deleteFromRealm();
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
        return contatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View v;
        public ImageView foto;
        public TextView nome;
        public TextView email;
        public TextView telefone;

        public ViewHolder(View view){
            super(view);
            v=view;
            foto     = (ImageView) itemView.findViewById(R.id.foto);
            nome     = (TextView)  itemView.findViewById(R.id.nome);
            email    = (TextView)  itemView.findViewById(R.id.email);
            telefone = (TextView)  itemView.findViewById(R.id.telefone);
        }
    }
}
