 package com.example.homestay;

//import static com.example.homestay.AddPropertyActivity.SELECT_IMAGE_CODE;



import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdatePropertyActivity extends AppCompatActivity {


    private static final int MAX_IMAGES = 3;


    RecyclerView recyclerView;
    ArrayList<Uri> uri = new ArrayList<>();
    RecyclerAdapter adapter;

   // private static final int SELECT_IMAGE_CODE = ;
    // creating variables for our edit text
    private EditText propertyNameEdt, propertyAddressEdt,propertyAmountEdt;

    // creating variable for button
    private Button updatePropertyBtn,btnImage,deletePropertyBtn;
    RadioButton cooking_selectedRadioButton,parking_selectedRadioButton;
    private String cooking_selectedOption,parking_selectedOption;

    // creating a strings for storing our values from edittext fields.
    private String propertyName;
//    private String propertyDuration;
    private String propertyAddress;
    private String propertyAmount;


    // creating a strings for storing our values from edittext fields.
    private String originalPropertyName;
    private ImageView itemImage;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Uri imagePath;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_property);

        // initializing our edittext and buttons
        updatePropertyBtn = findViewById(R.id.idBtnUpdate);
        deletePropertyBtn = findViewById(R.id.idBtnDelete);
        propertyNameEdt = findViewById(R.id.idEdtPropertyName);
//        propertyDurationEdt = findViewById(R.id.idEdtPropertyDuration);
        propertyAddressEdt = findViewById(R.id.idEdtPropertyAddress);
        propertyAmountEdt = findViewById(R.id.idEdtPropertyAmount);
        itemImage = findViewById(R.id.imageview);
        btnImage = findViewById(R.id.idImageBtn);
        int SELECT_IMAGE_CODE = 1;

        recyclerView = findViewById(R.id.recyclerView_Gallery_Images);
        adapter = new RecyclerAdapter(uri);
        recyclerView.setLayoutManager(new GridLayoutManager(UpdatePropertyActivity.this, 4));
        recyclerView.setAdapter(adapter);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        // on below line we are setting data to our edit text field.
//        propertyNameEdt.setText(getIntent().getStringExtra("propertyName"));
//        propertyDurationEdt.setText(getIntent().getStringExtra("propertyDuration"));
//        propertyAddressEdt.setText(getIntent().getStringExtra("propertyAddress"));
//        propertyAmountEdt.setText(getIntent().getStringExtra("propertyAmount"));
//
 originalPropertyName = getIntent().getStringExtra("propertyName");
        RadioGroup cooking_radioGroup = (RadioGroup) findViewById(R.id.cooking_radio_group);
        RadioGroup parking_radioGroup = (RadioGroup) findViewById(R.id.parking_radio_group);



            getDataFromServer();


        deletePropertyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a course.
                deleteProperty(originalPropertyName);
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


        updatePropertyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                propertyName = propertyNameEdt.getText().toString();
