package com.example.yumyumplanner.authentication.view.loginfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.authentication.presenter.LoginPresenter;
import com.example.yumyumplanner.firebase.login.LoginFirebaseModel;
import com.example.yumyumplanner.home.view.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment implements LoginView{


    TextView signUpTv;

    private ProgressBar progressbar;

    private EditText emailEditText ;
    private EditText passwordEditText;
    private Button loginUpBtn;
    private LoginPresenter loginPresenter;


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

        loginPresenter = new LoginPresenter(this, new LoginFirebaseModel());

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        loginUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginPresenter.loginUser(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        return view;
    }



    private void initUI(View view){
        signUpTv = view.findViewById(R.id.sign_Up_nav);
        emailEditText = view.findViewById(R.id.email_et);
        passwordEditText = view.findViewById(R.id.password_et);
        progressbar =view.findViewById(R.id.loading);
        loginUpBtn = view.findViewById(R.id.login_btn);
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