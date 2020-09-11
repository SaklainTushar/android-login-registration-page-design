package com.example.signin_signup_demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText login_email_edittext,password_edittext;
    private TextView email_warning_textview,success;
    private ImageView show_pass_btn;
    private TextWatcher loginemailWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        emailCheck();

        findViewById(R.id.btn_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupActivity();
            }
        });
        show_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_hidePassword();
            }
        });
    }

    private void Login()
    {
        if(login_email_edittext.getText().toString().trim().isEmpty())
        {
            login_email_edittext.setError("Empty");
            login_email_edittext.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(login_email_edittext.getText().toString().trim()).matches())
        {
            login_email_edittext.setError("Invalid email");
            login_email_edittext.requestFocus();
            return;
        }else if(password_edittext.getText().toString().trim().isEmpty())
        {
            password_edittext.setError("Empty");
            password_edittext.requestFocus();
            return;
        }else
        {
            ConnectivityManager connectivityManager=(ConnectivityManager)
                    getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo == null || !(networkInfo.isConnected()) || !(networkInfo.isAvailable()))
            {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialogBuilder.setMessage("No Internet Connection");
                alertDialogBuilder.setPositiveButton("try again",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }else
            {
                progressDialog.setTitle("Singing");
                progressDialog.setMessage("Please wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       progressDialog.dismiss();
                    }
                },3000);

                success.setVisibility(View.VISIBLE);
                password_edittext.setText("");
            }
        }
    }

    private void show_hidePassword()
    {
        if(password_edittext.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
        {
            (show_pass_btn).setImageResource(R.drawable.visibility_off);
            //show pass
            password_edittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else
        {
            (show_pass_btn).setImageResource(R.drawable.visible_icon);
            //hde pass
            password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void emailCheck()
    {
         loginemailWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = login_email_edittext.getText().toString();
                if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                    email_warning_textview.setText("invalid email");
                } else {
                    email_warning_textview.setText("");
                }
                if (email.isEmpty()) {
                    email_warning_textview.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        login_email_edittext.addTextChangedListener(loginemailWatcher);
    }

    private void initialize()
    {
        progressDialog=new ProgressDialog(this);
        login_email_edittext=(EditText)findViewById(R.id.login_email_edittext);
        password_edittext=(EditText)findViewById(R.id.password_edittext);
        show_pass_btn=(ImageView) findViewById(R.id.show_pass_btn);
        email_warning_textview=(TextView) findViewById(R.id.email_warning_textview);
        success=(TextView) findViewById(R.id.success);

    }

    private void SignupActivity()
    {
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(MainActivity.this);
        final View mView=getLayoutInflater().inflate(R.layout.signup,null);
        final EditText regEmail=(EditText)mView.findViewById(R.id.reg_email_edittext);
        final EditText regPassword=(EditText)mView.findViewById(R.id.reg_password_edittext);
        final EditText regConPassword=(EditText)mView.findViewById(R.id.confirm_password_edittext);
        final TextView email_warning_textview=(TextView) mView.findViewById(R.id.email_warning_textview);
        final TextView password_warning_edittex=(TextView) mView.findViewById(R.id.password_warning_edittex);
        final ImageView show_pass=(ImageView) mView.findViewById(R.id.show_pass_btn);
        final TextView match_unmatch=(TextView) mView.findViewById(R.id.match_unmatch);
        final Button regSignupBtn=(Button)mView.findViewById(R.id.btn_sign_up) ;
        mBuilder.setView(mView);
        final AlertDialog dialog=mBuilder.create();
        dialog.show();

        TextWatcher emailWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email=regEmail.getText().toString();
                if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches()))
                {
                    email_warning_textview.setText("invalid email");
                }else{
                    email_warning_textview.setText("");
                }
                if(email.isEmpty())
                {
                    email_warning_textview.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        regEmail.addTextChangedListener(emailWatcher);

        TextWatcher passwordLength=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass=regPassword.getText().toString();
                if(pass.length()<8){
                    password_warning_edittex.setText("password too short");

                }else
                {
                    password_warning_edittex.setText("");
                }
                if(pass.isEmpty())
                {
                    password_warning_edittex.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        regPassword.addTextChangedListener(passwordLength);

        TextWatcher conWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass=regPassword.getText().toString();
                String confirm=regConPassword.getText().toString();

                if(pass != confirm )
                {
                    match_unmatch.setText("unmatch");
                    match_unmatch.setTextColor(Color.RED);
                } if(pass.equals(confirm))
                {
                    match_unmatch.setText("matched");
                    match_unmatch.setTextColor(Color.GREEN);
                }
                if(confirm.isEmpty())
                {
                    match_unmatch.setText("Empty");
                    match_unmatch.setTextColor(Color.GRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        regConPassword.addTextChangedListener(conWatcher);
        show_pass.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(regPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
                 {
                     (show_pass).setImageResource(R.drawable.visibility_off);
                     //show pass
                     regPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                 }else
                 {
                     (show_pass).setImageResource(R.drawable.visible_icon);
                     //hde pass
                     regPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                 }
             }
             });
        regSignupBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(regEmail.getText().toString().trim().isEmpty())
                {
                    regEmail.setError("Empty");
                    regEmail.requestFocus();
                    return;
                }else if(!Patterns.EMAIL_ADDRESS.matcher(regEmail.getText().toString().trim()).matches())
                {
                    regEmail.setError("Invalid email");
                    regEmail.requestFocus();
                    return;
                }else if(regPassword.getText().toString().trim().isEmpty())
                {
                    regPassword.setError("Empty");
                    regPassword.requestFocus();
                    return;
                }else if(regPassword.getText().toString().trim().length()<8)
                {
                    regPassword.setError("too short password");
                    regPassword.requestFocus();
                    password_warning_edittex.setText("");
                    return;
                }else if(regConPassword.getText().toString().trim().isEmpty())
                {
                    regConPassword.setError("Empty");
                    regConPassword.requestFocus();
                    return;
                }else if(match_unmatch.getText().equals("unmatch"))
                {
                    match_unmatch.setText("unmatch");
                    return;
                }else
                    {
                        ConnectivityManager connectivityManager=(ConnectivityManager)
                                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                        if(networkInfo == null || !(networkInfo.isConnected()) || !(networkInfo.isAvailable()))
                        {
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialogBuilder.setMessage("No Internet Connection");
                            alertDialogBuilder.setPositiveButton("try again",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            arg0.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }else
                        {
                            progressDialog.setTitle("checking");
                            progressDialog.setMessage("please wait");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();

                            new Handler().postDelayed(new Runnable() {
                                                                  @Override
                                                                  public void run() {
                                                                      Toast.makeText(getApplicationContext(),"Registration successfully done !",Toast.LENGTH_SHORT).show();
                                                                      progressDialog.dismiss();
                                                                      dialog.cancel();
                                                                  }
                                                              },3000);

                        }
                }
            }
        });

    }
    ProgressDialog progressDialog;
}