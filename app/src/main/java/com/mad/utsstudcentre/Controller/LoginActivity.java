package com.mad.utsstudcentre.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.utsstudcentre.R;
import com.mad.utsstudcentre.Util.SaveSharedPreference;
import com.mad.utsstudcentre.Util.SessionManager;
import com.mad.utsstudcentre.Util.Values;

public class LoginActivity extends AppCompatActivity {

    public static final String USERNAME = "user ID";
    public static final String DATABDL = "data";
    private Button mLoginButton;
    private EditText mIdInput;
    private EditText mPasswordInput;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        setUpListeners();
    }

    private void setUpListeners(){
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mPasswordInput.getText().toString().isEmpty() || mIdInput.getText().toString().isEmpty()) {
                    mPasswordInput.setError("Required");
                    mIdInput.setError("Required");
                } else {
                    mProgress = ProgressDialog.show(v.getContext()
                            , Values.LOADING_TITLE,
                            Values.PROGRESS_TITLE, true);

                    final String userId = mIdInput.getText().toString();
                    final String userPassword = mPasswordInput.getText().toString();

                    mDatabase.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(userId) && dataSnapshot.child(userId).child("password").getValue().toString().equals(userPassword)) {
                                SessionManager.registerSession(getApplicationContext(), userId);mProgress.hide();
                                Bundle bdl = new Bundle();
                                bdl.putString(USERNAME, userId);
                                SaveSharedPreference.setUserName(LoginActivity.this, userId);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(USERNAME, userId);
                                startActivity(intent);
                                finish();
                            } else {
                                mProgress.hide();
                                Snackbar.make(v, "Error! Wrong ID or password", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    private void initialize() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mLoginButton = (Button) findViewById(R.id.loginBtn);
        mIdInput = (EditText) findViewById(R.id.idInput);
        mPasswordInput = (EditText) findViewById(R.id.passwordInput);
    }
}


