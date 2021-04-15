package com.breakdown.bilader.adapters;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

    /**
     * Sends a GET request to server with specified details and parameters to
     * get a JSON response.
     *
     * @param requestType Type of request as a RequestType enum.
     * @param params      Request parameters as String key-value map.
     * @param instance    Instance of the activity for the request.
     * @return Response as a JSONObject.
     */
    public static void getRequestJSON( final VolleyCallback callback,
                                       RequestType requestType, Map< String,
            String > params, Activity instance ) {
        String path;
        String url;
        RequestQueue queue;
        JsonObjectRequest jsonObjectRequest;

        finalResponse = new JSONObject();
        queue = Volley.newRequestQueue( instance );
        path = requestType.getPath();
        url = addParameters( MAIN_URL + path, params );
        System.out.println( "url: " + url );

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

        // Add the request to the queue.
        queue.add( jsonObjectRequest );
    }

    private static String addParameters(String url, Map<String, String> params ){
        if(params == null || params.size() == 0){
            return url;
        }
        StringBuffer paramBuffer;
        paramBuffer = new StringBuffer();
        for(String key: params.keySet()){
            paramBuffer.append( "&" + key + "=" + params.get( key ) );
        }
        paramBuffer.setCharAt( 0,'?');
        return url + paramBuffer.toString();
    }
}
