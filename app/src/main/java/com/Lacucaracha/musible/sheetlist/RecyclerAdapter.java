package com.lacucaracha.musible.sheetlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.lacucaracha.musible.R;
import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.databinding.SheetListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<MusicSheet> mList;




//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        SheetListItemBinding binding;
//        if(convertView == null){
//            LayoutInflater infater = LayoutInflater.from(parent.getContext());
//            binding = SheetListItemBinding.inflate(infater,parent,false);
//        }else{
//            binding = DataBindingUtil.getBinding(convertView);
//        }
//        binding.setMusicSheet(mList.get(position));
//        binding.setLifecycleOwner(mLifeCycleOwner);
//        binding.executePendingBindings();;
//        return binding.getRoot();
//    }
    private void setList(List<MusicSheet> musicSheets){
        this.mList = musicSheets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SheetListItemBinding listItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.sheet_list_item,parent,false);
        return new MyViewHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MusicSheet currentMusicSheet = mList.get(position);
        holder.mListitemBinding.setMusicSheet(currentMusicSheet);
    }

    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }else{
            return 0;
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private final SheetListItemBinding mListitemBinding;
        public MyViewHolder(@NonNull SheetListItemBinding listItemBinding){
            super(listItemBinding.getRoot());
            this.mListitemBinding=listItemBinding;
        }
    }

}
