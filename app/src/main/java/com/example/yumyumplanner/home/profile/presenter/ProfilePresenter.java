package com.example.yumyumplanner.home.profile.presenter;

import android.content.Context;
import android.net.Uri;

import com.example.yumyumplanner.model.data.UserProfile;

public interface ProfilePresenter {
     void logOut();
     void onImageChosen(Uri imageUri);
     void onSaveEditClicked(UserProfile userProfile, Uri imageUri);

}
