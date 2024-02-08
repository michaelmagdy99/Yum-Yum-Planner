    package com.example.yumyumplanner.home.profile.view;

    import com.example.yumyumplanner.model.data.UserProfile;
    public interface ProfileView {
        void logOutSuccessMessage();
        void displayUserData(String name , String imageURl);
        void showImageChooser();

        void showMessage(String message);

}
