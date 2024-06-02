package net.ddns.djpinxo.warecontrol.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.ArrayList;
import java.util.List;

public class ViewItemFragment extends Fragment implements FragmentCallback<List<Item>> {


    private RecyclerView recyclerViewItem;
    private AdapterItemList adapter;
    //private List<Item> itemList = BBDDExample.getItems();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_item, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        /*recyclerViewItem = view.findViewById(R.id.recyclerViewItem);
        recyclerViewItem.setLayoutManager(new LinearLayoutManager(getContext()));

        //adapter = new AdapterItemList(MainActivity.itemDao.getItems(), getActivity());
        adapter = new AdapterItemList(new ArrayList<Item>(), getActivity());
        recyclerViewItem.setAdapter(adapter);


        view.findViewById(R.id.buttonInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertItemFragment insertItemFragment = new InsertItemFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, insertItemFragment);

            }

        });

        MainActivity.itemDao.getItems(this);*/

    }

    @Override
    public void callbackDataAcessSuccess(List<Item> items) {
        adapter.updateData(items);
    }

    @Override
    public void callbackDataAcessError(List<Item> items) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
    /*
    private void addItem(Item item) {
        itemList.add(item);
        adapter.notifyItemInserted(itemList.size() - 1);
    }*/
}