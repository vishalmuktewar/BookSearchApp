/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.flavor;

import java.util.ArrayList;

/**
 * {@link BookList} represents a single Android platform release.
 * Each object has 3 properties: name, version number, and image resource ID.
 */
public class BookList {

    // Name of the title of the book
    private String mBookTitle;

    //Name of the author
    private ArrayList<String> mAuthor;

    //Maturity rating of the book
    private String mMaturityRating;


    /*
    * Create a new AndroidFlavor object.
    *
    * @param vName is the name of the Android version (e.g. Gingerbread)
    * @param vNumber is the corresponding Android version number (e.g. 2.3-2.7)
    * @param image is drawable reference ID that corresponds to the Android version
    * */
    public BookList(String vBookTitle, ArrayList<String> vAuthor, String vMaturityRating) {
        mBookTitle = vBookTitle;

        mAuthor = vAuthor;
        mMaturityRating = vMaturityRating;

    }
    /**
     * Get the name of the Book Title
     */
    public String getBookTitle() {
        return mBookTitle;
    }


    /**
     * Get the name of the author
     */
    public ArrayList<String> getAuthor() {
        return mAuthor;
    }

    /*
    *Get the maturity rating
     */
    public String getMaturityRating(){
        return mMaturityRating;
    }




}
