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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dogsapp.R;
import com.example.dogsapp.databinding.FragmentListBinding;
import com.example.dogsapp.view.view.model.DogBreed;
import com.example.dogsapp.view.view.viewmodel.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private ListViewModel viewModel;
    private DogsListAdapter dogsListAdapter = new DogsListAdapter(new ArrayList<>());


    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();

        binding.dogsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.dogsList.setAdapter(dogsListAdapter);

        binding.refreshLayout.setOnRefreshListener(() -> {
            binding.dogsList.setVisibility(View.GONE);
            binding.listError.setVisibility(View.GONE);
            binding.loadingView.setVisibility(View.VISIBLE);
            viewModel.refreshByPassCache();
            binding.refreshLayout.setRefreshing(false);
        });
        observeViewModel();
    }

    private void observeViewModel(){
        viewModel.dogs.observe(getViewLifecycleOwner(), dogs -> {
            if(dogs != null && dogs instanceof List){
                binding.dogsList.setVisibility(View.VISIBLE);
                dogsListAdapter.updateDogsList(dogs);
            }
        });

        viewModel.dogLoadError.observe(getViewLifecycleOwner(), isError -> {
            if(isError != null && isError instanceof Boolean){
                binding.listError.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.loading.observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading != null && isLoading instanceof Boolean){
                binding.loadingView.setVisibility(isLoading ? View.VISIBLE :View.GONE );
                if(isLoading){
                    binding.listError.setVisibility(View.GONE);
                    binding.listError.setVisibility(View.GONE);
                }
            }
        });
    }

}