package uz.micro.star.sihatgoh.dialogs;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uz.micro.star.sihatgoh.R;
import uz.micro.star.sihatgoh.adapters.SanatoriumAdapterNew;
import uz.micro.star.sihatgoh.models.SanatoriumTinyData;

public class SearchSanDialog extends AlertDialog {
    OnDialogClickListener onDialogClickListener;
    private SearchView searchView;
    private RecyclerView list;
    private ArrayList<SanatoriumTinyData> data;
    private ArrayList<SanatoriumTinyData> newData = new ArrayList<>();
    private SanatoriumAdapterNew adapter;

    public SearchSanDialog(@NonNull Context context, ArrayList<SanatoriumTinyData> tinyData) {
        super(context);
        getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        getWindow().setGravity(Gravity.TOP);
        setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_search_nav, null, false);
        searchView = view.findViewById(R.id.searchView);
        list = view.findViewById(R.id.list);
//        data=Database.getDatabase().getSanatories();
        data = tinyData;
        adapter = new SanatoriumAdapterNew(newData, context);
//        adapter = new SanatoriumAdapter(newData);
        list.setLayoutManager(new LinearLayoutManager(context));
        list.setAdapter(adapter);

        adapter.setClickListener((id, s) -> {
            if (onDialogClickListener != null) {
                onDialogClickListener.onDialogClick(s, id);
                dismiss();
            }
            return null;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(context, newText, Toast.LENGTH_SHORT).show();
                newData.clear();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getName().toLowerCase().startsWith(newText.toLowerCase())) {
                        newData.add(data.get(i));
                    }
                }
                if (newText.isEmpty()) newData.clear();
                adapter.notifyDataSetChanged();
//                adapter.setNewData(newData);
                return true;
            }
        });
        setView(view);
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public interface OnDialogClickListener {
        void onDialogClick(String name, int id);
    }
}