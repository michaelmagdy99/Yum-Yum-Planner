package com.example.yumyumplanner.authentication.register.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
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
import com.example.yumyumplanner.authentication.register.presenter.RegisterPresenterImp;
import com.example.yumyumplanner.home.HomeActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment implements RegisterView{


    private ProgressBar progressbar;

    private EditText nameEditText;
    private EditText emailEditText ;
    private EditText passwordEditText;
    private EditText rePasswEditText;
    private Button signUpBtn;
    private RegisterPresenterImp registerPresenter;
    private View view;
    private ImageButton backBtn;


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

        registerPresenter = RegisterPresenterImp.getInstance(this, getContext());

        //sign up action
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!valiadation()) {
                    Toast.makeText(getContext(), "Check Your Data", Toast.LENGTH_SHORT).show();
                }
                else{
                    registerPresenter.register(
                            emailEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            nameEditText.getText().toString()
                    );
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
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
        backBtn = view.findViewById(R.id.back_btn);
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

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private boolean valiadation(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String reEnteredPassword = rePasswEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Enter your name");
            return false;
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            emailEditText.setError("Enter a valid email address");
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long");
            return false;
        }

        if (!password.equals(reEnteredPassword)) {
            rePasswEditText.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}