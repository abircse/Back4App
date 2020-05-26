package com.coxtunes.back4app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coxtunes.back4app.databinding.ActivityUpdateBinding;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class UpdateActivity extends AppCompatActivity {


    private ActivityUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_update);
        getSupportActionBar().setTitle("Update user");
        Intent i = getIntent();
        final String userid = i.getStringExtra("ID");
        String username = i.getStringExtra("Name");
        String useremail = i.getStringExtra("Email");
        binding.name.setText(username);
        binding.email.setText(useremail);

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRegistration");
                query.getInBackground(userid, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            // Update the fields we want to
                            object.put("Name", binding.name.getText().toString());
                            object.put("Email", binding.email.getText().toString());
                            // All other fields will remain the same
                            object.saveInBackground();
                            Toast.makeText(UpdateActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }
                });
            }
        });

    }
}
