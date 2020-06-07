package com.coxtunes.back4app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coxtunes.back4app.databinding.ActivityAddBinding;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add);
        getSupportActionBar().setTitle("Add user");
        // Test Parse Connection working or not
        //ParseInstallation.getCurrentInstallation().saveInBackground();

        /*
         * Send Data to Database
         * */
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject parseObject = new ParseObject("UserRegistration");
                parseObject.put("Name", binding.name.getText().toString());
                parseObject.put("Email", binding.email.getText().toString());
                parseObject.put("Type", binding.type.getText().toString());
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null)
                        {
                            Toast.makeText(AddActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    e.getMessage().toString(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                });
            }
        });
    }
}
