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

public class InsertItemFragment extends Fragment implements FragmentCallback <Item> {

    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextContenedor;
    private Button buttonInsert;
    private Button buttonCancel;
    private Item itemModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insert_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.item_insert_title);
        editTextId = view.findViewById(R.id.editTextId);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextContenedor = view.findViewById(R.id.editTextContenedor);
        buttonInsert = view.findViewById(R.id.buttonInsert);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm);
                builder.setMessage(R.string.confirm_dialog);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        insertItem();
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
                ViewItemFragment viewItemFragment=new ViewItemFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewItemFragment);
            }

        });
    }

    private void insertItem(){
        String sId = editTextId.getText().toString().trim();
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String sIdContenedor = editTextContenedor.getText().toString().trim();

        if(validateItemForm()){
            Contenedor contenedorPadre = (sIdContenedor.isEmpty())?null:new Contenedor(Long.valueOf(sIdContenedor), null, null, null, null, null);
            itemModel=new Item(0l,name,descripcion, contenedorPadre);
            MainActivity.itemDao.insertItem(this, itemModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateItemForm(){
        String sId = editTextId.getText().toString().trim();
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String sIdContenedor = editTextContenedor.getText().toString().trim();

        boolean result=true;
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
        Toast.makeText(getContext(), R.string.item_inserted_dialog, Toast.LENGTH_LONG).show();
        itemModel=item;
        SelectItemFragment selectItemFragment=new SelectItemFragment(itemModel);
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);
    }

    @Override
    public void callbackDataAcessError(Item item) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}