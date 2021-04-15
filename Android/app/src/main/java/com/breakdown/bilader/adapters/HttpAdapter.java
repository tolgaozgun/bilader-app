package com.breakdown.bilader.adapters;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

public class HttpAdapter {

    private final static String MAIN_URL = "http://88.99.11.149:8080/server/";

    /**
     * Sends a GET request to server with specified details and parameters to
     * get a JSON response.
     *
     * @param requestType Type of request as a RequestType enum.
     * @param params      Request parameters as String key-value map.
     * @param instance    Instance of the activity for the request.
     * @return Response as a JSONObject.
     */
    public static JSONObject getRequestJSON( RequestType requestType,
                                             Map< String, String > params,
                                             Activity instance ) {
        JSONObject[] finalResponse;
        JSONObject jsonParameters;
        String path;
        String url;
        RequestQueue queue;
        JsonObjectRequest jsonObjectRequest;

        finalResponse = new JSONObject[ 1 ];
        jsonParameters = null;
        queue = Volley.newRequestQueue( instance );
        path = requestType.getPath();
        url = MAIN_URL + path;

        // Since JsonObjectRequest only accepts JSONObject as parameters, we
        // need to convert.
        if ( params != null ) {
            jsonParameters = new JSONObject( params );
        }

        // Initialize the request.
        jsonObjectRequest = new JsonObjectRequest( Request.Method.GET, url,
                jsonParameters, new Response.Listener< JSONObject >() {

            @Override
            public void onResponse( JSONObject response ) {
                finalResponse[ 0 ] = response;
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse( VolleyError error ) {
                finalResponse[ 0 ] = null;

            }
        } );

        // Add the request to the queue.
        queue.add( jsonObjectRequest );
        return finalResponse[ 0 ];
    }
}
