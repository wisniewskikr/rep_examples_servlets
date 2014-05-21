package pl.kwi.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

public class InputValidatorTest {
	
	private InputValidator validator;
	private HttpServletRequest request;

	@Before
	public void setUp() throws Exception {
		validator = new InputValidator();
		request = mock(HttpServletRequest.class);
	}

	@Test
	public void getErrorMessages() {

		Map<String, String> errorMessages = validator.getErrorMessages(request);
		
		assertEquals("Please fill this field", errorMessages.get("name"));		
		verify(request).setAttribute("errorMessages", errorMessages);
		
	}
	
	@Test
	public void getErrorMessages_noErrors() {

		when(request.getParameter(anyString())).thenReturn("ExampeName");
		Map<String, String> errorMessages = validator.getErrorMessages(request);
		
		assertTrue(errorMessages.isEmpty());
		
	}

}
