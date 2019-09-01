package com.example.healthcare.ui.dietplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthcare.R;

public class DietplanFragment extends Fragment {

    private DietplanViewModel dietplanViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dietplanViewModel =
                ViewModelProviders.of(this).get(DietplanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dietplan, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        dietplanViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}