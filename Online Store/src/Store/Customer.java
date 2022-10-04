package Store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

public class Customer {

	public static void main(String[] args) {
		Scanner scan  = new Scanner(System.in);
		Random rand = new Random();
		int firstEnter = 0;//So that it will enter the while loop
		int logchoice = 1;//So that it will enter the while loop
		
		String fullname = null;
		String email;
		String password;
		String cell;
		String address;
		String userName = null;
		int eWallet;
		int userID = 0;
		
		//I removed my SQL username and password and just put "removed"
		
		//System.out.println("Enter MySql username: ");
	    //String mysqlname = scan.nextLine();
		String mysqlname = "removed";
				
		//System.out.println("Enter MySql password: ");
		//String mysqlpass = scan.nextLine();
		String mysqlpass = "removed";
		//These will go into connections
		
		try(
				Connection iCom = DriverManager.getConnection("jdbc:mysql://localhost:3306/animeonline",mysqlname,mysqlpass);
				Statement stats = iCom.createStatement();
				
				Connection iCom2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/animeOPH",mysqlname,mysqlpass);
				Statement stats2 = iCom2.createStatement();
				){
			while(firstEnter != 1 && firstEnter != 2 && firstEnter != 3) {//Keep showing the message until the user chooses the correct input
				//Menu for user input
				System.out.println("Choose an option: ");
				System.out.println("1)Create an account");
				System.out.println("2)Log in");
				firstEnter = scan.nextInt();
				
				//--------------------Creating an account-----------------------
				if(firstEnter == 1) {
					System.out.println("Enter your full name: ");
					scan.nextLine();//Code would skip this
					fullname = scan.nextLine();
					
					System.out.println("Enter your email address: ");
					email = scan.nextLine();
					
					System.out.println("Enter your password: ");
					password = scan.nextLine();
					
					System.out.println("Enter your phone number: ");
					cell = scan.nextLine();
					
					System.out.println("Enter your address: ");
					address = scan.nextLine();
					
					System.out.println("Enter a username would you like to use: ");
					userName = scan.nextLine();
					
					System.out.println("Enter how much would you like to tranfer to your e-wallet: ");
					eWallet = scan.nextInt();
					
					userID = rand.nextInt(10000);
					
					String sqlInsert = "insert into Members values (" + userID +", '" + userName + "' , '" + fullname +
							"' , '"+ email + "' , '" + password + "' , '" + address + "' , '"+ cell + "',"+ eWallet + ")";
					System.out.println("The SQL Query is : " + sqlInsert);
					int countInserted = stats.executeUpdate(sqlInsert);
					System.out.println(countInserted + " records affected\n");
					
					//Creating a Purchase History table
					String sqlPH = "create table user" + userID + "(item_ID int not null,Title varchar(50) not null,"
							+ "howMany int not null,Price$ int not null,PurchaseDate Date,PaidFor varchar(10) not null);";
					System.out.println("The SQL Query is : " + sqlPH);
					int countInserted2 = stats2.executeUpdate(sqlPH);//This adds new table
					//It won't count because you are only creating not adding
					//System.out.println(countInserted2 + " records affected\n");
					
					firstEnter = 0;//So that the loop will break and go back to Menu
				}
				//--------------------------Logging in---------------------------
				else if(firstEnter == 2) {
					int countchecked = 0;
					
					while(countchecked == 0) {
						System.out.println("Enter username: ");
						scan.nextLine();//Code would skip this
						userName = scan.nextLine();
						
						System.out.println("Enter password: ");
						password = scan.nextLine();
						
						//I know I don't actually want to update
						//I just want to check if the user inputed the correct user name and password
						//But I want this loop to continue until he/she has logged in
						//I will update it with the exact same user name
						//So that I can get a count for an update (update won't happen if log in is incorrect)
						//I can only get a count if something has been changed (just viewing won't cut it)
						//This is the only method that is suited for this
						String sqlcheck = "update Members set userName = '" + userName
						+ "' where userName = '" + userName + "' and  password  = '" + password + "' ";
				
						countchecked = stats.executeUpdate(sqlcheck);
					}//End of Logging in while loop
					
					//------------------Getting name value-----------------------
					//*Remember " '" + word + "' " there is a ' in those 
				    String getNameA = "select fullname from members where username = '" + userName + "'";
				    ResultSet getNameB = stats.executeQuery(getNameA);
				    String getNameC = null;
				    
					while (getNameB.next()) {//this while loop and for loop is for printing
                    getNameC = ""; //not " " I don't want a space before the word
						for(int i = 1; i <= 1; i++) {
							getNameC += getNameB.getString(i);
							}
						}
				    
					//---------------------Getting userID---------------------------
					 String getIDA = "select user_ID from members where username = '" + userName + "'";
					 ResultSet getIDB = stats.executeQuery(getIDA);
					    
					 while (getIDB.next()) {//this while loop and for loop is for printing
	                 userID = 0;
					 for(int i = 1; i <= 1; i++) {
						 userID += getIDB.getInt(i);
						 }
					 }
					
					 //-----------------Getting E-Wallet----------------
					 String getWalletA = "select e_wallet$ from members where username = '" + userName + "'";
					 ResultSet getWalletB = stats.executeQuery(getWalletA);
					 int wallet = 0;
					 
					 while (getWalletB.next()) {//this while loop and for loop is for printing
	                 wallet = 0;
					 for(int i = 1; i <= 1; i++) {
						 wallet += getWalletB.getInt(i);
						 }
					 }
					 
					System.out.println("Welcome! " + getNameC);
					while(logchoice != 0) {
						
						//---------------Getting rid of unpaid items from last time---------------
						String getRidA = "delete from user" + userID + " where paidfor = 'false'";
						int countdeleted = stats2.executeUpdate(getRidA);
						
						//--------------Getting rid of unpaid orders that was not completed----------
						//This will be done before actually paying, it will safe time because if I do it later
						//I will have to make another while and for loop which will take so many lines
						//This method is faster,easier and uses less lines
						//'null' is only for strings, null is for dates and numbers, even though you enter '2020-04-08'
						//use "is null" or "=' ' "
						String getRidPayA = "delete from order_list where deliverydate is null";
						int countedpaygone = stats.executeUpdate(getRidPayA);
						
						System.out.println("\nCash $" + wallet);
						System.out.println("Choose an option:");
						System.out.println("1)Buy something");
						System.out.println("2)Check purchase history");
						System.out.println("3)Cancel order");
						System.out.println("4)Delete account");
						System.out.println("0)Exit");
						logchoice = scan.nextInt();
						
						//--------------------Buying something-----------------
						if(logchoice == 1) {
							int howMany = 1;

							//---------------Getting rid of null value before executing 1---------------
							//Look at 11 & 12 at the bottom
							String getRidNullA = "delete from user" + userID + " where title = 'null'";
							int countdeletedNull = stats2.executeUpdate(getRidNullA);
							
							while(howMany > 0){//This method works just fine for numbers but not words
								System.out.println("\nCash $" + wallet);
								System.out.println("Enter the category:");
							    System.out.println("1)Gaming");
							    System.out.println("2)Posters");
							    System.out.println("3)Blu-ray");
							    System.out.println("4)Comics");
							    System.out.println("5)CD's");
							    System.out.println("6)CheckOut");
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
								else if(miniSelect ==6) {
									break;
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
							
						                                                   
								System.out.println("Enter the item ID you would like to add to cart: ");
							    int itemID = scan.nextInt();
								System.out.println("How many of that item? (Enter 0 to exit)");
								howMany = scan.nextInt();
								
								if(howMany != 0) {//Should not execute if equal to 0
									
									//-------------------Getting title of item---------------------
									String getTitA = "select Title from " + value + " where item_ID = " + itemID;
									ResultSet getTitB = stats.executeQuery(getTitA);
									String title = null;
									
									while(getTitB.next()) {
										title = "";
										for(int i = 1; i <=1; i++) {
											title += getTitB.getString(i);
										}
									}
									
									//----------------------Getting price$-------------------------
									String getPriA = "select price$ from " + value + " where item_ID = " + itemID;
									ResultSet getPriB = stats.executeQuery(getPriA);
									int price$ = 0;
									
									while(getPriB.next()) {
										for(int i =1; i <=1; i++) {
											price$ += getPriB.getInt(i);
										}
									}
									
									//-----------------------Purchase Date--------------------
									SimpleDateFormat toDate = new SimpleDateFormat("yyyy/MM/dd");
									Date invoice_date = new Date();
									String today = toDate.format(invoice_date);
									
									//------------------------Is it paid for?-------------------
									String paid = "false";
									
									String sqlInsert = "insert into user" + userID + " values (" + itemID +", '" + title + "' , " + howMany +
											" , "+ price$ + " , '" + today + "' , '" + paid + "','" + value +"')";
									System.out.println("The SQL Query is : " + sqlInsert);
									int countInserted = stats2.executeUpdate(sqlInsert);
									System.out.println(countInserted + " records affected\n");
									
									//------------Adding to order list (will be removed if not paid for)----------
									String sqladdorder = "insert into order_list values (" + itemID + ", " + howMany + ", '"
											+ title + "', " + userID + ", '" + userName + "', '" + today + "', null)";
									System.out.println("The SQL Query is : " + sqladdorder);
									int countInserted2 = stats.executeUpdate(sqladdorder);
									System.out.println(countInserted2 + " records affected\n");
									}
								}
							//---------------Getting rid of null value before executing 2---------------
							String getRidNullA2 = "delete from user" + userID + " where title = 'null'";
							int countdeletedNull2 = stats2.executeUpdate(getRidNullA2);
							
							//-------------------------Checkout-----------------
							System.out.println("\nChecking out!");
							System.out.println("Customer: " + getNameC);
							
							//--------------Getting titles and prices--------------
							// " where paidfor = 'false'" is used to show and calculate unpaid items only
							String allA = "select title,price$,howMany from user" + userID + " where paidfor = 'false'";
							ResultSet allB = stats2.executeQuery(allA);
							String allItem;
							
							System.out.println("Title | Price($) | How many | ");
							while(allB.next()) {
								allItem = "";
								for(int i =1; i <=3; i++) {
									allItem += allB.getString(i) + " | ";
								}
								System.out.println(allItem);
							}
							//----------Getting total of all the items-----------
							String totsA = "select price$,howMany from user" + userID + " where paidfor = 'false'";
							//String totsA = "select sum(price$) from user" + userID; for the total in a single step
							ResultSet totsB = stats2.executeQuery(totsA);
							int totsC = 0;
							int totsD = 0;
							int num = 1;
							int finaltots = 0;

							while(totsB.next()) {
								for(int i=1; i <=2; i++) {//put i <=2 to print more than 1 value (price$,howMany)
									                                      //if i <=1 only price$ will be printed
									num = num + 1;//Start off with even number
									//1st loop price$ goes thru 2nd loop howMany goes thru than this pattern continues (3rd...4th..)
									if(num% 2 ==0) {//(1) price$ is added first
										totsC = 0;//(3) delete the value once price$ and howMany has been added together in finaltots
										totsC += totsB.getInt(i);//(5&6)
									}
									else if(num% 2 != 0) {//(2) howMany is added in the next loop
										totsD = 0;//(4) delete the value
										totsD = totsC * totsB.getInt(i);//(5&6)So that the old values are not affected
										finaltots += totsD;//The final total where every price$ * howMany is added
									}
								}
							}
							System.out.println("\nTotal $" + finaltots);
							
							
							System.out.println("\nDo you want to purchase these?");
							System.out.println("\n1)Yes\n2)No");
							int checkPls = scan.nextInt();
							int checkPlsGo = 0;
							
							if(checkPls ==1) {
								System.out.println("Do you want:");
								System.out.println("1)Premium Delivery | 1-3 Days | $30");
								System.out.println("2)Standard Delivery | 7-10 Days | $10");
								System.out.println("3)Less Priority Delivery | 23-30 Days | $2");
								int delChoice = scan.nextInt();
								int deliver = 0;
								int deliverDay = 0;
								
								if(delChoice == 1) {
									deliver = 30;
									deliverDay = rand.nextInt(4);//MAX 3, last number doesn't get chosen
								}
								else if(delChoice == 2) {
									deliver = 10;
									deliverDay = rand.nextInt(4) + 7;//MAX 3 + 7
								}
								else {
									deliver = 2;
									deliverDay = rand.nextInt(8) + 23;//MAX 7 + 23
								}
							    checkPlsGo = wallet - finaltots - deliver;
							    if(checkPlsGo < 0) {
							    	System.out.println("Insufficient funds! Good bye!");
							    	break;
							    }
							    else {
							    	//-------------Turn 'false' into 'true'----------
							    	String sqlcheck = "update user" + userID +" set paidFor = 'true' where paidfor = 'false'";
						            countchecked = stats2.executeUpdate(sqlcheck);
						            
						            //-------------Getting full name----------------
						            String sqlname = "select fullname from members where user_ID = " + userID;
						            ResultSet getname = stats.executeQuery(sqlname);
						            String fullname2 = null;
						            
						            while(getname.next()) {
						            	for(int i = 1; i <=1; i++) {
						            		fullname2 = getname.getString(i);
						            		// having += will make null next to the name
						            	}
						            }
									
									//-------------Deduct from wallet--------------
							        String sqlwallet = "update members set e_wallet$ = " + checkPlsGo 
							        		+ " where user_ID = " + userID;
								    countchecked = stats.executeUpdate(sqlwallet);
									
									//------------------Future Delivery Date-----------
									SimpleDateFormat toDate = new SimpleDateFormat("yyyy/MM/dd");
									
									Date invoice_date = new Date();
									String today = toDate.format(invoice_date);
									
									//convert date to calendar
									Calendar c = Calendar.getInstance();
									c.setTime(invoice_date);
									
									//manipulate date
									c.add(Calendar.DATE, deliverDay);
									Date future = c.getTime();
									String futureDate = toDate.format(future);
									System.out.println(futureDate);
						
									//--------------------Make an order----------------
									String neworder = "update order_list set deliverydate = '" + futureDate 
											+ "' where deliverydate is null";
									countchecked = stats.executeUpdate(neworder);
									
									//----------------Make an receipt in a text file------------
									//Used for name of text file
									SimpleDateFormat toDate2 = new SimpleDateFormat("yyyyMMddHHmmss");
									String textname = toDate2.format(invoice_date);
									//String textname = toDate2.format(today); is wrong because "today" is a string
									
									//Time and date of purchase
									SimpleDateFormat toDate3 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
									String receiptTime = toDate3.format(invoice_date);
									
									// create a new formatter
									try {
									Formatter f = new Formatter(textname+".txt");
									// print the formatted strings to the file
									f.format("%s", "CHECKOUT RECEIPT");
									f.format("%s%s", "\nName: ", fullname2 ," \r\n");
									f.format("%s%s", "\n\nOld eWallet Balance: $", wallet, "\r\n");
									f.format("%s%s", "\nTotal Cost: -$", finaltots, "\r\n");
									f.format("%s%s", "\nDelivery cost: -$",deliver,"\r\n");
									f.format("%s%s", "\nNew eWallet Balance: $", checkPlsGo, "\r\n");
									f.format("%s%s", "\n\nPurchase Date: ", receiptTime, "\r\n");
									f.format("%s%s", "\nDelivery Date: ", futureDate, "\r\n");
									f.format("%s", "\n\nOpen your Anime Online account to view your items", "\r\n");
									f.format("%s", "\nThank you for buying at Anime Online! Come back next time!");
									f.close(); //%s%s = no space between words & %s %s = space
									} 

									catch (Exception e) {
										System.out.println("Error");
									}
									
								   //-------------------Getting business amount----------------
									String getMonA = "select * from money_made";
								    ResultSet getMonB = stats.executeQuery(getMonA);
								    int myMoney = 0;
									    
									while (getMonB.next()) {//this while loop and for loop is for printing
					                myMoney = 0;
									for(int i = 1; i <= 1; i++) {
										myMoney += getMonB.getInt(i);
										}
									}
									
									//-----------------Add profit to the business------------
									int bossMoney = deliver + finaltots;
									myMoney = myMoney + bossMoney;
									String sqlmoney =  "update money_made set money$ = " + myMoney;
									countchecked = stats.executeUpdate(sqlmoney);
									 
									wallet = checkPlsGo; //To make sure the changes in check is equal to the wallet
									                                      //Without needing to rerun the program to see the new balance
									}
							}
							else {
								break;
							}
							
						}
						//--------------------Checking purchase history-----------------------
						else if(logchoice == 2) {
							String chaseA = "select * from gaming";
							ResultSet chaseB = stats.executeQuery(chaseA);
							
							String myChase = "";
							
							System.out.println("item_ID |  Title | howMany | Price$ | PurchaseDate | PaidFor | Category |");
							while(chaseB.next()) {
								for(int i = 1; i <=4; i++) {
									myChase += chaseB.getString(i) + " | ";
								}
								System.out.println(myChase);
							}
							
						}
						//---------------------------Cancel order-----------------------
						else if(logchoice ==3) {
							String cancel = "insert into cancelled_order_list select * from order_list where user_ID = " + userID;
							int cancelSet = stats.executeUpdate(cancel);
						}
						//------------------------Deleting account-------------------------
						else if(logchoice == 4) {
							//delete from members
							String delete = "delete from members where user_ID = " + userID;
							int deleteSet = stats.executeUpdate(delete);
							
							//delete user purchase history
                           String deleteChase = "drop table user" + userID;	
                           int delete2Set = stats.executeUpdate(deleteChase);
						} 
						//-----------------------------Exit Loop-----------------------------
						else if (logchoice == 0){
							break;
						}
						
					}
					
				}
				
			}//End bracket for while loop
			
			//*Notes: I can't use more than one ResultSet values at a time! One has to be done before doing the other one
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}