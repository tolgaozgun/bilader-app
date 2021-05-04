package database.requests;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class MultipartServlet
 */
@MultipartConfig( fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
		* 5, maxRequestSize = 1024 * 1024 * 5 * 5 )
public class MultipartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "images";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MultipartServlet() {
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
		doPost( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost( HttpServletRequest request,
			HttpServletResponse response )
			throws ServletException, IOException {
		JSONObject json;
		String fileName = "";
		String uploadPath;
		File uploadDir;
		String id;
		String url;
		json = new JSONObject();
		
		if ( request.getParameterMap().containsKey( "id" ) ) {
			id = request.getParameter( "id" );
		} else {
			id = UUID.randomUUID().toString();
		}

		uploadPath = getServletContext().getRealPath( "" ) + File.separator
				+ UPLOAD_DIRECTORY + File.separator + id;
		uploadPath = uploadPath.replaceAll( "server" + File.separator, "" );


		uploadDir = new File( uploadPath );

		if ( !uploadDir.exists() ) {
			uploadDir.mkdir();
		}

		for ( Part part : request.getParts() ) {
			fileName = getFileName( part );
			part.write( uploadPath + File.separator + fileName );
		}

		response.setContentType( "application/json" );
		url = "http://88.99.11.149:8080/" + UPLOAD_DIRECTORY + File.separator
				+ id + File.separator + fileName;
		json.put( "url", url );
		response.getWriter().println( json );

	}

	private String getFileName( Part part ) {
		for ( String content : part.getHeader( "content-disposition" )
				.split( ";" ) ) {
			if ( content.trim().startsWith( "filename" ) )
				return content.substring( content.indexOf( "=" ) + 2,
						content.length() - 1 );
		}
		return "default.png";
	}

}
