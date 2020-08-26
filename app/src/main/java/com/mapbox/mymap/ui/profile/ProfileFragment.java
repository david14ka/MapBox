package com.mapbox.mymap.ui.profile;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mapbox.mymap.MapViewActivity;
import com.mapbox.mymap.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FirebaseAuth mAuth;
    private TextView usernameTextView;
    private TextView emailTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        usernameTextView = root.findViewById(R.id.username);
        emailTextView = root.findViewById(R.id.email);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        return root;
    }



    private void updateUI(FirebaseUser currentUser) {

        if (currentUser!=null){
            emailTextView.setText(currentUser.getEmail());
            usernameTextView.setText(currentUser.getDisplayName());
        }
    }
}