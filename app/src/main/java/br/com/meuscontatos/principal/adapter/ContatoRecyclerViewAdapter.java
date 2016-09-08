package br.com.meuscontatos.principal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import java.util.List;
import br.com.meuscontatos.principal.R;
import br.com.meuscontatos.principal.domain.Contato;

public class ContatoRecyclerViewAdapter extends RecyclerView.Adapter<ContatoRecyclerViewAdapter.ViewHolder>{

    private List<Contato> contatos;
    private LayoutInflater layoutInflater;
    private Context context;

    public ContatoRecyclerViewAdapter(Context context, List<Contato> contatos) {
        this.context=context;
        this.contatos=contatos;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_contatos,viewGroup,false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.my_image);
        viewHolder.foto.setImageResource(R.drawable.sem_foto);
        viewHolder.nome.setText(contatos.get(position).getNome());
        viewHolder.email.setText(contatos.get(position).getEmail());
        viewHolder.telefone.setText(contatos.get(position).getTelefone());
        YoYo.with(Techniques.FadeIn).duration(500).playOn(viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView foto;
        public TextView nome;
        public TextView email;
        public TextView telefone;

        public ViewHolder(View itemView){
            super(itemView);
            foto     = (ImageView) itemView.findViewById(R.id.foto);
            nome     = (TextView)  itemView.findViewById(R.id.nome);
            email    = (TextView)  itemView.findViewById(R.id.email);
            telefone = (TextView)  itemView.findViewById(R.id.email);
        }
    }
}
