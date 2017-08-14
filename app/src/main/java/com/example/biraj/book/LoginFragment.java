package com.example.biraj.book;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    View rootView;
    String loginEmail="";
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Login Page");
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        Button btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean isVerified = verifyLoginCredentials(false);
                    if (isVerified) {
                        Intent bookIntent = new Intent(getActivity(), UserViewActivity.class);
                        bookIntent.putExtra("loginEmail", loginEmail);
                        startActivity(bookIntent);
                    } else {
                        Toast.makeText(getActivity(), "Please verify your user name or password!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("Login Err:", e.getMessage());
                }
            }
        });
        Button btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("Register Err:", e.getMessage());
                }
            }
        });


        return rootView;
    }

    public boolean verifyLoginCredentials(boolean check){
        try {
            //SharedPreferences.Editor editor = getSharedPreferences("DataStore", MODE_PRIVATE).edit();
            SharedPreferences sharedPref = this.getActivity().getSharedPreferences("DataStore", getContext().MODE_PRIVATE);
            String strJson = sharedPref.getString("jsondata", "0");//second parameter is necessary ie.,Value to return if this preference does not exist.
            if (strJson!=null) {
                EditText editTextUserName = (EditText)rootView.findViewById(R.id.userName);
                EditText editTextPassword = (EditText)rootView.findViewById(R.id.userPassword);
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
                    String email = userInfo.get(i).getEmail();
                    String password = userInfo.get(i).getPassword();
                    if((loginEmail.trim().equals(email.trim())) && (loginPassword.trim().equals(password)) ) {
                        check = true;
                        break;
                    }
                }
            }
        } catch(Exception e){
            Log.d("verifyLoginErr:",e.getMessage());
        }
        return check;
    }
}
