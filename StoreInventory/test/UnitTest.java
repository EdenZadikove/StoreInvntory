
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import com.store.controller.OrdersController;
import com.store.controller.StoreController;
import com.store.controller.UserSessionServiceController;
import com.store.controller.UsersController;
import com.store.model.repository.OrdersRepository;
import com.store.model.repository.StoreRepository;
import com.store.model.repository.UsersRepository;
import com.store.model.services.OrdersService;
import com.store.model.services.StoreService;
import com.store.model.services.UsersService;

class UnitTest {	

	private OrdersController ordersController_;
	private StoreController storeController_;
	private UsersController usersController_;
	private UserSessionServiceController userSessionServiceController_;
	private StoreService storeService_;
	private OrdersService ordersService_;
	private UsersService usersService_;
	
	private static final String storePath_  = "..\\StoreInventory\\files\\tests\\store.text";
	private static final String ordersPath_  = "..\\StoreInventory\\files\\tests\\orders.text";
	private static final String usersPath_  = "..\\StoreInventory\\files\\tests\\users.text";
	
	private static final String DIR_PATH = "..\\StoreInventory\\files\\tests"; 
	
	@BeforeAll
	public static void createsDemoDB(){
		StoreRepository.setPath_(storePath_);
		OrdersRepository.setPath_(ordersPath_);
		UsersRepository.setPath_(usersPath_);	
	}
	
	@BeforeEach
	public void setUp() {
		ordersController_ = new OrdersController();
		storeController_ = new StoreController();
		usersController_ = new UsersController();
		userSessionServiceController_ = new UserSessionServiceController();
		storeService_ = new StoreService();
		ordersService_ = new OrdersService();
		usersService_ = new UsersService();
		
		//create 2 users for test and save to file.
		usersService_.createNewUser("Danny_Bernshtein", "456", "danny@gmail.com", 2);
		usersService_.createNewUser("Sharon_Mauda", "789", "sharon@gmail.com", 3);
		usersService_.saveToFileUsers();
		
		//connect to the system as an admin
		userSessionServiceController_.login("eden@gmail.com", "123");
	}
	
	@AfterAll
	public static void deleteDemoDB(){
		File dir = new File(DIR_PATH);
		for(File file: dir.listFiles())
				file.delete();
	}
	
	//LOGIN
	@Test
	void loginSuccess() {
		try{
			int userType = userSessionServiceController_.login("eden@gmail.com", "123");
			assertEquals(1, userType);
		} catch(IllegalArgumentException e) {
			fail("failed when should success");
		}
	}
	
	@Test
	void failLoginEmptyEmailTest() {
		try{
			userSessionServiceController_.login("", "123");
			fail("Login success when should failed.");
		} catch(IllegalArgumentException e) {
			assertEquals("\n! Email must not be null. Please try again.", e.getMessage());
		}
	}

