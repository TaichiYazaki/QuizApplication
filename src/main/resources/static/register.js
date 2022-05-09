"use strict"
$(function() {
	$(document).on("keyup", "#confirmPassword", function() {
		$.ajax({
			url: "http://localhost:8080/administrator/passwordCheck",
			type: "post",
			dataType: "json",
			data: {
				confirmPassword: $("#confirmPassword").val(),
				password:$("#password").val()
			},
			async: true,
		}).done(function(data) {
			console.log(data)
			console.dir(JSON.stringify(data))
			$("#duplicateMessage").text(data.duplicateMessage)

		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error!!!!!")
			console.log("XMLHttpRequest :" + XMLHttpRequest.status)
			console.log("textStatus :" + textStatus)
			console.log("errorThrown :" + errorThrown.message)
		})
	})
})