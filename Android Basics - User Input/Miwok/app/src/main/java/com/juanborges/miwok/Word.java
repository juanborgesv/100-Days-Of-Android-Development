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
    int imageResourceId = NO_SOURCE_PROVIDED;

    // Sound resource id for the word.
    int soundResourceId = NO_SOURCE_PROVIDED;

    static final int NO_SOURCE_PROVIDED = -1;

    /**
     * Custom constructor of the class. Set the Default and Miwok translation of the Word.
     * @param mDefaultTranslation is the word in the default language.
     * @param mMiwokTranslation is the word in the Miwok language.
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation) {
        defaultTranslation = mDefaultTranslation;
        miwokTranslation = mMiwokTranslation;
    }

    /**
     * Custom constructor of the class. Set the Default and Miwok translation of the Word,
     * and a sound resource id.
     * @param mDefaultTranslation is the word i the default language.
     * @param mMiwokTranslation is the word in the Miwok language.
     * @param mSoundResourceId is the drawable image resource ID for the word's image.
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation, int mSoundResourceId) {
        defaultTranslation = mDefaultTranslation;
        miwokTranslation = mMiwokTranslation;
        soundResourceId = mSoundResourceId;
    }

    /**
     * Custom constructor of the class. Set the Default and Miwok translation of the word, image
     * and sound resource id.
     * @param mDefaultTranslation
     * @param mMiwokTranslation
     * @param mImageResourceId
     * @param mSoundResourceId
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation, int mImageResourceId, int mSoundResourceId) {
        defaultTranslation = mDefaultTranslation;
        miwokTranslation = mMiwokTranslation;
        imageResourceId = mImageResourceId;
        soundResourceId = mSoundResourceId;
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

    /**
     * Return the image resource ID of the word.
     * @return soundResourceId
     */
    public int getSoundResourceId() {
        return soundResourceId;
    }

    /**
     * Check if the word has an Image resource id.
     * @return
     */
    public boolean hasImage(){
        return imageResourceId != NO_SOURCE_PROVIDED;
    }

    /**
     * Check if the word has a Sound resource id.
     * @return
     */
    public boolean hasSound(){
        return soundResourceId != NO_SOURCE_PROVIDED;
    }
}
