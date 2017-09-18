package com.example.android.flavor;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.flavor.MainActivity.LOG_TAG;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link BookList} objects.
     */
    public static List<BookList> fetchBookListData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<BookList> bookLists = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return bookLists;
    }

    /**
     * Return a list of {@link BookList} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<BookList> extractFeatureFromJson(String bookListJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookListJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<BookList> bookLists = new ArrayList<>();



        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {


            JSONObject baseJsonResponse = new JSONObject(bookListJSON);
            JSONArray itemsArray = baseJsonResponse.getJSONArray("items");

            //for each book in the itemsArray,create an {@link book object}
            for (int i = 0; i < itemsArray.length(); i++){
                //initializing all the variables
                JSONObject currentBook;
                JSONObject volumeInfo;
                String title = null;
                JSONArray authorsArray;
                ArrayList<String> authors = new ArrayList<>();
                String maturityRating =null;

                currentBook = itemsArray.getJSONObject(i);
                volumeInfo = currentBook.getJSONObject("volumeInfo");

                //Extract the value for the key called title
                title = volumeInfo.getString("title");

                ///Check for the key, if it exists, getString
                if (volumeInfo.has("authors")) {
                    authorsArray = volumeInfo.getJSONArray("authors");

                    //Extract Author Names
                    for (int a = 0; a < authorsArray.length(); a++) {
                        String author = authorsArray.getString(a);
                        authors.add(author);
                    }
                }

                //check for the key, if it exists, getString
                if(volumeInfo.has("maturityRating")){
                    maturityRating = volumeInfo.getString("maturityRating");
                }

                //Create a new Object with the title, author and maturityRating
                BookList bookList = new BookList(title,authors,maturityRating);
                bookLists.add(bookList);
                //Add the new book to search resultarra
                // For a given bookList, extract the JSONObject associated with the
                // key called "volumeInfo", which represents a list of all properties
                // for that bookList.
            }
            // build up a list of BookList objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return bookLists;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}