package pl.kwi.servlets;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.kwi.services.NameService;
import pl.kwi.validators.InputValidator;


/**
 * Class of Servlet handles requests from "Input" jsp page. 
 * 
 * @author Krzysztof Wisniewski
 *
 */
@WebServlet(value="/input.do")
public class InputServlet extends HttpServlet{
	

	private static final long serialVersionUID = 1L;
	
	private NameService nameService;
	private InputValidator inputValidator;
	
	
	@PostConstruct
	private void setUp() {
		nameService = new NameService();
		inputValidator = new InputValidator();
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
		} else if("OK".equals(submit)) {
			handleOkButton(request, response);
		} else {
			throw new ServletException("No handling of action: " + submit);
		}
			
	}
	
	/**
	 * Method displays page *.jsp with input.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displayPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/inputJsp.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	/**
	 * Method handles pressing button "Ok" on "Input" jsp page.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleOkButton(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
					
		Map<String, String> errorMessages = inputValidator.getErrorMessages(request);
		if(!errorMessages.isEmpty()) {			
			displayPage(request, response);
			return;
		}
		
		String name = request.getParameter("name");	
		nameService.save(name);
		response.sendRedirect("output.do?submit=Display");
		
	}
	
	
	// ************************************************************************************** //
	// ****************************** GETTERS AND SETTERS *********************************** //
	// ************************************************************************************** //
	
	
	public void setNameService(NameService nameService) {
		this.nameService = nameService;
	}

	public void setInputValidator(InputValidator inputValidator) {
		this.inputValidator = inputValidator;
	}
	

}
