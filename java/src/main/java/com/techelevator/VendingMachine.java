package com.techelevator;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VendingMachine {
    private BigDecimal balance = new BigDecimal("0.00");
    private final String[] SLOTS = {"A1","A2","A3","A4","B1","B2","B3","B4","C1","C2","C3","C4","D1","D2","D3","D4"};
    private String[] itemNames;
    private BigDecimal[] itemPrices;
    private int[] itemQuantity;
    Vendable[] arrayOfVendables = new Vendable[SLOTS.length];
    Scanner console = new Scanner(System.in);

    public void displayInventory(){
    	Vendable[] vendableArr = getArrayOfVendables();
        int itemQuantity = 5;
        System.out.println("\nWelcome to Vendo-Matic 800!\n\n");
        for (int i = 0; i < vendableArr.length; i++) {
        	System.out.println(SLOTS[i] + ": " + vendableArr[i].getName() + ": " + 
        			NumberFormat.getCurrencyInstance().format(vendableArr[i].getPrice()) + ": ");
        }
    }

    public void feedMoney(){
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
				System.out.println("Thanks! Your new balance is " + NumberFormat.getCurrencyInstance().format(balance) + ".");
			} 
			else System.err.print("Sorry, invalid selection! Please enter a selection (1-4) that corresponds to amount to deposit"); 
        }
    }
    
    
    public void selectProduct(){

    }
    public void finishTransaction(){

    }
    public void getChange(){

    }
    public void recordTransaction(){

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

	public int[] getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int[] itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Scanner getConsole() {
		return console;
	}

	public String[] getSLOTS() {
		return SLOTS;
	}
	
	public Vendable[] getArrayOfVendables(){
        Chips potatoCrisps = new Chips(new BigDecimal("3.05"),"Potato Crisps");
        Chips stackers = new Chips(new BigDecimal("1.45"), "Stackers");
        arrayOfVendables[0] = potatoCrisps;
        arrayOfVendables[1] = stackers;
        arrayOfVendables[2] = potatoCrisps;
        arrayOfVendables[3] = stackers;
        arrayOfVendables[4] = potatoCrisps;
        arrayOfVendables[5] = stackers;
        arrayOfVendables[6] = potatoCrisps;
        arrayOfVendables[7] = stackers;
        arrayOfVendables[8] = potatoCrisps;
        arrayOfVendables[9] = stackers;
        arrayOfVendables[10] = potatoCrisps;
        arrayOfVendables[11] = stackers;
        arrayOfVendables[12] = potatoCrisps;
        arrayOfVendables[13] = stackers;
        arrayOfVendables[14] = potatoCrisps;
        arrayOfVendables[15] = stackers;
        

        return arrayOfVendables;

    }

	public void setArrayOfVendables(Vendable[] arrayOfVendables) {
		this.arrayOfVendables = arrayOfVendables;
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
