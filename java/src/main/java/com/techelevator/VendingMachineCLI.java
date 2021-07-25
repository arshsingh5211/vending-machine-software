package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
	
	private static final String   PURCHASE_MENU_FEED_MONEY = "Feed Money";
	private static final String   PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
	private static final String   PURCHASE_MENU_EXIT = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_FEED_MONEY, PURCHASE_MENU_SELECT_PRODUCT, PURCHASE_MENU_EXIT };

	private VendingMachine vm = new VendingMachine();
	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		while (true) {
			System.out.println("\n---------------------------");
			System.out.println("Welcome to Vendo-Matic 800!");
			System.out.println("---------------------------");
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				vm.displayInventory();
			} 
			else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
					this.purchaseMenu();
			} 
			else if (choice.equals(MAIN_MENU_OPTION_EXIT)) { // exits system not exits to main menu as README asked
				System.out.println("\n------------------------------------------------------");
				System.out.println("Thank you for using Vendo-Matic 800! Have a nice day.");
				System.out.println("------------------------------------------------------");
				System.exit(0);
			}
		}
	}

	public void purchaseMenu() {
		boolean run = true;
		while (run) {
			String purchaseMenuChoice = (String)menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if (purchaseMenuChoice.equals(PURCHASE_MENU_FEED_MONEY)) {
				vm.feedMoney();
			}
			else if (purchaseMenuChoice.equals(PURCHASE_MENU_SELECT_PRODUCT)) {
				vm.selectProduct();
			}
			else if (purchaseMenuChoice.equals(PURCHASE_MENU_EXIT)) {
				vm.finishTransaction();
				break;
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();


	}
}
