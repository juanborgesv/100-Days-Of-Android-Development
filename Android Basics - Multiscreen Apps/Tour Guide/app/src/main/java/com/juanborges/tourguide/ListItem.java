package com.juanborges.tourguide;


import java.util.List;

/**
 * {@link ListItem} represents an item's information about anything in any category.
 */
public class ListItem {

    // Image resource id for the item
    int imageResourceId;

    // Item's name
    String itemName;

    String itemDescription;

    String itemLocation;

    String itemSchedule;

    String itemNumber;

    static final int NO_SOURCE_PROVIDED = -1;

    /**
     * Custom constructor of the class.
     * @param image
     * @param name
     * @param number
     */
    public ListItem(int image, String name, String number)
    {
        imageResourceId = image;
        itemName = name;
        itemNumber = number;
    }

    public ListItem(int image, String name, String location, String number)
    {
        imageResourceId = image;
        itemName = name;
        itemLocation = location;
        itemNumber = number;
    }

    public ListItem(int image, String name, String location, String time, String number)
    {
        imageResourceId = image;
        itemName = name;
        itemLocation = location;
        itemSchedule = time;
        itemNumber = number;
    }

    /**
     * Custom constructor of the class.
     * @param image
     * @param name
     * @param description
     * @param location
     * @param time
     * @param number
     */
    public ListItem(int image, String name, String description, String location, String time, String number)
    {
        imageResourceId = image;
        itemName = name;
        itemDescription = description;
        itemLocation = location;
        itemSchedule = time;
        itemNumber = number;
    }


    // Get methods below
    //
    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getContactNumber() {
        return itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription(){
        return itemDescription;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public String getItemSchedule() {
        return itemSchedule;
    }

    public boolean hasImage() {
        return imageResourceId != NO_SOURCE_PROVIDED;
    }
}
