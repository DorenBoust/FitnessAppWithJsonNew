package com.example.fitnessapp.loginAndRegisterAnimated;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAniFragment extends Fragment {

    private LoginAniViewModel mViewModel;
    private TextView tvToRegister;
    private Button btnLogin;
    private TextInputLayout etUser;
    private TextInputLayout etPass;
    private FirebaseAuth fAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_ani_fragment, container, false);

        tvToRegister = v.findViewById(R.id.tv_register_click_to_login);
        btnLogin = v.findViewById(R.id.btn_login_enter);
        etUser = v.findViewById(R.id.username_login);
        etPass = v.findViewById(R.id.password_login);
        fAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(btn->{
            String user = etUser.getEditText().getText().toString();
            String pass = etPass.getEditText().getText().toString();

            validationField();

            fAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        startActivity(new Intent(getContext(), MainActivity.class));

                    } else{
                        Toast.makeText(getContext(), "שם משתמש או סיסמא שגויים", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


        tvToRegister.setOnClickListener(view->{
            getFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.enter_top_to_bottom,R.anim.exite_bottom_to_top,R.anim.enter_bottom_to_top,R.anim.exite_top_to_bottom).
                    replace(R.id.mFragment, new RegisterAniFragment()).commit();
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginAniViewModel.class);
        // TODO: Use the ViewModel
    }

    private void validationField(){
        String user = etUser.getEditText().getText().toString();
        String pass = etPass.getEditText().getText().toString();

        if (TextUtils.isEmpty(user)){
            etUser.setError("לא הוזן מייל");
        } else {
            etUser.setError(null);
        }

        if (TextUtils.isEmpty(pass)){
            etPass.setError("לא הוזנה סיסמא");
        } else {
            etPass.setError(null);
        }

    }

}
