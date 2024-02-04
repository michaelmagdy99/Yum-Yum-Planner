package com.example.yumyumplanner.home.profile.view;

import static androidx.core.content.ContextCompat.getDrawable;
import static com.google.common.io.Files.getFileExtension;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yumyumplanner.R;
import com.example.yumyumplanner.authentication.AuthenticationActivity;
import com.example.yumyumplanner.authentication.login.presenter.LoginPresenterImp;
import com.example.yumyumplanner.home.profile.presenter.ProfilePresenterImp;
import com.example.yumyumplanner.model.authentication_repo.AuthenticationRepositryImp;
import com.example.yumyumplanner.model.backup_repo.BackUpRepository;
import com.example.yumyumplanner.model.backup_repo.BackUpRepositoryImp;
import com.example.yumyumplanner.model.data.UserProfile;
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
    private ProgressBar progressbar;
    ImageButton logout;
    ImageButton editImage;
    ProfilePresenterImp profilePresenter;
    TextView nameTv ;
    TextView emailTv ;
    View view;
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
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
        progressbar =view.findViewById(R.id.loading);

        profilePresenter = ProfilePresenterImp.getInstance(this,
                BackUpRepositoryImp.getInstance(mContext));

        profilePresenter.onViewCreated();

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


    @Override
    public void displayUserData(UserProfile userProfile) {
        Log.d("ProfileFragment", "displayUserData: Displaying user data");

        if (mContext != null) {
            name.setText(userProfile.getName());
            email.setText(userProfile.getEmail());
            nameTv.setText(userProfile.getName());
            emailTv.setText(userProfile.getEmail());

            Log.i("TAG", "displayUserData: " + userProfile.getProfileImageURL());
            if (userProfile.getProfileImageURL() != null && !userProfile.getProfileImageURL().isEmpty()) {
                Glide.with(requireContext())
                        .load(userProfile.getProfileImageURL())
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile)
                        .into(profileImage);
            } else {
                Toast.makeText(getContext(), "Data not Found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.i("ProfileFragment", "displayUserData: "+ userProfile.getProfileImageURL());
            Log.e("ProfileFragment", "displayUserData: Context is null");
        }
    }

    @Override
    public void showImageChooser() {
        openFileChooser();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void saveUserData() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        UserProfile userProfile = new UserProfile(userName, userEmail, "");
        profilePresenter.onSaveEditClicked(userProfile, imageUri);

    }

}