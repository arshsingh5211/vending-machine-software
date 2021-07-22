package com.techelevator;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;

public class VendingMachine {
    private BigDecimal balance;
    private final String[] SLOTS = {"A1","A2","A3","A4","B1","B2","B3","B4","C1","C2","C3","C4","D1","D2","D3","D4"};
    private String[] itemNames;
    private BigDecimal[] itemPrices;
    private int[] itemQuantity;

    public void displayInventory(){
        /*String[] slots = {"A1","A2","A3","A4","B1","B2","B3","B4","C1","C2","C3","C4","D1","D2","D3","D4",};

        String[] itemNames = {"Potato Crisps", "Stackers", "Grain Waves","Cloud Popcorn",
                "Moonpie", "Cowtails", "Wonka Bar", "Crunchie","Cola", "Dr. Salt", "Mountain Melter",
                "Heavy", "U-Chews", "Little League Chew", "Chiclets", "Triplemint"};

        BigDecimal[] itemPrices = {new BigDecimal("3.05"), new BigDecimal("1.45"), new BigDecimal("2.75"),new BigDecimal("3.65"),
                new BigDecimal("1.80"),new BigDecimal("1.50"),new BigDecimal("1.50"),new BigDecimal("1.75"),
                new BigDecimal("1.25"),new BigDecimal("1.50"),new BigDecimal("1.50"),new BigDecimal("1.50"),
                new BigDecimal("0.85"), new BigDecimal(".95"), new BigDecimal("0.75"),new BigDecimal("0.75")};

        int[] itemQuantity = new int[itemNames.length];
        */

        for (int i = 0; i < itemNames.length; i++) {
            itemQuantity[i] = 5;
            System.out.println(SLOTS[i] + ": " + itemNames[i] + ": " + NumberFormat.getCurrencyInstance().format(itemPrices[i]) + ": " + itemQuantity[i]);
        }
    }

    public void feedMoney(){

    }
    public void selectProduct(){

    }
    public void finishTransaction(){

    }
    public void getChange(){

    }
    public void recordTransaction(){

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
