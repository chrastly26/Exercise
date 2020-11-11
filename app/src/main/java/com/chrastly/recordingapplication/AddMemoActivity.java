package com.chrastly.recordingapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddMemoActivity extends AppCompatActivity  {

    private TextInputEditText titleTextField;
    private TextInputEditText descriptionTextField;

    private String title, description;

    private TextInputEditText startDateTextField;
    private TextInputEditText startTimeTextField;
    private TextInputEditText endDateTextField;
    private TextInputEditText endTimeTextField;

    private TextInputLayout startDateTextFieldLayout, startTimeTextFieldLayout, endDateTextFieldLayout, endTimeTextFieldLayout;
    private TextInputLayout titleTextFieldLayout, descriptionTextFieldLayout;

    private int calendarDate, calendarMonth, calendarYear, calendarHour, calendarMinute;
    private String startDateString, startTimeString, endDateString, endTimeString;

    private String selectedDate;

    private Dialog dialogueChoosing;
    private ImageView closeDialogue;
    private LinearLayout cameraLayout, galleryLayout;

    private ImageView photoChosen;

    String currentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        // Pre-call The Class

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("com.chrastly.recordingapplication.MEMODATE");


        // Title
        titleTextFieldLayout = findViewById(R.id.titleTextFieldLayout);
        titleTextField = findViewById(R.id.titleTextField);
        titleTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                titleTextFieldLayout.setError(null);
            }
        });


        // Start Date

        startDateTextFieldLayout = findViewById(R.id.startDateTextFieldLayout);
        startDateTextField = findViewById(R.id.startDateTextField);
        startDateTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    startDateTextFieldLayout.setError(null);

                    final Calendar calendar = Calendar.getInstance();

                    calendarDate = calendar.get(Calendar.DAY_OF_MONTH);
                    calendarMonth = calendar.get(Calendar.MONTH);
                    calendarYear = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddMemoActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            startDateString = dayOfMonth + "-" + (month + 1) + "-" + year;
                            TextInputEditText startDateTextField = findViewById(R.id.startDateTextField);
                            startDateTextField.setText(startDateString);
                        }
                    }, calendarYear, calendarMonth, calendarDate);

                    datePickerDialog.show();

                }

            }

        });

        // Start Time

        startTimeTextFieldLayout = findViewById(R.id.startTimeTextFieldLayout);
        startTimeTextField = findViewById(R.id.startTimeTextField);
        startTimeTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    startTimeTextFieldLayout.setError(null);

                    final Calendar calendar = Calendar.getInstance();
                    calendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                    calendarMinute = calendar.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddMemoActivity.this, android.R.style.Theme_Holo_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            startTimeString = hourOfDay + "-" + minute;
                            TextInputEditText startTimeTextField = findViewById(R.id.startTimeTextField);
                            startTimeTextField.setText(startTimeString);
                        }
                    }, calendarHour, calendarMinute, true);

                    timePickerDialog.show();

                }

            }

        });

        // End Date

        endDateTextFieldLayout = findViewById(R.id.endDateTextFieldLayout);
        endDateTextField = findViewById(R.id.endDateTextField);
        endDateTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    endDateTextFieldLayout.setError(null);

                    final Calendar calendar = Calendar.getInstance();

                    calendarDate = calendar.get(Calendar.DAY_OF_MONTH);
                    calendarMonth = calendar.get(Calendar.MONTH);
                    calendarYear = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddMemoActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            endDateString = dayOfMonth + "-" + (month + 1) + "-" + year;
                            TextInputEditText endDateTextField = findViewById(R.id.endDateTextField);
                            endDateTextField.setText(endDateString);
                        }
                    }, calendarYear, calendarMonth, calendarDate);

                    datePickerDialog.show();

                }

            }

        });

        // End Time

        endTimeTextFieldLayout = findViewById(R.id.endTimeTextFieldLayout);
        endTimeTextField = findViewById(R.id.endTimeTextField);
        endTimeTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    endTimeTextFieldLayout.setError(null);

                    final Calendar calendar = Calendar.getInstance();
                    calendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                    calendarMinute = calendar.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddMemoActivity.this, android.R.style.Theme_Holo_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            endTimeString = hourOfDay + "-" + minute;
                            TextInputEditText endTimeTextField = findViewById(R.id.endTimeTextField);
                            endTimeTextField.setText(endTimeString);
                        }
                    }, calendarHour, calendarMinute, true);

                    timePickerDialog.show();

                }

            }

        });

        Button cameraButton = findViewById(R.id.cameraButton);
        Button galleryButton = findViewById(R.id.galleryButton);
        photoChosen = findViewById(R.id.photoChosenMemo);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 103);
            }
        });



    }




    // Save function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_memo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_memo:

                Memo memo = new Memo();

                titleTextField = findViewById(R.id.titleTextField);
                title = titleTextField.getText().toString();

                descriptionTextField = findViewById(R.id.descriptionTextField);
                description = descriptionTextField.getText().toString();

                startDateString = startDateTextField.getText().toString();
                startTimeString = startTimeTextField.getText().toString();
                endDateString = endDateTextField.getText().toString();
                endTimeString = endTimeTextField.getText().toString();


                // Validate Data
                boolean checkedTitle = validateTitle(title, titleTextFieldLayout);

                boolean checkedTime = validateTime(startDateString, startTimeString, endDateString, endTimeString,
                        startDateTextFieldLayout, startTimeTextFieldLayout, endDateTextFieldLayout, endTimeTextFieldLayout);

                memo.setTitle(title);
                memo.setDescription(description);
                memo.setStartDate(startDateString);
                memo.setStartTime(startTimeString);
                memo.setEndDate(endDateString);
                memo.setEndTime(endTimeString);

                Toast.makeText(getApplicationContext(),memo.getStartDate().toString(), Toast.LENGTH_SHORT).show();

                if (checkedTitle == true && checkedTime ==true){
                    saveData(selectedDate, memo);
                    Toast.makeText(getApplicationContext(),memo.toString(), Toast.LENGTH_SHORT).show();
                }

  /*              Toast.makeText(getApplicationContext(),memo.toString(), Toast.LENGTH_SHORT).show();*/

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // Title Validation
    public boolean validateTitle(String title, TextInputLayout titleInputTextLayout){

        if(title.trim().equals("")){
            titleInputTextLayout.setError("Please add a title");
            return false;
        }else {
            return true;
        }

    }


    // Date and Time Validation

    public boolean validateTime(String startDateString, String startTimeString, String endDateString, String endTimeString,
                             TextInputLayout startDateTextFieldLayout, TextInputLayout startTimeTextFieldLayout,
                             TextInputLayout endDateTextFieldLayout,TextInputLayout endTimeTextFieldLayout){

        Date startDateTime, endDateTime;

        if (startDateString.trim().equals("") || startTimeString.trim().equals("") ||
                    endDateString.trim().equals("") || endTimeString.trim().equals("")){


            Toast.makeText(getApplicationContext(),"AHAHAHAH", Toast.LENGTH_SHORT).show();


            if (startDateString.trim().equals("")){
                startDateTextFieldLayout.setError("Please select start date");
            }

            if (startTimeString.trim().equals("")){
                startTimeTextFieldLayout.setError("Please select start time");
            }

            if (endDateString.trim().equals("")){
                endDateTextFieldLayout.setError("Please select end date");
            }

            if (endTimeString.trim().equals("")){
               endTimeTextFieldLayout.setError("Please select end time");
            }

            return false;
        }

        else{

            String startDateTimeString = startDateString + "-" + startTimeString ;
            String endDateTimeString = endDateString + "-" + endTimeString ;

            SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");


            try {
                startDateTime = sdfDateTime.parse(startDateTimeString);
                endDateTime = sdfDateTime.parse(endDateTimeString);

                if (startDateTime.compareTo(endDateTime) > 0) {
                    endDateTextFieldLayout.setError("Please check time");
                    endTimeTextFieldLayout.setError("Please check time");

                    return false;

                }

            } catch (ParseException e) {
                e.printStackTrace();

            }

            return true;
        }

    }


    // Save Data
    public void saveData(String selectedDate, Memo memo){

        final String FILE_NAME = "RecordingApplicationMemo" + selectedDate;
        File file = new File(this.getFilesDir(), FILE_NAME);

        if(file.exists()){

            String loadedString = loadData(selectedDate);
            if(loadedString != "") {
                Gson returnedGson = new Gson();
                ArrayList<Memo> existingMemoArrayList = returnedGson.fromJson(loadedString, new TypeToken<ArrayList<Memo>>() {}.getType());
                existingMemoArrayList.add(memo);

                Gson gson = new Gson();
                String objectJSON = gson.toJson(existingMemoArrayList);

                Toast.makeText(getApplicationContext(), objectJSON.toString(), Toast.LENGTH_SHORT).show();

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(objectJSON.getBytes());
                    fileOutputStream.flush();
                    fileOutputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();


            }
        }

        else {

            ArrayList<Memo> memoArrayList = new ArrayList();
            memoArrayList.add(memo);

            Gson gson = new Gson();
            String objectJSON = gson.toJson(memoArrayList);

            Toast.makeText(getApplicationContext(), objectJSON.toString(), Toast.LENGTH_SHORT).show();

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(objectJSON.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    // Load Data
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



    private void askCameraPermission(){

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, 101);

        }

        else{

           dispatchTakePictureIntent();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 101){
            if(grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                dispatchTakePictureIntent();

            }
        }else{
            Toast.makeText(getApplicationContext(),"No Camera Permission", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 102){
            if(resultCode == Activity.RESULT_OK){
                File FileURI = new File(currentPhotoPath);
                photoChosen.setImageURI(Uri.fromFile(FileURI));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(FileURI);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

            }
        }

        if(requestCode == 103){
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_" + getFileExt(contentUri);
                photoChosen.setImageURI(contentUri);

            }
        }


    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        /*File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);*/
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

                ex.printStackTrace();

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.chrastly.recordingapplication.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 102);
            }
        }


    }

    private String getFileExt(Uri contentUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(contentUri));
    }





}


