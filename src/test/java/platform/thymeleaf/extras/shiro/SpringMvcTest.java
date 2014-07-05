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
	
	private void login(String username,String password) throws Exception {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
	}
	
	@Test
	public void guest() throws Exception {
		
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			subject.logout();
		}
		
		mvc.perform(get("/guest"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("guest")));
	}
	
	@Test
	public void authenticated() throws Exception {
		
		login("admin","admin");
		
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
		
		login("admin","admin");
		
		mvc.perform(get("/user"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("user")));
	}

	@Test
	public void hasRole() throws Exception {
		
		login("admin","admin");
		
		mvc.perform(get("/hasRole"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("manager")));
	}

	@Test
	public void hasAnyRoles() throws Exception {
		
		login("admin","admin");
		
		mvc.perform(get("/hasAnyRoles"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("has any roles [manager or guest]")));
	}

	@Test
	public void lacksRole() throws Exception {
		
		login("test1","test1");
		
		mvc.perform(get("/lacksRole"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("lack role manager")));
	}

	@Test
	public void hasPermission() throws Exception {
		
		login("admin","admin");
		
		mvc.perform(get("/hasPermission"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("has permission user:add")));
	}
	
	@Test
	public void hasAnyPermissions() throws Exception {
		login("admin","admin");
		
		mvc.perform(get("/hasAnyPermissions"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("has any permissions [user:add or role:print]")));
	}
	
	@Test
	public void lacksPermission() throws Exception {
		login("admin","admin");
		
		mvc.perform(get("/lacksPermission"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("lack permission user:print")));
	}
	
	@Test
	public void principal() throws Exception {
		login("admin","admin");
		
		mvc.perform(get("/principal"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("admin")));
	}
	
	@Configuration
	@Import(ExtrasConfiguration.class)
	public static class TestConfiguration {}
	
}
