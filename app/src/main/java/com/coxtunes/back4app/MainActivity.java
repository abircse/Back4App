package com.coxtunes.back4app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.coxtunes.back4app.databinding.ActivityMainBinding;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<UserModel> userlist;
    private UserAdapter adapter;
    private String userid = "";
    private String username = "";
    private String useremail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        userlist = new ArrayList<>();
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        // call to load when activity
        fetchUserInformation();

        /*
        * Search Funcationality
        *
        * */
        binding.searchedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // call filter method & pass user inputed text
                filter(s.toString());
            }
        });
    }

    /*
     * Fetch Data from database & load it to recyclerview
     * */
    private void fetchUserInformation()
    {
        binding.progressBar.setVisibility(View.VISIBLE);
        userlist.clear();
        // Table object query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRegistration");
        //query.setLimit(2);
        query.addDescendingOrder(ParseObject.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        // get Auth generate key from database row
                        userid = objects.get(i).getObjectId();
                        // Get other column data
                        username = String.valueOf(objects.get(i).get("Name"));
                        useremail = String.valueOf(objects.get(i).get("Email"));
                        // bind these data to model
                        userlist.add(new UserModel(userid, username, useremail));
                        binding.progressBar.setVisibility(View.GONE);
                    }
                    // set adapter & binding data & set to recyclerview
                    adapter = new UserAdapter(userlist, MainActivity.this);
                    binding.recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    // Delete call back implementation
                    adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(final int position) {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRegistration");
                            query.getInBackground(userlist.get(position).getUserid(), new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {

                                    if (e == null)
                                    {
                                        try {
                                            object.delete();
                                            object.saveInBackground();
                                            Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                        } catch (ParseException ex) {
                                            ex.printStackTrace();
                                        }

                                    }
                                }
                            });

                        }
                    });
                }
            }
        });
    }

    /*
     * Data filter Method
     * */
    private void filter(String text) {

        List<UserModel> userdatalist = new ArrayList<>();
        for (UserModel model : userlist)
        {
            // We are searching Username here
            if(model.getName().toLowerCase().contains(text.toLowerCase()))
            {
                userdatalist.add(model);
            }
        }
        // call adapter class filter method
        adapter.filterlist(userdatalist);
    }


    public void gotoadd(View view) {
        startActivity(new Intent(getApplicationContext(), AddActivity.class));
    }
}
