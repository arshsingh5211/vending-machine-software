package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class VendingMachine {
    private BigDecimal balance = new BigDecimal("0.00");
    private String[] slots = new String[16];
    private int[] itemQuantityArr = new int[16];;
    private Vendable[] vendableArr = new Vendable[16];
    private File inputFile = new File("vendingmachine.csv");
    private File transactionLog = new File("log.txt");
    private BigDecimal previousBalance;
    private Scanner console = new Scanner(System.in);

    /*
    What I did:
        1) added comments for to-do list for tomm
        2) fixed formatting of strings, added regex for spaces when user selects slot in selectProduct()
                --- should we do that for all user input in console?
                --- Please choose an option >>>        3
                --- ***        3 is not a valid option ***
                --- regex will take out all spaces for evaluation
        3) cleaned up code (shortened loop in selectProduct(), got rid of unused variables/methods,
            extracted constructor in stockInventory() method, switched to switch,
            changed itemQuantityArr using Arrays.fill(), etc)
        4) we want to stock based on vendingmachine.csv instead of creating objects like before, so just did a
            general fileReader thing to read from that file, see stockInventory()

        STILL LEFT TO DO BELOW:
     */


    // how do we close the scanner?
    // add a check to only print to transaction log for GIVE CHANGE if change is > 0
    // fix the select product showing twice thing
    // fix formatting throughout the class
    // stocked via an input file -- READ FROM FILE NOT INCLUDE IN CONSTRUCTOR!!
    // Feed Money on log should be $5.00 $5.00 (example) like in README not $0 $5
    // add a check/verify user wants to deposit that amount of money
    // add "Current Money Provided: [balance]" per README
    // should we change feed money options to 1, 2, 5, 10 -- is $20 too high?
    // "After completing their purchase, the user is returned to the "Main" menu to continue using the vending machine."
        // ^^^ right now it sends them back to Purchase Menu not Main Menu
    // **SELECTPRODUCT() BUG ONLY HAPPENS WHEN YOU FEED MONEY FIRST THEN TRY TO SELECT -- otherwise works fine
    // Do we need "Your transaction is now complete. You may now collect your change." in finish transaction?
    // selectProduct() -- there is a way to use regex to ensure user only enters [A-D || a-d] && [1-4] but I have to figure out how
                // that way we wouldn't need to loop through array
                // wait nvm we would still have to find index anyways, so loop is inevitable

    public VendingMachine () {
        this.stockInventory();
    }

    public void stockInventory() {
        try (Scanner inputScanner = new Scanner (inputFile)) {
            int lineNum = 1;
            while (inputScanner.hasNextLine()) {
                String line = inputScanner.nextLine();
                String[] lineArr = line.trim().split("\\|");
                slots[lineNum - 1] = lineArr[0];
                BigDecimal priceBD = new BigDecimal(lineArr[2]);
                switch (lineArr[3]) {
                    case "Chip":
                        vendableArr[lineNum - 1] = new Chips(priceBD, lineArr[1]);
                        break;
                    case "Candy":
                        vendableArr[lineNum - 1] = new Candy(priceBD, lineArr[1]);
                        break;
                    case "Beverages":
                        vendableArr[lineNum - 1] = new Beverages(priceBD, lineArr[1]);
                        break;
                    default:
                        vendableArr[lineNum - 1] = new Gum(priceBD, lineArr[1]);
                        break;
                }
                lineNum++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Arrays.fill(itemQuantityArr, 5);
    }

    public void displayInventory(){
        System.out.println("\nWelcome to Vendo-Matic 800!\n\n");
        for (int i = 0; i < vendableArr.length; i++) {
            String quantityUpdate = itemQuantityArr[i] > 0 ? Integer.toString(itemQuantityArr[i]) : "SOLD OUT!!";
        	System.out.println("[" + slots[i] + "] " + vendableArr[i].getName() + ": " +
        			NumberFormat.getCurrencyInstance().format(vendableArr[i].getPrice()) + " (Qty: " + quantityUpdate + ")");
        }
    }

    // When the customer selects "(1) Display Vending Machine Items",
    // they're presented with a list of all items in the vending machine with its quantity remaining:
    // ^ per README -- so we don't need to display price here?

    public void feedMoney(){
        previousBalance = balance;
    	BigDecimal[] feedMoneyOptions = new BigDecimal[] {new BigDecimal("1.00"), new BigDecimal("5.00"), new BigDecimal("10.00"), new BigDecimal("20.00")};
    	boolean run = true;
    	while (run) {
			System.out.println("\n1. $1\t\t\t\t2. $5");
			System.out.println("3. $10\t\t\t\t4. $20 ");
			System.out.print("\nPlease choose an option >>> ");

			int selection = console.nextInt();
			if (selection > 0 && selection < 5) {
				balance = balance.add(feedMoneyOptions[selection - 1]);
				run = false;
				// should we add a check here to make sure user meant to do this -- "Are you sure you want to ...Y/N"
				System.out.println("Thanks! Your new balance is " + NumberFormat.getCurrencyInstance().format(balance) + ".");
			} 
			else System.err.println("\nSorry, invalid selection! Please enter a selection (1-4) that corresponds to amount to deposit. ");
        }
    	logTransaction(previousBalance, "FEED MONEY");
    }
    
    
    public void selectProduct(){
        BigDecimal previousBalance = balance;
        System.out.println(); // just wanted to add a line separator before list is shown
        for (int i = 0; i < vendableArr.length; i++) {
            System.out.println("[" + slots[i] + "] " + vendableArr[i].getName() + " (" +
                    NumberFormat.getCurrencyInstance().format(vendableArr[i].getPrice()) + ")");
        }
        System.out.print("\nPlease choose an option >>> ");
        String selection = console.nextLine().toUpperCase().replaceAll("\\s", "");
        int indexOfItem = -1;
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].equals(selection)) indexOfItem = i;
        }
        if (indexOfItem == -1) System.out.println("Whoops! That is not a valid selection!");
        else {
            if (balance.compareTo(vendableArr[indexOfItem].getPrice()) < 0) {
                System.out.println("Sorry, current balance insufficient! Please feed more money or select a different item.");
            }
            else if (itemQuantityArr[indexOfItem] == 0) {
                System.out.println("\n*****Oh no! " + slots[indexOfItem] + " is SOLD OUT!*****\n");
            }
            else {
                balance = balance.subtract(vendableArr[indexOfItem].getPrice());
                System.out.println("\nNice! You purchased " + vendableArr[indexOfItem].getName() + " for " +
                        NumberFormat.getCurrencyInstance().format(vendableArr[indexOfItem].getPrice()) +
                        ", and your remaining balance is " + NumberFormat.getCurrencyInstance().format(balance) + ".\n"
                        + vendableArr[indexOfItem].getSound());
                itemQuantityArr[indexOfItem]--;
                logTransaction(previousBalance, vendableArr[indexOfItem].getName() + " " + slots[indexOfItem]);
            }
        }
    }

    public void finishTransaction() {
        BigDecimal previousBalance = balance;
        System.out.println("\nYour transaction is now complete. You may now collect your change.\n");
        this.getChange();
        balance = new BigDecimal("0.00");
        logTransaction(previousBalance, "GIVE CHANGE");
    }

    public void getChange(){
        BigDecimal changeInPenniesBD = balance.multiply(new BigDecimal("100"));
        int changeInPennies = changeInPenniesBD.intValue();
        int numbersOfQuarters = 0;
        int numberOfDimes = 0;
        int numberOfNickels = 0;

        while(changeInPennies > 0){
            numbersOfQuarters = changeInPennies / 25;
            changeInPennies = changeInPennies % 25;
            numberOfDimes = changeInPennies / 10;
            changeInPennies = changeInPennies % 10;
            numberOfNickels = changeInPennies / 5;
            changeInPennies = changeInPennies % 5;
        }
        System.out.println("Your change is: " + numbersOfQuarters + " Quarters, " + numberOfDimes + " Dimes, & " +
                numberOfNickels + " Nickels");
    }

    public void logTransaction(BigDecimal previousBalance, String actionItem){
        try (FileWriter fileWriter = new FileWriter(transactionLog,true);
                PrintWriter pw = new PrintWriter(fileWriter)) { // try with resources
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
                LocalDateTime currentDateTime = LocalDateTime.now();
                String dateTimeFormatted = currentDateTime.format(dateFormat);
                pw.println(dateTimeFormatted + " " + actionItem + " " + NumberFormat.getCurrencyInstance().format(previousBalance)
                        + " " + NumberFormat.getCurrencyInstance().format(balance));
            }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

	public int[] getItemQuantityArr() {
		return itemQuantityArr;
	}

	public Scanner getConsole() {
		return console;
	}

	public String[] getSlots() {
		return slots;
	}
	
	public Vendable[] getVendableArr(){
        return vendableArr;
    }

	public BigDecimal getBalance() {
        return balance;
    }

    public File getInputFile() {
        return inputFile;
    }

    public File getTransactionLog() {
        return transactionLog;
    }

    public BigDecimal getPreviousBalance() {
        return previousBalance;
    }
}
