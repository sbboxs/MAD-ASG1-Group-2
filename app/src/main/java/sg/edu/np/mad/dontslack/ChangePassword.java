package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    DBHandler dbHandler = new DBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        //Get bundle from previous activity
        Bundle userBundle = getIntent().getBundleExtra("Bundle");
        User currentUser = (User) userBundle.getSerializable("User");

        ImageView goBackButton = findViewById(R.id.backHome5);
        goBackButton.setOnClickListener(v -> {
            Intent myIntent2 = new Intent(ChangePassword.this, Profile.class);
            startActivity(myIntent2);
        });

        //Confirm change password button
        Button confirmChangeButton = findViewById(R.id.changePWButton);
        confirmChangeButton.setOnClickListener(v -> {
            @SuppressLint("CutPasteId") EditText etCurrentPassword = findViewById(R.id.editTextCurrentPW);
            @SuppressLint("CutPasteId") EditText etNewPassword = findViewById(R.id.editTextNewPW);
            EditText etConfirmNewPassword = findViewById(R.id.editTextConfirmPW);
            String currentPassword = etCurrentPassword.getText().toString();
            String newPassword = etNewPassword.getText().toString();
            String confirmNewPassword = etConfirmNewPassword.getText().toString();

            //Check if all the field is filled
            if(!currentPassword.equals("") && !newPassword.equals("") && !confirmNewPassword.equals("")){
                //Check if the current password is correct
                if(currentPassword.equals(currentUser.getPassword())){
                    //Check if the new password same to the confirm new password
                    if(newPassword.equals(confirmNewPassword)){
                        //Check if the new password is same as the current password
                        if(!confirmNewPassword.equals(currentPassword)){
                            currentUser.setPassword(confirmNewPassword);
                            dbHandler.updateUserData(currentUser);
                            Toast.makeText(ChangePassword.this,"Password change successfully",Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(ChangePassword.this,Profile.class);
                            startActivity(myIntent);
                        }
                        else{
                            Toast.makeText(ChangePassword.this,"New password could not be same as current password",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(ChangePassword.this,"New password is not match",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ChangePassword.this,"Incorrect current password",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(ChangePassword.this,"Please ensure all fields are filed",Toast.LENGTH_SHORT).show();
            }
        });




    }
}