package pl.kwi.servlets;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.kwi.services.NameService;


/**
 * Class of Servlet handles requests from "Output" jsp page. 
 * 
 * @author Krzysztof Wisniewski
 *
 */
@WebServlet(value="/output.do")
public class OutputServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	
	private NameService nameService;
	
	
	@PostConstruct
	private void setUp() {
		nameService = new NameService();
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
					
		String submit = request.getParameter("submit");
		
		if("Display".equals(submit)) {
			displayPage(request, response);
		} else if("Back".equals(submit)) {
			handleBackButton(request, response);
		} else {
			throw new ServletException("No handling of action: " + submit);
		}
				
	}
	
	/**
	 * Method displays page *.jsp with output.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displayPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			
		request.setAttribute("name", loadName());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/outputJsp.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	/**
	 * Method handles pressing button "Back" on "Output" jsp page.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleBackButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		response.sendRedirect("input.do?submit=Display");
		
	}
	
	/**
	 * Method loads current name of user.
	 * 
	 * @return object <code>String</code> with user`s name
	 */
	protected String loadName(){
		return nameService.load();
	}
	
	
	// ************************************************************************************** //
	// ****************************** GETTERS AND SETTERS *********************************** //
	// ************************************************************************************** //
	
	
	public void setNameService(NameService nameService) {
		this.nameService = nameService;
	}
	

}
