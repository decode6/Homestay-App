package com.example.homestay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class AddPropertyActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE_CODE = 1;
    // creating variables for our edit text
    private EditText pnameEdt, pdurationEdt, pamountEdt,paddressEdt;


    // creating variable for button
    private Button submitPropertyBtn, readPropertyBtn, btnImage;

    RadioButton cooking_selectedRadioButton,parking_selectedRadioButton;
   private String cooking_selectedOption,parking_selectedOption;

    // creating a strings for storing our values from edittext fields.
    private String propertyName, propertyDuration, propertyAddress,propertyAmount;
    //private final int PICK_IMAGE_REQUEST = 1;
//    private final int CAMERA_REQUEST = 2;
//    private ImageView imageView;
//    private Button selectImageButton;
//    private Button cameraButton;
//    private Uri imageUri;
    private ImageView itemImage;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 99;
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
        pdurationEdt = findViewById(R.id.pduration);
        paddressEdt = findViewById(R.id.paddress);
        pamountEdt = findViewById(R.id.pamount);
        submitPropertyBtn = findViewById(R.id.idBtnSubmitProperty);

        RadioGroup cooking_radioGroup = (RadioGroup) findViewById(R.id.cooking_radio_group);
        RadioGroup parking_radioGroup = (RadioGroup) findViewById(R.id.parking_radio_group);


//        readPropertyBtn = findViewById(R.id.idBtnReadProperty);
        itemImage = findViewById(R.id.imageview);
        btnImage = findViewById(R.id.idImageBtn);
        int SELECT_IMAGE_CODE = 1;
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
                propertyDuration = pdurationEdt.getText().toString();
                propertyAddress = paddressEdt.getText().toString();
                propertyAmount = pamountEdt.getText().toString();


                //  userName = userNameEdt.getText().toString();
                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(propertyName)) {
                    pnameEdt.setError("Please enter property Name");
                } else if (TextUtils.isEmpty(propertyDuration)) {
                    pdurationEdt.setError("Please enter property duration");

                } else if (TextUtils.isEmpty(propertyAddress)) {
                    paddressEdt.setError("Please enter property address");
                } else if (TextUtils.isEmpty(propertyAmount)) {
                    pamountEdt.setError("Please enter property amount");
                }else {
                    // calling method to add data to Firebase Firestore.
                    addDataToDatabase(propertyName,propertyDuration,propertyAddress,propertyAmount,cooking_selectedOption,parking_selectedOption);
                }

            }

        });
    }

    //image upload
    private void chooseImage() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "title"), SELECT_IMAGE_CODE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                Uri uri = data.getData();
                itemImage.setImageURI(uri);
            imagePath = data.getData();
            imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
            itemImage.setImageBitmap(imageToStore);
            Toast.makeText(this, "image to bitmap", Toast.LENGTH_SHORT).show();
           }
//            if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK && data == null && data.getData() != null) {
//
//
//            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addDataToDatabase(String propertyName, String propertyDuration,String propertyAddress,String propertyAmount,String cooking_selectedOption,String parking_selectedOption) {

        // convert image into byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
         imageToStore.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageByteArray = stream.toByteArray();

        // create a ParseFile with image data
        ParseFile imageFile = new ParseFile("image.png", imageByteArray);

        imageFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // create a ParseObject to store data
                    ParseObject course = new ParseObject("properties");
                    course.put("propertyName", propertyName);
                    course.put("propertyDuration", propertyDuration);
                    course.put("propertyAddress", propertyAddress);
                    course.put("propertyAmount", propertyAmount);
                    course.put("propertyParking", parking_selectedOption);
                    course.put("propertyCooking", cooking_selectedOption);
                    course.put("image", imageFile);
                    course.put("userEmail",ParseUser.getCurrentUser().getEmail());
                    // save the data in a background thread
                    course.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(AddPropertyActivity.this, "Data added successfully!", Toast.LENGTH_SHORT).show();
                                pnameEdt.setText("");
                                pdurationEdt.setText("");
                                paddressEdt.setText("");
                                pamountEdt.setText("");

                            } else {
                                Toast.makeText(AddPropertyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(AddPropertyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
//
//        // Configure Query
//        ParseObject courseList = new ParseObject("courses");
//        courseList.put("courseName", courseName);
//        courseList.put("courseDescription", courseDescription);
//        courseList.put("courseDuration", courseDuration);
//        courseList.put("userEmail",ParseUser.getCurrentUser().getEmail());
//
////        // converting Bitmap to byte array
////        ByteArrayOutputStream stream = new ByteArrayOutputStream();
////        imageToStore.compress(Bitmap.CompressFormat.PNG, 100, stream);
////        byte[] byteArray = stream.toByteArray();
////
////        // saving byte array to the parse object field
////        ParseFile file = new ParseFile("item_image.png", byteArray);
////        courseList.put("image", file);
////
////        ByteArrayOutputStream stream = new ByteArrayOutputStream();
////        imageToStore.compress(Bitmap.CompressFormat.PNG, 100, stream);
////        byte[] image = stream.toByteArray();
////        ParseFile parseFile = new ParseFile("image.png", image);
////        courseList.put("image", parseFile);
//
//        // after adding all data we are calling a
//        // method to save our data in background.
//        courseList.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                // inside on done method we are checking
//                // if the error is null or not.
//                if (e == null) {
//
//                    // if the error is null we are displaying a simple toast message.
//                    Toast.makeText(AddPropertyActivity.this, "Data has been successfully added to Database", Toast.LENGTH_SHORT).show();
//
//                    // on below line we are setting our edit text fields to empty value.
//                    courseNameEdt.setText("");
//                    courseDescriptionEdt.setText("");
//                    courseDurationEdt.setText("");
//                } else {
//                    // if the error is not null we will be
//                    // displaying an error message to our user.
//                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
//}
