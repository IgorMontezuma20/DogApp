package com.example.dogsapp.view.view.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.palette.graphics.Palette;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dogsapp.R;
import com.example.dogsapp.databinding.FragmentDetailBinding;
import com.example.dogsapp.databinding.FragmentListBinding;
import com.example.dogsapp.databinding.SendSmsDialogBinding;
import com.example.dogsapp.view.view.model.DogBreed;
import com.example.dogsapp.view.view.model.DogPallete;
import com.example.dogsapp.view.view.model.SmsInfo;
import com.example.dogsapp.view.view.util.Util;
import com.example.dogsapp.view.view.viewmodel.DetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;

    private int dogUuid;
    private DetailViewModel viewModel;

    private Boolean sendSmsStarted = false;

    private DogBreed currentDog;

    public DetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        this.binding = binding;
        //setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            dogUuid = DetailFragmentArgs.fromBundle(getArguments()).getDogUuid();

        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.fetch(dogUuid);

        observeViewModel();

    }

    private void observeViewModel() {
        viewModel.dogLiveData.observe(getViewLifecycleOwner(), dogBreed -> {
            if (dogBreed != null && dogBreed instanceof DogBreed && getContext() != null) {
                currentDog = dogBreed;
                binding.dogName.setText(dogBreed.dogBreed);
                binding.dogPurpose.setText(dogBreed.bredFor);
                binding.dogTemperament.setText(dogBreed.temperament);
                binding.dogLifespan.setText(dogBreed.lifeSpan);

                if (dogBreed.imageUrl != null) {
                    Util.loadImage(binding.dogImage, dogBreed.imageUrl,
                            new CircularProgressDrawable(getContext()));

                    setuBackGroundColor(dogBreed.imageUrl);
                }
            }
        });
    }

    private void setuBackGroundColor(String url) {
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource)
                                .generate(palette -> {
                                    int intColor = palette.getLightMutedSwatch().getRgb();
                                    DogPallete myPalette = new DogPallete(intColor);
                                    binding.setPallete(myPalette);
                                });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

//    @Override
//    //public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.action_send_sms:{
//                if(!sendSmsStarted){
//                    sendSmsStarted = true;
//                    ((MainActivity) getActivity()).checkSmsPermission();
//                }
//                break;
//            }
//            case R.id.action_share:{
//                Toast.makeText(getContext(), "Action share", Toast.LENGTH_SHORT).show();
//                break;
//            }
//        }
//
//        return super.onOptionsItemSelected(item);
   // }

//    public void onPermissionResult(Boolean permissionGranted){
//        if(isAdded() && sendSmsStarted && permissionGranted){
//            SmsInfo smsInfo = new SmsInfo("", currentDog.dogBreed + "bred for " + currentDog.bredFor, currentDog.imageUrl);
//
//            SendSmsDialogBinding dialogBinding = DataBindingUtil.inflate(
//                    LayoutInflater.from(getContext()),
//                    R.layout.send_sms_dialog,
//                    null,
//                    false
//            );
//
//            new AlertDialog.Builder(getContext())
//                    .setView(dialogBinding.getRoot())
//                    .setPositiveButton("Enviar sms", ((dialog, wich) -> {
//                        if(!dialogBinding.smsDestination.getText().toString().isEmpty()){
//                            smsInfo.to = dialogBinding.smsDestination.getText().toString();
//                            sendSms(smsInfo);
//                        }
//                    }))
//                    .setNegativeButton("Cancelar", ((dialog, wich) -> {}))
//                    .show();
//            sendSmsStarted = false;
//
//            dialogBinding.setSmsInfo(smsInfo);
//        }
//    }

    private void sendSms(SmsInfo smsInfo){

    }
}