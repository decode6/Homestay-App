package com.example.homestay;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;

public class PropertyRVAdapter extends RecyclerView.Adapter<PropertyRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PropertyModel> PropertyModelArrayList;

    // creating a constructor class.
    public PropertyRVAdapter(Context context, ArrayList<PropertyModel> propertyModelArrayList) {
        this.context = context;
        this.PropertyModelArrayList = propertyModelArrayList;
    }

    @NonNull
    @Override
    public PropertyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.property_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        PropertyModel courses = PropertyModelArrayList.get(position);
        holder.propertyName.setText(courses.getPropertyName());
//        holder.propertyDuration.setText(courses.getPropertyDuration());
        holder.propertyAddress.setText(courses.getPropertyAddress());
//        holder.propertyAmount.setText(courses.getPropertyAmount());
        holder.userEmail.setText(courses.getEmail());
//        holder.propertyParking.setText(courses.getPropertyParking());
//        holder.propertyCooking.setText(courses.getPropertyCooking());


        final ImageView itemImage = holder.itemView.findViewById(R.id.imageView);
        ParseFile imageFile = courses.getImage();
        if (imageFile != null) {

            Glide.with(context).load(imageFile.getUrl()).into(itemImage);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calling a intent to open new activity.
             Intent i = new Intent(context, UpdatePropertyActivity.class);


// Convert the image to a byte array


// Update the extra in the Intent

                ParseFile imageByteArray = courses.getImage();
                i.putExtra("image", imageByteArray);
                // on below line we are passing data to our intent on below line.
                i.putExtra("propertyName", courses.getPropertyName());
                i.putExtra("propertyDuration", courses.getPropertyDuration());
                i.putExtra("propertyAddress", courses.getPropertyAddress());
                i.putExtra("propertyAmount", courses.getPropertyAmount());
                i.putExtra("propertyParking", courses.getPropertyParking());
                i.putExtra("propertyCooking", courses.getPropertyCooking());
//



                // starting our activity on below line.
            context.startActivity(i);
            }
        });



    }


    @Override
    public int getItemCount() {
        return PropertyModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView propertyName;
//        private final TextView propertyDuration;
        private final TextView propertyAddress;
//        private final TextView propertyAmount;
        private final TextView userEmail;
//        private final TextView propertyParking;
//        private final TextView propertyCooking;
        private final ImageView image;





        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            propertyName = itemView.findViewById(R.id.idpropertyName);
//            propertyDuration = itemView.findViewById(R.id.idpropertyDuration);
            propertyAddress = itemView.findViewById(R.id.idpropertyAddress);
//            propertyAmount = itemView.findViewById(R.id.idpropertyAmount);
            userEmail = itemView.findViewById(R.id.iduserEmail);
//            propertyParking = itemView.findViewById(R.id.idpropertyParking);
//            propertyCooking = itemView.findViewById(R.id.idpropertyCooking);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
