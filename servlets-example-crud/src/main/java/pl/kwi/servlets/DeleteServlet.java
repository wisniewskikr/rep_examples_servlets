package pl.kwi.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kwi.entities.UserEntity;
import pl.kwi.services.UserService;

public class DeleteServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(DeleteServlet.class);
	private UserService userService;
	
	
	public DeleteServlet(){
		userService = new UserService();
	}
	

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String submit = request.getParameter("submit");
		
		if("Display".equals(submit)){
			displayPage(request, response);
			return;
		}else if("OK".equals(submit)){
			handleOkButton(request, response);
			return;
		}else if("Back".equals(submit)){
			handleBackButton(request, response);
			return;
		}
		
	}
	
	private void displayPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String id = request.getParameter("id");
		
		UserEntity entity = userService.readUser(Long.valueOf(id));
		
		request.setAttribute("userName", entity.getName());
		request.setAttribute("id", entity.getId());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/DeleteJSP.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleOkButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String userName = request.getParameter("userName");
		String id = request.getParameter("id");
		
		UserEntity entity = new UserEntity();
		entity.setId(Long.valueOf(id));
		entity.setName(userName);
		userService.deleteUser(entity);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/table.do?submit=Display");
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleBackButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/table.do?submit=Display");
		requestDispatcher.forward(request, response);
		
	}

}
