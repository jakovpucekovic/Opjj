function getButtons() {
	$.ajax(
	  {
		  url: "rest/picInfo",
		  data: {},
		  dataType: "json",
		  success: function(data) {
				var tags = data;
				var html = "";
				if(tags.length == 0) {
					html = "No tags.."
				} else {
					for(var i=0; i < tags.length; ++i) {
						html += "<button id='tagButton' onclick='getPicsByTag(\"" + htmlEscape(tags[i]) + "\")'>" + htmlEscape(tags[i]) + "</button>";
					}
				}
				$("#tags").html(html);
		  }
	  }
	);
}

function getPicsByTag(tag){	
	$.ajax(
			  {
				  url: "rest/picInfo/" + tag,
				  data: {},
				  dataType: "json",
				  success: function(data) {
						var pics = data;
						var html = "";
						if(pics.length == 0) {
							html = "No pictures with the tag" + tag;
						} else {
							for(var i=0; i < pics.length; ++i) {
								html += "<img id='thumbnail' src='pic?tag=" + htmlEscape(pics[i]) + "' onclick='getPicture(\"" + htmlEscape(pics[i]) + "\")'>";
							}
						}
						$("#pics").html(html);
						$("#thePicture").html("");
				  }
			  }
			);
}

function getPicture(name){
	$.ajax(
			  {
				  url: "rest/picInfo/pic/" + name,
				  data: {},
				  dataType: "json",
				  success: function(data) {
						var pic = data;
						var html = "<img id='thePic' src='pic?name=" + htmlEscape(pic.name) + "'>";
						html += "<p id='desc'>" + htmlEscape(pic.description) + "</p>";
						html += "<p id='picTag'>" + htmlEscape(pic.tags) + "</p>";
						$("#pics").html("");						
						$("#thePicture").html(html);
				  }
			  }
			);
}