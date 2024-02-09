package com.example.yumyumplanner.authentication.login.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.authentication.AuthenticationActivity;
import com.example.yumyumplanner.authentication.login.presenter.LoginPresenterImp;
import com.example.yumyumplanner.database.MealDAO;
import com.example.yumyumplanner.database.MealDatabase;
import com.example.yumyumplanner.home.HomeActivity;
import com.example.yumyumplanner.remote.firebase.backup.BackUpDataSourceImp;
import com.example.yumyumplanner.utils.InternetConnectivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;

public class LoginFragment extends Fragment implements LoginView  {


    private TextView signUpTv;

    private ProgressBar progressbar;
    private EditText emailEditText ;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;

    private EditText passwordEditText;
    private Button loginUpBtn;
    private LoginPresenterImp loginPresenter;
    private ImageButton googleSignIn;

    private ImageButton twitterSignIn;

    private TextView gustModeBtn;

    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initUI(view);
        gustModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.isGuestMode = true ;
                showGuestModeMessage();
            }
        });

        loginPresenter = LoginPresenterImp.getInstance(this, getContext());
        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        loginUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || !isValidEmail(email)) {
                    emailEditText.setError("Enter a valid email address");
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Enter your password");
                    return;
                }
                loginPresenter.login(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });


        //google signIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("603633507261-6h2prlbg2e3f518n493b7ip6a7ftpcph.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            goToHome();
            Toast.makeText(getContext(), "Login Successfuly", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), HomeActivity.class));
            //getActivity().finish();
        }

        twitterSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeLoginWithTwitter();
            }
        });


        return view;
    }

    private void initUI(View view){
        gustModeBtn = view.findViewById(R.id.gust_btn);
        signUpTv = view.findViewById(R.id.sign_Up_nav);
        emailEditText = view.findViewById(R.id.email_et);
        passwordEditText = view.findViewById(R.id.password_et);
        progressbar =view.findViewById(R.id.loading);
        loginUpBtn = view.findViewById(R.id.login_btn);
        googleSignIn = view.findViewById(R.id.google_icon_btn);
        twitterSignIn =view.findViewById(R.id.twitter_icon_btn);
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
        loginUpBtn.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
        loginUpBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoginSuccessMessage() {
        HomeActivity.isGuestMode = false;
        if(getContext()!= null) {
            Toast.makeText(getContext(), "Login successful!!", Toast.LENGTH_LONG).show();
            // Intent to home activity
            goToHome();
        }else {
            Log.i("TAG","Context is null here");
        }
    }

    @Override
    public void showLoginErrorMessage(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (signInAccountTask.isSuccessful()) {
                String s = "Google sign in successful";
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    if (googleSignInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(requireActivity(),
                                new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    HomeActivity.isGuestMode = false;
                                    ////////
                                    goToHome();
                                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void goToHome(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void showGuestModeMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Wait! Are Sure?");
        builder.setMessage("You'll miss out on personalized contant and saving our delicious recipes");

        builder.setPositiveButton("YES, I'M SURE", (dialog, which) -> {
            Intent intent = new Intent(getActivity() , HomeActivity.class);
            startActivity(intent);
        });

        builder.setNegativeButton("No, Go BACK", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void makeLoginWithTwitter() {

        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
        provider.addCustomParameter("lang", "en");
        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    goToHome();
                                    Toast.makeText(getContext(), "Twitter Login In Sucseefully", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Twitter Login In Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.i("TAG", "onFailure: twitter faile in if" + e.getMessage());
                                }
                            });
        } else {
            firebaseAuth
                    .startActivityForSignInWithProvider(getActivity(), provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    goToHome();
                                    Toast.makeText(getContext(), "Twitter Login In Sucseefully", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Twitter Login In Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.i("TAG", "onFailure: twitter faile in else" + e.getMessage());

                                }
                            });
        }
    }

}