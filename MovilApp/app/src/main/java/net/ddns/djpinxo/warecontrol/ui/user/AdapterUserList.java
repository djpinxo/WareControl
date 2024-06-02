package net.ddns.djpinxo.warecontrol.ui.user;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
                    //Toast.makeText(v.getContext().getApplicationContext(), "evento consulta pulsado"+textViewEmail.getText(), Toast.LENGTH_LONG).show();
                    SelectUserFragment selectUserFragment=new SelectUserFragment(new User(textViewEmail.getText().toString(), null, null));
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);

                }

            });
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext().getApplicationContext(), "evento modificar pulsado"+textViewEmail.getText(), Toast.LENGTH_LONG).show();
                    UpdateUserFragment updateUserFragment=new UpdateUserFragment(userModel);
                    ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, updateUserFragment);
                }

            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext().getApplicationContext(), "evento borrar pulsado"+textViewEmail.getText(), Toast.LENGTH_LONG).show();
                }

            });

            userModel = new User(textViewEmail.getText().toString(), null, null);

        }
    }
}
