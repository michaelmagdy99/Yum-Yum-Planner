package com.example.yumyumplanner.home.profile.presenter;

import android.net.Uri;

import com.example.yumyumplanner.home.profile.view.ProfileView;
import com.example.yumyumplanner.model.backup_repo.BackUpRepository;
import com.example.yumyumplanner.model.backup_repo.BackUpRepositoryImp;
import com.example.yumyumplanner.model.backup_repo.UserDataCallback;
import com.example.yumyumplanner.model.data.UserProfile;

public class ProfilePresenterImp implements ProfilePresenter, UserDataCallback{

    private ProfileView view;
    private BackUpRepositoryImp userRepository;
    private static ProfilePresenterImp profilePresenterImp;


    public static ProfilePresenterImp getInstance(ProfileView profileView, BackUpRepositoryImp userRepository) {
        if (profilePresenterImp == null) {
            profilePresenterImp = new ProfilePresenterImp(profileView, userRepository);
        }
        return profilePresenterImp;
    }

    private ProfilePresenterImp(ProfileView view, BackUpRepositoryImp userRepository) {
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
    public void onViewCreated() {
        userRepository.getUserData(this);
    }

    @Override
    public void onSuccess(UserProfile userProfile) {
        view.displayUserData(userProfile);
    }

    @Override
    public void onFailure(String error) {
        view.showMessage(error);
    }
}
