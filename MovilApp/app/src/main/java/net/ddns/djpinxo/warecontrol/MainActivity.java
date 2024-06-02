package net.ddns.djpinxo.warecontrol;

import android.annotation.SuppressLint;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


import net.ddns.djpinxo.warecontrol.data.daos.ContenedorDao;
import net.ddns.djpinxo.warecontrol.data.daos.ItemDao;
import net.ddns.djpinxo.warecontrol.data.daos.UserDao;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.ApiClient;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.ContenedorDaoApiImplement;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.ItemDaoApiImplement;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.LoginDaoApiImplement;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.UserDaoApiImplement;
import net.ddns.djpinxo.warecontrol.data.daosimplements.local.ContenedorDaoLocalBBDDImplements;
import net.ddns.djpinxo.warecontrol.data.daosimplements.local.ItemDaoLocalBBDDImplements;
import net.ddns.djpinxo.warecontrol.data.daosimplements.local.UserDaoLocalBBDDImplements;
import net.ddns.djpinxo.warecontrol.data.model.User;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ViewContenedorFragment;
import net.ddns.djpinxo.warecontrol.ui.item.ViewItemFragment;
import net.ddns.djpinxo.warecontrol.ui.login.DetailUserFragment;
import net.ddns.djpinxo.warecontrol.ui.login.LoginFragment;
import net.ddns.djpinxo.warecontrol.ui.login.RegisterUserFragment;
import net.ddns.djpinxo.warecontrol.ui.user.ViewUserFragment;

public class MainActivity extends AppCompatActivity {

    public static UserDao userDao;
    public static ItemDao itemDao;
    public static ContenedorDao contenedorDao;
    public static LoginDaoApiImplement loginDao;

    public static LinearLayout appBar;
    public static User userLogin;

    public MainActivity(){
        super();
        userDao=new UserDaoApiImplement();
        itemDao=new ItemDaoApiImplement();
        contenedorDao=new ContenedorDaoApiImplement();
        loginDao=new LoginDaoApiImplement();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiClient.getClient(getString(R.string.ApiURL), userLogin);

        if (userLogin==null){
            loadLoginLayout();

        }
        else{
            loadMainActivityLayout();
        }

    }

    public void changeFragment (int LayoutContenedorDeFragment, Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        transaction.replace(LayoutContenedorDeFragment, fragment);
        transaction.commit();
    }
/*
    public void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirm_dialog);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acción a realizar si el usuario confirma

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acción a realizar si el usuario cancela
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }*/

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint({"NonConstantResourceId", "ResourceType"})
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.userMenuOption) {
                    ViewUserFragment viewUserFragment = new ViewUserFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.LinearLayoutContenedorDeFragment, viewUserFragment);
                    transaction.commit();
                    return true;
                }
                else if (item.getItemId() == R.id.itemMenuOption) {
                    ViewItemFragment viewItemFragment = new ViewItemFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.LinearLayoutContenedorDeFragment, viewItemFragment);
                    transaction.commit();
                    return true;
                }
                else if (item.getItemId() == R.id.contendorMenuOption) {
                    ViewContenedorFragment viewContenedorFragment = new ViewContenedorFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.LinearLayoutContenedorDeFragment, viewContenedorFragment);
                    transaction.commit();
                    return true;
                }
                else{
                    return false;
                }
            }
        });

        if(userLogin==null || !userLogin.isAdmin()){
            popupMenu.getMenu().removeItem(R.id.userMenuOption);
        }

        popupMenu.show();
    }

    private void showPopupLoginMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.login_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint({"NonConstantResourceId", "ResourceType"})
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logoutMenuOption) {
                    userLogin=null;
                    Toast.makeText(getBaseContext(), R.string.logout_dialog, Toast.LENGTH_LONG).show();
                    loadLoginLayout();
                    return true;
                }
                else if (item.getItemId() == R.id.loginUserMenuOption) {
                    DetailUserFragment detailUserFragment = new DetailUserFragment(userLogin);
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.LinearLayoutContenedorDeFragment, detailUserFragment);
                    transaction.commit();
                    return true;
                }
                else{
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    public void loadLoginLayout(){
        setContentView(R.layout.fragment_login);
        Button buttonLogin = findViewById(R.id.login_button);
        Button buttonRegister = findViewById(R.id.registrer_button);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginFragment().sendLogin(MainActivity.this);
            }

        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRegisterLayout();
            }

        });
    }

    public void loadRegisterLayout(){
        setContentView(R.layout.fragment_register);
        Button buttonInsert = findViewById(R.id.buttonInsert);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegisterUserFragment().showConfirmationDialog(MainActivity.this);
            }

        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoginLayout();
            }

        });
    }
    public void loadMainActivityLayout(){
        setContentView(R.layout.activity_main);
        appBar=this.findViewById(R.id.app_bar);
        ImageButton buttonShowMenu = findViewById(R.id.buttonShowMenu);
        buttonShowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
        ImageButton buttonLoginUser = findViewById(R.id.buttonLoginUser);
        buttonLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupLoginMenu(view);
            }
        });
    }
}