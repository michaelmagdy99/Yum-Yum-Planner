package com.example.yumyumplanner.remote.firebase;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Intent;

import com.example.yumyumplanner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserFirebaseModel {
    GoogleSignInClient googleSignInClient;

    public static final int RC_SIGN_IN = 100;
    private FirebaseAuth auth;
    private static UserFirebaseModel firebaseModel;
    private FirebaseFirestore firestore;

    private static final String TAG = "GOOGle_SIGN_IN_TAG";

    public static synchronized UserFirebaseModel getInstance(){
        if(firebaseModel == null){
            firebaseModel = new UserFirebaseModel();
        }
        return firebaseModel;
    }

    private UserFirebaseModel() {
        this.auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

}
