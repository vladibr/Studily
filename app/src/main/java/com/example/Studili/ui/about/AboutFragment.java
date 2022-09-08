package com.example.Studili.ui.about;

import android.icu.util.VersionInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class AboutFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Element freepik = new Element();
        Element boltbite = new Element();
        Element version = new Element();
        version.setTitle("Version 1.0");
        edit_Element(freepik,1);
        edit_Element(boltbite,2);

        View view =  new AboutPage(getContext())
                .isRTL(false)
                .addEmail("us@example.com", "Contact Us")
                .addItem(freepik)
                .addItem(boltbite)
                .setDescription("this app was made by two junior developers to help people concentrate and not fall into the temptation of using their phone")
                .addItem(version)
                .create();

        return  view;
    }

    private void edit_Element(Element element, int num)
    {
        if (num == 1)
        {
            element.setTitle("Icons created by Freepik");
        }
        else
        {
            element.setTitle("Animations created by Boltbite");
        }
    }

}