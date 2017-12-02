package fma.familymapapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment loginFrag = fm.findFragmentById(R.id.login_fragment_container);

        if(loginFrag == null)
        {
            loginFrag = new LoginFragment();
            fm.beginTransaction().add(R.id.login_fragment_container, loginFrag).commit();
        }

    }
}
