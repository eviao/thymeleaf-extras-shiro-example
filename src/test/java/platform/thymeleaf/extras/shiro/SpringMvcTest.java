package platform.thymeleaf.extras.shiro;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class SpringMvcTest {
	
	@Autowired
	WebApplicationContext ctx;
	
	MockMvc mvc;
	
	@Before
	public void initContext() throws Exception {
		mvc = webAppContextSetup(ctx).build();
	}
	
	@Before
	public void initSecurityManager() throws Exception {
		Factory<SecurityManager> factory = 
				new IniSecurityManagerFactory("classpath:security.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
	}
	
	@Test
	public void guest() throws Exception {
		mvc.perform(get("/guest"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("guest")));
	}
	
	@Test
	public void authenticated() throws Exception {
		UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin", true);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		
		mvc.perform(get("/authenticated"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("authenticated")));
	}
	
	@Test
	public void notAuthenticated() throws Exception {
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			subject.logout();
		}
		
		mvc.perform(get("/notAuthenticated"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("notAuthenticated")));
	}
	
	@Test
	public void user() throws Exception {
		mvc.perform(get("/user"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("user")));
	}
	
	@Configuration
	@Import(ExtrasConfiguration.class)
	public static class TestConfiguration {}
	
}
