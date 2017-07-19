package com.example.biraj.book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    String loginEmail="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = (Button)(findViewById(R.id.btnLogin));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean isVerified = verifyLoginCredentials(false);
                    if(isVerified){
                        Intent bookIntent = new Intent(LoginActivity.this, BookMenuActivity.class);
                        bookIntent.putExtra("loginEmail", loginEmail);
                        startActivity(bookIntent);
                    }else {
                        Toast.makeText(LoginActivity.this, "Please verify your user name or password!",Toast.LENGTH_SHORT).show();
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        Button btnRegister = (Button)(findViewById(R.id.btnRegister));
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Catch Error!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
    public boolean verifyLoginCredentials(boolean check){
        try {
            //SharedPreferences.Editor editor = getSharedPreferences("DataStore", MODE_PRIVATE).edit();
            SharedPreferences sharedPref = getSharedPreferences("DataStore", MODE_PRIVATE);
            String strJson = sharedPref.getString("jsondata", "0");//second parameter is necessary ie.,Value to return if this preference does not exist.
            if (strJson!=null) {
                EditText editTextUserName = (EditText)findViewById(R.id.userName);
                EditText editTextPassword = (EditText)findViewById(R.id.userPassword);
                loginEmail = editTextUserName.getText().toString();
                String loginPassword = editTextPassword.getText().toString();

                // Gson lib for loading json string value to userInfo object of class UserInfo

                /* strJson = "[ {'name':'Biraj','email':'biraj@m.com','password':'asdfgh','address':'htd','contact':'+97786444577'},"+
                        "{'name':'Niraj','email':'niraj@m.com','password':'asdfgh','address':'bkt','contact':'+9779851444577'} ]";*/
                //UserInfo[] userInfo = g.fromJson(strJson, UserInfo[].class);
                //Type type = new TypeToken<MyDto>() {}.getType();
                Type arrayListType = new TypeToken<ArrayList<UserInfo>>() {}.getType();
                Gson g = new Gson();
                ArrayList<UserInfo> userInfo = g.fromJson(strJson, arrayListType);

                for(int i=0; i<userInfo.size(); i++){
                    //System.out.println(userInfo.get(i).getEmail());
                    String email = userInfo.get(i).getEmail();
                    String password = userInfo.get(i).getPassword();
                    if((loginEmail.trim().equals(email.trim())) && (loginPassword.trim().equals(password)) ) {
                        check = true;
                        break;
                    }
                }
                //String name = userInfo.getName();

                //String address = userInfo.getAddress();
                //String contact = userInfo.getContact();






            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return check;
    }
}
