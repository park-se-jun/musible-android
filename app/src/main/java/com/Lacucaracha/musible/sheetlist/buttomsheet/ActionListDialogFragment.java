package com.lacucaracha.musible.sheetlist.buttomsheet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.lacucaracha.musible.R;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ImageListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ActionListDialogFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private static final String TAG = "ImageListDialog";
    // TODO: Customize parameters
    public static ActionListDialogFragment newInstance(int itemCount) {
        final ActionListDialogFragment fragment = new ActionListDialogFragment();
        return fragment;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.textView).setOnClickListener(v->{
                    Toast.makeText(getContext(),"갤러리",Toast.LENGTH_SHORT).show();
                }
        );
        view.findViewById(R.id.textView2).setOnClickListener(v->{
            Toast.makeText(getContext(),"카메라",Toast.LENGTH_SHORT).show();
        });
    }
}