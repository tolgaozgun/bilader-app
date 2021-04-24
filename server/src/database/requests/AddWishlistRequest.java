package database.requests;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.adapters.RequestAdapter;
import database.handlers.AddWishlistHandler;

/**
 * Servlet implementation class AddWishlistRequest
 */
public class AddWishlistRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddWishlistRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet( HttpServletRequest request,
			HttpServletResponse response )
			throws ServletException, IOException {
		handleRequest( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost( HttpServletRequest request,
			HttpServletResponse response )
			throws ServletException, IOException {
		handleRequest( request, response );
	}

	private void handleRequest( HttpServletRequest request,
			HttpServletResponse response )
			throws ServletException, IOException {
		AddWishlistHandler handler;
		handler = new AddWishlistHandler( request.getParameterMap() );
		RequestAdapter.handleRequest( request, response, handler );
	}

}
