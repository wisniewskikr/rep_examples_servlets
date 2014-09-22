package pl.kwi.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.kwi.entities.UserEntity;
import pl.kwi.services.UserService;

@WebServlet(value="/table.do")
public class TableServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	private UserService userService;
	
	
	public TableServlet(){
		userService = new UserService();
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
		}else if("View".equals(action)){
			handleViewButton(request, response);
			return;
		}else if("Edit".equals(action)){
			handleEditButton(request, response);
			return;
		}else if("Delete".equals(action)){
			handleDeleteButton(request, response);
			return;
		}
				
	}
	
	private void displayPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		List<UserEntity> users = userService.getUserList();		
		request.setAttribute("users", users);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/tableJsp.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleCreateButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/create.do?action=Display");
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleViewButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String id = request.getParameter("id");
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view.do?action=Display&id=" + id);
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleEditButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String id = request.getParameter("id");
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit.do?action=Display&id=" + id);
		requestDispatcher.forward(request, response);
		
	}	
	
	private void handleDeleteButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String id = request.getParameter("id");
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete.do?action=Display&id=" + id);
		requestDispatcher.forward(request, response);
		
	}
		
}
