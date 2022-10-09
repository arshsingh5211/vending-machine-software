package com.techelevator.common;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class VendingMachine {
    private BigDecimal balance = new BigDecimal("0.00");
    private final String[] slots = new String[16];
    private final int[] itemQuantityArr = new int[16];
    private final Item[] items = new Item[16];
    private final File inputFile = new File("vendingmachine.csv");
    private final File transactionLog = new File("log.txt");
    private BigDecimal previousBalance;
    private final Scanner console = new Scanner(System.in);


    // Feed Money on log should be $5.00 $5.00 (example) like in README not $0 $5
    // add "Current Money Provided: [balance]" per README

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
                String itemType = lineArr[3];
                if (itemType.equalsIgnoreCase("Chip")){
                    items[lineNum-1] = new Chips(priceBD, lineArr[1]);
                }
                else if (itemType.equalsIgnoreCase("Candy")){
                    items[lineNum-1] = new Candy(priceBD, lineArr[1]);
                }
                else if (itemType.equalsIgnoreCase("Drink")){
                    items[lineNum-1] = new Beverages(priceBD, lineArr[1]);
                }
                else items[lineNum-1] = new Gum(priceBD, lineArr[1]);
                lineNum++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Arrays.fill(itemQuantityArr, 5);
    }

    public void displayInventory(){
        for (int i = 0; i < items.length; i++) {
            String quantityUpdate = itemQuantityArr[i] > 0 ? Integer.toString(itemQuantityArr[i]) : "SOLD OUT!!";
        	System.out.println("[" + slots[i] + "] " + items[i].getName() + ": " +
        			NumberFormat.getCurrencyInstance().format(items[i].getPrice()) + " (Qty: " + quantityUpdate + ")");
        }
        System.out.println("\nPlease press [enter] to go back to Main Menu:");
        console.nextLine();  //make the user hit ENTER before continuing
    }

    // When the customer selects "(1) Display Vending Machine Items",
    // they're presented with a list of all items in the vending machine with its quantity remaining:
    // ^ per README -- so we don't need to display price here?

    public void feedMoney(){
        previousBalance = balance;
    	BigDecimal[] feedMoneyOptions = new BigDecimal[] {new BigDecimal("1.00"), new BigDecimal("2.00"), new BigDecimal("5.00"), new BigDecimal("10.00")};
    	boolean run = true;
    	while (run) {
			System.out.println("\n1. $1\t\t\t\t2. $2");
			System.out.println("3. $5\t\t\t\t4. $10 ");
			System.out.print("\nPlease choose an option >>> ");

			int selection = console.nextInt();
			if (selection > 0 && selection < 5) {
                System.out.print("Are you sure you want to feed " +
                        NumberFormat.getCurrencyInstance().format(feedMoneyOptions[selection - 1]) +
                        " to the Vending Machine? Y/N ");
                String verifyChoice = console.next().trim();
                if (!verifyChoice.equalsIgnoreCase("y") && !verifyChoice.equalsIgnoreCase("yes")) {
                    System.out.println("No worries! Balance is still " + NumberFormat.getCurrencyInstance().format(balance) + ".");
                }
                else {
                    balance = balance.add(feedMoneyOptions[selection - 1]);
                    System.out.println("Thanks! Your new balance is " + NumberFormat.getCurrencyInstance().format(balance) + ".");
                    logTransaction(previousBalance, "FEED MONEY");
                }
                run = false;
            }
			else System.err.println("\nSorry, invalid selection! Please enter a selection (1-4) that corresponds to amount to deposit. ");
        }
    }


    public void selectProduct(){
        BigDecimal previousBalance = balance;
        System.out.println(); // just wanted to add a line separator before list is shown
        for (int i = 0; i < items.length; i++) {
            System.out.println("[" + slots[i] + "] " + items[i].getName() + " (" +
                    NumberFormat.getCurrencyInstance().format(items[i].getPrice()) + ")");
        }
        System.out.println();
        System.out.print("Please choose an option >>> ");
        String selection = console.nextLine().toUpperCase().trim().replaceAll("\\s", ""); //this line might be the issue
        int indexOfItem = -1;
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].equals(selection)) indexOfItem = i;
        }
        if (indexOfItem == -1) System.out.println("Whoops! That is not a valid selection!");
        else {
            if (balance.compareTo(items[indexOfItem].getPrice()) < 0) {
                System.out.println("Sorry, current balance insufficient! Please feed more money or select a different item.");
            }
            else if (itemQuantityArr[indexOfItem] == 0) {
                System.out.println("\n*****Oh no! " + slots[indexOfItem] + " is SOLD OUT!*****\n");
            }
            else {
                balance = balance.subtract(items[indexOfItem].getPrice());
                System.out.println("\nNice! You purchased " + items[indexOfItem].getName() + " for " +
                        NumberFormat.getCurrencyInstance().format(items[indexOfItem].getPrice()) +
                        ", and your remaining balance is " + NumberFormat.getCurrencyInstance().format(balance) + ".\n"
                        + items[indexOfItem].getSound());
                itemQuantityArr[indexOfItem]--;
                logTransaction(previousBalance, items[indexOfItem].getName() + " " + slots[indexOfItem]);
            }
        }
    }

    public void finishTransaction() {
        BigDecimal previousBalance = balance;
        System.out.println("\nYour transaction is now complete.");

        if (previousBalance.compareTo(new BigDecimal("0.00")) != 0){
            getChange();
            balance = new BigDecimal("0.00");
            logTransaction(previousBalance, "GIVE CHANGE");
        }
        else System.out.println("Balance is zero -- no change required!");

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
        System.out.println("Your change is: " + numbersOfQuarters + " Quarter(s), " + numberOfDimes + " Dime(s), and " +
                numberOfNickels + " Nickel(s)");
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

	public Item[] getItems(){
        return items;
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
