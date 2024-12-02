package com.example.doliprosp.fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.MainActivity;
import com.example.doliprosp.R;
import com.example.doliprosp.ViewModel.ApplicationViewModel;
import com.example.doliprosp.adapter.MyShowAdapter;
import com.example.doliprosp.adapter.ShowAdapter;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.Show;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ShowFragment extends Fragment implements MyShowAdapter.OnItemClickListener {


    private IApplication applicationManager;
    private ArrayList<Show> showList;
    private ArrayList<Show> myShowList;
    private ShowAdapter adapterShow;
    private MyShowAdapter adapterMyShow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonCreateShow = view.findViewById(R.id.buttonCreateShow);
        RecyclerView recyclerView = view.findViewById(R.id.showRecyclerView);

        RecyclerView recyclerViewMyShow = view.findViewById(R.id.myShowRecyclerView);


        applicationManager = ApplicationViewModel.getApplication();

        // Salon existant
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        try {
            showList = (ArrayList<Show>) applicationManager.getSavedShow(getContext());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Set l'adapter des shows récupéré
        adapterShow = new ShowAdapter(showList);
        recyclerView.setAdapter(adapterShow);

        // Salon créer
        GridLayoutManager layoutManagerMyShow = new GridLayoutManager(getContext(), 3);
        recyclerViewMyShow.setLayoutManager(layoutManagerMyShow);

        myShowList = (ArrayList<Show>) applicationManager.getLocalShow();

        // Set l'adapter des shows de l'utilisateur
        adapterMyShow = new MyShowAdapter(myShowList, this);
        recyclerViewMyShow.setAdapter(adapterMyShow);

        buttonCreateShow.setOnClickListener(v -> {
            CreateShowDialogFragment dialog = new CreateShowDialogFragment();
            dialog.show(getChildFragmentManager(), "CreateShowDialog");
        });

    }


    @Override
    public void onDeleteClick(int position) {
        Show show = myShowList.get(position);

        applicationManager.deleteLocalShow(show);

        // mets a jour la liste des salons
        showList.remove(position);
        Log.d("showlist",showList.toString());
        adapterMyShow.notifyItemRemoved(position);
    }

}