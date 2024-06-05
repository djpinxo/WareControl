package net.ddns.djpinxo.warecontrol.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ViewContenedorFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewUserFragment extends Fragment implements FragmentCallback<List<User>> {


    private RecyclerView recyclerViewUser;
    private AdapterUserList adapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_user, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.user_view_title);

        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);

        recyclerViewUser = view.findViewById(R.id.recyclerViewUser);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AdapterUserList(new ArrayList<User>(), getActivity());
        recyclerViewUser.setAdapter(adapter);


        view.findViewById(R.id.buttonInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertUserFragment insertUserFragment = new InsertUserFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, insertUserFragment);

            }

        });

        MainActivity.userDao.getUsers(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.userDao.getUsers(ViewUserFragment.this);

            }
        });

    }

    @Override
    public void callbackDataAcessSuccess(List<User> users) {
        adapter.updateData(users);
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void callbackDataAcessError(List<User> users) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}