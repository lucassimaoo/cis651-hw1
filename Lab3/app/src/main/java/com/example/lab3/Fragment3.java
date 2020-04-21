package com.example.lab3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment3 extends Fragment {

    private FragmentTracker ft;
    private View v;

    public static final String fragmentTitle = "Language Info";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ft.fragmentVisible(fragmentTitle);
        v = inflater.inflate(R.layout.fragment_3, container, false);
        Button b_next = v.findViewById(R.id.finish_button);
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText lang = v.findViewById(R.id.language);
                ft.saveLanguage(lang.getText().toString());
                ft.finished();
            }
        });

        Button b_back = v.findViewById(R.id.back_button3);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.goBack();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ft = (FragmentTracker) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EditText lang = v.findViewById(R.id.language);
        ft.saveLanguage(lang.getText().toString());
        v = null;
    }
}
