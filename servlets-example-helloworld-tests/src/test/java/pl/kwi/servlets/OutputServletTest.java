package pl.kwi.servlets;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import pl.kwi.services.NameService;

public class OutputServletTest {

	private OutputServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestDispatcher dispatcher;
	private NameService nameService;

	@Before
	public void setUp() throws Exception {
		servlet = new OutputServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		dispatcher = mock(RequestDispatcher.class);
		nameService = mock(NameService.class);
		
		servlet.setNameService(nameService);
	}

	@Test
	public void service_display() throws ServletException, IOException {
		
		when(request.getParameter("submit")).thenReturn("Display");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		when(nameService.load()).thenReturn("exampleName");
		
		servlet.service(request, response);
		
		verify(request).setAttribute("name", "exampleName");
		verify(request).getRequestDispatcher("pages/outputJsp.jsp");
		verify(dispatcher).forward(request, response);
		
	}
	
	@Test
	public void service_back() throws ServletException, IOException {
		
		when(request.getParameter("submit")).thenReturn("Back");
		
		servlet.service(request, response);
		
		verify(response).sendRedirect("input.do?submit=Display");
		
	}
	
	@Test(expected = ServletException.class)
	public void service_exception()
			throws ServletException, IOException {
		
		when(request.getParameter("submit")).thenReturn("Another");
		
		servlet.service(request, response);
		
	}

}
