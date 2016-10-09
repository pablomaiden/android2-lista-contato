package br.com.meuscontatos.principal;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
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
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import br.com.meuscontatos.principal.activity.MainActivity;
import br.com.meuscontatos.principal.activity.SignUpActivity;
import br.com.meuscontatos.principal.domain.Usuario;
import br.com.meuscontatos.principal.service.Service;
import io.realm.Realm;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText et_login;
    private EditText et_senha;
    private SignInButton signInButton;
    private Button entrar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN_GOOGLE   = 7859;
    private static final int RC_SIGN_IN_FACEBOOK = 64206;
    private Usuario user;
    public static CallbackManager callbackmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        Realm realm = Service.getInstace().getRealm(getApplicationContext());
        user = realm.where(Usuario.class).findFirst();

        et_login = (EditText) findViewById(R.id.et_login);
        et_senha = (EditText) findViewById(R.id.et_senha);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        entrar = (Button) findViewById(R.id.entrar);

        //Autenticar pelo facebook
        callbackManager = CallbackManager.Factory.create();
        //Autenticar pelo google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(AppIndex.API).build();

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
                                    Toast.makeText(SignInActivity.this, R.string.error_login, Toast.LENGTH_LONG).show();
                                } else {
                                    FirebaseUser userFirebase = task.getResult().getUser();
                                    if (user != null) {
                                        Usuario usuario = new Usuario();
                                        usuario.setId(1L);
                                        usuario.setIdUserFireBase(userFirebase.getUid());
                                        usuario.setNameUserFireBase(userFirebase.getDisplayName());
                                        usuario.setEmail(userFirebase.getEmail());
                                        if (userFirebase.getPhotoUrl() != null) {
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

        FacebookSdk.sdkInitialize(getApplicationContext());
        Button btnFacebookLogin = (Button) findViewById(R.id.btnFacebookLogin);
        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFacebookLogin();
            }
        });
        verifyLogged();
    }

    private void verifyLogged(){
        if( mAuth.getCurrentUser() != null ){
            callMainActivity();
        }
        else{
            mAuth.addAuthStateListener( mAuthListener );
        }
        AccessToken token = AccessToken.getCurrentAccessToken();
        if(token!=null){
            Log.d("FACEBOOK", "Você esta logado");
            callMainActivity();
        }
    }

    private void onFacebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "user_friends", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FACEBOOK", "Facebook Succesfully Login!");

                String token = loginResult.getAccessToken().getToken();
                Log.d("FACEBOOK", "TOKEN: " + token);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject json, GraphResponse response) {
                        if (response.getError() != null) {
                            Log.d("FACEBOOK", response.getError().getErrorMessage());
                        } else {
                            try {
                                String jsonresult = String.valueOf(json);
                                Log.d("FACEBOOK", "Facebook Data: " + jsonresult);
                                Usuario usuario = new Usuario();
                                usuario.setId(1L);
                                usuario.setIdUserFireBase(json.getString("id"));
                                usuario.setNameUserFireBase(json.getString("name"));
                                usuario.setEmail(json.getString("email"));

                                JSONObject jsonPicture = json.getJSONObject("picture");
                                JSONObject jsonUrl     = jsonPicture.getJSONObject("data");
                                usuario.setUrlFotoFireBase(jsonUrl.getString("url"));

                                Realm realm = Service.getInstace().getRealm(getApplicationContext());
                                realm.beginTransaction();
                                realm.insertOrUpdate(usuario);
                                realm.commitTransaction();
                                callMainActivity();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,id,name,friends,last_name,first_name,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("FACEBOOK", "Facebook Login Canceled!");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("FACEBOOK", e.toString());
            }
        });
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

        if (requestCode == RC_SIGN_IN_FACEBOOK) {
            //callMainActivity();
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void registrar(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        verifyLogged();
        Log.d("FACEBOOK", "RESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
        verifyLogged();
        Log.d("FACEBOOK", "PAUSE");
    }

    @Override
    public void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        verifyLogged();
        Log.d("FACEBOOK", "START");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(mGoogleApiClient, getIndexApiAction());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SignIn Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mGoogleApiClient, getIndexApiAction());
        mGoogleApiClient.disconnect();
    }
}
