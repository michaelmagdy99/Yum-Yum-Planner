package com.example.yumyumplanner.home.profile.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.example.yumyumplanner.home.profile.view.ProfileView;
import com.example.yumyumplanner.model.data.UserProfile;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;
import com.example.yumyumplanner.remote.firebase.backup.UserDataCallback;

public class ProfilePresenterImp implements ProfilePresenter {

    private ProfileView view;
    private BackUpDataSourceImp userRepository;
    private static ProfilePresenterImp profilePresenterImp;


    public static ProfilePresenterImp getInstance(ProfileView profileView, BackUpDataSourceImp userRepository) {
        if (profilePresenterImp == null) {
            profilePresenterImp = new ProfilePresenterImp(profileView, userRepository);
        }
        return profilePresenterImp;
    }

    private ProfilePresenterImp(ProfileView view, BackUpDataSourceImp userRepository) {
        this.view = view;
        this.userRepository = userRepository;
    }
    @Override
    public void logOut() {
        userRepository.logOut();
    }

    @Override
    public void onImageChosen(Uri imageUri) {
        view.showMessage("Image chosen: " + imageUri.toString());
    }

    @Override
    public void onSaveEditClicked(UserProfile userProfile, Uri imageUri) {
        if (imageUri != null) {
            userRepository.saveUserData(userProfile, imageUri);
        } else {
            userRepository.saveUserData(userProfile, null);
        }
    }

    @Override
    public void onViewCreated(Context context) {
       // userRepository.getUserData(this);

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("UserName", "");
        String imageURl = sharedPreferences.getString("UserProfileImageURL", "");
        view.displayUserData(name,imageURl);
        Log.i("UserData", "Name: " + name + ", ImageURL: " + imageURl);
    }

}
