package com.example.yumyumplanner.home.profile.view;

import static androidx.core.content.ContextCompat.getDrawable;
import static com.google.common.io.Files.getFileExtension;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.authentication.AuthenticationActivity;
import com.example.yumyumplanner.authentication.login.presenter.LoginPresenterImp;
import com.example.yumyumplanner.home.profile.presenter.ProfilePresenterImp;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositryImp;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment implements ProfileView{

    CircleImageView profileImage;
    EditText email;
    EditText name;
    Button saveEdit;
    ImageButton logout;
    ImageButton editImage;
    ProfilePresenterImp profilePresenter;

    TextView nameTv ;
    TextView emailTv ;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    private View view;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profile_image_settings);
        logout = view.findViewById(R.id.logout);
        email = view.findViewById(R.id.email_edit_text);
        name = view.findViewById(R.id.name_edit_text);
        editImage = view.findViewById(R.id.edit_profile_image_settings);
        saveEdit = view.findViewById(R.id.save_edit);
        nameTv = view.findViewById(R.id.user_name_settings);
        emailTv = view.findViewById(R.id.user_email_settings);

         profilePresenter = ProfilePresenterImp.getInstance(this);
         // load data
         loadUserDataFromFirestore();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutSuccessMessage();
                getActivity().finish();
            }
        });
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }


        });
        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        return view;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Your Image"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }

    @Override
    public void logOutSuccessMessage() {
        profilePresenter.logOut();
        Toast.makeText(getContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), AuthenticationActivity.class));
    }

    private void saveUserDataToFirestore(String name, String email, String imageUrl) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            Map<String, Object> userData = new HashMap<>();
            userData.put("name", name);
            userData.put("email", email);
            userData.put("profileImage", imageUrl);

            FirebaseFirestore.getInstance().collection("users")
                    .document(userId)
                    .set(userData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "User data saved successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to save user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "Must Login First to can edit Data", Toast.LENGTH_SHORT).show();
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void loadUserDataFromFirestore() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
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

                            name.setText(userName);
                            email.setText(userEmail);

                            nameTv.setText(userName);
                            emailTv.setText(userEmail);
                            if (userProfileImage != null && !userProfileImage.isEmpty()) {
                                Glide.with(requireContext()).load(userProfileImage).into(profileImage);
                            }
                        } else {
                            Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to retrieve user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "Must Login First to can get Data", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveUserData() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();

        // Check if a new image is selected
        if (imageUri != null) {
            // If a new image is selected, upload it and update the URL
            uploadImageAndUpdateUserData(userName, userEmail);
        } else {
            // If no new image is selected, retrieve the existing image URL from Firestore
            loadExistingImageUrl(userName, userEmail);
        }
    }

    private void uploadImageAndUpdateUserData(String userName, String userEmail) {
        StorageReference fileReference = FirebaseStorage.getInstance().getReference()
                .child("profile_images")
                .child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

        fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        saveUserDataToFirestore(userName, userEmail, imageUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loadExistingImageUrl(String userName, String userEmail) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            FirebaseFirestore.getInstance().collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String existingImageUrl = documentSnapshot.getString("profileImage");
                            saveUserDataToFirestore(userName, userEmail, existingImageUrl);
                        } else {
                            Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to retrieve user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "Must Login First to can get Data", Toast.LENGTH_SHORT).show();
        }
    }

}