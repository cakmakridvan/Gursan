package com.rotamobile.gursan.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;

import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;

import static com.rotamobile.gursan.data.Server.GetToken;
import static com.rotamobile.gursan.data.Server.GetUsers;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Toolbar lToolbar;
    private Button  giris;
    private CheckBox remember_me;
    private EditText name,password;
    private CoordinatorLayout coordinatorLayout;

    private UserLogin userLogin = null;
    private ProgressDialog progressDialog;

    private String get_mesaj,get_name,get_surname,get_userID,get_userTypeID,get_projectID,get_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Paper.init(this);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        name = findViewById(R.id.edt_kullanici_adi);
        password = findViewById(R.id.edt_sifre);
        giris = findViewById(R.id.btn_login);
        remember_me = findViewById(R.id.btn_remember);
        lToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(lToolbar);
        
        getRemember_meData();

        giris.setOnClickListener(this);

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        }

    private void getRemember_meData() {

        String get_name = Paper.book().read("username");
        String get_password = Paper.book().read("password");
        Boolean get_checked = Paper.book().read("checked");

        if(!TextUtils.isEmpty(get_name) && !TextUtils.isEmpty(get_name) && get_checked.equals(true)){

            name.setText(get_name);
            password.setText(get_password);
            remember_me.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_login:

                if(!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())){

                    if(remember_me.isChecked()){

                        Boolean boolChecked = remember_me.isChecked();
                        Paper.book().write("username",name.getText().toString());
                        Paper.book().write("password",password.getText().toString());
                        Paper.book().write("checked",boolChecked);

                    }else{
                        Paper.book().delete("username");
                        Paper.book().delete("password");
                        Paper.book().delete("checked");
                    }

                    userLogin = new UserLogin(name.getText().toString().trim(),password.getText().toString().trim());
                    userLogin.execute((Void) null);

                }else{
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, getResources().getString(R.string.bilgiler_eksik), Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
                    snackbar.show();
                }

                break;

            case R.id.btn_remember:

                break;

        }
    }

    public class UserLogin extends AsyncTask<Void, Void, Boolean>{

        private final String name;
        private final String password;

        UserLogin(String name,String password){

            this.name = name;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            String login_service = GetToken(name,password,"password");
            if(!login_service.trim().equalsIgnoreCase("false") && !login_service.trim().equalsIgnoreCase("hata")){

                try {

                    JSONObject jObject = new JSONObject(login_service);
                    get_mesaj = jObject.getString("Successful");
                    get_token = jObject.getString("access_token");
                    get_name = jObject.getString("Name");
                    get_surname = jObject.getString("LastName");
                    get_userID = jObject.getString("ID");
                    get_userTypeID = jObject.getString("UserTypeID");
                    get_projectID = jObject.getString("ProjectID");
                 //Saved getting project_id
                    Paper.book().write("project_id",get_projectID);

                    Log.i("name",get_name);
                    Log.i("Surname",get_surname);
                    Log.i("projectID",get_projectID);

                } catch (JSONException e) {
                    Log.i("Exception: ",e.getMessage());

                }
            }

            else{

                if(login_service.trim().equalsIgnoreCase("false")){

                    get_mesaj = "user_error";
                }
                else if(login_service.trim().equalsIgnoreCase("hata")){

                    get_mesaj = "internet_error";
                }



            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(get_mesaj.equalsIgnoreCase("true")) {

                progressDialog.dismiss();

                Intent go_main = new Intent(getApplicationContext(), Main.class);
                go_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                go_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
/*                go_main.putExtra("name",get_name);
                go_main.putExtra("last_name",get_surname);*/
                //Saving name,surname and token
                Paper.book().write("name",get_name);
                Paper.book().write("last_name",get_surname);
                Paper.book().write("token",get_token);

                //Saving UserID to Paper
                Paper.book().write("user_id",get_userID);
                //Saving UserTypeID to Paper
                Paper.book().write("user_type_id",get_userTypeID);

                startActivity(go_main);
                finish();
            }
            else if(get_mesaj.equalsIgnoreCase("false")){

                progressDialog.dismiss();

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getResources().getString(R.string.user_autorization), Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
                snackbar.show();
            }
            else if(get_mesaj.equals("internet_error")){

                progressDialog.dismiss();

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getResources().getString(R.string.connection), Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
                snackbar.show();
            }
            else if(get_mesaj.equals("user_error")){

                progressDialog.dismiss();

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getResources().getString(R.string.user_info_error), Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
                snackbar.show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            userLogin = null;
            progressDialog.dismiss();
        }
    }
}
