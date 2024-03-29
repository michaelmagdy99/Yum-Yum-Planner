package com.example.yumyumplanner.remote.firebase.authentication;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserFirebaseModel {
    GoogleSignInClient googleSignInClient;

    public static final int RC_SIGN_IN = 100;
    private FirebaseAuth auth;
    private static UserFirebaseModel firebaseModel;
    private FirebaseFirestore firestore;

    private static final String TAG = "GOOGle_SIGN_IN_TAG";

    FirebaseUser cUser;

    public static synchronized UserFirebaseModel getInstance(){
        if(firebaseModel == null){
            firebaseModel = new UserFirebaseModel();
        }
        return firebaseModel;
    }

    private UserFirebaseModel() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        cUser = auth.getCurrentUser();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

}
