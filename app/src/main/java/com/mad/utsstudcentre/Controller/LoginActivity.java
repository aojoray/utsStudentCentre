package com.mad.utsstudcentre.Controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.utsstudcentre.R;
import com.mad.utsstudcentre.Util.SessionManager;
import com.mad.utsstudcentre.Util.Values;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity {

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
                                startActivity(new Intent(v.getContext(), MainActivity.class));
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


