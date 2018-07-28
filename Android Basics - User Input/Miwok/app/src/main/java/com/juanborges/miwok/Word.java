package com.juanborges.miwok;

/**
 * {@link Word} represents a vocabulary word that the user wants to learn its Miwok translation.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {

    // Default translation of the word
    String defaultTranslation;

    // Miwok translation of the word
    String miwokTranslation;

    /**
     * Constructor of the class. Set the Default and Miwok translation of the Word.
     * @param mDefaultTranslation
     * @param mMiwokTranslation
     */
    public Word(String mDefaultTranslation, String mMiwokTranslation) {
        defaultTranslation = mDefaultTranslation;
        miwokTranslation = mMiwokTranslation;
    }

    /**
     * Get the Default translation
     * @return defaultTranslation
     */
    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    /**
     * Get the Miwok translation
     * @return miwokTranslation
     */
    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    /**
     * Set the Default translation
     * @param mDefaultTranslation
     */
    public void setDefaultTranslation(String mDefaultTranslation) {
        defaultTranslation = mDefaultTranslation;
    }

    /**
     * Set the Miwok translation
     * @param mMiwokTranslation
     */
    public void setMiwokTranslation(String mMiwokTranslation){
        miwokTranslation = mMiwokTranslation;
    }
}
