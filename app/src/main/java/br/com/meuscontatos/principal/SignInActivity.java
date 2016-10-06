package br.com.meuscontatos.principal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.meuscontatos.principal.activity.MainActivity;
import br.com.meuscontatos.principal.activity.SignUpActivity;
import br.com.meuscontatos.principal.domain.Contato;
import br.com.meuscontatos.principal.domain.Usuario;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText et_login;
    private EditText et_senha;
    private SignInButton signInButton;
    private LoginButton login_button_facebook;
    private Button entrar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN_GOOGLE = 7859;
    private Usuario user;
    private AccessToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.signin_activity);

        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        user = realm.where(Usuario.class).findFirst();

        et_login              = (EditText)     findViewById(R.id.et_login);
        et_senha              = (EditText)     findViewById(R.id.et_senha);
        signInButton          = (SignInButton) findViewById(R.id.sign_in_button);
        login_button_facebook = (LoginButton) findViewById(R.id.login_button_facebook);
        entrar                = (Button)       findViewById(R.id.entrar);

        //Autenticar pelo facebook
        callbackManager = CallbackManager.Factory.create();
        login_button_facebook.setReadPermissions("email");

        login_button_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(SignInActivity.this, "Sucesso.", Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                //Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignInActivity.this, "Erro.", Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

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
        mAuthListener = getFirebaseAuthResultHandler();
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(et_login.getText().toString(), et_senha.getText().toString())
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignInActivity.this,R.string.error_login, Toast.LENGTH_LONG).show();
                                } else {
                                    FirebaseUser userFirebase = task.getResult().getUser();
                                    if (user != null) {
                                        Usuario usuario = new Usuario();
                                        usuario.setId(1L);
                                        usuario.setIdUserFireBase(userFirebase.getUid());
                                        usuario.setNameUserFireBase(userFirebase.getDisplayName());
                                        usuario.setEmail(userFirebase.getEmail());
                                        if(userFirebase.getPhotoUrl()!=null){
                                            usuario.setUrlFotoFireBase(userFirebase.getPhotoUrl().toString());
                                        }
                                        Realm realm = Service.getInstace().getRealm(getApplicationContext());
                                        realm.beginTransaction();
                                        realm.insertOrUpdate(usuario);
                                        realm.commitTransaction();
                                        callMainActivity();
                                    }
                                }
                            }
                        });
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }

    private void handleFacebookAccessToken(AccessToken token) {
        //Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                           Toast.makeText(SignInActivity.this, "Authentication ok.", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private FirebaseAuth.AuthStateListener getFirebaseAuthResultHandler() {
        FirebaseAuth.AuthStateListener callback = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser userFirebase = firebaseAuth.getCurrentUser();
                if (userFirebase == null) {
                    return;
                }
                //verificar se existe alguma usuário cadastrado
                if (user == null) {
                    Usuario usuario = new Usuario();
                    usuario.setId(1L);
                    usuario.setIdUserFireBase(userFirebase.getUid());
                    usuario.setNameUserFireBase(userFirebase.getDisplayName());
                    usuario.setEmail(userFirebase.getEmail());
                    if(userFirebase.getPhotoUrl()!=null){
                       usuario.setUrlFotoFireBase(userFirebase.getPhotoUrl().toString());
                    }
                    Realm realm = Service.getInstace().getRealm(getApplicationContext());
                    realm.beginTransaction();
                    realm.insertOrUpdate(usuario);
                    realm.commitTransaction();
                }
                //chamada principal do aplicativo
                callMainActivity();
            }
        };
        return (callback);
    }

    private void callMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
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
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                //Falha na autenticação
            }
        }
    }

    public void registrar(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
