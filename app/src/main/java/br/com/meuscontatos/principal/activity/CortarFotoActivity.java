package br.com.meuscontatos.principal.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropImageView;
import br.com.meuscontatos.principal.R;

public class CortarFotoActivity extends AppCompatActivity {

    private CropImageView mCropImageView;
    private CropImageOptions mOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cropp_image_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        getSupportActionBar().setTitle("Edição de foto");
        mCropImageView = (CropImageView) findViewById(R.id.cropImageView);
    }
}
