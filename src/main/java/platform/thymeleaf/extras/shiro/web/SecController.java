package platform.thymeleaf.extras.shiro.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SecController.class);

	@RequestMapping(value = "/guest")
	public String guest() throws Exception {
		return "guest";
	}
	
	@RequestMapping(value = "/user")
	public String user() throws Exception {
		return "user";
	}
	
	@RequestMapping(value = "/authenticated")
	public String authenticated() throws Exception {
		return "authenticated";
	}
	
	@RequestMapping(value = "/notAuthenticated")
	public String notAuthenticated() throws Exception {
		return "notAuthenticated";
	}

	@RequestMapping(value = "/hasRole")
	public String hasRole() throws Exception {
		return "hasRole";
	}
	
	@RequestMapping(value = "/hasAnyRoles")
	public String hasAnyRoles() throws Exception {
		return "hasAnyRoles";
	}

	@RequestMapping(value = "/lacksRole")
	public String lacksRole() throws Exception {
		return "lacksRole";
	}

	@RequestMapping(value = "/hasPermission")
	public String hasPermission() throws Exception {
		return "hasPermission";
	}

	@RequestMapping(value = "/hasAnyPermissions")
	public String hasAnyPermissions() throws Exception {
		return "hasAnyPermissions";
	}

	@RequestMapping(value = "/lacksPermission")
	public String lacksPermission() throws Exception {
		return "lacksPermission";
	}

	@RequestMapping(value = "/principal")
	public String principal() throws Exception {
		return "principal";
	}
}
