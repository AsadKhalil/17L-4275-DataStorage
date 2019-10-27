package com.example.datastorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements TextWatcher,View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    EditText username;
    EditText password;
    CheckBox rememberMe;
    Button login;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static String  PREFS_NAME="mypref";
    public static String PREF_USERNAME="username";
    public static String PREF_PASSWORD="password";
    private static final String KEY_REMEMBER = "remember";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        username=findViewById(R.id.et1_user);
        password=findViewById(R.id.et2_password);
        rememberMe=findViewById(R.id.checkBox);
        login=findViewById(R.id.button);


        if(sharedPreferences.getBoolean(KEY_REMEMBER,false))
            rememberMe.setChecked(true);
        else
            rememberMe.setChecked(false);


        username.setText(sharedPreferences.getString(PREF_USERNAME,""));
        password.setText(sharedPreferences.getString(PREF_PASSWORD,""));

        username.addTextChangedListener(this);
        password.addTextChangedListener(this);
        rememberMe.setOnCheckedChangeListener(this);

        login.setOnClickListener(this);

        if(username.getText()!=null&password.getText()!=null)
        {
            Intent intent=new Intent(MainActivity.this,Home.class);
            startActivity(intent);

        }
        else
        {
            Context context = getApplicationContext();
            CharSequence text = "Enter Username or Password!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }
    @Override
    public void onClick(View view) {

        if(username.getText()!=null&password.getText()!=null)
        {
            Intent intent=new Intent(MainActivity.this,Home.class);
            startActivity(intent);

        }
        else
        {
            Context context = getApplicationContext();
            CharSequence text = "Enter Username or Password!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    public void afterTextChanged(Editable editable) {

    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }
    private void managePrefs(){
        if(rememberMe.isChecked()){
            editor.putString(PREF_USERNAME, username.getText().toString().trim());
            editor.putString(PREF_PASSWORD, password.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }
        else
            {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(PREF_PASSWORD);//editor.putString(KEY_PASS,"");
            editor.remove(PREF_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
            }
    }

}
