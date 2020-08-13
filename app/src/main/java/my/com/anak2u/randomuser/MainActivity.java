package my.com.anak2u.randomuser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
TextView nameTextView, emailTextView, addressTextView, phoneTextView, dobTextView;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        addressTextView = findViewById(R.id.addressTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        dobTextView = findViewById(R.id.dobTextView);
        imageView = findViewById(R.id.imageView);
    }

    public void retrieveData(View view) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.randomuser.me/api";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray resultsArray = jsonObject.getJSONArray("results");
                            JSONObject userObject = resultsArray.getJSONObject(0);
                            JSONObject  nameObject = userObject.getJSONObject("name");
                            String firstName = nameObject.getString("first");
                            String title = nameObject.getString("title");
                            String lastName = nameObject.getString("last");

                            // title  + firstName + lastName
                            nameTextView.setText(title +" "+firstName+" "+lastName);

                            String email = userObject.getString("email");

                            emailTextView.setText(email);

                            String dob = userObject.getJSONObject("dob").getString("date");
                            dobTextView.setText(dob);

                            String phone = userObject.getString("phone");
                            phoneTextView.setText(phone);

                            JSONObject addressObject = userObject.getJSONObject("location");
                            JSONObject streetObject = addressObject.getJSONObject("street");
                            Integer number = streetObject.getInt("number");
                            String streetName = streetObject.getString("name");
                            String city = addressObject.getString("city");
                            String state = addressObject.getString("state");
                            String country = addressObject.getString("country");
                            String postcode = addressObject.getString("postcode");

                            addressTextView.setText(number + " "+streetName+" "+city+" "+state+" " +
                                    ""+postcode+" "+country );


                            //We will do imageView together tomorrow Or you can try read the documentation:
                            // Glide
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nameTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
