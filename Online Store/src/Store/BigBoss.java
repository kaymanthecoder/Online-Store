package Store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class BigBoss {

	public static void main(String[] args) {
		Scanner scan =  new Scanner(System.in);
		Random rand = new Random();
		int selection = 1;
		
		//For the categories
	    int itemID = 0;
	    String itemName = null;
	    int price$ = 0;
	    int quantity = 0;
		
	    //I removed my SQL username and password and just put "removed"
		
		//System.out.println("Enter MySql username: ");
	    //String mysqlname = scan.nextLine();
		String mysqlname = "removed";
				
		//System.out.println("Enter MySql password: ");
		//String mysqlpass = scan.nextLine();
		String mysqlpass = "removed";
		//These will go into connections
		
		try(
				//Creating table connection and statement
				Connection conNet = DriverManager.getConnection("jdbc:mysql://localhost:3306/animeonline",mysqlname,mysqlpass);
				Statement stats = conNet.createStatement();
				){
			while(selection > 0) {
			//Menu for user input
			System.out.println("\n1)Add an item");
			System.out.println("2)Update an item");
			System.out.println("3)Delete an item");
			System.out.println("4)View an item");
			System.out.println("5)View all items in a category");
			System.out.println("6)View every single item in the store");
			System.out.println("7)View list of members");
			System.out.println("8)View list of orders");
			System.out.println("9)View list of cancelled orders");
			System.out.println("10)View how much the business has");
			System.out.println("0)Exit");
			selection = scan.nextInt();
			
			//-------------------------------Adding an item---------------------------------
			if(selection == 1) {
				itemID = rand.nextInt(1000);
				
				System.out.println("Enter the name of the item: ");
				scan.nextLine();//Code would skip this
				itemName = scan.nextLine();
				
				System.out.println("Enter the price($): ");
				price$ = scan.nextInt();
				
				System.out.println("Enter the quantity of the item: ");
				quantity = scan.nextInt();
				
				System.out.println("Enter the category:");
				System.out.println("1)Gaming");
				System.out.println("2)Posters");
				System.out.println("3)Blu-ray");
				System.out.println("4)Comics");
				System.out.println("5)CD's");
				int miniSelect = scan.nextInt();
				String value = null;
				
				if(miniSelect == 1) {
					value = "Gaming";
				}
				else if(miniSelect == 2) {
					value = "Posters";
				}
				else if(miniSelect == 3) {
					value = "Blu_ray";
				}
				else if(miniSelect == 4) {
					value = "Comics";
				}
				else if(miniSelect == 5) {
					value = "CDs";
				}
				else {
					System.out.println("Invalid input!!");
				}
				
				//"'Title'" so that it is 'Title' in MySql
				String sqlInsert = "insert into " + value + " values (" + itemID + ",'" + itemName + "'," 
				+ price$ + "," + quantity + ");";
				System.out.println("The Query is : " + sqlInsert);
				int countInserted = stats.executeUpdate(sqlInsert);
				System.out.println(countInserted + " records inserted\n");
				
			}
			
			//------------------------------Updating an item--------------------------------
			else if(selection == 2) {
				System.out.println("Enter the category:");
				System.out.println("1)Gaming");
				System.out.println("2)Posters");
				System.out.println("3)Blu-ray");
				System.out.println("4)Comics");
				System.out.println("5)CD's");
				int miniSelect = scan.nextInt();
				String value = null;
				
				if(miniSelect == 1) {
					value = "Gaming";
				}
				else if(miniSelect == 2) {
					value = "Posters";
				}
				else if(miniSelect == 3) {
					value = "Blu_ray";
				}
				else if(miniSelect == 4) {
					value = "Comics";
				}
				else if(miniSelect == 5) {
					value = "CDs";
				}
				else {
					System.out.println("Invalid input!!");
				}
				
				System.out.println("\nEnter item ID: ");
				itemID = scan.nextInt();
				
				
				int chooseColumn = 1;//So that the program will enter the loop
				String updateColumn;
				int countUpdated = 0;
				
				while(chooseColumn > 0) {
					System.out.println("What would you like to change?");
					System.out.println("1)Title");
					System.out.println("2)Price");
					System.out.println("3)Quantity");
					System.out.println("0)Exit");//Might make more than one update
					chooseColumn = scan.nextInt();
					
					if(chooseColumn ==1) {
						updateColumn = "Title";
						System.out.println("Enter the title: ");
						scan.nextLine(); //Program would skip this
						itemName = scan.nextLine();
						
						//" '" + item + "' " so that it will be 'item'
						String sqlUpdate = "update " + value + " set " + updateColumn + " = '" + itemName 
								+ "' where item_ID = " + itemID;
						System.out.println("The SQL query is: " + sqlUpdate);
						countUpdated = stats.executeUpdate(sqlUpdate);
					}
					else if(chooseColumn == 2) {
						updateColumn = "Price$";
						System.out.println("Enter the price($): ");
						scan.nextLine(); //Program would skip this
						price$ = scan.nextInt();
						
						String sqlUpdate = "update " + value + " set " + updateColumn + " = " + price$
								+ " where item_ID = " + itemID;
						countUpdated = stats.executeUpdate(sqlUpdate);
					}
					else if(chooseColumn == 3) {
						updateColumn = "Quantity";
						System.out.println("Enter the Quantity: ");
						scan.nextLine(); //Program would skip this
						quantity = scan.nextInt();
						
						String sqlUpdate = "update " + value + " set " + updateColumn + " = " + price$
								+ " where item_ID " + itemID;
						countUpdated = stats.executeUpdate(sqlUpdate);
					}
					else {
						System.out.println("Invalid input!!");
					}
				}//End of while loop in Selection 2
				
				System.out.println(countUpdated + " records affected\n");
			}

			//------------------------------Deleting an item---------------------------------
			else if(selection == 3) {
				System.out.println("Enter the category:");
				System.out.println("1)Gaming");
				System.out.println("2)Posters");
				System.out.println("3)Blu-ray");
				System.out.println("4)Comics");
				System.out.println("5)CD's");
				int miniSelect = scan.nextInt();
				String value = null;
				
				if(miniSelect == 1) {
					value = "Gaming";
				}
				else if(miniSelect == 2) {
					value = "Posters";
				}
				else if(miniSelect == 3) {
					value = "Blu_ray";
				}
				else if(miniSelect == 4) {
					value = "Comics";
				}
				else if(miniSelect == 5) {
					value = "CDs";
				}
				else {
					System.out.println("Invalid input!!");
				}
				
				System.out.println("\nEnter item ID: ");
				itemID = scan.nextInt();
				
				String sqlDelete = "Delete from " + value + " where item_ID = " + itemID;
				System.out.println("The SQL query is: " + sqlDelete);
				int countDeleted = stats.executeUpdate(sqlDelete);
				System.out.println(countDeleted + " records affected\n");
			}

			//--------------------------------View an item-----------------------------------
			else if(selection == 4) {
				System.out.println("Enter the category:");
				System.out.println("1)Gaming");
				System.out.println("2)Posters");
				System.out.println("3)Blu-ray");
				System.out.println("4)Comics");
				System.out.println("5)CD's");
				int miniSelect = scan.nextInt();
				String value = null;
				
				if(miniSelect == 1) {
					value = "Gaming";
				}
				else if(miniSelect == 2) {
					value = "Posters";
				}
				else if(miniSelect == 3) {
					value = "Blu_ray";
				}
				else if(miniSelect == 4) {
					value = "Comics";
				}
				else if(miniSelect == 5) {
					value = "CDs";
				}
				else {
					System.out.println("Invalid input!!");
				}
				
				System.out.println("\nEnter item ID: ");
				itemID = scan.nextInt();
				
				String sqlSelect = "select * from " + value + " where item_ID = " + itemID;
				ResultSet rset = stats.executeQuery(sqlSelect);
				
				System.out.println("item_ID | Title | Price($) | Quantity |");
				while (rset.next()) {//this while loop and for loop is for printing all the books
					String myList = " ";
					for(int i = 1; i <= 4; i++) {
						myList += rset.getString(i) + " | ";
					}
					System.out.println(myList);
					}
			}

			//-----------------------View all items in a category-----------------------------
			else if(selection == 5) {
				System.out.println("Enter the category:");
				System.out.println("1)Gaming");
				System.out.println("2)Posters");
				System.out.println("3)Blu-ray");
				System.out.println("4)Comics");
				System.out.println("5)CD's");
				int miniSelect = scan.nextInt();
				String value = null;
				
				if(miniSelect == 1) {
					value = "Gaming";
				}
				else if(miniSelect == 2) {
					value = "Posters";
				}
				else if(miniSelect == 3) {
					value = "Blu_ray";
				}
				else if(miniSelect == 4) {
					value = "Comics";
				}
				else if(miniSelect == 5) {
					value = "CDs";
				}
				else {
					System.out.println("Invalid input!!");
				}
				
				String sqlSelect = "select * from " + value;
				ResultSet rset = stats.executeQuery(sqlSelect);
				
				System.out.println("item_ID | Title | Price($) | Quantity |");
				while (rset.next()) {//this while loop and for loop is for printing all the books
					String myList = " ";
					for(int i = 1; i <= 4; i++) {
						myList += rset.getString(i) + " | ";
					}
					System.out.println(myList);
					}
			}

			//----------------------View every single item in the store-----------------------
			else if(selection == 6) {
				System.out.println("Table for Gaming: ");
				String sqlSelect = "select * from Gaming";
				System.out.println("item_ID | Title | Price($) | Quantity |");
				
				ResultSet rset = stats.executeQuery(sqlSelect);
				while (rset.next()) {//this while loop and for loop is for printing all the books
					String myList = " ";
					for(int i = 1; i <= 4; i++) {
						myList += rset.getString(i) + " | ";
					}
					System.out.println(myList);
					}
				
				System.out.println("\nTable for Posters: ");
				String sqlSelect2 = "select * from Posters";
				System.out.println("item_ID | Title | Price($) | Quantity |");
				
				ResultSet rset2 = stats.executeQuery(sqlSelect2);
				while (rset2.next()) {//this while loop and for loop is for printing all the books
					String myList2 = " ";
					for(int i = 1; i <= 4; i++) {
						myList2 += rset2.getString(i) + " | ";
					}
					System.out.println(myList2);
					}
				
				System.out.println("\nTable for Blu-ray: ");
				String sqlSelect3 = "select * from Blu_ray";
				System.out.println("item_ID | Title | Price($) | Quantity |");
				
				ResultSet rset3 = stats.executeQuery(sqlSelect3);
				while (rset3.next()) {//this while loop and for loop is for printing all the books
					String myList3 = " ";
					for(int i = 1; i <= 4; i++) {
						myList3 += rset3.getString(i) + " | ";
					}
					System.out.println(myList3);
					}
				
				System.out.println("\nTable for Comics: ");
				String sqlSelect4 = "select * from Comics";
				System.out.println("item_ID | Title | Price($) | Quantity |");
				
				ResultSet rset4 = stats.executeQuery(sqlSelect4);
				while (rset4.next()) {//this while loop and for loop is for printing all the books
					String myList4 = " ";
					for(int i = 1; i <= 4; i++) {
						myList4 += rset4.getString(i) + " | ";
					}
					System.out.println(myList4);
					}
				
				System.out.println("\nTable for CD's: ");
				String sqlSelect5 = "select * from CDs";
				System.out.println("item_ID | Title | Price($) | Quantity |");
				
				ResultSet rset5 = stats.executeQuery(sqlSelect5);
				while (rset5.next()) {//this while loop and for loop is for printing all the books
					String myList5 = " ";
					for(int i = 1; i <= 4; i++) {
						myList5 += rset5.getString(i) + " | ";
					}
					System.out.println(myList5);
					}
				
			}
			//---------------------------View list of members---------------------------------
			else if(selection == 7) {
				System.out.println("Table for Members: ");
				String sqlSelect = "select * from Members";
				System.out.println("User_ID | Username | Full name | Email | Password | Address | Cell-number | E-Wallet |");
				
				ResultSet rset = stats.executeQuery(sqlSelect);
				while (rset.next()) {//this while loop and for loop is for printing all the books
					String myList = " ";
					for(int i = 1; i <= 8; i++) {
						myList += rset.getString(i) + " | ";
					}
					System.out.println(myList);
					}
			}

			//----------------------------View list of orders------------------------------------
			else if(selection == 8) {
				System.out.println("Table for List of orders: ");
				String sqlSelect = "select * from order_list";
				System.out.println("item_ID | How Many | Title | User_ID | Username | Order Date | Delivery Date |");
				
				ResultSet rset = stats.executeQuery(sqlSelect);
				while (rset.next()) {//this while loop and for loop is for printing all the books
					String myList = " ";
					for(int i = 1; i <= 7; i++) {
						myList += rset.getString(i) + " | ";
					}
					System.out.println(myList);
					}
			}

			//--------------------------View list of cancelled orders-----------------------------
			else if(selection == 9) {
				System.out.println("Table for List of cancelled orders: ");
				String sqlSelect = "select * from cancelled_order_list";
				System.out.println("item_ID | How many | Title | User_ID | Username | Order Date | Cancelled Date |");
				
				ResultSet rset = stats.executeQuery(sqlSelect);
				while (rset.next()) {//this while loop and for loop is for printing all the books
					String myList = " ";
					for(int i = 1; i <= 7; i++) {
						myList += rset.getString(i) + " | ";
					}
					System.out.println(myList);
					}
			}

			//-----------------------View how much the business has----------------------------
			else if(selection == 10) {
				System.out.println("Table for Money: ");
				String sqlSelect = "select * from money_made";
				System.out.println("Money($) |");
				
				ResultSet rset = stats.executeQuery(sqlSelect);
				while (rset.next()) {//this while loop and for loop is for printing all the books
					String myList = " ";
					for(int i = 1; i <= 1; i++) {
						myList += rset.getString(i) + " | ";
					}
					System.out.println(myList);
					}
			}
			else {
				System.out.println("Invalid input!!!");
			}
			
			}//End bracket for while loop
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
}