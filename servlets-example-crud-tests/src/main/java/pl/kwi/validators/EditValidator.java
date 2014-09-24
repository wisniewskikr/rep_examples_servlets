package pl.kwi.validators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * Class of validator for "Edit" page.
 * 
 * @author Krzysztof Wisniewski
 *
 */
public class EditValidator {
	
	/**
	 * Method handles validation for "Edit" page. If some error exists then
	 * error message is added to list. This list is added to request`s attribute.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @return object <code>Map</code> with error field name and error message
	 */
	public Map<String, String> getErrorMessages(HttpServletRequest request) {
		
		Map<String, String> errorMessages = new HashMap<String, String>();
		
		String name = request.getParameter("name");
		if(StringUtils.isBlank(name)) {
			errorMessages.put("name", "Please fill this field");
		}
		
		if(!errorMessages.isEmpty()) {
			request.setAttribute("errorMessages", errorMessages);
		}
		
		return errorMessages;
		
	}

}
