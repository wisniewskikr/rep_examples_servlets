package pl.kwi.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.kwi.entities.UserEntity;
import pl.kwi.services.UserService;

@WebServlet(value="/edit.do")
public class EditServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	private UserService userService;
	
	public EditServlet(){
		userService = new UserService();
	}
	

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if("Display".equals(action)){
			displayPage(request, response);
			return;
		}else if("Update".equals(action)){
			handleUpdateButton(request, response);
			return;
		}else if("Back".equals(action)){
			handleBackButton(request, response);
			return;
		}
		
	}
	
	private void displayPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String id = request.getParameter("id");
		
		UserEntity entity = userService.readUser(Long.valueOf(id));
		
		request.setAttribute("user", entity);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/editJsp.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleUpdateButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String userName = request.getParameter("name");
		String id = request.getParameter("userId");
		
		UserEntity entity = new UserEntity();
		entity.setId(Long.valueOf(id));
		entity.setName(userName);
		userService.updateUser(entity);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/table.do?action=Display");
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleBackButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/table.do?action=Display");
		requestDispatcher.forward(request, response);
		
	}

}
