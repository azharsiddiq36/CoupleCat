package com.azhar.couplecat.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.azhar.couplecat.Fragment.PersonalDataFotoFragment;
import com.azhar.couplecat.Fragment.PersonalDataKtpFragment;
import com.azhar.couplecat.Fragment.PersonalDataSuccessFragment;
import com.azhar.couplecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalDataActivity extends AppCompatActivity {
    @BindView(R.id.btnNext)
    Button btnNext;
    int step;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    PersonalDataKtpFragment personalDataKtpFragment;
    PersonalDataFotoFragment personalDataFotoFragment;
    PersonalDataSuccessFragment personalDataSuccessFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        personalDataKtpFragment = new PersonalDataKtpFragment();
        personalDataFotoFragment = new PersonalDataFotoFragment();
        personalDataSuccessFragment = new PersonalDataSuccessFragment();
        ButterKnife.bind(this);
        if (step == 0) {
            loadFragment(personalDataKtpFragment);

        }
    }

    @OnClick(R.id.btnNext)
    protected void btnNext(View view) {
        if ((personalDataKtpFragment).partimage != null){
            this.step +=1;
        }
        if (step == 1) {
            loadFragment(personalDataFotoFragment);
            if ((personalDataKtpFragment).partimage != null){
                (personalDataKtpFragment).sendKtp();
                Toast.makeText(this, "Tahap Upload KTP Berhasil", Toast.LENGTH_SHORT).show();
                this.step += 1;
            }
            else{
                Toast.makeText(this, "Harap Klik Icon Foto Untuk Mulai Upload", Toast.LENGTH_SHORT).show();
            }
        } else if (this.step == 2) {
            loadFragment(personalDataSuccessFragment);
            if ((personalDataFotoFragment).partimage != null){
                (personalDataFotoFragment).sendFotoDiri();
                Toast.makeText(this, "Tahap Upload Foto Diri Berhasil", Toast.LENGTH_SHORT).show();
                btnNext.setText("Selesai");
                this.step += 1;
            }
            else{
                Toast.makeText(this, "Harap Klik Icon Foto Untuk Mulai Upload", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            onBackPressed();
            finish();
        }

    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
