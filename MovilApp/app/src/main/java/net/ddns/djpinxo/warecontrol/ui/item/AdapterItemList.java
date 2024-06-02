package net.ddns.djpinxo.warecontrol.ui.item;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;

import java.util.List;

public class AdapterItemList extends RecyclerView.Adapter<AdapterItemList.ViewHolder> {
    private List<Item> items;
    private Activity activity;

    public AdapterItemList(List<Item> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Item> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_list, parent, false);
        return new ViewHolder(view, activity);
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
        private ImageButton buttonUpdate;
        private ImageButton buttonDelete;
        private Activity activity;
        private Item itemModel;

        public ViewHolder(View itemView, Activity activity) {
            super(itemView);
            this.activity = activity;
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);


            textViewId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectItemFragment selectItemFragment=new SelectItemFragment(new Item(Long.valueOf(textViewId.getText().toString()), null, null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);

                }

            });

            textViewNombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectItemFragment selectItemFragment=new SelectItemFragment(new Item(Long.valueOf(textViewId.getText().toString()), null, null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);

                }

            });
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateItemFragment updateItemFragment=new UpdateItemFragment(new Item(Long.valueOf(textViewId.getText().toString()), null, null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, updateItemFragment);
                }

            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DeleteItemFragment(new Item(Long.valueOf(textViewId.getText().toString()), null, null, null)).showConfirmationDialog(activity);
                }

            });

        }
    }
}
