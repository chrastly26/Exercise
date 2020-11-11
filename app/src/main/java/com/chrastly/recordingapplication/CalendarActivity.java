package com.chrastly.recordingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    private String selectedDate;
    private String loadedString;
    private Calendar calendar;

    private RecyclerView calendarRecyclerView;
    private ArrayList<Memo> memoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendar = Calendar.getInstance();
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
        selectedDate = sdfDateTime.format(calendar.getTime());
        selectedDate = selectedDate.substring(0,10);

        loadedString = loadData(selectedDate);

        if(loadedString != "") {
            Gson gson = new Gson();
            memoArrayList = gson.fromJson(loadedString, new TypeToken<ArrayList<Memo>>() {}.getType());

            Toast.makeText(getApplicationContext(), memoArrayList.toString(),Toast.LENGTH_SHORT).show();

            calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
            CalendarRecyclerViewAdapter adapter = new CalendarRecyclerViewAdapter();
            adapter.setMemoArrayList(memoArrayList);

            calendarRecyclerView.setAdapter(adapter);
            calendarRecyclerView.setLayoutManager(new LinearLayoutManager(this ));


        }


        CalendarView calendarViewOne = findViewById(R.id.calendarViewOne);
        calendarViewOne.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year + "-" + (month+1) + "-" + dayOfMonth;

                Toast.makeText(getApplicationContext(), selectedDate.toString(),Toast.LENGTH_SHORT).show();


                loadedString = loadData(selectedDate);
                if(loadedString != "") {
                    Gson gson = new Gson();
                    memoArrayList = gson.fromJson(loadedString, new TypeToken<ArrayList<Memo>>() {}.getType());

                    Toast.makeText(getApplicationContext(), memoArrayList.toString(),Toast.LENGTH_SHORT).show();

                    calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
                    CalendarRecyclerViewAdapter adapter = new CalendarRecyclerViewAdapter();
                    adapter.setMemoArrayList(memoArrayList);

                    calendarRecyclerView.setAdapter(adapter);
                    calendarRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                }

            }
        });


    }

    // Adding Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calendar_menu, menu);
        return true;
    }

    // Menu Action
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_memo:
                Intent addMemoIntent = new Intent (CalendarActivity.this, AddMemoActivity.class);
                addMemoIntent.putExtra("com.chrastly.recordingapplication.MEMODATE", selectedDate);

                Toast.makeText(getApplicationContext(),selectedDate.toString(),Toast.LENGTH_SHORT).show();

                startActivity(addMemoIntent);
                return true;

            case R.id.search_memo:
                Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private String loadData(String selectedDate) {

        Gson gson = new Gson();
        String stringResponse = "";

        final String FILE_NAME = "RecordingApplicationMemo" + selectedDate;
        File file = new File(this.getFilesDir(), FILE_NAME);

        try {
            InputStream inputStream = new FileInputStream(file);
            StringBuilder stringBuilder = new StringBuilder();

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String receiveString = "";

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                stringResponse = stringBuilder.toString();
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringResponse;

    }


}