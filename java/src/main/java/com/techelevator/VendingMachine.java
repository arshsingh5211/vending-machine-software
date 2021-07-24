package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VendingMachine {
    private BigDecimal balance = new BigDecimal("0.00");
    private final String[] SLOTS = {"A1","A2","A3","A4","B1","B2","B3","B4","C1","C2","C3","C4","D1","D2","D3","D4"};
    private String[] itemNames;
    private BigDecimal[] itemPrices;
    private int[] itemQuantityArr;
    private Vendable[] vendableArr;
    private File transactionLog = new File("log.txt");
    private BigDecimal previousBalance;

    Scanner console = new Scanner(System.in);

    //need to let user select slots instead of numbers
    //need to update transactionLog report for select product and giveChange

    public VendingMachine () {
        itemQuantityArr = new int[SLOTS.length];
        for (int i = 0; i < itemQuantityArr.length; i++) {
            itemQuantityArr[i] = 5;
        }


        Chips a1 = new Chips(new BigDecimal("3.05"), "Potato Crisps");
        Chips a2 = new Chips(new BigDecimal("1.45"), "Stackers");
        Chips a3 = new Chips(new BigDecimal("2.75"), "Grain Waves");
        Chips a4 = new Chips(new BigDecimal("3.65"), "Cloud Popcorn");
        Candy b1 = new Candy(new BigDecimal("1.80"), "Moonpie");
        Candy b2 = new Candy(new BigDecimal("1.50"), "Cowtales");
        Candy b3 = new Candy(new BigDecimal("1.50"), "Wonka Bar");
        Candy b4 = new Candy(new BigDecimal("1.75"), "Crunchie");
        Beverages c1 = new Beverages(new BigDecimal("1.25"), "Cola");
        Beverages c2 = new Beverages(new BigDecimal("1.50"), "Dr. Salt");
        Beverages c3 = new Beverages(new BigDecimal("1.50"), "Mountain Melter");
        Beverages c4 = new Beverages(new BigDecimal("1.50"), "Heavy");
        Gum d1 = new Gum(new BigDecimal("0.85"), "U-Chews");
        Gum d2 = new Gum(new BigDecimal("0.95"), "Little League Chew");
        Gum d3 = new Gum(new BigDecimal("0.75"), "Chiclets");
        Gum d4 = new Gum(new BigDecimal("0.75"), "Triplemint");


        vendableArr = new Vendable[]{a1, a2, a3, a4, b1, b2, b3, b4, c1, c2, c3, c4, d1, d2, d3, d4};
    }

    // stack of numbers 1 -5
    // Stack of Vendable items, have 5 in stack, and after each purchase, take from stack
    // stack.size() to get how many items are in the stack


    public void displayInventory(){
        int itemQuantity = 5;
        System.out.println("\nWelcome to Vendo-Matic 800!\n\n");
        for (int i = 0; i < vendableArr.length; i++) {
            String quantityUpdate = itemQuantityArr[i] > 0 ? Integer.toString(itemQuantityArr[i]) : "SOLD OUT!!";
        	System.out.println(SLOTS[i] + ": " + vendableArr[i].getName() + ": " + 
        			NumberFormat.getCurrencyInstance().format(vendableArr[i].getPrice()) + ": " + quantityUpdate); // ADD QUANTITY REMAINING
        }
    }

    public void feedMoney(){
        previousBalance = balance;
        String amountToDeposit = "";
    	BigDecimal[] feedMoneyOptions = new BigDecimal[] {new BigDecimal("1.00"), new BigDecimal("5.00"), new BigDecimal("10.00"), new BigDecimal("20.00")};
    	boolean run = true;
    	while (run) {
			System.out.println("\n1. $1\t\t\t\t2. $5");
			System.out.println("3. $10\t\t\t\t4. $20 ");
			System.out.print("\nPlease select the number corresponding to your choice: ");

			int selection = console.nextInt();
			if (selection > 0 && selection < 5) {
				balance = balance.add(feedMoneyOptions[selection - 1]);
				run = false;
				// should we add a check here to make sure user meant to do this -- "Are you sure you want to ...Y/N"
				System.out.println("Thanks! Your new balance is " + NumberFormat.getCurrencyInstance().format(balance) + ".");
			} 
			else System.err.print("Sorry, invalid selection! Please enter a selection (1-4) that corresponds to amount to deposit"); 
        }
    	logTransaction(previousBalance);

    }
    
    
    public void selectProduct(){
        // need print statement of all slots and their vendable items
        for (int i = 0; i < vendableArr.length; i++) {
            System.out.println(" [" + SLOTS[i] + "] " + vendableArr[i].getName() + " (" + NumberFormat.getCurrencyInstance().format(vendableArr[i].getPrice()) + ")");
            // add quantity remaining!!
            //need to let user select slots instead of numbers

        }
        boolean run = true;
        while (run) {
            System.out.print("Please enter option corresponding to your product selection: ");
            int selection = console.nextInt();
            if (selection > 0 && selection < 17) {
                BigDecimal selectionPrice = vendableArr[selection-1].getPrice();
                if (balance.compareTo(selectionPrice) < 0) {
                    System.out.println("Current balance is less than item price! Please feed money and try again."); // can tell them how much more money they need -- do later
                    run = false;
                }
                else if (itemQuantityArr[selection-1] == 0) {
                    System.out.print("Sorry, item is sold out!");
                    run = false;
                }

                // Sorr

                else {
                    itemQuantityArr[selection-1] = itemQuantityArr[selection-1] - 1;
                    balance = balance.subtract(selectionPrice);
                    System.out.println("\n" + vendableArr[selection-1].getSound() + " Your new balance is " + NumberFormat.getCurrencyInstance().format(balance) + ".");
                    run = false;
                }
                // we need to add a check somewhere to make sure balance is never below $0
                // rn it lets you keep buying stuff even if your balance would fall below 0 after purchase
            }
            // if there entry is beyond array length then one error message to try again, if it's not enough money take back to purchase menu
            // loop back to previous menu
            else System.err.print("Sorry, invalid selection! Please feed more money or choose a different item.");
            // ******Needs to go back to purchase menu instead of asking for a selection again.************
        }
    }

    public void finishTransaction() {
        //calculate change
        //reset balance to $0
        System.out.println("\nYour transaction is now complete. You may now collect your change.\n");
        this.getChange();

        balance = new BigDecimal("0.00");


        // make sure everything is restocked to 5
        //return to main menu --> return to run somehow?

    }

    public void getChange(){
        int changeInPennies = balance.intValue()*100;
        int numbersOfQuarters = 0;
        int numberOfDimes = 0;
        int numberOfNickels = 0;

        while(changeInPennies > 0){
            numbersOfQuarters = changeInPennies / 25;
            changeInPennies = changeInPennies % 25;
            numberOfDimes = changeInPennies / 10;
            changeInPennies = changeInPennies % 10;
            numberOfNickels = changeInPennies / 5;
        }
        /*
        get change using the smallest amount of coins possible
         */


        //BigDecimal changeDue = salePrice.subtract(amountPaid); // int - int (or any primitive)
        //BigDecimal dollars = (BigDecimal) changeDue;

        System.out.println("Your change is: " + numbersOfQuarters + " Quarters, " + numberOfDimes + " Dimes, & " + numberOfNickels + " Nickels");
        //if change is grater than 0, "Your change is: amount of quarters, amount of dimes, amount of nickels "



    }


    public void createLogFile(){
        String currentDirectory = System.getProperty("user.dir"); // get directory
        File transactionLog = new File(currentDirectory + "/log.txt"); // create new file object

        try {

            if (!transactionLog.exists()) {
                transactionLog.createNewFile();
            }

        } catch(Exception e){
            System.out.println(e);
        }
        /*
        feed money log
        select product log - item picked and slot it was in
        give change - finish transaction - create file as instance
         */
    }

    public void logTransaction(BigDecimal previousBalance){
        createLogFile();
        try (FileWriter fileWriter = new FileWriter(transactionLog,true);
                PrintWriter pw = new PrintWriter(fileWriter)) { // try with resources
            //transactionLog.createNewFile(); // create actual file
            //collect data from transaction for printing
            //PrintWriter dataOutput = new PrintWriter(transactionLog) {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
                LocalDateTime currentDateTime = LocalDateTime.now();
                String dateTimeFormatted = currentDateTime.format(dateFormat);
                pw.println(dateTimeFormatted + "Feed Money: " + NumberFormat.getCurrencyInstance().format(previousBalance)
                        + " " + NumberFormat.getCurrencyInstance().format(balance));


            }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public String[] getItemNames() {
		return itemNames;
	}

	public void setItemNames(String[] itemNames) {
		this.itemNames = itemNames;
	}

	public BigDecimal[] getItemPrices() {
		return itemPrices;
	}

	public void setItemPrices(BigDecimal[] itemPrices) {
		this.itemPrices = itemPrices;
	}

	public int[] getItemQuantityArr() {
		return itemQuantityArr;
	}

	public void setItemQuantityArr(int[] itemQuantityArr) {
		this.itemQuantityArr = itemQuantityArr;
	}

	public Scanner getConsole() {
		return console;
	}

	public String[] getSLOTS() {
		return SLOTS;
	}
	
	public Vendable[] getVendableArr(){
        return vendableArr;
    }

	public void setVendableArr(Vendable[] arrayOfVendables) {
		this.vendableArr = arrayOfVendables;
	}

	public BigDecimal getBalance() {
        return balance;
    }

    /*
    String path = "vendingmachine.csv";
        File inventoryFile = new File(path);

        if(!inventoryFile.exists()){
            System.out.println("Sorry, file does not exist");
        }
        if(!inventoryFile.isFile()){
            System.out.println("Sorry, file not a valid file");
        }

        try(Scanner fileScanner = new Scanner(inventoryFile)){
            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                System.out.println(line);
            }

        } catch(FileNotFoundException e) {
            System.err.println("Sorry, file not found");
            e.printStackTrace();
            }
     */

}
