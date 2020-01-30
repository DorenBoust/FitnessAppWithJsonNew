package com.example.fitnessapp.loginAndRegisterAnimated;

import androidx.annotation.DrawableRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fitnessapp.R;
import com.example.fitnessapp.models.AsynUserJSON;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class RegisterAniFragment extends Fragment {


    //TODO:change edittext user to email
    private RegisterAniViewModel mViewModel;
    private TextView tvToLogin;
    private LottieAnimationView registeAnimation;

    private TextInputLayout etUserName;
    private TextInputLayout etPass;
    private int counterPassEye = 0;

    private ImageView ivEyePass;
    private TextInputLayout etPassValidation;
    private int counterPassValidationEye = 0;

    private ImageView ivEyePassValidation;
    private TextInputLayout etIntegrationCode;
    private int counterIntegrationCodeEye = 0;

    private ImageView ivEyePassIntegrationCode;
    private Button btnRegister;

    private FirebaseAuth fAuth;

    private String intagrationCodeStatus = "";

    private String intagrationCode = "";
    private MutableLiveData<String> mLiveData;




    public static RegisterAniFragment newInstance() {
        return new RegisterAniFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.register_ani_fragment, container, false);



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegisterAniViewModel.class);

        tvToLogin = getView().findViewById(R.id.tv_register_click_to_login);
        registeAnimation = getView().findViewById(R.id.register_animation);

        etUserName = getView().findViewById(R.id.username_login);
        etPass = getView().findViewById(R.id.password_register);
        ivEyePass = getView().findViewById(R.id.iv_register_eye_pass);
        etPassValidation = getView().findViewById(R.id.passwordvalidation_register);
        ivEyePassValidation = getView().findViewById(R.id.iv_register_eye_passvalidation);
        etIntegrationCode = getView().findViewById(R.id.validation_register);
        ivEyePassIntegrationCode = getView().findViewById(R.id.iv_register_eye_integrationcode);
        btnRegister = getView().findViewById(R.id.btn_login_create_account);

        fAuth = FirebaseAuth.getInstance();

        //go to login text
        tvToLogin.setOnClickListener(view->{
            getFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.enter_top_to_bottom,R.anim.exite_bottom_to_top,R.anim.enter_bottom_to_top,R.anim.exite_top_to_bottom).
                    replace(R.id.mFragment, new LoginAniFragment()).commit();
        });




        //Register Button
        btnRegister.setOnClickListener(v->{

            //Register Animation
            btnRegister.setVisibility(View.INVISIBLE);
            registeAnimation.setVisibility(View.VISIBLE);
            registeAnimation.playAnimation();

            //Validation Integration code by EditText code
            mLiveData = new MutableLiveData<>();

            TestAsync testAsync = new TestAsync(mLiveData);
            String code = etIntegrationCode.getEditText().getText().toString();
            testAsync.execute(code);

            //code Async Live Data
            mLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {

                    //validation Integration code (if the json empty it mean that not have user on wordpress
                    intagrationCode = s;
                    if (intagrationCode.equals("[]")){
                        etIntegrationCode.setError("קוד התממשקות לא תקין");
                    } else {
                        etIntegrationCode.setError(null);
                    }

                    validationField();

                    //dusplay btn register again
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.faidin);
                    btnRegister.setAnimation(animation);
                    btnRegister.setVisibility(View.VISIBLE);
                    registeAnimation.setVisibility(View.INVISIBLE);

                    //create new auth on firebase
                    String user = etUserName.getEditText().getText().toString();
                    String pass = etPassValidation.getEditText().getText().toString();
                    String integrationCode = etIntegrationCode.getEditText().getText().toString();

                    //if not have any error its mean that can made auth at firebase
                    if (Objects.equals(etUserName.getError(), null) && Objects.equals(etPass.getError(), null) && Objects.equals(etPassValidation.getError(), null) && Objects.equals(etIntegrationCode.getError(), null)){
                        fAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //if success go to login screen
                                if (task.isSuccessful()){
                                    Toast.makeText(getContext(), "ההרשמה בוצעה בהצלחה!", Toast.LENGTH_SHORT).show();
                                    getFragmentManager().beginTransaction().
                                            setCustomAnimations(R.anim.enter_top_to_bottom,R.anim.exite_bottom_to_top,R.anim.enter_bottom_to_top,R.anim.exite_top_to_bottom).
                                            replace(R.id.mFragment, new LoginAniFragment()).commit();
                                } else {
                                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }



                }
            });

        });


        //Eyes Functions
        ivEyePass.setOnClickListener(v->{
            if (counterPassEye == 0){
                ivEyePass.setImageResource(R.drawable.ic_eye_red_24dp);
                etPass.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                counterPassEye++;
            }else{
                ivEyePass.setImageResource(R.drawable.ic_eye_white_24dp);
                etPass.getEditText().setInputType(129);
                counterPassEye--;
            }
        });

        ivEyePassValidation.setOnClickListener(v->{
            if (counterPassValidationEye == 0){
                ivEyePassValidation.setImageResource(R.drawable.ic_eye_red_24dp);
                etPassValidation.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                counterPassValidationEye++;
            }else{
                ivEyePassValidation.setImageResource(R.drawable.ic_eye_white_24dp);
                etPassValidation.getEditText().setInputType(129);
                counterPassValidationEye--;
            }
        });

        ivEyePassIntegrationCode.setOnClickListener(v->{
            if (counterIntegrationCodeEye == 0){
                ivEyePassIntegrationCode.setImageResource(R.drawable.ic_eye_red_24dp);
                etIntegrationCode.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                counterIntegrationCodeEye++;
            }else{
                ivEyePassIntegrationCode.setImageResource(R.drawable.ic_eye_white_24dp);
                etIntegrationCode.getEditText().setInputType(129);
                counterIntegrationCodeEye--;
            }
        });



    }


    public static class TestAsync extends AsyncTask<String, Integer, String>{

        MutableLiveData<String> mLiveData;

        public TestAsync(MutableLiveData<String> mLiveData) {
            this.mLiveData = mLiveData;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String finalUrl = "http://appfitness.boust.me/wp-json/acf/v3/trainers?appConnection=" + strings[0];
                URL url = new URL(finalUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = reader.readLine()) != null){
                    sb.append(line);
                }

                return sb.toString();


            }catch (IOException e){
                System.out.println("TestAsync");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            mLiveData.setValue(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void validationField(){
        //TODO:add validation if the auth exsist on firebase
        String userName = etUserName.getEditText().getText().toString();
        String pass = etPass.getEditText().getText().toString();
        String passValidation = etPassValidation.getEditText().getText().toString();
        String intagrationCode = etIntegrationCode.getEditText().getText().toString();


        if (TextUtils.isEmpty(userName)){
            etUserName.setError("שדה חובה");
        } else{
            etUserName.setError(null);
        }

        if (TextUtils.isEmpty(pass)){
            etPass.setError("שדה חובה");
        }else{
            etPass.setError(null);
        }

        if (TextUtils.isEmpty(passValidation)){
            etPassValidation.setError("שדה חובה");
        }else if (!pass.equals(passValidation)){
            etPassValidation.setError("הסיסמאות לא תואמות");
            etPass.setError("הסיסמאות לא תואמות");
        } else {
            etPassValidation.setError(null);
        }

        if (TextUtils.isEmpty(intagrationCode)){
            etIntegrationCode.setError("שדה חובה");
        }


    }

}
