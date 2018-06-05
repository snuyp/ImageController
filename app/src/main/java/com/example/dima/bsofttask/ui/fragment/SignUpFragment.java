package com.example.dima.bsofttask.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.mvp.presenter.LoginPresenter;
import com.example.dima.bsofttask.mvp.view.LoginView;
import com.example.dima.bsofttask.ui.PhotoActivity;

import es.dmoral.toasty.Toasty;

public class SignUpFragment extends MvpAppCompatFragment implements LoginView{
    @InjectPresenter
    LoginPresenter authPresenter;

    private Button onLoginButton;
    private EditText login,password,repeatPassword;

    private static SignUpFragment signUpFragment = null;
    public static SignUpFragment getInstance() {
        if (signUpFragment == null) {
            signUpFragment = new SignUpFragment();
        }
        return signUpFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up_fragment,container,false);
        login = v.findViewById(R.id.edit_login);
        password = v.findViewById(R.id.edit_password);
        repeatPassword = v.findViewById(R.id.edit_password_repeat);
        onLoginButton = v.findViewById(R.id.button_login);

        onLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPresenter.onSignUp(login.getText().toString(),password.getText().toString(),repeatPassword.getText().toString());
            }
        });
        return v;
    }

    @Override
    public void onLoginResult(Integer messageResult) {
        Toasty.success(getContext(),getString(messageResult), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginError(Integer messageError) {
        Toasty.error(getContext(),getString(messageError), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseError(String error) {
        Toasty.error(getContext(),error, Toast.LENGTH_SHORT).show();
    }
}
