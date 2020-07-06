
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import com.store.controller.OrdersController;
import com.store.controller.StoreController;
import com.store.controller.UserSessionServiceController;
import com.store.model.repository.StoreRepository;
import com.store.model.services.StoreService;
import com.store.model.services.UserSessionService;

class UnitTest {	

	private OrdersController ordersController_;
	private StoreController storeController_;
	private StoreService storeService_;
	
	@BeforeAll
	public static void cerateDemoStore(){
		StoreRepository storeRepository = StoreRepository.getInstance();
		storeRepository.setPath_("..\\StoreInventory\\files\\DemoStore.txt");
	}
	@BeforeEach
	public void setUp() {
		ordersController_ = new OrdersController();
		storeController_ = new StoreController();
		storeService_ = new StoreService();
	}
	
	@AfterEach
	public void tearDown() {
		ordersController_ = null;
		storeController_ = null;
	}
	 
	@Test
	void failLoginEmptyEmailTest() {
		UserSessionServiceController userSessionServiceController = new UserSessionServiceController();
		try{
			userSessionServiceController.login("", 123);
			fail("Login success when should failed.");
		} catch(IllegalArgumentException e) {
			assertEquals("\n! Email must not be null. Please try again.", e.getMessage());
		}
	}

	@Test
	void failCreatOrderNotValidQuantity() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			ordersController_.createOrder("Gloves", 0);
		});
	}
	
	@Test
	void cancelOrderOrderNotExists() {
		ordersController_ = new OrdersController();
		try {
			ordersController_.cancelOrder(0, "pending");	
		} catch(Exception e) {
			assertEquals("! Order id- 0 does not exists.", e.getMessage());
		}
		
	}
	
	@Test
	void 
	
	@Test
	void editPriceProductNotExists(){	
		boolean res;
		try {
			res = storeController_.editPrice("Gloves", 10.0, 5.0);
			assertFalse(res);
		} catch(Exception e) {	}
	}
	
	@Test
	void editPriceEqualToOldPrice(){	
		try {
			storeController_.editPrice("Gloves", 10.0, 10.0);
			fail("Edit price success when should failed.");
		} catch(Exception e) {
			assertEquals("\n! Gloves price is already 10.0$.", e.getMessage());
		}
	}
	
	@Test
	void editPriceIllegalNewPrice(){	
		try {
			storeController_.editPrice("Gloves", 0, 10.0);
			fail("Edit price success when should failed.");
		} catch(Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.getClass().getName());
		}
	}
	
	@Test
	void removeProductNotExistsItem() {
		boolean res = storeService_.removeProduct("bla bla");
		assertFalse(res);
	}
}
