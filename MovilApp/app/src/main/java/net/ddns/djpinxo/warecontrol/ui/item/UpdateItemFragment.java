package net.ddns.djpinxo.warecontrol.ui.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;

public class UpdateItemFragment extends Fragment implements FragmentCallback<Item> {

    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextContenedor;
    private Button buttonUpdate;
    private Button buttonCancel;
    private static Item itemModel;
    private boolean isUpdating=false;

    public UpdateItemFragment(){
        super();
    }
    public UpdateItemFragment (Item itemModel){
        this();
        this.itemModel=itemModel;
        isUpdating=false;
        MainActivity.itemDao.getItem(this, itemModel.getId());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.item_update_title);
        editTextId = view.findViewById(R.id.editTextId);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextContenedor = view.findViewById(R.id.editTextContenedor);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm);
                builder.setMessage(R.string.confirm_dialog);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateItem();
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectItemFragment selectItemFragment=new SelectItemFragment(itemModel);
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);
            }

        });
    }

    private void updateItem(){
        String sId = editTextId.getText().toString().trim();
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String sIdContenedor = editTextContenedor.getText().toString().trim();

        if(validateItemForm()){
            Contenedor contenedor = (sIdContenedor.isEmpty())?null:new Contenedor(Long.valueOf(sIdContenedor), null, null, null, null, null);
            //itemModel=new Item(email,name,password);
            itemModel.setNombre(name);
            itemModel.setDescripcion(descripcion);
            itemModel.setContenedor(contenedor);
            isUpdating=true;
            MainActivity.itemDao.updateItem(this, itemModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateItemForm() {
        String sId = editTextId.getText().toString().trim();
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String sIdContenedor = editTextContenedor.getText().toString().trim();

        boolean result = true;
        if(sId.isEmpty()) {
            editTextId.setError(R.string.id + " " + R.string.required_dialog);
            result = false;
        }
        if(!sId.isEmpty()){
            try{
                Long.valueOf(sId);
            }catch (NumberFormatException e) {
                editTextId.setError(R.string.dadcontainer+ " " + R.string.notnumeric_dialog);
                result = false;
            }
        }
        if(!sId.equals(itemModel.getId().toString())) {
            editTextId.setError(R.string.id + " " + "introducido no concuerda con el original");
            result = false;
        }
        if(name.isEmpty()) {
            editTextNombre.setError(R.string.name + " " + R.string.required_dialog);
            result = false;
        }
        if(!sIdContenedor.isEmpty()){
            try{
                Long.valueOf(sIdContenedor);
            }catch (NumberFormatException e) {
                editTextContenedor.setError(R.string.dadcontainer+ " " + R.string.notnumeric_dialog);
                result = false;
            }
        }

        return result;
    }

    @Override
    public void callbackDataAcessSuccess(Item item) {
        itemModel = item;
        editTextId.setText(itemModel.getId().toString());
        editTextNombre.setText(itemModel.getNombre());
        editTextDescripcion.setText(itemModel.getDescripcion());
        if(itemModel.getContenedor()!=null) {
            editTextContenedor.setText(itemModel.getContenedor().getId().toString());
        }
        if(isUpdating) {
            Toast.makeText(getContext(), R.string.item_updated_dialog, Toast.LENGTH_LONG).show();
            SelectItemFragment selectItemFragment = new SelectItemFragment(itemModel);
            ((MainActivity) getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);
        }
        else if (!isUpdating) {
            ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, this);
        }
        isUpdating=false;
    }

    @Override
    public void callbackDataAcessError(Item item) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}