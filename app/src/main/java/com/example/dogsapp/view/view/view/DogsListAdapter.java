package com.example.dogsapp.view.view.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogsapp.R;
import com.example.dogsapp.view.view.model.DogBreed;
import com.example.dogsapp.view.view.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DogsListAdapter extends RecyclerView.Adapter<DogsListAdapter.DogViewHolder> {

    private ArrayList<DogBreed> dogsList;

    public  DogsListAdapter(ArrayList<DogBreed> dogsList){
        this.dogsList = dogsList;
    }

    public void updateDogsList(List<DogBreed> newDogList){
        dogsList.clear();
        dogsList.addAll(newDogList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog, parent,
                false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        ImageView image = holder.itemview.findViewById(R.id.imageView);
        TextView name = holder.itemview.findViewById(R.id.name);
        TextView lifespan = holder.itemview.findViewById(R.id.lifespan);

        name.setText(dogsList.get(position).dogBreed);
        lifespan.setText(dogsList.get(position).lifeSpan);
        Util.loadImage(image, dogsList.get(position).imageUrl, Util.getProgressDrawable(image.getContext()));
    }

    @Override
    public int getItemCount() {
        return dogsList.size();
    }

    class DogViewHolder extends RecyclerView.ViewHolder{

        public View itemview;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemview = itemView;
        }
    }

}
