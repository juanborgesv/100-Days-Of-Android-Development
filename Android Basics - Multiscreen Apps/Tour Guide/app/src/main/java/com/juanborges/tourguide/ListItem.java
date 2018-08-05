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

    //
    String contactNumber;

    static final int NO_SOURCE_PROVIDED = -1;

    // var locationId;

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
        contactNumber = number;
    }

    public ListItem(String name, String number)
    {
        itemName = name;
        contactNumber = number;
    }

    // Get methods below
    //

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean hasImage() {
        return imageResourceId != NO_SOURCE_PROVIDED;
    }
}
