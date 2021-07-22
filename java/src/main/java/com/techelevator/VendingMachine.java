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

    public Vendable[] getArrayOfVendables(){
        //arrayOfVendables = new Vendable[SLOTS.length];
        Chips potatoCrisps = new Chips(new BigDecimal("3.05"),"Potato Crisps");
        Chips stackers = new Chips(new BigDecimal("1.45"), "Stackers");
        arrayOfVendables[0] = potatoCrisps;
        arrayOfVendables[1] = stackers;

        return arrayOfVendables;

    }

    public void displayInventory(){
        /*String[] slots = {"A1","A2","A3","A4","B1","B2","B3","B4","C1","C2","C3","C4","D1","D2","D3","D4",};

        String[] itemNames = {"Potato Crisps", "Stackers", "Grain Waves","Cloud Popcorn",
                "Moonpie", "Cowtails", "Wonka Bar", "Crunchie","Cola", "Dr. Salt", "Mountain Melter",
                "Heavy", "U-Chews", "Little League Chew", "Chiclets", "Triplemint"};

        BigDecimal[] itemPrices = {new BigDecimal("3.05"), new BigDecimal("1.45"), new BigDecimal("2.75"),new BigDecimal("3.65"),
                new BigDecimal("1.80"),new BigDecimal("1.50"),new BigDecimal("1.50"),new BigDecimal("1.75"),
                new BigDecimal("1.25"),new BigDecimal("1.50"),new BigDecimal("1.50"),new BigDecimal("1.50"),
                new BigDecimal("0.85"), new BigDecimal(".95"), new BigDecimal("0.75"),new BigDecimal("0.75")};
  */
        int itemQuantity = 5;

        try {
            for (int i = 0; i < getArrayOfVendables().length; i++) {

                System.out.println(SLOTS[i] + ": " + getArrayOfVendables()[i].getName() + ": " + getArrayOfVendables()[i].getPrice() + ": ");

                //itemQuantity[i] = 5;
                //System.out.println(SLOTS[i] + ": " + itemNames[i] + ": " + NumberFormat.getCurrencyInstance().format(itemPrices[i]) + ": " + itemQuantity[i]);
            }
        } catch (Exception e) {
            System.out.println("Array is null");
            e.printStackTrace();
        }
    }

    public void feedMoney(){

        try{
            String amountToDeposit = "";
            boolean run = true;

            while (run) {
                System.out.println("1) $1   2) $5");
                System.out.println("3) $10  4) $20");
                System.out.print("Please select the number of the amount you would like to deposit: ");

                int selection = console.nextInt();

                if (selection > 0 && selection < 5) {
                    run = false;
                } else {
                    System.out.println("Sorry, invalid selection. Please enter a selection (1-4) that corresponds to amount to deposit");
                }
            }
        }catch (InputMismatchException e) {
            System.out.println("Sorry, invalid selection. PROGRAM TERMINATED");
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
