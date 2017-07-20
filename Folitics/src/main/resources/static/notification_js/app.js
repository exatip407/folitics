/*APP.asyncPoll = function(index) {
	return $.ajax({
		url : "/appMsgsAsync?startId=" + index,
		accepts : {
			text : "application/json"
		}
	});
};

APP.updateAsync = function(data) {
	_.each(data, function(appMsg) {
		var id = appMsg.id;
		var latency = Date.now() - appMsg.timeStamp;
		if (latency > 1000) {
			latency = Math.floor(latency / 1000) + ' second(s)'
		} else {
			latency = latency + ' ms';
		}
		APP.asyncIndex = (id >= APP.asyncIndex) ? id + 1 : APP.asyncIndex;
		$('#asyncTable tbody').append(
				'<tr><td>' + data + '</td></tr>');
	});
	($('#asyncTable tbody tr').length > 0) ? $('#asyncTable').show() : $(
			'#asyncTable').hide();

};

$(document)
		.ready(
				function() {

					APP.recurseAsync = function() {
						$('#asyncSpinner').show();
						APP
								.asyncPoll(APP.asyncIndex)

								.done(function(result) {
									APP.updateAsync(result);
									APP.recurseAsync();
								})
								.error(
										function(xhr, errorStr, statusTxt) {
											console.dir(arguments);
											console
													.log("error making async call - waiting 60 seconds until next try");
											// proxy timeout - let's just start
											// again
											error(function(xhr, errorStr,
													errorMsg) {
												console.dir(arguments);
												if (xhr.status === 504) {
													console
															.log("Proxy Timeout - making new call immediately.");
													APP.recurseAsync();
												}
												// some other error - possibly
												// the server went down
												else {
													console
															.log("error making async call (server might be down)- waiting 60 seconds until next try.");
													setTimeout(
															APP.recurseAsync,
															60000);
												}
											});
										});
					};
					APP.recurseAsync();
				});
*/