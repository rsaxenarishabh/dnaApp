package com.dnamedical.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dnamedical.R;
import com.dnamedical.utils.Utils;

import butterknife.BindView;

public class EditProfileActivity extends AppCompatActivity {


    @BindView(R.id.update_edit_name)
    EditText editTextUpdateName;

    @BindView(R.id.update_edit_username)
    EditText editTextUsername;

    @BindView(R.id.update_selectCollege)
    Spinner spinnerSelectCollege;

    @BindView(R.id.btn_update)
    Button btnUpdate;


    private String update_edit_name;
    private String update_edit_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



    btnUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validation();
        }
    });
    }



    private void validation() {

        update_edit_name = editTextUpdateName.getText().toString();
        update_edit_username = editTextUsername.getText().toString();
       /* edit_email = editEmailId.getText().toString();
        edit_phonetxt = edit_phone.getText().toString();
        edit_password = editPassword.getText().toString();*/


        if (TextUtils.isEmpty(update_edit_name.trim()) || update_edit_name.length() == 0) {
            editTextUpdateName.setError(getString(R.string.invalid_name));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_name));
            return;
        }
        if (TextUtils.isEmpty(update_edit_username.trim()) || update_edit_username.length() == 0) {
            editTextUsername.setError(getString(R.string.invalid_username));
            Utils.displayToast(getApplicationContext(), getString(R.string.invalid_username));
            return;
        }


       /* if (TextUtils.isEmpty(edit_phonetxt)) {

            edit_phone.setError(getString(R.string.invalid_email));

            return;
        } else {
            if (edit_phonetxt.length() < 10) {
                edit_phone.setError(getString(R.string.valid_phone));
                return;
            }
        }
   */
    }
    
}
