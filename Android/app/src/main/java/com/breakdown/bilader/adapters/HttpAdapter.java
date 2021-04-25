package com.breakdown.bilader.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.breakdown.bilader.controllers.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpAdapter {

    private final static String MAIN_URL = "http://88.99.11.149:8080/server/";
    private static JSONObject finalResponse;
    private static RequestQueue queue;


    public static void getRequestJSON( final VolleyCallback callback,
                                       RequestType requestType, Map< String,
            String > params, Context context, boolean loggedIn ) {
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
                Intent intent;
                try {
                    if ( loggedIn && response.has( "session_error" ) && response.getBoolean(
                            "session_error" ) ) {
                        intent = new Intent(context, LoginActivity.class );
                        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                        context.startActivity( intent );
                        Toast.makeText( context, "Session expired!", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                } catch ( JSONException e ) {
                    e.printStackTrace();
                }
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
