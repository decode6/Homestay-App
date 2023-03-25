package com.example.homestay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class AddPropertyActivity extends AppCompatActivity {
    private static final int MAX_IMAGES = 3;
    RecyclerView recyclerView;
    ArrayList<Uri> uri = new ArrayList<>();
    RecyclerAdapter adapter;

    static final int SELECT_IMAGE_CODE = 1;
    private static final int Read_Permission = 101;

    // creating variables for our edit text
    private EditText pnameEdt, pdurationEdt, pamountEdt, paddressEdt;


    // creating variable for button
    private Button submitPropertyBtn, readPropertyBtn, btnImage;

    RadioButton cooking_selectedRadioButton, parking_selectedRadioButton;
    private String cooking_selectedOption, parking_selectedOption;

    // creating a strings for storing our values from edittext fields.
    private String propertyName, propertyDuration, propertyAddress, propertyAmount;

    private ImageView itemImage;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 3;
    private Uri imagePath;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_property);


//        imageView = findViewById(R.id.imageView);
//        selectImageButton = findViewById(R.id.selectImageButton);
//        cameraButton = findViewById(R.id.cameraButton);
        // initializing our edittext and buttn
        pnameEdt = findViewById(R.id.pname);
//        pdurationEdt = findViewById(R.id.pduration);
        paddressEdt = findViewById(R.id.paddress);
        pamountEdt = findViewById(R.id.pamount);
        submitPropertyBtn = findViewById(R.id.idBtnSubmitProperty);

        RadioGroup cooking_radioGroup = (RadioGroup) findViewById(R.id.cooking_radio_group);
        RadioGroup parking_radioGroup = (RadioGroup) findViewById(R.id.parking_radio_group);


//        readPropertyBtn = findViewById(R.id.idBtnReadProperty);
        itemImage = findViewById(R.id.imageview);
        btnImage = findViewById(R.id.idImageBtn);
        recyclerView = findViewById(R.id.recyclerView_Gallery_Images);
        adapter = new RecyclerAdapter(uri);
        recyclerView.setLayoutManager(new GridLayoutManager(AddPropertyActivity.this, 4));
        recyclerView.setAdapter(adapter);
        int SELECT_IMAGE_CODE = 3;
//        if (ContextCompat.checkSelfPermission(AddPropertyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(AddPropertyActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Read_Permission);
//
//        }

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        cooking_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                cooking_selectedRadioButton = (RadioButton) findViewById(checkedId);
                cooking_selectedOption = cooking_selectedRadioButton.getText().toString();

            }
        });

        parking_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                parking_selectedRadioButton = (RadioButton) findViewById(checkedId);
                parking_selectedOption = parking_selectedRadioButton.getText().toString();

            }
        });

//        readPropertyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // opening new intent to read our data.
//                Intent i = new Intent(AddPropertyActivity.this, PropertyViewActivity.class);
//                startActivity(i);
//            }
//        });

        submitPropertyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                //String username = ParseUser.getCurrentUser().getUsername();
                propertyName = pnameEdt.getText().toString();
//                propertyDuration = pdurationEdt.getText().toString();
                propertyAddress = paddressEdt.getText().toString();
                propertyAmount = pamountEdt.getText().toString();


                //  userName = userNameEdt.getText().toString();
                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(propertyName)) {
                    pnameEdt.setError("Please enter property Name");
//                } else if (TextUtils.isEmpty(propertyDuration)) {
//                    pdurationEdt.setError("Please enter property duration");

                } else if (TextUtils.isEmpty(propertyAddress)) {
                    paddressEdt.setError("Please enter property address");
                } else if (TextUtils.isEmpty(propertyAmount)) {
                    pamountEdt.setError("Please enter property amount");
                } else {

                    addDataToDatabase(propertyName,  propertyAddress, propertyAmount, cooking_selectedOption, parking_selectedOption);
                }

            }


        });
    }

    //image upload
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            if (data.getClipData() != null) {
                int x = data.getClipData().getItemCount();


                for (int i = 0; i < x; i++) {
                    try{
                        if(uri.size() < MAX_IMAGES){
                            uri.add(data.getClipData().getItemAt(i).getUri());

                        }else{
                            Toast.makeText(this,"You can select upto "+MAX_IMAGES+" images", Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        Log.e("AddPropertyActivity","Error adding image to list:"+e.getMessage());
                    }

                }
                adapter.notifyDataSetChanged();
                Uri uri = data.getData();

            } else if (data.getData() != null) {
                String imageURL = data.getData().getPath();
                uri.add(Uri.parse(imageURL));
            }
//
        }
    }

    private void addDataToDatabase(String propertyName, String
            propertyAddress, String propertyAmount, String cooking_selectedOption, String
                                           parking_selectedOption) {


        // convert image into byte array
        List<ParseFile> imageFiles = new ArrayList<>();
        for (Uri uri : uri) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageByteArray = stream.toByteArray();

                ParseFile imageFile = new ParseFile("image.png", imageByteArray);
                imageFiles.add(imageFile);

            } catch (IOException e) {
                Log.e("AddProperty", "Error getting bitmap from URI: " + e.getMessage());
            }
        }


        // create a ParseFile with image data
        int status =  0;

        ParseObject course = new ParseObject("properties");
        course.put("propertyName", propertyName);
//        course.put("propertyDuration", propertyDuration);
        course.put("propertyAddress", propertyAddress);
        course.put("propertyAmount", propertyAmount);
        course.put("propertyParking", parking_selectedOption);
        course.put("propertyCooking", cooking_selectedOption);
        course.put("userEmail", ParseUser.getCurrentUser().getEmail());
        course.addAll("images", imageFiles);
        course.put("status",status);
        course.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(AddPropertyActivity.this, "Data added successfully!", Toast.LENGTH_SHORT).show();
                    pnameEdt.setText("");
//                    pdurationEdt.setText("");
                    paddressEdt.setText("");
                    pamountEdt.setText("");

                } else {
                    Toast.makeText(AddPropertyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}