	//ORDERS
	
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
			ordersController_.cancelOrder(0);	
		} catch(Exception e) {
			assertEquals("! Order id- 0 does not exists.", e.getMessage());
		}	
	}
	
	@Test
	void cancelOrdeWithStatusCanceled() {
		ordersService_ = new OrdersService();
		
		/*1. create order
		  2. cancel order
		  3. cancel again*/
		
		int orderId = ordersService_.createOrder("Gloves", 10);
		try{
			ordersService_.cancelOrder(orderId);
		}catch(Exception e){
			fail("failed when should be successed");
		}
		
		try {
			ordersController_.cancelOrder(orderId);
			fail("success when should be failed");
		} catch(Exception e) {
			assertEquals("! Order id- " + orderId + " can not be canceled bacause it's already done.", e.getMessage());
		}	
	}
	
	@Test
	void deleteOrdeWithStatusPending() {
		ordersService_ = new OrdersService();
		
		/*1. create order
		  2. delete order*/
		
		int orderId = ordersService_.createOrder("Gloves", 10);
		try{
			ordersService_.deleteOrder(orderId);
			fail("success when should be failed.");
		}catch(Exception e){
			assertEquals("! Order id- " + orderId + " can not be deleted because its waiting for supplier's response.\nIf"
										+ " you want to delete this order - 1. Cancel this order\n" 
										+ "                                   2. Delete this order.", e.getMessage());
		}
	}
	
	@Test
	void editOrderQuantityOrderNotExists() {
		int quantity = 100;
		int orderId = 1;
		
		try {
			boolean res = ordersService_.editOrder(orderId, quantity);
			assertFalse(res);
		} catch(Exception e) {}
	}
	
	@Test 
	void editOrderNotValidOrderId() {
		int quantity = 100;
		int orderId = -10; //orderId must be more then 0
		try {
			ordersController_.editOrder(orderId, quantity);
			fail("success when should be failed.");
		} catch(Exception e) {
			assertEquals("! Order Id must be more then 0. Please try again.", e.getMessage());
		}
		
	}
	
	//STORE
	
	@Test
	void editPriceProductNotExists(){	
		boolean res;
		try {
			res = storeController_.editPrice("blabla", 10.0, 5.0);
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
	
	@Test
	void changeOrderStatusOrderNotExists() {
		int orderId = 0; //orderId must be more then 0;
		try {
			boolean res = ordersService_.changeOrderStatus(orderId, "approved");
			assertFalse(res);
		}catch(Exception e) {}
	}
	
	
	@Test
	void changeOrderStatusWithPendingStatus() {
		//first create order with status "pending"
		String itemName = "Gloves";
		int quantity = 100;
		int orderId = 0; //initialize
		
		try {
			orderId = ordersController_.createOrder(itemName, quantity);
		} catch (Exception e) {}
		
		// change order status to from "pending" to approved
		try {
			ordersService_.changeOrderStatus(orderId, "approved");
		}catch(Exception e) {}
		
		//try to change order status from "approved" to denied (need to failed because only orders with status "pending" can be changed)
		try {
			boolean res = ordersService_.changeOrderStatus(orderId, "denied");
			assertFalse(res);
		}catch(Exception e) {}
		
	}
	
	//USERS
	
	@Test
	void addExistingUser() {
		String email = "eden";
		try {
			boolean res = usersController_.createNewUser("kuku", "koko", "123", email, "Admin");
			assertFalse(res);
		}catch(Exception e) {}
	}
	
	@Test
	void addUserEmptyValues() {
		try {
			usersController_.createNewUser("", "", "123", "", "Admin");
			fail("Passed when should be failed.");
		}catch(Exception e) {
			assertEquals("! One or more of the inputs is empty. Please try again.", e.getMessage());
		}
	}
	
	@Test
	void deleteUserSuccess() {
		String email = "sharon@gmail.com";
		try {
			boolean res = usersController_.deleteUser(email);
			assertTrue(res);
		}catch(Exception e) {
			fail("failed when should be success.");
		}
	}
	
	@Test
	void deleteUserNotValidEmail() {
		String email = "eden@";
		try {
			usersController_.deleteUser(email);
			fail("Passed when should be failed.");
		}catch(Exception e) {
			assertEquals("! Email is not valid. Please try again.", e.getMessage());
		}
	}
	
	
	@Test
	void deleteUserNotExists() {
		String email = "blabla@gmail.com";
		try {
			boolean res = usersController_.deleteUser(email);
			assertFalse(res);
		}catch(Exception e) {}
	}
	
	
	@Test
	void deleteUserActiveUser() {
		String email = "eden@gmail.com";
		userSessionServiceController_.login(email, "123");
		
		try {
			usersController_.deleteUser("eden@gmail.com");
			fail("success when should be failed.");
		}catch(Exception e) {
			assertEquals("! You can not delete your own user.", e.getMessage());
		}
	}
}
