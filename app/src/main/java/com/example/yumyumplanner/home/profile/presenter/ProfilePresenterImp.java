package com.example.yumyumplanner.home.profile.presenter;

import android.net.Uri;

import com.example.yumyumplanner.home.profile.view.ProfileView;
import com.example.yumyumplanner.model.backup_repo.BackUpRepository;
import com.example.yumyumplanner.model.backup_repo.UserDataCallback;
import com.example.yumyumplanner.model.data.UserProfile;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImp implements ProfilePresenter{

    private ProfileView view;
    private BackUpRepository userRepository;
    private static ProfilePresenterImp profilePresenterImp;


    public static ProfilePresenterImp getInstance(ProfileView profileView, BackUpRepository userRepository) {
        if (profilePresenterImp == null) {
            profilePresenterImp = new ProfilePresenterImp(profileView, userRepository);
        }
        return profilePresenterImp;
    }

    private ProfilePresenterImp(ProfileView view, BackUpRepository userRepository) {
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
            userRepository.saveUserData(userProfile, imageUri, new UserDataCallback() {
                @Override
                public void onUserDataLoaded(UserProfile userProfile) {
                    view.showMessage("Success");
                }

                @Override
                public void onDataNotFound(Exception e) {
                    view.showMessage(e.getMessage());
                }

                @Override
                public void success() {
                    view.showMessage("Success");
                }

                @Override
                public void error(String string) {
                    view.showMessage(string);
                }
            });
        } else {
            userRepository.saveUserData(userProfile, null, new UserDataCallback() {
                @Override
                public void onUserDataLoaded(UserProfile userProfile) {
                    view.showMessage("Success");

                }

                @Override
                public void onDataNotFound(Exception e) {
                    view.showMessage(e.getMessage());
                }

                @Override
                public void success() {
                    view.showMessage("Success");
                }

                @Override
                public void error(String string) {
                    view.showMessage(string);
                }
            });
        }
    }

    @Override
    public void onViewCreated() {
        userRepository.getUserData(new UserDataCallback() {
            @Override
            public void onUserDataLoaded(UserProfile userProfile) {
                view.displayUserData(userProfile);
            }

            @Override
            public void onDataNotFound(Exception e) {
                view.showMessage("Faild to connect" + e.getMessage());
            }

            @Override
            public void success() {
                view.showMessage("Show data Scussfully");
            }

            @Override
            public void error(String string) {
                view.showMessage("Faild to connect" + string);

            }
        });
    }

}
