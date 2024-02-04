package com.example.yumyumplanner.model.backup_repo;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.yumyumplanner.home.profile.view.ProfileFragment;
import com.example.yumyumplanner.home.profile.view.ProfileView;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositryImp;
import com.example.yumyumplanner.model.data.UserProfile;
import com.example.yumyumplanner.remote.firebase.UserFirebaseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class BackUpRepositoryImp implements BackUpRepository{

    private UserFirebaseModel userFirebaseModel;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private StorageReference storageReference;

    private static BackUpRepositoryImp backUpRepositoryImp;
    private Context ProfileFragment;

    private final Context context;
    public static synchronized BackUpRepositoryImp getInstance(Context context) {
        if (backUpRepositoryImp == null) {
            backUpRepositoryImp = new BackUpRepositoryImp(context);
        }
        return backUpRepositoryImp;
    }

    private BackUpRepositoryImp(Context context) {
        this.context = context;
        this.userFirebaseModel = UserFirebaseModel.getInstance();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void logOut() {
        auth.signOut();
    }

    @Override
    public void getUserData(UserDataCallback callback) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            FirebaseFirestore.getInstance().collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String userName = documentSnapshot.getString("name");
                            String userEmail = documentSnapshot.getString("email");
                            String userProfileImage = documentSnapshot.getString("profileImage");
                            UserProfile user = new UserProfile(userName, userEmail, userProfileImage);
                            callback.onSuccess(user);
                            Log.e("TAG", "getUserData: Done for getting user data");
                        } else {
                            Log.e("TAG", "getUserData: Done by no current for getting user data");
                            callback.onFailure("No Data Founded");
                        }
                    }).addOnFailureListener(
                            e -> {
                                callback.onFailure(e.getMessage());
                                Log.e("TAG", "getUserData: Error getting user data", e);
                            }

                    );
        } else {
            callback.onFailure("Must Login First to can get Data");
        }

    }

    @Override
    public void saveUserData(UserProfile userProfile, Uri imageUri) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            Map<String, Object> userData = new HashMap<>();
            userData.put("name", userProfile.getName());
            userData.put("email", userProfile.getEmail());

            if (imageUri != null) {
                StorageReference fileReference = storageReference
                        .child("profile_images")
                        .child(System.currentTimeMillis() + "." + getFileExtension(imageUri));


                fileReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();
                                userProfile.setProfileImageURL(imageUrl);
                                userData.put("profileImage", imageUrl);
                                saveUserDataToFirestore(userId, userData);
                                Log.i("TAG", "saveUserData: " + userData);
                                Log.i("TAG", "saveUserData: image " + imageUrl);
                                Log.i("TAG", "saveUserData: clss " + userProfile.getProfileImageURL());
                            });
                        })
                        .addOnFailureListener(e -> {
                            Log.i("TAG", "saveUserData: ");
                        });
            } else {
                saveUserDataToFirestore(userId, userData);
            }
        } else {
        }
    }

    private void saveUserDataToFirestore(String userId, Map<String, Object> userData) {
        firestore.collection("users").document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    Log.i("TAG", "saveUserDataToFirestore: success");
                })
                .addOnFailureListener(e -> {
                    Log.i("TAG", "saveUserDataToFirestore: failed");
                });
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}
