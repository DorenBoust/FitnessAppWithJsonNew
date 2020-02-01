package com.example.fitnessapp.user;

import android.content.Intent;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AsyncJSON extends AsyncTask<String, Integer, User>{



    @Override
    protected User doInBackground(String... strings) {

        List<Exercise> exercises = new ArrayList<>();
        List<Day> days = new ArrayList<>();


        try {
            URL url = new URL("http://appfitness.boust.me/wp-json/acf/v3/trainers?appConnection=" + strings[0]);
            System.out.println(url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            InputStream inputStream = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null){
                sb.append(line);
            }

            String json = sb.toString();

            JSONArray rootJSONArray = new JSONArray(json);
            JSONObject rootJSONObject = rootJSONArray.getJSONObject(0);

            JSONObject acf = rootJSONObject.getJSONObject("acf");

            String name = acf.getString("name");
            String bDay = acf.getString("bDay");
            String height = acf.getString("height");
            String job = acf.getString("job");
            String phoneNumber = acf.getString("phoneNumber");
            String email = acf.getString("email");
            String goal = acf.getString("goal");
            String limitation = acf.getString("limitation");

            JSONObject training = acf.getJSONObject("training");
            String[] daysName = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

            for (int i = 0; i < daysName.length ; i++) {
                JSONObject day = training.getJSONObject(daysName[i]);

                String numberOfExercies = day.getString("numberOfExercies");
                int numberOfExerciesInt = Integer.parseInt(numberOfExercies);

                for (int j = 0; j <numberOfExerciesInt ; j++) {
                    String exNumber = "ex" + (j+1);
                    JSONObject ex = day.getJSONObject(exNumber);

                    String exNameJson = ex.getString("exName");
                    String[] exSplit = exNameJson.split("/");
                    String exJSON = "https://appfitness.boust.me/wp-json/acf/v3/exercises?name=" + exSplit[exSplit.length-1];

                    URL urlEx = new URL(exJSON);
                    HttpURLConnection conEx = (HttpURLConnection) urlEx.openConnection();

                    InputStream inputStreamEx = conEx.getInputStream();
                    BufferedReader readerEx = new BufferedReader(new InputStreamReader(inputStreamEx));

                    StringBuilder sbEx = new StringBuilder();
                    String lineEx = null;

                    while ((lineEx = readerEx.readLine()) != null){
                        sbEx.append(lineEx);
                    }

                    String jsonEx = sbEx.toString();
                    System.out.println(jsonEx);

                    JSONArray rootJSONArrayEx = new JSONArray(jsonEx);
                    JSONObject rootJSONObjectEx = rootJSONArrayEx.getJSONObject(0);

                    JSONObject acfEx = rootJSONObjectEx.getJSONObject("acf");

                    String exName = acfEx.getString("name");
                    System.out.println(acfEx);
                    //TODO:Error image null
                    JSONObject image = acfEx.getJSONObject("image");
                    String imageUrl = image.getString("url");

                    JSONObject video = acfEx.getJSONObject("video");
                    String videoUrl = video.getString("url");




                    String set = ex.getString("sets");
                    String repitition = ex.getString("repitition");
                    String rest = ex.getString("rest");
                    String notes = ex.getString("הערות");

                    Exercise exercise = new Exercise(exName,set,repitition,rest,notes,imageUrl,videoUrl);
                    exercises.add(exercise);

                }
                days.add(new Day(exercises));
            }

            User user = new User(name,bDay,height,job,phoneNumber,email,goal,limitation,days);

            return user;

        }catch (IOException e){
            System.out.println("Error user JSON");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(User user) {
        System.out.println(user);
    }
}
