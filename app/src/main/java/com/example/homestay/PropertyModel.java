package com.example.homestay;

import com.parse.ParseFile;

public class PropertyModel {

    // variables for our course name,
    //private String pname, pduration, paddress,pamount;

    // description and duration.
    private String propertyName;
    private String propertyDuration;
    private String propertyAddress;
    private String propertyAmount;
    private ParseFile image;
    private String userEmail;
    private String propertyParking;
    private String propertyCooking;
    // constructor class.
    public PropertyModel(String propertyName, String propertyAddress, String userEmail,ParseFile image) {
        this.propertyName = propertyName;
//        this.propertyDuration = propertyDuration;
        this.propertyAddress = propertyAddress;
        this.userEmail =  userEmail;
//        this.propertyAmount = propertyAmount;

//        this.propertyParking =propertyParking;
//        this.propertyCooking =propertyCooking;
        this.image = image;
    }

    // getter and setter methods.
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyDuration() {
        return propertyDuration;
    }

    public void setPropertyDuration(String propertyDuration) {
        this.propertyDuration = propertyDuration;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }


    public String getPropertyAmount() {
        return propertyAmount;
    }

    public void setPropertyAmount(String propertyAmount) {
        this.propertyAmount = propertyAmount;
    }


    public String getPropertyParking() {
        return propertyParking;
    }

    public void setPropertyParking(String propertyParking) {
        this.propertyParking = propertyParking;
}
    public String getPropertyCooking() {
        return propertyCooking;
    }

   public void setPropertyCooking(String propertyCooking) {
        this.propertyCooking = propertyCooking;
    }


    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }

}

