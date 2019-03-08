package id.ac.pradita.it.duniastartup;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import id.ac.pradita.it.duniastartup.Fragment.Profile;
import id.ac.pradita.it.duniastartup.Fragment.home_awal;

public class home extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

   Toolbar toolbar = (findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);

        loadHome(new home_awal()); //default nya HomeIklan jadi fragment utama
        bottomNav= findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId())
                {
                    case R.id.home_menu:
                        fragment = new home_awal();
                        break;
                    case R.id.profile_menu:
                        fragment = new Profile();
                        break;
                }
                return loadHome(fragment);
            }
        });
    }

    //fungsi buat nentuin fragment mana yang akan ditampilin(fragment HomeIklan atau Profile)
    private boolean loadHome(Fragment fragment) {
        if (fragment !=null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }


    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }



}
