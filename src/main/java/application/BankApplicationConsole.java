package application;
import controller.Controller;

public class BankApplicationConsole {
	
	public static void main(String[] args) {
		System.out.println("Remote BankApplication");
		Controller refController = new Controller();
		refController.toServiceMenu();
	}

}
