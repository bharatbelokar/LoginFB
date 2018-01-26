package bharat.com.fblogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginButton;
    ImageView imageView;
    Button showLikes,showPost;
    List<LikeResponse> likeResponseList,postResponseList;

    TextView tv, tvLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

         loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_likes", "user_posts"));


        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            imageView = (ImageView) findViewById(R.id.imageview);
                            tv = (TextView) findViewById(R.id.username);
                            String userid = (String) object.get("id");
                            String userName = (String) object.get("name");

                            Picasso.with(MainActivity.this).load("https://graph.facebook.com/" + userid + "/picture?type=large").into(imageView);
                            //Bitmap b = (Bitmap) object.get("picture");
                            tv.setText("Hello" + " " + userName);
                            Log.d("o/p", "name " + userid);

                            new GraphRequest(
                                    AccessToken.getCurrentAccessToken(),
                                    "/me/posts",
                                    null,
                                    HttpMethod.GET,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse response) {
                                            Log.e("Bharat", response.toString());

                                            try {
                                                postResponseList=new ArrayList<>();
                                                JSONArray jsonArray=response.getJSONObject().getJSONArray("data");
                                                for (int i=0;i<jsonArray.length();i++){
                                                  JSONObject postData=(JSONObject)jsonArray.get(i)  ;
                                                    LikeResponse postObj=new LikeResponse(postData.getString("story"),postData.getString("created_time"));
                                                    postResponseList.add(postObj);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

            /* handle the result */
                                        }
                                    }
                            ).executeAsync();

                            new GraphRequest(
                                    AccessToken.getCurrentAccessToken(),
                                    "/me/likes",
                                    null,
                                    HttpMethod.GET,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse response) {
                                            Log.e("Bharat", response.toString());
                                            try {
                                                likeResponseList = new ArrayList<>();
                                                JSONArray jsonArray = response.getJSONObject().getJSONArray("data");
                                                for (int j = 0; j < jsonArray.length(); j++) {
                                                    JSONObject likeData = (JSONObject) jsonArray.get(j);
                                                    LikeResponse likeObj = new LikeResponse(likeData.getString("name"), likeData.getString("created_time"));
                                                    likeResponseList.add(likeObj);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


            /* handle the result */
                                        }
                                    }
                            ).executeAsync();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,birthday,picture");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    public void show(View view) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
showLikes=(Button)findViewById(R.id.likes);
        showPost=(Button)findViewById(R.id.post);
        if (view.getId() == R.id.likes) {
            recyclerView.setAdapter(new LikeAdapter(likeResponseList, getApplicationContext()));
            showLikes.setVisibility(View.GONE);
            showPost.setVisibility(View.VISIBLE);

        } else if (view.getId() == R.id.post) {
            recyclerView.setAdapter(new LikeAdapter(postResponseList,getApplicationContext()));
            showLikes.setVisibility(View.VISIBLE);
            showPost.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
