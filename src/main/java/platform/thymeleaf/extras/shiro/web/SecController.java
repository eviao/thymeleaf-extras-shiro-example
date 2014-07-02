package platform.thymeleaf.extras.shiro.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sec")
public class SecController {
	
	private static Logger logger = LoggerFactory.getLogger(SecController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String sec() throws Exception {
		
		logger.info("into sec method()");
		
		return "sec";
	}
}
