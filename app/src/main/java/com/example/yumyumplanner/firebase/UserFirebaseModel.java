package com.example.yumyumplanner.firebase;

import com.google.firebase.auth.FirebaseAuth;

public class UserFirebaseModel {
    private static final int RC_SIGN_IN = 100;
    private FirebaseAuth auth;
    private static UserFirebaseModel firebaseModel;
    private static final String TAG = "GOOGle_SIGN_IN_TAG";

    public static synchronized UserFirebaseModel getInstance(){
        if(firebaseModel == null){
            firebaseModel = new UserFirebaseModel();
        }
        return firebaseModel;
    }

    private UserFirebaseModel() {
        this.auth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

}
