package com.example.lab3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment1 extends Fragment {

    private FragmentTracker ft;
    private View v;

    public static final String fragmentTitle = "Personal Info";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ft.fragmentVisible(fragmentTitle);
        v = inflater.inflate(R.layout.fragment_1, container, false);
        Button b_next = v.findViewById(R.id.next_button);
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.goNext();
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
        EditText uname = v.findViewById(R.id.u_name);
        EditText lname = v.findViewById(R.id.u_lastname);
        ft.saveNameAndLastName(uname.getText().toString(), lname.getText().toString());
        v = null;
    }
}
