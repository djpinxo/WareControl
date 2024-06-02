package net.ddns.djpinxo.warecontrol.ui.item;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

public class DeleteItemFragment implements FragmentCallback<Boolean> {

    //TODO creado por mantener una estructura
    //hacer validacion y mostrar AlertDialog

    Activity activity;

    private Item itemModel;

    public DeleteItemFragment(){
        super();
    }
    public DeleteItemFragment (Item itemModel){
        this();
        this.itemModel=itemModel;

    }

    public void showConfirmationDialog(Activity activity) {
        this.activity=activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirm_dialog);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem();
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

    private void deleteItem(){
        if(validateItemForm()){
            //MainActivity.itemDao.deleteItem(this, itemModel.getEmail());
        }
        else {
            Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateItemForm() {
        return true;//!itemModel.getEmail().trim().isEmpty();
    }

    @Override
    public void callbackDataAcessSuccess(Boolean object) {
        //Toast.makeText(activity, R.string.item_deleted_dialog, Toast.LENGTH_LONG).show();
        ViewItemFragment viewItemFragment = new ViewItemFragment();
        ((MainActivity) activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewItemFragment);
    }

    @Override
    public void callbackDataAcessError(Boolean object) {
        Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}
