package pl.kwi.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.kwi.entities.UserEntity;
import pl.kwi.services.UserService;
import pl.kwi.validators.CreateValidator;

@WebServlet(value="/create.do")
public class CreateServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	private UserService userService;
	private CreateValidator createValidator;
	
	
	public CreateServlet(){
		userService = new UserService();
		createValidator = new CreateValidator();
	}
	

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if("Display".equals(action)){
			displayPage(request, response);
			return;
		}else if("Create".equals(action)){
			handleCreateButton(request, response);
			return;
		}else if("Back".equals(action)){
			handleBackButton(request, response);
			return;
		}
		
	}
	
	private void displayPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/createJsp.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleCreateButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Map<String, String> errorMessages = createValidator.getErrorMessages(request);
		if(!errorMessages.isEmpty()) {			
			displayPage(request, response);
			return;
		}
		
		String name = request.getParameter("name");
		
		UserEntity entity = new UserEntity();
		entity.setName(name);		
		userService.createUser(entity);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/table.do?action=Display");
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleBackButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/table.do?action=Display");
		requestDispatcher.forward(request, response);
		
	}

}
