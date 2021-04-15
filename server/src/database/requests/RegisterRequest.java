package database.requests;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONObject;

import database.handlers.RegisterHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterRequest
 */
public class RegisterRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterRequest() {
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
		RegisterHandler handler;
		JSONObject json;
		PrintWriter out;

		response.setContentType( "application/json" );
		out = response.getWriter();
		handler = new RegisterHandler( request.getParameterMap() );
		try {
			json = handler.getResult();
		} catch ( ClassNotFoundException | ServletException | IOException
				| SQLException e ) {
			json = new JSONObject();
			json.put( "success", false );
			json.put( "message", e.getMessage() );
		}
		out.print( json );
		out.flush();

	}

}
