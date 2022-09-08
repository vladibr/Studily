package com.example.Studili;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Studili.classes.DAOuser;
import com.example.Studili.classes.Stats;
import com.example.Studili.classes.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.time.LocalDate;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    TextView loginBtn;
    private GoogleSignInClient client;

    DAOuser dao = new DAOuser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initVarbs();

        initButtons();
    }

    private void initViews() {
        loginBtn = findViewById(R.id.login_button);
    }

    private void initButtons() {
        loginBtn.setOnClickListener(this);
    }

    private void initVarbs() {

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this,options);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                login();
        }
    }

    private void login() {
        Intent i = client.getSignInIntent();
        startActivityForResult(i,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                                    if (isNew){
                                        String email = task.getResult().getUser().getEmail();
                                        String name = task.getResult().getUser().getDisplayName();
                                        Integer[] gifs = {R.drawable.rank_1};
                                        LocalDate date = LocalDate.now();
                                        User user = new User(email,name,1,new Stats(0,1,0,
                                                0,0,0),date.toString(),R.drawable.rank_1);
                                        dao.add(user);
                                        user.setGifs(gifs);
                                        dao.addGif(gifs[0]);
                                    }
                                    Intent intent = new Intent(getApplicationContext(),NavDrawerActivity.class);
                                    startActivity(intent);

                                }else {
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null){
            Intent intent = new Intent(this,NavDrawerActivity.class);
            startActivity(intent);
        }
    }
}