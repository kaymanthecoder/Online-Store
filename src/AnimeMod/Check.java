package AnimeMod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

public class Check {

	public static void main(String[] args) {
		Scanner what = new Scanner(System.in);
		Random rand = new Random();
		
		SimpleDateFormat toDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		Date invoice_date = new Date();
		String today = toDate.format(invoice_date);
		
		//convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(invoice_date);
		
		//manipulate date
		c.add(Calendar.DATE, 40);
		Date futuro = c.getTime();
		String giornoFuturo = toDate.format(futuro);
		
		System.out.println(giornoFuturo);
		
		//it does not pick that last number
		int pick = rand.nextInt(2);
		System.out.println(pick);
		
		SimpleDateFormat toDate2 = new SimpleDateFormat("yyyyMMddHHmmss");
		String giornoFuturo2 = toDate2.format(futuro);
		System.out.println(giornoFuturo2);

		//Used for name of text file
		SimpleDateFormat toDate21 = new SimpleDateFormat("yyyyMMddHHmmss");
		String textname = toDate21.format(futuro);
		
		SimpleDateFormat toDate3 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		String receiptTime = toDate3.format(futuro);
		
		// create a new formatter
		try {
		Formatter f = new Formatter(textname+".txt.");
		// print the formatted strings to the file
		f.format("%s", "CHECKOUT RECEIPT");
		f.format("%s %s", "\nName: ", "Naruto " ," \r\n");
		f.format("%s%s", "\nNew eWallet Balance: $", "50", "\r\n");
		f.format("%s %s", "\nTotal: $", "100", "\r\n");
		f.format("%s %s", "\nPurchase Date: ",receiptTime, "\r\n");
		f.format("%s", "\nOpen your Anime Online account to view your items", "\r\n");
		f.format("%s", "\nThank you for buying at Anime Online! Come back next time!");
		f.close();    
		} 

		catch (Exception e) {
			System.out.println("Error");
		}
	}

}
