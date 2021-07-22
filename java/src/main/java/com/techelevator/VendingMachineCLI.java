package com.techelevator;

import com.techelevator.view.Menu;

import java.util.Arrays;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };

	private VendingMachine vm = new VendingMachine();
	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				vm.displayInventory();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
			} else if(choice.equals(MAIN_MENU_OPTION_EXIT)){
				System.out.println("Have a nice day");
				break;

			}
		}
	}

	public static void main(String[] args) {
		/*Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
		*/
		Scanner console = new Scanner(System.in);
		String amountToDeposit = "";
		boolean run = true;
		System.out.println("Please select whole dollar amount to deposit: ");
		while(run){
			String response = console.nextLine();
			if(response.){
				amountToDeposit = console.nextLine();
				run = false;
			} else {
				System.out.println("Not a valid amount, please try again ");
			}
		}
	}
}
