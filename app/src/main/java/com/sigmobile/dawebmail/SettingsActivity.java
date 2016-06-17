package com.sigmobile.dawebmail;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.sigmobile.dawebmail.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by rish on 6/10/15.
 */
public class SettingsActivity extends AppCompatActivity {

    @Bind(R.id.settings_networkcell_switch)
    Switch switch_mobile;
    @Bind(R.id.settings_toolbar)
    Toolbar toolbar;

    boolean toggleMobileData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarText));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Settings");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        SharedPreferences prefs = getSharedPreferences(Constants.USER_PREFERENCES, Context.MODE_PRIVATE);
        toggleMobileData = prefs.getBoolean(Constants.TOGGLE_MOBILEDATA, true);

        switch_mobile.setChecked(toggleMobileData);
        switch_mobile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    showConfirmToggleDataDialog();
            }
        });
    }

    private void showConfirmToggleDataDialog() {
        final MaterialDialog materialDialog = new MaterialDialog(SettingsActivity.this);
        materialDialog.setTitle(getString(R.string.dialog_title_toggle_data));
        materialDialog.setMessage(getString(R.string.dialog_msg_toggle_data));
        materialDialog.setPositiveButton(getString(R.string.dialog_btn_positive_toggle_data), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMobileDataSettings();
                materialDialog.dismiss();
            }
        });
        materialDialog.setNegativeButton(getString(R.string.dialog_btn_negative_toggle_data), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_mobile.setChecked(true);
                saveMobileDataSettings();
                materialDialog.dismiss();
            }
        });
        materialDialog.setCanceledOnTouchOutside(false);
        materialDialog.show();
    }

    private void saveMobileDataSettings() {
        SharedPreferences prefs = getSharedPreferences(Constants.USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Constants.TOGGLE_MOBILEDATA, switch_mobile.isChecked()).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}