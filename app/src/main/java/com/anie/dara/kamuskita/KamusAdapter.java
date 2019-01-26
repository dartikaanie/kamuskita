package com.anie.dara.kamuskita;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.KamusHolder> {
    private OnKlikKeyword klikKeyword;
    private ArrayList<kamus> dataKamus = new ArrayList<>();

    public void addItem(ArrayList<kamus> data) {
        this.dataKamus = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public KamusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus, parent, false);
        return new KamusHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull KamusHolder holder, int position) {
        kamus kamusItem = dataKamus.get(position);
        holder.tvKeyword.setText(kamusItem.getKeyword());

    }

    @Override
    public int getItemCount() {
        if(dataKamus != null){
            return dataKamus.size();
        }
        return 0;
    }

    public class KamusHolder extends RecyclerView.ViewHolder{
        TextView tvKeyword;
        public KamusHolder(View itemView) {
            super(itemView);
            tvKeyword = itemView.findViewById(R.id.keywordItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kamus kamusItem = dataKamus.get(getAdapterPosition());
                    klikKeyword.OnKlikKeyword(kamusItem);
                }
            });
        }

    }

    public interface OnKlikKeyword{
        void OnKlikKeyword (kamus kamusItem);

    }

    public void setClickHandler(OnKlikKeyword clickHandler) {
        this.klikKeyword = clickHandler;
    }

}
