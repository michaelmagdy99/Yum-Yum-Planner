package com.example.yumyumplanner.authentication.view.signupfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yumyumplanner.R;
import com.example.yumyumplanner.authentication.presenter.RegisterPresenter;
import com.example.yumyumplanner.firebase.register.RegisterFirebaseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment implements RegisterView{


    private ProgressBar progressbar;

    private EditText nameEditText;
    private EditText emailEditText ;
    private EditText passwordEditText;
    private EditText rePasswEditText;
    private Button signUpBtn;
    private RegisterPresenter registerPresenter;
    private String email, password, name, re_password;

    private View view;

    public RegisterFragment() {
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
        view =  inflater.inflate(R.layout.fragment_register, container, false);
        initUI(view);
        registerPresenter = new RegisterPresenter(this, new RegisterFirebaseModel());
        //sign up action
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerPresenter.registerUser(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });


        return view;
    }

    private void initUI(View view){
        nameEditText = view.findViewById(R.id.name_et);
        emailEditText = view.findViewById(R.id.email_et_sign_up);
        passwordEditText = view.findViewById(R.id.password_et_sing_up);
        rePasswEditText = view.findViewById(R.id.re_password_et);
        progressbar =view.findViewById(R.id.loading_sing_up);
        signUpBtn = view.findViewById(R.id.sign_up_btn);
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
        signUpBtn.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
        signUpBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRegisterSuccessMessage() {
        Toast.makeText(getContext(), "Sign UP Successfully", Toast.LENGTH_LONG).show();
        Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
    }

    @Override
    public void showRegisterErrorMessage(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }
}