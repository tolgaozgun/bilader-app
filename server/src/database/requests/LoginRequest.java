package database.requests;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONObject;

import database.adapters.JsonAdapter;
import database.handlers.LoginHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginRequest
 */
public class LoginRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginHandler handler;
		JSONObject json;
		PrintWriter out;

		response.setContentType("application/json");
		out = response.getWriter()	;
		handler = new LoginHandler(request.getParameterMap());
		try {
			json = handler.getResult();
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
			e.printStackTrace(out);
			json = JsonAdapter.createNullJSON();
		}
		out.print(json);
		out.flush();
		
	}
	

}
