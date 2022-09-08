package com.example.Studili.ui.stats;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;



import com.example.Studili.R;
import com.example.Studili.classes.DAOuser;


public class StatsFragment extends Fragment {

    View view;
    ListView card_list;

    Context context;

    DAOuser user = new DAOuser();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_stats,container,false);

        initViews();

        InitVarbs();

        return view;
    }

    private void InitVarbs() {
        context = getActivity();
        user.showUserStats(card_list,context);
    }


    private void initViews() {
        card_list = view.findViewById(R.id.stat_view);
    }

}