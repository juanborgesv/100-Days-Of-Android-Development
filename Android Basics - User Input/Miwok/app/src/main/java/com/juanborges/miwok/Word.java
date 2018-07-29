package com.juanborges.miwok;

/**
 * {@link Word} represents a vocabulary word that the user wants to learn its Miwok translation.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {

    // Default translation of the word.
    String defaultTranslation;

    // Miwok translation of the word.
    String miwokTranslation;

    // Image resource id for the word.
    int imageResourceId = NO_IMAGE_PROVIDED;

    static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Constructor of the class. Set the Default and Miwok translation of the Word.
     * @param mDefaultTranslation is the word in the default language.
     * @param mMiwokTranslation is the word in the Miwok language.
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation) {
        defaultTranslation = mDefaultTranslation;
        miwokTranslation = mMiwokTranslation;
    }

    /**
     * Constructor of the class. Set the Default and Miwok translation of the Word.
     * @param mDefaultTranslation is the word i the default language.
     * @param mMiwokTranslation is the word in the Miwok language.
     * @param mImageResourceId is the drawable image resource ID for the word's image.
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation, int mImageResourceId) {
        defaultTranslation = mDefaultTranslation;
        miwokTranslation = mMiwokTranslation;
        imageResourceId = mImageResourceId;
    }

    /**
     * Get the Default translation.
     * @return defaultTranslation
     */
    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    /**
     * Get the Miwok translation.
     * @return miwokTranslation
     */
    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    /**
     * Return the image resource ID of the word.
     * @return imageResourceId
     */
    public int getImageResourceId() {
        return imageResourceId;
    }

    public boolean hasImage(){
        return imageResourceId != NO_IMAGE_PROVIDED;
    }
}
