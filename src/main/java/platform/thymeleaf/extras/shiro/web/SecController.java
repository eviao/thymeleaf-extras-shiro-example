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
}
