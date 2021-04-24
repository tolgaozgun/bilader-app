package com.breakdown.bilader.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpAdapter {

    private final static String MAIN_URL = "http://88.99.11.149:8080/server/";
    private static JSONObject finalResponse;
    private static RequestQueue queue;

    /**
     * Sends a GET request to server with specified details and parameters to
     * get a JSON response.
     *
     * @param requestType Type of request as a RequestType enum.
     * @param params      Request parameters as String key-value map.
     * @param context     Instance of the activity for the request.
     * @return Response as a JSONObject.
     */
    public static void getRequestJSON( final VolleyCallback callback,
                                       RequestType requestType, Map< String,
            String > params, Context context ) {
        String path;
        String url;
        String token;
        String userId;
        SharedPreferences sharedPreferences;
        JsonObjectRequest jsonObjectRequest;

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( context );
        finalResponse = new JSONObject();
        if ( queue == null ) {
            queue = Volley.newRequestQueue( context );
        }
        path = requestType.getPath();

        // Add session parameters to request
        token = sharedPreferences.getString( "session_token", "" );
        userId = sharedPreferences.getString( "id", "" );
        params.put( "id", userId );
        params.put( "session_token", token );

        url = addParameters( MAIN_URL + path, params );
        System.out.println( "URL: " + url );


        // Initialize the request.
        jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, url,
                null, new Response.Listener< JSONObject >() {

            @Override
            public void onResponse( JSONObject response ) {
                System.out.println( "Success" );
                System.out.println( response.toString() );
                callback.onSuccess( response );
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse( VolleyError error ) {
                error.printStackTrace();
                callback.onFail( error.getMessage() );

            }
        } );

        jsonObjectRequest.setRetryPolicy( new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry( VolleyError error ) throws VolleyError {

            }
        } );

        // Add the request to the queue.
        queue.add( jsonObjectRequest );
    }

    private static String addParameters( String url,
                                         Map< String, String > params ) {
        if ( params == null || params.size() == 0 ) {
            return url;
        }
        StringBuffer paramBuffer;
        paramBuffer = new StringBuffer();
        for ( String key : params.keySet() ) {
            paramBuffer.append( "&" + key + "=" + params.get( key ) );
        }
        paramBuffer.setCharAt( 0, '?' );
        return url + paramBuffer.toString();
    }
}
