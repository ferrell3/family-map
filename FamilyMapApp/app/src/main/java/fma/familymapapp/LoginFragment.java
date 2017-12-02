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
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

public class LoginFragment extends Fragment {
    private boolean mNewUser;
    private Login mLogin;
    private Register mRegister;
    private String mServerHost;
    private String mServerPort;
    private String mAuthToken;
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
                new LoginTask().execute();
            }
        });
        mSignInButton.setEnabled(false);

        mRegisterButton = (Button)v.findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mNewUser = true;
                new LoginTask().execute();
            }
        });
        mRegisterButton.setEnabled(false);

        return v;
    }

    public class LoginTask extends AsyncTask<Void,Void, String> {
        String failure;

        @Override
        protected String doInBackground(Void... params) {
            Gson gson = new Gson();
            String path;
            String logData;

            if(mNewUser) //register
            {
                logData = gson.toJson(mRegister);
                path = "/user/register";
                failure = "Register Failed";
                mNewUser = false;
            }
            else //sign in
            {
                logData = gson.toJson(mLogin);
                path = "/user/login";
                failure = "Login Failed";
            }
            //String url = "http://192.168.2.163:8888" + path;
            String url = "http://" + mServerHost + ":" + mServerPort + path;

            HttpClient httpClient = new HttpClient();
            Response response = httpClient.post(url, logData);
            if(response.getMessage() == null) //No error message
            {
                mAuthToken = response.getAuthToken();
                return response.getPersonId();
//                url = "http://192.168.2.163:8888/person/" + response.getPersonId();
//                //String url = "http://" + mServerHost + ":" + mServerPort + "/person/" + response.getPersonId();
//                String personStr = httpClient.get(url, mAuthToken);
//                if (personStr.equals("ERROR"))
//                {
//                    return failure;
//                }
//                else
//                {
//                    Person person = gson.fromJson(personStr, Person.class);
//                    return person.getFirstName() + " " + person.getLastName();
//                }
            }
            else
            {
                return failure;
            }
        }

        @Override
        protected void onPostExecute(String str) {
            if(str.equals(failure))
            {
                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
            }
            else
            {
//                String[] strArray = new String[n]; //could pass in auth token and host and port, etc. //for a separate family async task class
//                strArray[0] = str;
//                new GetFamilyTask().execute(strArray);
                new GetFamilyTask().execute(str);
            }
        }
    }

    public class GetFamilyTask extends AsyncTask<String,Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Gson gson = new Gson();
            HttpClient client = new HttpClient();
            String name;

            //String url = "http://192.168.2.163:8888/person/" + params[0]; //params[0] = personId
            String url = "http://" + mServerHost + ":" + mServerPort + "/person/" + params[0];
            String personStr = client.get(url, mAuthToken);
            if (personStr.equals("ERROR"))
            {
                return "Failed To Retrieve Family Data"; // or return personStr?
            }
            else
            {
                Person person = gson.fromJson(personStr, Person.class);
                name = person.getFirstName() + " " + person.getLastName();
            }
            //GET user's family data
            //url = "http://192.168.2.163:8888/person";
            url = "http://" + mServerHost + ":" + mServerPort + "/person";
            String peopleStr = client.get(url, mAuthToken);
            System.out.println(peopleStr);

            return name;
        }

        @Override
        protected void onPostExecute(String name) {
            Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
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
