$(function() {
	$( document ).tooltip();
});

/**
 * Function sends form with specified action.
 * 
 * @param action object String with action
 */
function send(action){
	document.form.action = action;
	document.form.submit();	
}

/**
 * Function sends form with specified action and model attribute.
 * 
 * @param action object String with action
 * @param modelAttribute String with model attribute
 */
function sendWithModel(action, modelAttribute){	
	$('#' + modelAttribute).attr('action', action);
	$('#' + modelAttribute).submit();	
}