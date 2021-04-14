package database.requests;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONObject;

import database.handlers.TokenHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TokenRequest
 */
public class TokenRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TokenRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TokenHandler handler;
		JSONObject json;
		PrintWriter out;

		response.setContentType("application/json");
		out = response.getWriter();
		handler = new TokenHandler( request.getParameterMap());
		try {
			json = handler.getResult();
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
			json = new JSONObject();
			json.put("error", e.getMessage());
		}
		out.print(json);
		out.flush();
	}

}
