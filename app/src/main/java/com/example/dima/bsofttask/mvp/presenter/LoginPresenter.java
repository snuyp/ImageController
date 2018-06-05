package com.example.dima.bsofttask.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.dima.bsofttask.remote.Service;
import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.common.Common;
import com.example.dima.bsofttask.mvp.model.RegistrationResponse;
import com.example.dima.bsofttask.mvp.model.post.User;
import com.example.dima.bsofttask.mvp.view.LoginView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Dima on 25.05.2018.
 */
@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {
    private Service service = Common.getRetrofitService();

    public void onSignIn(String login, String password) {
        User user = new User(login, password);
        int loginCode = user.isValidData();
        if (loginCode == 0) {
            getViewState().onLoginError(R.string.you_must_enter_your_login);
        } else if (loginCode == 1) {
            getViewState().onLoginError(R.string.you_must_enter_valid_login);
        } else if (loginCode == 2) {
            getViewState().onLoginError(R.string.password_length_must_be_greater_than_7);
        } else {
            getSignInResponse(user);
        }
    }

    private void getSignInResponse(User user) {
        service.signInUser(user).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    //Common.registrationResponse  = response.body();
                    Common.token = response.body().getData().getToken();
                    getViewState().onLoginResult(R.string.login_success);
                } else {
                    Log.e("Error","Registration response error.");
                    getViewState().onLoginError(R.string.security_signin_incorrect);
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.e("On Failure",t.getMessage());
                getViewState().onResponseError(t.getMessage());
            }
        });
    }

    public void onSignUp(String login,String password,String passwordRepeat)
    {
        if(password.equals(passwordRepeat)) {
            User user = new User(login, password);
            int loginCode = user.isValidData();
            if (loginCode == 0) {
                getViewState().onLoginError(R.string.you_must_enter_your_login);
            } else if (loginCode == 1) {
                getViewState().onLoginError(R.string.you_must_enter_valid_login);
            } else if (loginCode == 2) {
                getViewState().onLoginError(R.string.password_length_must_be_greater_than_7);
            } else {
                getSignUpResponse(user);
            }
        }
        else
        {
            getViewState().onLoginError(R.string.password_must_be_equals);
        }
    }
    private void getSignUpResponse(User user)
    {
        service.signUpUser(user).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    //Common.registrationResponse = response.body();
                    Common.token = response.body().getData().getToken();
                    getViewState().onLoginResult(R.string.login_success);
                } else {
                    Log.e("Error","Registration response error.");
                    getViewState().onResponseError("Registration response error.");
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.e("On Failure",t.getMessage());
            }
        });
    }
}
