package br.com.meuscontatos.principal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
        ViewHolder myViewHolder = new ViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        public TextView nomeCompleto;

        public ViewHolder(View itemView){
            super(itemView);
            nomeCompleto = (TextView)itemView.findViewById(R.id.nome_completo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
