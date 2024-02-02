    package com.example.yumyumplanner.home.profile.view;

    import com.example.yumyumplanner.model.data.UserProfile;

    public interface ProfileView {
        void logOutSuccessMessage();
        void displayUserData(UserProfile userProfile);
        void showImageChooser();

        void showMessage(String message);

}
