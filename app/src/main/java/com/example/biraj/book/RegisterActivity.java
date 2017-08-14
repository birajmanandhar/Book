package com.example.biraj.book;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biraj.book.services.WebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextFullName;
    String fullName;
    EditText editTextEmail;
    String emailAddress;
    EditText editTextPassword;
    String password;
    EditText editTextAddress;
    String address;
    EditText editTextContact;
    String contact;
    ArrayList<UserInfo> userInformations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register Page");/**/
        //Loading the application views!
        loadRegisterPageView();

        Button btnRegister = (Button) (findViewById(R.id.btnRegister));
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserInformation();
            }
        });
    }

    public void loadRegisterPageView() {
        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextEmail = (EditText) findViewById(R.id.emailAddress);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextAddress = (EditText) findViewById(R.id.address);
        editTextContact = (EditText) findViewById(R.id.contact);
    }

    public boolean validateUserInfoFields() {
        try {
            fullName = editTextFullName.getText().toString();
            emailAddress = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();
            address = editTextAddress.getText().toString();
            contact = editTextContact.getText().toString();

            //validateUserDataFields();
            boolean isValidName = isValidName(fullName);
            boolean isValidEmail = isValidEmail(emailAddress);
            boolean isValidContact = isValidContact(contact);
            if (!isValidName) editTextFullName.setError("Please fill a valid name!");
            if (!isValidEmail) editTextEmail.setError("Please fill a valid Email!");
            if (!isValidContact) editTextContact.setError("Please fill a valid Contact!");
            if (password.trim().equals("")) editTextPassword.setError("Please fill in password!");
            System.out.println("Password" + password);
            if (address.trim().equals("")) editTextAddress.setError("Please fill in address!");
            if (isValidContact == true && isValidEmail == true && isValidName == true) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getRegistryInformation(){
        userInformations = new ArrayList<UserInfo>();
        UserInfo rowData = new UserInfo();
        rowData.setContact(contact);
        rowData.setPassword(password);
        rowData.setName(fullName);
        rowData.setAddress(address);
        rowData.setEmail(emailAddress);
        userInformations.add(rowData);
        Gson gson = new Gson();
        String result = gson.toJson(userInformations);
        return result;
    }

    public String updateUserInformations() {
        try {
            //Call for web service or load User information data in String form
            String userInformationData = pullUserInformations();
            //Now match string with Data Model UserInfo
            //String loadCode = loadUserInformationModel(userInformationData);

            if (userInformationData.equals("[]") || userInformationData.equals("0")) {
                //Gson g = new Gson();
                //UserInfo[] userInfo = g.fromJson(strJson, UserInfo[].class);
                /*userInformations = new UserInfo[1];
                userInformations[0] = new UserInfo();
                String id = getNewRowId();
                userInformations[0].setId(id);
                userInformations[0].setName(fullName);
                userInformations[0].setEmail(emailAddress);
                userInformations[0].setPassword(password);
                userInformations[0].setAddress(address);
                userInformations[0].setContact(contact);*/
                String id = getNewRowId();
                userInformations = new ArrayList<UserInfo>();
                UserInfo rowData = new UserInfo();
                rowData.setId(id);
                rowData.setContact(contact);
                rowData.setPassword(password);
                rowData.setName(fullName);
                rowData.setAddress(address);
                rowData.setEmail(emailAddress);
                userInformations.add(rowData);
                Gson gson = new Gson();
                String result = gson.toJson(userInformations);
                return result;
            } else {
                userInformations = loadUserInformationModel(userInformationData);
                //(userInformations.size() > 0)
                String id = getNewRowId();
                UserInfo rowData = new UserInfo();
                rowData.setId(id);
                rowData.setContact(contact);
                rowData.setPassword(password);
                rowData.setName(fullName);
                rowData.setAddress(address);
                rowData.setEmail(emailAddress);
                userInformations.add(rowData);
                /*userInformations.add();
                userInformations[userInformations.length - 1].setId(id);
                userInformations[userInformations.length - 1].setName(fullName);
                userInformations[userInformations.length - 1].setEmail(emailAddress);
                userInformations[userInformations.length - 1].setPassword(password);
                userInformations[userInformations.length - 1].setAddress(address);
                userInformations[userInformations.length - 1].setContact(contact);*/
                Gson gson = new Gson();
                String result = gson.toJson(userInformations);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void registerUserInformation() {
        try {
            boolean check = validateUserInfoFields();
            if (check) {
                String uploadData = getRegistryInformation();
                if (uploadData != null) {
                    BackgroundTask task = new BackgroundTask(RegisterActivity.this);
                    task.execute(uploadData);
                } else {
                    Toast.makeText(RegisterActivity.this, "Data upload failed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Please verify the form fields!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d("registerUserInfo Err", e.getMessage());
        }
    }

    class BackgroundTask extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        public BackgroundTask(RegisterActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Registering user info, please wait....");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String data = params[0];
            String result = null;
            try {
                result = WebService.post(WebService.REGISTER_URL, data);
            } catch (IOException e) {
                Log.d("Webservice ERR:",e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            /* Closing Shared Preferences
            SharedPreferences.Editor editor = getSharedPreferences("DataStore", MODE_PRIVATE).edit();
            editor.putString("jsondata", result);
            editor.commit();
            editor.apply();*/
            Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            finish();
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidName(String target) {
        return target.matches("^[\\p{L} \\.'-]+$");
    }

    public final static boolean isValidContact(String target) {
        return target.matches("^\\+[0-9]{8,20}$");
    }

    public String getNewRowId() {
        try {
            SharedPreferences sharedPref = getSharedPreferences("DataStore", MODE_PRIVATE);
            String jsonUserDatas = sharedPref.getString("jsondata", "1");// returns 1 by default
            String id;
            if (!jsonUserDatas.equals("[]")) {
                //Generates next id from data
                id = getMaxIdValue(jsonUserDatas);
            } else {
                id = "1";
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getMaxIdValue(String data) {
        try {
            Gson g = new Gson();
            UserInfo[] userInfo = g.fromJson(data, UserInfo[].class);
            String id = "";
            for (int i = 0; i < userInfo.length; i++) {
                id = userInfo[i].getId();
            }
            return (Integer.parseInt(id) + 1) + "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public UserInfo[] ABCpullUserInformations() {
        try {
            SharedPreferences sharedPref = getSharedPreferences("DataStore", MODE_PRIVATE);
            String strJson = sharedPref.getString("jsondata", "0");
            if (strJson.equals("0")) {
                UserInfo[] userInfo = new UserInfo[0];
                return userInfo;
            } else {
                Gson g = new Gson();
                UserInfo[] userInfo = g.fromJson(strJson, UserInfo[].class);
                return userInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String pullUserInformations() {
        try {
            SharedPreferences sharedPref = getSharedPreferences("DataStore", MODE_PRIVATE);
            String strJson = sharedPref.getString("jsondata", "0");
            return strJson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ArrayList<UserInfo> loadUserInformationModel(String data) {
        try {
            Type arrayListType = new TypeToken<ArrayList<UserInfo>>() {
            }.getType();
            Gson g = new Gson();
            ArrayList<UserInfo> userInfo = g.fromJson(data, arrayListType);
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}