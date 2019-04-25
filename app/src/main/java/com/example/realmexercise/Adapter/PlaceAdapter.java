package com.example.realmexercise.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.realmexercise.Object.Place;
import com.example.realmexercise.R;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolderPlace> implements View.OnClickListener, View.OnLongClickListener {

    public class ViewHolderPlace extends RecyclerView.ViewHolder{
        TextView placeName;
        TextView placeDescription;

        public ViewHolderPlace(View itemView){
            super(itemView);

            placeName = itemView.findViewById(R.id.tv_itemName);
            placeDescription = itemView.findViewById(R.id.tv_itemDescription);
        }
    }

    private Context context;
    private List<Place> listPlaces;
    private View.OnClickListener clickListener;
    private View.OnLongClickListener longClickListener;

    public PlaceAdapter(Context context, List<Place> places){
        this.context = context;
        this.listPlaces = places;
    }

    @NonNull
    @Override
    public ViewHolderPlace onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place, viewGroup, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolderPlace(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPlace viewHolderPlace, int i) {
        viewHolderPlace.placeName.setText(listPlaces.get(i).getName());
        viewHolderPlace.placeDescription.setText(listPlaces.get(i).getDescription());

    }

    @Override
    public int getItemCount() {
        return listPlaces.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.clickListener = listener;
    }


    @Override
    public void onClick(View v) {
        if(clickListener != null){
            clickListener.onClick(v);
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener){
        this.longClickListener = onLongClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        if(longClickListener != null){
            longClickListener.onLongClick(v);
        }

        return false;
    }

}
