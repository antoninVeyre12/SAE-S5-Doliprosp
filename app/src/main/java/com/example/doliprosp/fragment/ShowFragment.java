package com.example.doliprosp.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.R;
import com.example.doliprosp.Service.Outils;
import com.example.doliprosp.adapter.MyShowAdapter;
import com.example.doliprosp.adapter.ShowAdapter;
import com.example.doliprosp.Service.SalonService;

import java.util.ArrayList;

public class ShowFragment extends Fragment implements MyShowAdapter.OnItemClickListener {

    private ISalonService salonService;
    private ArrayList<Salon> showSavedList;
    private static ArrayList<Salon> showLocalList;
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
        salonService = new SalonService();
        showLocalList = new ArrayList<Salon>();
        showSavedList = new ArrayList<Salon>();
        showLocalList.add(new Salon("Test"));

        Button buttonCreateShow = view.findViewById(R.id.buttonCreateShow);
        RecyclerView recyclerView = view.findViewById(R.id.showRecyclerView);

        RecyclerView recyclerViewMyShow = view.findViewById(R.id.myShowRecyclerView);

        // Salon existant
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        salonService.getSalonsEnregistres(getContext(), new Outils.APIResponseCallbackArrayTest() {
            @Override
            public void onSuccess(ArrayList<Salon> shows) {

                showSavedList = shows;

                // Set l'adapter des shows récupéré
                adapterShow = new ShowAdapter(showSavedList);
                recyclerView.setAdapter(adapterShow);

                // Salon créer
                GridLayoutManager layoutManagerMyShow = new GridLayoutManager(getContext(), 3);
                recyclerViewMyShow.setLayoutManager(layoutManagerMyShow);


                // Set l'adapter des shows de l'utilisateur
                adapterMyShow = new MyShowAdapter(showLocalList, ShowFragment.this);
                recyclerViewMyShow.setAdapter(adapterMyShow);

                buttonCreateShow.setOnClickListener(v -> {
                    CreateShowDialogFragment dialog = new CreateShowDialogFragment();
                    dialog.show(getChildFragmentManager(), "CreateShowDialog");
                });

            }


            @Override
            public void onError(String error) {

                Log.d("SHOW_LIST_ERROR", error);
            }
        });


    }

    public static void ajouterSalonLocal(Salon salonLocal)
    {
        showLocalList.add(salonLocal);
    }
    public static void supprimerSalonLocal(Salon salon)
    {

    }

    @Override
    public void onDeleteClick(int position) {
        Salon salon = showLocalList.get(position);

        supprimerSalonLocal(salon);

        // mets a jour la liste des salons
        showSavedList.remove(position);
        Log.d("showlist", showSavedList.toString());
        adapterMyShow.notifyItemRemoved(position);
    }

}