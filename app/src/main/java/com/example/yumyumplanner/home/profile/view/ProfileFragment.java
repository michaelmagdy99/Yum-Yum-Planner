package com.example.yumyumplanner.home.profile.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.authentication.AuthenticationActivity;
import com.example.yumyumplanner.database.MealDatabase;
import com.example.yumyumplanner.home.HomeActivity;
import com.example.yumyumplanner.home.profile.presenter.ProfilePresenterImp;

import com.example.yumyumplanner.model.data.UserProfile;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment implements ProfileView{

    CircleImageView profileImage;
    EditText name;
    Button saveEdit;
    private ProgressBar progressbar;
    ImageButton logout;
    ImageButton editImage;
    ProfilePresenterImp profilePresenter;
    TextView nameTv ;
    View view;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImage = view.findViewById(R.id.profile_image_settings);
        logout = view.findViewById(R.id.logout);
        name = view.findViewById(R.id.name_edit_text);
        editImage = view.findViewById(R.id.edit_profile_image_settings);
        saveEdit = view.findViewById(R.id.save_edit);
        nameTv = view.findViewById(R.id.user_name_settings);
        progressbar =view.findViewById(R.id.loading);

        profilePresenter = ProfilePresenterImp.getInstance(this,
                BackUpDataSourceImp.getInstance(requireContext()));

        profilePresenter.onViewCreated(requireContext());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutSuccessMessage();
                //clear room
                MealDatabase.getInstance(getContext()).clearAllData();
            }
        });
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }


        });
        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Your Image"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }


    @Override
    public void logOutSuccessMessage() {
        profilePresenter.logOut();
        Toast.makeText(getContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), AuthenticationActivity.class));
        getActivity().finish();
    }


    @Override
    public void displayUserData(String nameValue , String imageURl) {
        Log.d("ProfileFragment", "displayUserData: Displaying user data");

        if (getView() != null) {
            // Use getView() instead of getContext() to ensure the fragment's view is available
            name.setText(nameValue);
            nameTv.setText(nameValue);

            Log.i("TAG", "displayUserData: " + imageURl);
            if (imageURl != null && !imageURl.isEmpty()) {
                Glide.with(requireContext())
                        .load(imageURl)
                        .placeholder(R.drawable.panda_cooking)
                        .error(R.drawable.panda_cooking)
                        .into(profileImage);
            } else {
                Toast.makeText(requireContext(), "Data not Found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("ProfileFragment", "displayUserData: View is null");
        }
    }


    @Override
    public void showImageChooser() {
        openFileChooser();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void saveUserData() {
        String userName = name.getText().toString();
        UserProfile userProfile = new UserProfile(userName,"", "");
        profilePresenter.onSaveEditClicked(userProfile, imageUri);
        Toast.makeText(requireContext(),"Updated Profile",Toast.LENGTH_SHORT).show();
    }

}