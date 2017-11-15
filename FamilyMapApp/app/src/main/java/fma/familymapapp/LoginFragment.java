package fma.familymapapp;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.IOException;

public class LoginFragment extends Fragment {
    private boolean mFilledHost;
    private boolean mFilledPort;
    private boolean mFilledUsername;
    private boolean mFilledPassword;
    private boolean mFilledFirstName;
    private boolean mFilledLastName;
    private boolean mFilledEmail;
    private boolean mFilledGender;
    private Login mLogin;
    private Register mRegister;
    private String mServerHost;
    private String mServerPort;
    private EditText mHost;
    private EditText mPort;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private RadioButton mGenderMale;
    private RadioButton mGenderFemale;
    private Button mSignInButton;
    private Button mRegisterButton;
    private static final String TAG = "LoginFragment";

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLogin = new Login();
        mRegister = new Register();
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //SERVER HOST
        mHost = (EditText) v.findViewById(R.id.server_host);
        mHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mServerHost = s.toString();
//                mLogin.setHost(s.toString());
                enableSignIn();
                enableRegister();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mPort = (EditText) v.findViewById(R.id.server_port);
        mPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mServerPort = s.toString();
                //mLogin.setPort(s.toString());
                enableSignIn();
                enableRegister();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mUsername = (EditText) v.findViewById(R.id.username);
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mLogin.setUsername(s.toString());
                mRegister.setUsername(s.toString());
                enableSignIn();
                enableRegister();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mPassword = (EditText) v.findViewById(R.id.password);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mLogin.setPassword(s.toString());
                mRegister.setPassword(s.toString());
                enableSignIn();
                enableRegister();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mFirstName = (EditText) v.findViewById(R.id.first_name);
        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mRegister.setFirstName(s.toString());
                enableRegister();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mLastName = (EditText) v.findViewById(R.id.last_mame);
        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mRegister.setLastName(s.toString());
                enableRegister();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mEmail = (EditText) v.findViewById(R.id.email);
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mRegister.setEmail(s.toString());
                enableRegister();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });


        mGenderMale = (RadioButton)v.findViewById(R.id.radio_male);
        mGenderMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mRegister.setGender("m");
                    enableRegister();
                    printLogin();
                }
            }
        });

        mGenderFemale = (RadioButton)v.findViewById(R.id.radio_female);
        mGenderFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mRegister.setGender("f");
                    enableRegister();
                    printLogin();
                }
            }
        });


        mSignInButton = (Button)v.findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new FetchItemsTask().execute();
                //fill in functionality later
            }
        });
        mSignInButton.setEnabled(false);

        mRegisterButton = (Button)v.findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new FetchItemsTask().execute();
                //fill in functionality later
            }
        });
        mRegisterButton.setEnabled(false);

        return v;
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {

            String url = "http://" + mServerHost + ":" + mServerPort + "/user/login"; // + mLogin.getUsername() + "/2";
            HttpClient httpClient = new HttpClient();
            httpClient.post(url, mLogin);
            return null;
        }
    }

    private void enableSignIn()
    {
        if(mServerHost != null
                && mServerPort != null
                && mLogin.getUsername() != null
                && mLogin.getPassword() != null) {
            if(mServerHost.equals("")
                    || mServerPort.equals("")
                    || mLogin.getUsername().equals("")
                    || mLogin.getPassword().equals(""))
            {
                mSignInButton.setEnabled(false);  //if a value was erased
            }
            else
            {
                mSignInButton.setEnabled(true); //values are all valid
            }
        }
        else
        {
            mSignInButton.setEnabled(false); //values are not filled
        }
    }

    private void enableRegister()
    {
        if(mServerHost != null
                && mServerPort != null
                && mRegister.getUsername() != null
                && mRegister.getPassword() != null
                && mRegister.getFirstName() != null
                && mRegister.getLastName() != null
                && mRegister.getEmail() != null
                && mRegister.getGender() != null) {

            //check if a value was erased
            if (mServerHost.equals("")
                    || mServerPort.equals("")
                    || mRegister.getUsername().equals("")
                    || mRegister.getPassword().equals("")
                    || mRegister.getFirstName().equals("")
                    || mRegister.getLastName().equals("")
                    || mRegister.getEmail().equals("")
                    || mRegister.getGender().equals("")) {
                mRegisterButton.setEnabled(false); //if a value was erased
            }
            else
            {
                mRegisterButton.setEnabled(true); //values are all valid
            }
        }
        else {
            mRegisterButton.setEnabled(false); //values are not filled
        }
    }

    private void printLogin(){
        System.out.println("Host: " + mServerHost);
        System.out.println("Port: " + mServerPort);
        System.out.println("Username: " + mRegister.getUsername());
        System.out.println("Password: " + mRegister.getPassword());
        System.out.println("First Name: " + mRegister.getFirstName());
        System.out.println("Last Name: " + mRegister.getLastName());
        System.out.println("Email: " + mRegister.getEmail());
        System.out.println("Gender: " + mRegister.getGender());
    }
}
