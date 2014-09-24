package pl.kwi.validators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * Class of validator for table page.
 * 
 * @author Krzysztof Wisniewski
 *
 */
public class TableValidator {
	
	/**
	 * Method handles validation for table. Only one checkbox has to be selected. 
	 * If some error exists then error message is added to list. This list is 
	 * added to request`s attribute.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @return object <code>Map</code> with error field name and error message
	 */
	public Map<String, String> getErrorMessages(HttpServletRequest request) {
		
		Map<String, String> errorMessages = new HashMap<String, String>();
		
		String selectedUsersIds = request.getParameter("selectedUsersIds");
		String[] selectedUserIdsArray = request.getParameterValues("selectedUsersIds");
		
		if(StringUtils.isBlank(selectedUsersIds)) {
			errorMessages.put("selectedUsersIds", "Select at least on row");
		} else if(selectedUserIdsArray.length > 1) {
			errorMessages.put("selectedUsersIds", "Only one row can be selected");
		}
		
		if(!errorMessages.isEmpty()) {
			request.setAttribute("errorMessages", errorMessages);
		}
		
		return errorMessages;
		
	}

}
