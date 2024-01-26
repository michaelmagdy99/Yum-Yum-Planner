package com.example.yumyumplanner.authentication.login.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.example.yumyumplanner.authentication.login.presenter.LoginPresenterImp;
import com.example.yumyumplanner.home.HomeActivity;

public class LoginFragment extends Fragment implements LoginView {


    private TextView signUpTv;

    private ProgressBar progressbar;

    private EditText emailEditText ;
    private EditText passwordEditText;
    private Button loginUpBtn;
    private LoginPresenterImp loginPresenter;
    private ImageButton googleSignIn;

    private ImageButton facebookSignIn;

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
                Intent intent = new Intent(getActivity() , HomeActivity.class);
                startActivity(intent);
            }
        });

        loginPresenter = LoginPresenterImp.getInstance(this);
        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        loginUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        facebookSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        facebookSignIn = view.findViewById(R.id.facebook_icon_btn);
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
        Toast.makeText(getContext(), "Login successful!!", Toast.LENGTH_LONG).show();
        // Intent to home activity
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginErrorMessage(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }
}