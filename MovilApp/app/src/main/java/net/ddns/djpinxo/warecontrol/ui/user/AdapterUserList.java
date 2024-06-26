package net.ddns.djpinxo.warecontrol.ui.user;

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
import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.List;

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.ViewHolder> {
    private List<User> users;
    private Activity activity;

    public AdapterUserList(List<User> users, Activity activity) {
        this.users = users;
        this.activity = activity;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<User> users) {
        this.users = users;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail_list, parent, false);
        return new ViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewEmail.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewEmail;
        private ImageButton buttonUpdate;
        private ImageButton buttonDelete;
        private Activity activity;
        private User userModel;

        public ViewHolder(View itemView, Activity activity) {
            super(itemView);
            this.activity = activity;
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);


            textViewEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectUserFragment selectUserFragment=new SelectUserFragment(new User(textViewEmail.getText().toString(), null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);

                }

            });
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateUserFragment updateUserFragment=new UpdateUserFragment(new User(textViewEmail.getText().toString(), null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, updateUserFragment);
                }

            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DeleteUserFragment(new User(textViewEmail.getText().toString(), null, null)).showConfirmationDialog(activity);
                }

            });

        }
    }
}