//                propertyDuration = propertyDurationEdt.getText().toString();
                propertyAddress = propertyAddressEdt.getText().toString();
                propertyAmount = propertyAmountEdt.getText().toString();
                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(propertyName)) {
                    propertyNameEdt.setError("Please enter Property Name");

                } else if (TextUtils.isEmpty(propertyAddress)) {
                   propertyAddressEdt.setError("Please enter address");

                } else if (TextUtils.isEmpty(propertyAmount)) {
                    propertyAmountEdt.setError("Please enter amount");
                } else {
                    // calling method to update data.
                    updateData(originalPropertyName, propertyName,propertyAddress,propertyAmount,parking_selectedOption,cooking_selectedOption);
                }
            }
        });
    }




    private void getDataFromServer() {
        String originalPropertyName = getIntent().getStringExtra("propertyName");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("properties");
        query.whereEqualTo("propertyName", originalPropertyName);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    if (object != null) {
                        propertyNameEdt = findViewById(R.id.idEdtPropertyName);

                        propertyAddressEdt = findViewById(R.id.idEdtPropertyAddress);
                        propertyAmountEdt = findViewById(R.id.idEdtPropertyAmount);
                        RadioGroup cookingRadioGroup = findViewById(R.id.cooking_radio_group);
                        RadioGroup parkingRadioGroup = findViewById(R.id.parking_radio_group);

                        propertyNameEdt.setText(object.getString("propertyName"));
//                        propertyDurationEdt.setText(object.getString("propertyDuration"));
                        propertyAddressEdt.setText(object.getString("propertyAddress"));
                        propertyAmountEdt.setText(object.getString("propertyAmount"));

                        String cookingSelectedOption = object.getString("propertyCooking");
                        if (cookingSelectedOption != null) {
                            int cookingRadioButtonId = cookingSelectedOption.equals("Yes") ? R.id.cooking_yes : R.id.cooking_no;
                            cookingRadioGroup.check(cookingRadioButtonId);
                        }

                        String parkingSelectedOption = object.getString("propertyParking");
                        if (parkingSelectedOption != null) {
                            int parkingRadioButtonId = parkingSelectedOption.equals("Yes") ? R.id.parking_yes : R.id.parking_no;
                            parkingRadioGroup.check(parkingRadioButtonId);
                        }
                    } else {
                        Log.e(TAG, "Error fetching data: no results found for query");
                    }
                } else {
                    Log.e(TAG, "Error fetching data: " + e.getMessage());
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
                        Log.e("UpdatePropertyActivity","Error adding image to list:"+e.getMessage());
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




    private void deleteProperty(String propertyName) {

        // Configure Query with our query.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("properties");

        // adding a condition where our course name
        // must be equal to the original course name
        query.whereEqualTo("propertyName", propertyName);

        // on below line we are finding the course with the course name.
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                // if the error is null.
                if (e == null) {
                    // on below line we are getting the first course and
                    // calling a delete method to delete this course.
                    objects.get(0).deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            // inside done method checking if the error is null or not.
                            if (e == null) {
                                // if the error is not null then we are displaying a toast message and opening our home activity.
                                Toast.makeText(UpdatePropertyActivity.this, "Property Deleted..", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UpdatePropertyActivity.this, AddPropertyActivity.class);
                                startActivity(i);
                            } else {
                                // if we get error we are displaying it in below line.
                                Toast.makeText(UpdatePropertyActivity.this, "Fail to delete property..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // if we don't get the data in our database then we are displaying below message.
                    Toast.makeText(UpdatePropertyActivity.this, "Fail to get the object..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void updateData(String originalPropertyName, String propertyName, String propertyAddress,String propertyAmount,String propertyParking,String propertyCooking) {
// convert image into byte array
        ParseQuery<ParseObject> query= ParseQuery.getQuery("properties");

        // adding a condition where our course name must be equal to the original course name
        query.whereEqualTo("propertyName", originalPropertyName);

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
                Log.e("UpdateProperty", "Error getting bitmap from URI: " + e.getMessage());
            }
        }


        // Configure Query with our query.




//ParseObject object = query.getFirst();


        query.getFirstInBackground(new GetCallback<ParseObject>(){
            @Override
            public void done(ParseObject object, ParseException e) {
                        // inside done method we check
                        // if the error is null or not.
                        if (e == null) {

                            // if the error is null then we are getting
                            // our object id in below line.

                            String objectID=object.getObjectId().toString();
//                            Log.i("UpdateProperty", "objectId: " + objectID);





                                                   // after getting our object id we will
                            // move towards updating our course.
                            // calling below method to update our course.
                            query.getInBackground(objectID, new GetCallback<ParseObject>() {

                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    // in this method we are getting the
                                    // object which we have to update.
                                    if (e == null ){

                                        // in below line we are adding new data
                                        // to the object which we get from its id.
                                        // on below line we are adding our data
                                        // with their key value in our object.
                                        object.put("propertyName", propertyName);
//                                        object.put("propertyDuration", propertyDuration);
                                        object.put("propertyAddress", propertyAddress);
                                        object.put("propertyAmount", propertyAmount);
                                        object.put("propertyParking", propertyParking);
                                        object.put("propertyCooking", propertyCooking);
                                        object.addAll("image", imageFiles);

//                                        object.setObjectId(objectID);
                                        // after adding new data then we are
                                        // calling a method to save this data
                                        object.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                // inside on done method we are checking
                                                // if the error is null or not.
                                                if (e == null) {
                                                    // if the error is null our data has been updated.
                                                    // we are displaying a toast message and redirecting
                                                    // our user to home activity where we are displaying course list.
                                                    Toast.makeText(UpdatePropertyActivity.this, "Course Updated..", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(UpdatePropertyActivity.this, PropertyViewActivity.class);
                                                    startActivity(i);
                                                } else {
                                                    // below line is for error handling.
                                                    Toast.makeText(UpdatePropertyActivity.this, "Fail to update data " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        // on below line we are displaying a message
                                        // if we don't get the object from its id.
                                        Toast.makeText(UpdatePropertyActivity.this, "Fail to update course " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // this is error handling if we don't get the id for our object
                            Toast.makeText(UpdatePropertyActivity.this, "Fail to get object ID..", Toast.LENGTH_SHORT).show();
                        }
                    }

    });
}}
