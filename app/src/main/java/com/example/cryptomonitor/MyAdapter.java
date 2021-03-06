package com.example.cryptomonitor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public MyAdapter(List<CardItems> cardItemsList, Context context) {

        this.cardItemsList = cardItemsList;
        this.context = context;
    }

    private List<CardItems> cardItemsList;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_items, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        CardItems cardItem = cardItemsList.get(position);

        final String curr = cardItem.getCurrency();
        final double btcVal = cardItem.getBtc_value();
        final double EthVal = cardItem.getEth_value();

        holder.currency.setText(curr);
        holder.bitcoinTv.setText(String.format("%1$,.2f", btcVal));
        holder.etheriumTv.setText(String.format("%1$,.2f", EthVal));


    }

    @Override
    public int getItemCount() {
        return cardItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bitcoinTv, etheriumTv, currency;
        LinearLayout cardLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            bitcoinTv = itemView.findViewById(R.id.bitcoinTv);
            etheriumTv = itemView.findViewById(R.id.etheriumTv);
            currency = itemView.findViewById(R.id.textViewCurrency);
            cardLinearLayout = itemView.findViewById(R.id.cardLinearLayout);
        }
    }
}
