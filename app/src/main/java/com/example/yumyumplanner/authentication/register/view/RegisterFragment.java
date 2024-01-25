package com.example.yumyumplanner.authentication.register.view;

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
import com.example.yumyumplanner.authentication.register.presenter.RegisterPresenterImp;
import com.example.yumyumplanner.home.HomeActivity;

public class RegisterFragment extends Fragment implements RegisterView{


    private ProgressBar progressbar;

    private EditText nameEditText;
    private EditText emailEditText ;
    private EditText passwordEditText;
    private EditText rePasswEditText;
    private Button signUpBtn;
    private RegisterPresenterImp registerPresenter;
    private String email, password, name, re_password;

    private View view;
    private ImageButton backBtn;

    private TextView gustModeBtn;


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

        gustModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , HomeActivity.class);
                startActivity(intent);
            }
        });
        registerPresenter = RegisterPresenterImp.getInstance(this);

        //sign up action
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPresenter.register(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString()
                );
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
        gustModeBtn = view.findViewById(R.id.gust_btn_sign_up);
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