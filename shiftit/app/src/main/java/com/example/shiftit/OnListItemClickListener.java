package com.example.shiftit;

import android.view.View;

public interface OnListItemClickListener<T> {

    void onItemClick(View v, T shift);

}
