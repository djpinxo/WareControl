package net.ddns.djpinxo.warecontrol.ui.item;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ModalSelectContenedor;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ModalViewContenedor;

public class SelectItemFragment extends Fragment implements FragmentCallback<Item> {

    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextContenedor;
    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonBack;
    private static Item itemModel;

    public SelectItemFragment (){
        super();
    }
    public SelectItemFragment (Item itemModel){
        this();
        this.itemModel=itemModel;
        MainActivity.itemDao.getItem(this, itemModel.getId());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.item_select_title);
        editTextId = view.findViewById(R.id.editTextId);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextContenedor = view.findViewById(R.id.editTextContenedor);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        buttonBack = view.findViewById(R.id.buttonBack);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateItemFragment updateItemFragment=new UpdateItemFragment(itemModel);
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, updateItemFragment);
            }

        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteItemFragment(itemModel).showConfirmationDialog(getActivity());
            }

        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewItemFragment viewItemFragment=new ViewItemFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewItemFragment);
            }

        });

        ImageButton buttonInfo = view.findViewById(R.id.buttonInfo);
        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long.valueOf(editTextContenedor.getText().toString().trim());
                    showSelectModal();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(getContext(), getString(R.string.container)+ " " + getString(R.string.notnumeric_dialog), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //crear modal de informacion de contenedor
    private void showSelectModal() {
        ModalSelectContenedor modalSelectContenedor = new ModalSelectContenedor(new Contenedor(Long.valueOf(editTextContenedor.getText().toString().trim()),null,null,null,null,null));
        modalSelectContenedor.show(getParentFragmentManager(), "ContenedorSelect");
    }
    //finish crear modal


    public void callbackDataAcessSuccess(Item item){
        itemModel = item;
        editTextId.setText(itemModel.getId().toString());
        editTextNombre.setText(itemModel.getNombre());
        editTextDescripcion.setText(itemModel.getDescripcion());
        if(itemModel.getContenedor()!=null) {
            editTextContenedor.setText(itemModel.getContenedor().getId().toString());
        }
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, this);
    }

    public void callbackDataAcessError(Item item){
        ViewItemFragment viewItemFragment=new ViewItemFragment();
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewItemFragment);
    }
}