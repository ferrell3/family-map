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
                mLogin.setHost(s.toString());
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
                mLogin.setPort(s.toString());
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
                mLogin.setFirstName(s.toString());
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
                mLogin.setLastName(s.toString());
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
                mLogin.setEmail(s.toString());
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
                    mLogin.setGender("m");
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
                    mLogin.setGender("f");
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
                //fill in functionality later
            }
        });
        mRegisterButton.setEnabled(false);

        return v;
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String url = mLogin.getHost() + ":" + mLogin.getPort() + "/user/login"; // + mLogin.getUsername() + "/2";
            HttpClient httpClient = new HttpClient();
//            HttpPost httpPost = new HttpPost();
            httpClient.post(url, mLogin);
//            try {
//                String result = new ServerFetcher().getUrlString(url);
//                Log.i(TAG, "Fetched contents of URL: " + result);
//            } catch (IOException ioe) {
//                Log.e(TAG, "Failed to fetch URL: ", ioe);
//            }
            return null;
        }
    }

    private void enableSignIn()
    {
        if(mLogin.getHost() != null
                && mLogin.getPort() != null
                && mLogin.getUsername() != null
                && mLogin.getPassword() != null) {
            if(mLogin.getHost().equals("")
                    || mLogin.getPort().equals("")
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
        if(mLogin.getHost() != null
                && mLogin.getPort() != null
                && mLogin.getUsername() != null
                && mLogin.getPassword() != null
                && mLogin.getFirstName() != null
                && mLogin.getLastName() != null
                && mLogin.getEmail() != null
                && mLogin.getGender() != null) {

            //check if a value was erased
            if (mLogin.getHost().equals("")
                    || mLogin.getPort().equals("")
                    || mLogin.getUsername().equals("")
                    || mLogin.getPassword().equals("")
                    || mLogin.getFirstName().equals("")
                    || mLogin.getLastName().equals("")
                    || mLogin.getEmail().equals("")
                    || mLogin.getGender().equals("")) {
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
        System.out.println("Host: " + mLogin.getHost());
        System.out.println("Port: " + mLogin.getPort());
        System.out.println("Username: " + mLogin.getUsername());
        System.out.println("Password: " + mLogin.getPassword());
        System.out.println("First Name: " + mLogin.getFirstName());
        System.out.println("Last Name: " + mLogin.getLastName());
        System.out.println("Email: " + mLogin.getEmail());
        System.out.println("Gender: " + mLogin.getGender());
    }
}
