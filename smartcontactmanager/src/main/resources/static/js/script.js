const toggleSidebar = () => {
	if ($(".sidebar").is(":visible")) {
		//true
		//band karna hai
		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");
	} else {
		//false
		//show karna hai
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");
	}
};
document.querySelectorAll(".nav-link").forEach((link) => {
	if (link.href === window.location.href.split("?")[0]) { 
		link.classList.add("active");
	}
}
); 
document.querySelectorAll("#side-link").forEach((link) => {
	if (link.href === window.location.href.split("?")[0]) {
		console.log(window.location.href.split("?")[0]);
		console.log("here");
		
	 link.classList.add("active"); 
	}
	if (window.location.href.split("?")[0].startsWith("http://localhost:8080/user/view_contacts/") & link.href.startsWith("http://localhost:8080/user/view_contacts/"))
	 {
		console.log(window.location.href.split("?")[0]);
		console.log("here");
		
	 link.classList.add("active"); 
	}
}
); 