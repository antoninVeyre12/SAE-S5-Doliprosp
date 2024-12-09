package com.example.doliprosp.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doliprosp.Interface.ISalonService;
import com.example.doliprosp.MainActivity;
import com.example.doliprosp.Model.Salon;
import com.example.doliprosp.R;
import com.example.doliprosp.Services.Outils;
import com.example.doliprosp.Services.SalonService;
import com.example.doliprosp.adapter.MyShowAdapter;
import com.example.doliprosp.adapter.ShowAdapter;
import com.example.doliprosp.viewModel.SalonViewModel;
import com.example.doliprosp.viewModel.UtilisateurViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowFragment extends Fragment implements MyShowAdapter.OnItemClickListener, ShowAdapter.OnItemClickListener {

    private ISalonService salonService;
    private ArrayList<Salon> showSavedList;
    private ShowAdapter adapterShow;
    private MyShowAdapter adapterMyShow;
    private SalonViewModel salonViewModel;
    private UtilisateurViewModel utilisateurViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        salonService = new SalonService();
        salonViewModel = new ViewModelProvider(requireActivity()).get(SalonViewModel.class);
        utilisateurViewModel = new ViewModelProvider(requireActivity()).get(UtilisateurViewModel.class);
        showSavedList = new ArrayList<Salon>();

        Button buttonCreateShow = view.findViewById(R.id.buttonCreateShow);
        RecyclerView recyclerView = view.findViewById(R.id.showRecyclerView);
        RecyclerView recyclerViewMyShow = view.findViewById(R.id.myShowRecyclerView);
        // Salon existant
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);



        salonService.getSalonsEnregistres(getContext(), utilisateurViewModel.getUtilisateur(getContext()), new Outils.APIResponseCallbackArrayTest() {
            @Override
            public void onSuccess(ArrayList<Salon> shows) {

                showSavedList = shows;

                // Set l'adapter des shows récupéré
                adapterShow = new ShowAdapter(showSavedList, ShowFragment.this);
                recyclerView.setAdapter(adapterShow);

                // Salon créer
                GridLayoutManager layoutManagerMyShow = new GridLayoutManager(getContext(), 3);
                recyclerViewMyShow.setLayoutManager(layoutManagerMyShow);


                // Set l'adapter des shows de l'utilisateur
                adapterMyShow = new MyShowAdapter(salonViewModel.getSalonList(), ShowFragment.this);
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

    @Override
    public void onDeleteClick(int position) {

        // mets a jour la liste des salons
        salonViewModel.getSalonList().remove(position);
        adapterMyShow.notifyItemRemoved(position);
    }

    @Override
    public void onSelectClick(int position, List<Salon> salonList) {
        Salon salon = salonList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("salon", (Serializable) salon);
        ProspectFragment prospectFragment = new ProspectFragment();
        prospectFragment.setArguments(bundle);
        ((MainActivity) getActivity()).loadFragment(prospectFragment);
    }
}