package com.lacucaracha.musible.util;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.sheetlist.RecyclerAdapter;

import java.util.List;

public class DatabindingUtils {
    @BindingAdapter("app:items")
    public static void setItem(RecyclerView recyclerView, List<MusicSheet> items){
        RecyclerAdapter adapter = (RecyclerAdapter) recyclerView.getAdapter();
        if(adapter !=null){
            adapter.replaceData(items);
        }
    }
}
