package com.example.retofitpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi,jsonPlaceHolderApiBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

//        Gson gson = new GsonBuilder().serializeNulls().create();
//
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().
//                addInterceptor(loggingInterceptor)
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com/")
//                .addConverterFactory(GsonConverterFactory.create (gson))
//                .client(okHttpClient)
//                .build();
//        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);



        Retrofit retrofitBank = new Retrofit.Builder()
                .baseUrl("https://api.jsonbin.io/b/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       jsonPlaceHolderApiBank = retrofitBank.create(JsonPlaceHolderApi.class);


       getBankData();

     // getPost();
      //getComment();
      //createPost();
      //updatePost();
        //deletePost();
    }

    private void getBankData() {

        Call<List<BankData>> call = jsonPlaceHolderApiBank.getBankData();
        call.enqueue(new Callback<List<BankData>>() {
            @Override
            public void onResponse(Call<List<BankData>> call, Response<List<BankData>> response) {
                if (!response.isSuccessful()){

                    textViewResult.setText("Code : "+response.code());
                    return;
                }

                List<BankData> bankData = response.body();

                for (BankData bankData1 : bankData){
                    String content = "";
                    content += "Name: "+ bankData1.getName() + "\n";
                    content += "principalAmount : "+bankData1.getPrincipalAmount() + "\n";
                    content += "interestRate: "+ bankData1.getInterestRate() + "\n";
                    content += "tenureInMonth: "+ bankData1.getTenureInMonth() + "\n";

                    textViewResult.append(content);
                }




            }

            @Override
            public void onFailure(Call<List<BankData>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost() {

        Call<Void> call = jsonPlaceHolderApi.deietePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                textViewResult.setText("Code "+ response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    private void updatePost() {
        Post post = new Post(12,null,"New Text");
        Call<Post> call = jsonPlaceHolderApi.putPost(5,post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){

                    textViewResult.setText("Code : "+response.code());
                    return;
                }


                Post postResponce = response.body();
                String content = "";
                content += "Code: " +response.code() + "\n";
                content += "ID: " + postResponce.getId()+"\n";
                content += "user ID: " +postResponce.getUserId()+"\n";
                content += "Title: " +postResponce.getTitle()+"\n";
                content += "Text : " +postResponce.getText()+"\n\n";

                textViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    private void createPost() {

       // Post post = new Post(23,"New Title","New Text");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId","25");
        fields.put("title","NEEW TITLE");
        fields.put("text","NEEW TEXT");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){

                    textViewResult.setText("Code : "+response.code());
                    return;
                }


                Post postResponce = response.body();
                String content = "";
                content += "Code: " +response.code() + "\n";
                content += "ID: " + postResponce.getId()+"\n";
                content += "user ID: " +postResponce.getUserId()+"\n";
                content += "Title: " +postResponce.getTitle()+"\n";
                content += "Text : " +postResponce.getText()+"\n\n";

                textViewResult.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        });

    }

    private void getComment() {
        Call<List<Comments>> call = jsonPlaceHolderApi.getComment("posts/3/comments");

        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                if (!response.isSuccessful()){

                    textViewResult.setText("Code : "+response.code());
                    return;
                }

                List<Comments> comments = response.body();
                for (Comments comment :comments){
                    String content = "";
                    content += "ID: " + comment.getId()+"\n";
                    content += "post ID: " +comment.getPostId()+"\n";
                    content += "Name: " +comment.getName()+"\n";
                    content += "Email : " +comment.getEmail()+"\n\n";

                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getPost() {

        Map<String , String> parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order", "desc");


        Call<List<Post>> call = jsonPlaceHolderApi.getPost(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()){

                    textViewResult.setText("Code : "+response.code());
                    return;
                }
                List<Post> posts = response.body();

                for (Post post : posts){

                    String content = "";
                    content += "ID: " + post.getId()+"\n";
                    content += "User ID: " +post.getUserId()+"\n";
                    content += "Title: " +post.getTitle()+"\n";
                    content += "Text : " +post.getText()+"\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                textViewResult.setText(t.getMessage());

            }
        });
    }
}