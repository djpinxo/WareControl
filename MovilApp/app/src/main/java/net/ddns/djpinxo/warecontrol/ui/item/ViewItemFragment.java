package net.ddns.djpinxo.warecontrol.ui.item;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ViewItemFragment extends Fragment implements FragmentCallback<List<Item>> {


    private RecyclerView recyclerViewItem;
    private AdapterItemList adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private EditText textSearch;
    private ImageButton buttonSearch;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_item, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.item_view_title);


        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);
        textSearch = view.findViewById(R.id.textSearch);
        buttonSearch = view.findViewById(R.id.buttonSearch);

        recyclerViewItem = view.findViewById(R.id.recyclerViewItem);
        recyclerViewItem.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AdapterItemList(new ArrayList<Item>(), getActivity());
        recyclerViewItem.setAdapter(adapter);


        view.findViewById(R.id.buttonInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertItemFragment insertItemFragment = new InsertItemFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, insertItemFragment);

            }

        });

        MainActivity.itemDao.getItems(this);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textSearch.getText().toString().trim().equals(""))
                    MainActivity.itemDao.getItems(ViewItemFragment.this, textSearch.getText().toString().trim());
                else
                    MainActivity.itemDao.getItems(ViewItemFragment.this);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!textSearch.getText().toString().trim().equals(""))
                    MainActivity.itemDao.getItems(ViewItemFragment.this, textSearch.getText().toString().trim());
                else
                    MainActivity.itemDao.getItems(ViewItemFragment.this);

            }
        });

    }

    @Override
    public void callbackDataAcessSuccess(List<Item> items) {
        adapter.updateData(items);
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void callbackDataAcessError(List<Item> items) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}