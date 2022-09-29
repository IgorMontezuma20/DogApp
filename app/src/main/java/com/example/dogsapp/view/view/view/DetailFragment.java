package com.example.dogsapp.view.view.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dogsapp.R;
import com.example.dogsapp.databinding.FragmentDetailBinding;
import com.example.dogsapp.databinding.FragmentListBinding;
import com.example.dogsapp.view.view.model.DogBreed;
import com.example.dogsapp.view.view.viewmodel.DetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;

    private int dogUuid;
    private DetailViewModel viewModel;

    public DetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            dogUuid = DetailFragmentArgs.fromBundle(getArguments()).getDogUuid();

        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.fetch();

        observeViewModel();

    }

    private void observeViewModel() {
        viewModel.dogLiveData.observe(getViewLifecycleOwner(), dogBreed -> {
            if(dogBreed != null && dogBreed instanceof DogBreed){
                binding.dogName.setText(dogBreed.dogBreed);
                binding.dogPurpose.setText(dogBreed.bredFor);
                binding.dogTemperament.setText(dogBreed.temperament);
                binding.dogLifespan.setText(dogBreed.lifeSpan);
            }
        });
    }

}