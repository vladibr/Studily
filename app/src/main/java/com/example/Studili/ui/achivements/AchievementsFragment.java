package com.example.Studili.ui.achivements;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;


import com.example.Studili.R;
import com.example.Studili.classes.DAOuser;



public class AchievementsFragment extends Fragment {

    View view;

    ImageButton prev,next;
    ImageSwitcher imageSwitcher;
    Context context;

    DAOuser user = new DAOuser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_achivements,container,false);

        initViews();

        initVarbs();

        return  view;
    }

    private void initVarbs() {
        context = getActivity();
        user.showAndSelectGif(prev,next,imageSwitcher,context);
    }


    private void initViews() {

        prev = view.findViewById(R.id.bt_prev);
        next = view.findViewById(R.id.bt_next);
        imageSwitcher = view.findViewById(R.id.image_switcher);
    }

}