package com.example.doliprosp.fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.R;
import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.adapter.ShowAdapter;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.Show;

import java.util.List;

public class ShowFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApplicationViewModel viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        IApplication applicationManager = viewModel.getApplication();
        RecyclerView recyclerView = view.findViewById(R.id.showRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Show> showList = applicationManager.getSavedShow();
        ShowAdapter adapter = new ShowAdapter(showList);
        recyclerView.setAdapter(adapter);

        Button buttonCreateShow = view.findViewById(R.id.buttonCreateShow);
        buttonCreateShow.setOnClickListener(v -> {
            CreateShowDialogFragment dialog = new CreateShowDialogFragment();
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });

    }

}