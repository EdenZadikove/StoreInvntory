import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.store.model.services.UserSessionService;

class UnitTest {

	@Test
	void test() throws IOException {
		
		UserSessionService us = new UserSessionService();
		boolean isUser;
		String email = "";
		int pass = 0;
		
		isUser = us.signIn(email, pass) == null ? false : true;
		
		assertFalse(isUser);
	}
	
	

	@Test
	void test1() throws IOException {
		
		UserSessionService us = new UserSessionService();
		boolean isUser;
		String email = "eden";
		int pass = 123;
		
		isUser = us.signIn(email, pass) == null ? false : true;
		
		assertTrue(isUser);
	}

}
