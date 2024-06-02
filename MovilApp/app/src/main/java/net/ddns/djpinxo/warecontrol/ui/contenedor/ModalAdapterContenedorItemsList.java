package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.ui.item.SelectItemFragment;

import java.util.List;

public class ModalAdapterContenedorItemsList extends RecyclerView.Adapter<ModalAdapterContenedorItemsList.ViewHolder> implements FragmentCallback<List<Item>> {
    private List<Item> items;
    private Activity activity;
    private DialogFragment dialog;

    public ModalAdapterContenedorItemsList(List<Item> items, Activity activity, DialogFragment dialog) {
        this.items = items;
        this.activity = activity;
        this.dialog = dialog;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Item> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_list_item, parent, false);
        return new ViewHolder(view, activity, dialog);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.textViewId.setText(item.getId().toString());
        holder.textViewNombre.setText(item.getNombre().toString());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId;
        private TextView textViewNombre;
        private Activity activity;
        private DialogFragment dialog;

        public ViewHolder(View itemView, Activity activity, DialogFragment dialog) {
            super(itemView);
            this.activity = activity;
            this.dialog = dialog;
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);


            textViewId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    SelectItemFragment selectItemFragment=new SelectItemFragment(new Item(Long.valueOf(textViewId.getText().toString()), null, null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);

                }

            });

            textViewNombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    SelectItemFragment selectItemFragment=new SelectItemFragment(new Item(Long.valueOf(textViewId.getText().toString()), null, null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);
                }

            });

        }
    }

    @Override
    public void callbackDataAcessSuccess(List<Item> items) {
        updateData(items);
    }

    @Override
    public void callbackDataAcessError(List<Item> items) {
        Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}
