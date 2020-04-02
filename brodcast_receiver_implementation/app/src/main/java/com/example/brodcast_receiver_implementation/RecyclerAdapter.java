package com.example.brodcast_receiver_implementation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ContactViewHolder> {



    private ArrayList<Contact> arrayList = new ArrayList<Contact>();
    public RecyclerAdapter(ArrayList<Contact> arrayList)
    {
        this.arrayList=arrayList;

    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.ID.setText(Integer.toString(arrayList.get(position).getId()));
        holder.NUMBER.setText(arrayList.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder
    {
        TextView ID,NUMBER;
        public ContactViewHolder(View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.textId);
            NUMBER=itemView.findViewById(R.id.textNumber);
        }
    }
}
