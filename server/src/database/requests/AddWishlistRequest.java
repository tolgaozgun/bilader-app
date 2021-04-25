package database.requests;

import java.io.IOException;

import database.adapters.RequestAdapter;
import database.handlers.AddWishlistHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
