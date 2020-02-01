package uz.micro.star.sihatgoh.dialogs;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uz.micro.star.sihatgoh.R;
import uz.micro.star.sihatgoh.adapters.SanatoriumAdapterNew;
import uz.micro.star.sihatgoh.models.SanatoriumTinyData;

public class ConnectionDialog extends AlertDialog {
    OnDialogClickListener onDialogClickListener;
    Button button;
    public ConnectionDialog(@NonNull Context context) {
        super(context);
        getWindow().getAttributes().windowAnimations = R.style.DialogThemeConnection;
//        getWindow().setGravity(Gravity.TOP);
        setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_connection, null, false);
        button = view.findViewById(R.id.btn);
        button.setOnClickListener(v -> {
            if (onDialogClickListener!=null)onDialogClickListener.onDialogClick("ok");
        });
        setView(view);
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public interface OnDialogClickListener {
        void onDialogClick(String name);
    }
}