class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')

        /** (Authentication) User Login and Logout */
        "/api/v1/auth/login" (controller: "login", action: "login", method: "POST")
        "/api/v1/auth/logout" (controller: "logout", action: "logout", method: "POST")
	}
}
