package com.example.dogsapp.view.view.view;

import static androidx.navigation.Navigation.findNavController;
import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.dogsapp.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment finalHost = NavHostFragment.create(R.navigation.dogs_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, finalHost)
                .setPrimaryNavigationFragment(finalHost)
                .commit();

    }

    @Override
    public boolean onSupportNavigateUp() {


        return super.onSupportNavigateUp();
    }


}