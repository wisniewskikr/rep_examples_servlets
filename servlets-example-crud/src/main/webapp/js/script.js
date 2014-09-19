$(function() {
	$( document ).tooltip();
});

/**
 * Function sends form with specified 'action' field.
 * 
 * @param action object String with 'action' field
 */
function send(action){
	$('#action').val(action);
	$('#form').submit();	
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