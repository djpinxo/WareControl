package net.ddns.djpinxo.warecontrol;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

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
import net.ddns.djpinxo.warecontrol.databinding.ActivityMainBinding;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ViewContenedorFragment;
import net.ddns.djpinxo.warecontrol.ui.item.ViewItemFragment;
import net.ddns.djpinxo.warecontrol.ui.user.ViewUserFragment;
import net.ddns.djpinxo.warecontrol.utils.HashUtils;

public class MainActivity extends AppCompatActivity implements FragmentCallback<User> {

    private AppBarConfiguration appBarConfiguration;

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

        //setContentView(R.layout.activity_main);
        //appBar=this.findViewById(R.id.app_bar);
        ApiClient.getClient(this);

        if (userLogin==null){
            loadLoginLayout();

        }
        else{
            loadMainActivityLayout();
        }

        //TODO revisar si esta logado mandar a un controller o a otro
        /*if (false){
            setContentView(R.layout.login_screen);

            Button loginButton = (Button) findViewById(R.id.login_button);
            loginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("BUTTONS", "User tapped the Supabutton"+R.id.login_button);

                }
            });
            Button regisButton = (Button) findViewById(R.id.registrer_button);
            regisButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("BUTTONS", "User tapped the Supabutton"+R.id.registrer_button);
                }
            });
        }
        else {
            Log.d(this.getPackageName(), "usuario logado");
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            ViewUserFragment viewUserFragment=new ViewUserFragment();
            ViewContenedorFragment viewContenedorFragment=new ViewContenedorFragment();
            transaction.add(R.id.LinearLayoutContenedorDeFragment, viewContenedorFragment);
            transaction.commit();
        }

        //TODO agegando menus
        ImageButton buttonShowMenu = findViewById(R.id.buttonShowMenu);
        buttonShowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });


*/

    }

    public void changeFragment (int LayoutContenedorDeFragment, Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        transaction.replace(LayoutContenedorDeFragment, fragment);
        transaction.commit();
    }

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
    }

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
                    /*ViewUserFragment viewUserFragment = new ViewUserFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.LinearLayoutContenedorDeFragment, viewUserFragment);
                    transaction.commit();*/
                    return true;
                }
                else if (item.getItemId() == R.id.loginUserMenuOption) {
                    /*ViewItemFragment viewItemFragment = new ViewItemFragment();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.LinearLayoutContenedorDeFragment, viewItemFragment);
                    transaction.commit();*/
                    return true;
                }
                else{
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    //TODO controlador de login
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private void sendLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(validateLoginForm()){
            password = HashUtils.hashString(password);
            User userModel=new User(email, null, password);
            MainActivity.loginDao.loginUser(this, userModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateLoginForm() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        boolean result = true;
        if(email.isEmpty()) {
            editTextEmail.setError(getString(R.string.email) + " " + getString(R.string.required_dialog));
            result = false;
        }
        if(password.isEmpty()) {
            editTextPassword.setError(getString(R.string.password) + " " + getString(R.string.required_dialog));
            result = false;
        }

        return result;
    }

    @Override
    public void callbackDataAcessSuccess(User user) {
        userLogin = user;
        //editTextEmail.setText(userModel.getEmail());
        //editTextNombre.setText(userModel.getNombre());
        //editTextPassword.setText(userModel.getPassword());
        Toast.makeText(getBaseContext(), R.string.welcome, Toast.LENGTH_LONG).show();
        //SelectUserFragment selectUserFragment = new SelectUserFragment(userModel);
        //((MainActivity) getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);
        loadMainActivityLayout();


    }

    @Override
    public void callbackDataAcessError(User user) {
        Toast.makeText(getBaseContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }

    private void loadLoginLayout(){
        setContentView(R.layout.fragment_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.login_button);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLogin();
            }

        });
    }
    private void loadMainActivityLayout(){
        setContentView(R.layout.activity_main);
        appBar=this.findViewById(R.id.app_bar);
        //FragmentManager fragmentManager=getSupportFragmentManager();
        //FragmentTransaction transaction=fragmentManager.beginTransaction();
        //ViewUserFragment viewUserFragment=new ViewUserFragment();
        //ViewContenedorFragment viewContenedorFragment=new ViewContenedorFragment();
        //transaction.add(R.id.LinearLayoutContenedorDeFragment, viewContenedorFragment);
        //transaction.commit();
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