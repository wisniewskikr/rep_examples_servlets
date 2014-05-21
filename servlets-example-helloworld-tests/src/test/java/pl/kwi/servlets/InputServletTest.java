package pl.kwi.servlets;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import pl.kwi.services.NameService;
import pl.kwi.validators.InputValidator;

public class InputServletTest {
	
	private InputServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestDispatcher dispatcher;
	private NameService nameService;
	private InputValidator inputValidator;

	@Before
	public void setUp() throws Exception {
		servlet = new InputServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		dispatcher = mock(RequestDispatcher.class);
		nameService = mock(NameService.class);
		inputValidator = mock(InputValidator.class);
		
		servlet.setInputValidator(inputValidator);
		servlet.setNameService(nameService);
	}

	@Test
	public void service_display()
			throws ServletException, IOException {
		
		when(request.getParameter("submit")).thenReturn("Display");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		servlet.service(request, response);
		
		verify(request).getRequestDispatcher("pages/inputJsp.jsp");
		verify(dispatcher).forward(request, response);
		
	}
	
	@Test
	public void service_ok()
			throws ServletException, IOException {
		
		when(request.getParameter("submit")).thenReturn("OK");
		Map<String, String> errorMessages = new HashMap<String, String>();
		when(inputValidator.getErrorMessages(request)).thenReturn(errorMessages);
		when(request.getParameter("name")).thenReturn("exampleName");
		
		servlet.service(request, response);
		
		verify(nameService).save("exampleName");
		verify(response).sendRedirect("output.do?submit=Display");
		
	}
	
	@Test
	public void service_ok_withError()
			throws ServletException, IOException {
		
		when(request.getParameter("submit")).thenReturn("OK");
		Map<String, String> errorMessages = new HashMap<String, String>();
		errorMessages.put("errorKey", "errorValue");
		when(inputValidator.getErrorMessages(request)).thenReturn(errorMessages);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		servlet.service(request, response);
		
		verify(request).getRequestDispatcher("pages/inputJsp.jsp");
		verify(dispatcher).forward(request, response);
		
	}
	
	@Test(expected = ServletException.class)
	public void service_exception()
			throws ServletException, IOException {
		
		when(request.getParameter("submit")).thenReturn("Another");
		
		servlet.service(request, response);
		
	}

}
