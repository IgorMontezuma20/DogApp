package com.example.dogsapp.view.view.view;

import static androidx.navigation.Navigation.findNavController;
import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.dogsapp.R;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_SEND_SMS = 0;

    private NavController navController;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);

        NavHostFragment finalHost = NavHostFragment.create(R.navigation.dogs_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, finalHost)
                .setPrimaryNavigationFragment(finalHost)
                .commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    public void checkSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permissão para enviar SMS")
                        .setMessage("Este aplicativo precisa de sua autorização para enviar SMS.")
                        .setPositiveButton("Perguntar novamente", (dialogInterface, i) -> {
                            requestSmsPermission();
                        })
                        .setNegativeButton("Negar", ((dialog, wich) -> {
                            notifyDetailFragment(false);
                        }))
                        .show();
            } else {
                requestSmsPermission();
            }
        } else {
            notifyDetailFragment(true);
        }
    }

    private void requestSmsPermission() {
        String[] permnissions = {Manifest.permission.SEND_SMS};
        ActivityCompat.requestPermissions(this, permnissions, PERMISSION_SEND_SMS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSION_SEND_SMS: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    notifyDetailFragment(true);
                }else{
                    notifyDetailFragment(false);
                }
                break;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void notifyDetailFragment(Boolean permissionGranted) {
        Fragment activeFragment = fragment.getChildFragmentManager().getPrimaryNavigationFragment();
        if(activeFragment instanceof DetailFragment){
            ((DetailFragment) activeFragment).onPermissionResult(permissionGranted);
        }
    }
}