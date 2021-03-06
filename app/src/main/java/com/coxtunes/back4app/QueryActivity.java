package com.coxtunes.back4app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.coxtunes.back4app.databinding.ActivityQueryBinding;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class QueryActivity extends AppCompatActivity {

    private ActivityQueryBinding binding;
    private ArrayAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_query);

        list = new ArrayList<>();

        /*
        *  Search data for list
        * */
        binding.searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.clear();
                String data = binding.usertype.getText().toString();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRegistration");
                query.whereEqualTo("Type", data);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> objects, ParseException e) {

                        if (e==null)
                        {

                            //Log.d("RESULT", objects.get(0).getString("Email"));
                           for (ParseObject object : objects)
                           {
                               String name = object.getString("Name")+"\n"+object.getString("Email");
                               list.add(name);
                           }
                           // Check data exist or not
                            if (list.isEmpty())
                            {
                                Toast.makeText(QueryActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }

                             adapter = new ArrayAdapter(QueryActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
                             binding.listview.setAdapter(adapter);
                             adapter.notifyDataSetChanged();
                             binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     String value = String.valueOf(adapter.getItem(position));
                                     Toast.makeText(QueryActivity.this, value, Toast.LENGTH_SHORT).show();
                                 }
                             });

                        }
                        else {
                            Log.d("ERROR", "Error: " + e.getMessage());
                        }
                    }
                });

            }
        });

        /*
        *  Search Data For Single Object
        *
        * */
        binding.namesearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = binding.username.getText().toString();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRegistration");
                query.whereEqualTo("Name", name);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object != null)
                        {

//                            /*
//                             * Object Read Write Permission check [Code Sample]
//                             * */
//                            ParseACL de = object.getACL();
//                            if (de.getPublicReadAccess()) // ALSO CHECK WRITE ACCESS BY [de.getPublicWriteAccess()]
//                            {
//                                // IF PUBLIC WRITE ACCESS TRUE
//                            }
//                            else
//                            {
//                                // IF PUBLIC WRITE ACCESS FLASE
//                            }

                            /*
                            * For read any uploaded file path
                            * */
//                            ParseFile image = (ParseFile) object.get("images");
//                            String url = image.getUrl();
//                            Log.d("IMAGEURL",url);

                            String email = object.getString("Email");
                            String createdat = String.valueOf(object.getCreatedAt());
                            String objectid = object.getObjectId();
                            binding.resulttext.setText("Userid: "+objectid+"\n"+"Email: "+email+"\n"+"Date: "+createdat);

                        } else {
                            binding.resulttext.setText("No Data Found");
                            Log.d("FAILED", "Retrieved the object.");
                        }
                    }
                });

            }
        });
    }

    /*
    * Some Query Constrain KeyWord/Method
    * */
//    query.whereNotEqualTo("playerName", "Michael Yabuti");
//    query.whereNotEqualTo("playerName", "Michael Yabuti");
//    query.whereGreaterThan("playerAge", 18);
//    query.setLimit(10); // limit to at most 10 results
//    query.setSkip(10); // skip the first 10 results
//    query.orderByAscending("score"); // Sorts the results in ascending order by the score field
//    query.orderByDescending("score"); // Sorts the results in descending order by the score field
//    query.addAscendingOrder("score"); // Sorts the results in ascending order by the score field if the previous sort keys are equal.
//    query.addDescendingOrder("score"); // Sorts the results in descending order by the score field if the previous sort keys are equal.
//    query.whereLessThan("wins", 50); // Restricts to wins < 50
//    query.whereLessThanOrEqualTo("wins", 50); // Restricts to wins <= 50
//    query.whereGreaterThan("wins", 50); // Restricts to wins > 50
//    query.whereGreaterThanOrEqualTo("wins", 50); // Restricts to wins >= 50
//    query.whereExists("score");  // Finds objects that have the score set
//    query.whereDoesNotExist("score"); // Finds objects that don't have the score set

    /*Array of data send for query to database*/
//    String[] names = {"Jonathan Walsh", "Dario Wunsch", "Shawn Simon"};
////    query.whereContainedIn("playerName", Arrays.asList(names));
//    String[] names = {"Jonathan Walsh", "Dario Wunsch", "Shawn Simon"};
//query.whereNotContainedIn("playerName", Arrays.asList(names));

    /*Arraylist of data send for query to database*/
    // Find objects where the array in arrayKey contains all of the numbers 2, 3, and 4.
//    ArrayList<Integer> numbers = new ArrayList<Integer>();
//    numbers.add(2);
//    numbers.add(3);
//    numbers.add(4);
//    query.whereContainsAll("arrayKey", numbers);


    /*
    * Relational Query
    *
    * */
//    public void loadMonuments() throws ParseException {
//        ParseQuery<ParseObject> cirtyQuery = ParseQuery.getQuery("City");
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("AttractionList");
//        query.whereEqualTo("cityId", cirtyQuery.get("PxeVSZFJJa"));
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null)
//                {
//                    for (int i = 0 ; i < objects.size(); i++)
//                    {
//                        ParseObject date = null;
//                        try {
//                            date = Objects.requireNonNull(objects.get(i).getParseObject("attractionId")).fetchIfNeeded();
//
//                            // Get Finally Data
//                            String name = date.getString("attractionName");
//                            String image = date.getString("image");
//
//                            Log.d("Something", name+image);
//                        } catch (ParseException ex) {
//                            ex.printStackTrace();
//                        }
//
//                    }
//                }
//                else
//                {
//                    MartinToast.Show(e.getMessage());
//                }
//
//            }
//        });
//
//    }


    // i have a pointer in a user object then how can i check
    //this for current user then how to get this id & related data from
    // from pointer object
//    private fun loadTestData() {
//        ParseUser.getCurrentUser()?.let {
//            val mTempId = it.getParseObject("schoolID")
//            when(mTempId == null){
//                true ->{
//                    showToast(this, "No school ID found")
//                }
//                else ->{
//                    mTempId.fetchIfNeededInBackground<ParseObject> { `object`, e ->
//                            when(e == null){
//                        true ->{
//                                `object`.getString("itinerary")?.let {
//                                showToast(this, it)
//                            }
//                        }
//                            else ->{
//                            showToast(this, e.message.toString())
//                        }
//                    }
//                    }
//                }
//            }
//        }
//    }


}
