package br.com.meuscontatos.principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.meuscontatos.principal.activity.MainActivity;
import br.com.meuscontatos.principal.activity.SignUpActivity;
import br.com.meuscontatos.principal.domain.Usuario;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText et_login;
    private EditText et_senha;
    private SignInButton signInButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN_GOOGLE = 7859;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        et_login      = (EditText) findViewById(R.id.et_login);
        et_senha      = (EditText) findViewById(R.id.et_senha);
        signInButton  = (SignInButton) findViewById(R.id.sign_in_button);

        //Autenticar pelo google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        //mAuthListener = getFirebaseAuthResultHandler();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    callMainActivity();
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    private FirebaseAuth.AuthStateListener getFirebaseAuthResultHandler(){
        FirebaseAuth.AuthStateListener callback = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser userFirebase = firebaseAuth.getCurrentUser();

                if( userFirebase == null ){
                    return;
                }

              //  if( user.getId() == null
                    //    && isNameOk( user, userFirebase ) ){

                   // user.setId( userFirebase.getUid() );
                   // user.setNameIfNull( userFirebase.getDisplayName() );
                   // user.setEmailIfNull( userFirebase.getEmail() );
                   // user.saveDB();
               // }

                callMainActivity();
            }
        };
        return( callback );
    }

    private void callMainActivity(){
        Intent intent = new Intent( this, MainActivity.class );
        startActivity(intent);
        finish();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "signInWithCredential", task.getException());
                            //Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    public void registrar(View view){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void autenticar(View view){
        Usuario usuario = new Usuario();
        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        Usuario usuario_= realm.where(Usuario.class).equalTo("usuario",et_login.getText().toString()).equalTo("senha",et_senha.getText().toString()).findFirst();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

        /*if(usuario_==null){
            AlertDialog builder = new AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("Usuário ou senha incorretos")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).create();
            builder.show();

        }else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
