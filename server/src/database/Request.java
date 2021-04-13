package database;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Request
 */
public class Request extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Request() {
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
		
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		handleRequest(request, response);
	}
	

	protected void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out;
		DatabaseAdapter adapter;
		Map<String, String> params;
		
		
		params = convertHeadersToMap(request);
		out = response.getWriter();
		adapter = new DatabaseAdapter(out);
		out.println("Checking");
		try {
			out.println(adapter.doesExist(params.get("table"), params));
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(out);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(out);
		}
	}
	
	private Map<String, String> convertHeadersToMap(HttpServletRequest request) {
		
		Map<String, String> params;
		Enumeration<String> parameters;
		String paramName;
		String paramValue;
		
		parameters = request.getParameterNames();
		params = new HashMap<String, String>();
		
		while( parameters.hasMoreElements() ) {
			paramName = parameters.nextElement();
			if(!paramName.toLowerCase().equals("table")) {
				paramValue = request.getParameter(paramName);
				params.put(paramName, paramValue);
			}
		}
		
		return params;
	}
}
