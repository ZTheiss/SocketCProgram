package Program;
import java.io.*;
import java.net.*;

public class MyServer {
	public static void getOldBalance() {
		//Read a file, look for account number, get balance, then return.
		//if not found, balance = 10000
	}
	public static void menuMain(Socket s, double balance, String account) {
		try {
			int x = 0;
			while (x == 0) {
				DataInputStream din = new DataInputStream(s.getInputStream());
				DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
				dout.writeUTF("Viewing Account "+account+"\n To view account balance, enter 1, to withdrawal enter 2, to deposit enter 3, to exit enter 9, to shut down enter 0.");
				dout.flush();
				String option="";
				option = din.readUTF();
				switch(option) {
				case "1":
					checkBalance(s, balance);
					break;
				case "2": 
					balance = withdrawal(s, balance);
					break;
				case "3":
					balance = deposit(s, balance);
					break;
				case "9":
					dout.writeUTF("Thank you for using Zach's ATM. Logging off. Good Bye.");
					dout.flush(); 
					x=1;
				case "0":
					dout.writeUTF("Thank you for using Zach's ATM. Shutting down. Good Bye.");
					System.exit(0);
				default:
					dout.writeUTF("Please enter an approprate option.");
					dout.flush(); 
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public static void checkBalance(Socket s, double balance) {
		try {
			DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
			dout.writeUTF("Checking Balance. \n Your Account Balance is: "+balance+"\n");
			dout.flush(); 
			return;
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static double withdrawal(Socket s, double balance) {
		try {
			double newBalance = 0, withdrawalAmount = 0;
			boolean failed = false;
			DataInputStream din = new DataInputStream(s.getInputStream());
			DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
			dout.writeUTF("Starting Withdrawal. \n Please enter the amount to withdrawal: ");
			dout.flush(); 
			String withdrawalNum="";
			
			while(!failed) {
				try {
					withdrawalNum = din.readUTF();
					withdrawalAmount = Double.parseDouble(withdrawalNum);	
					if (withdrawalAmount > balance) {
						dout.writeUTF("The amount is too large.");
						dout.flush();
						throw new NumberFormatException ("Value is too large.");
					}
					failed = true;
				}
				catch (NumberFormatException e) {
					dout.writeUTF("Please enter a double: ");
					dout.flush(); 
				}
			}
			newBalance = balance - withdrawalAmount;
			dout.writeUTF("New Balance is: "+newBalance+"\n");
			dout.flush(); 
			return newBalance;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	
	public static double deposit(Socket s, double balance) {
		try {
			double newBalance = 0, depositAmount = 0;
			boolean failed = false;
			DataInputStream din = new DataInputStream(s.getInputStream());
			DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
			dout.writeUTF("Starting Deposit. \n Please enter the amount to deposit: ");
			dout.flush(); 
			String depositNum="";
			
			while(!failed) {
				try {
					depositNum = din.readUTF();
					depositAmount = Double.parseDouble(depositNum);	
					failed = true;
				}
				catch (NumberFormatException e) {
					dout.writeUTF("Please enter a double: ");
					dout.flush(); 
				}
			}
			newBalance = balance + depositAmount;
			dout.writeUTF("New Balance is: "+newBalance+"\n");
			dout.flush(); 
			return newBalance;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	
	public static void main(String[] args) {
		
		try {
			ServerSocket ss = new ServerSocket(3333);
			Socket s=ss.accept();
			
			DataInputStream din=new DataInputStream(s.getInputStream());  
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());  

			double balance = 10000;
			
			dout.writeUTF("Welcome to Zach's ATM!");  
			dout.flush(); 
			dout.writeUTF("Please enter your account number.");
			dout.flush();
			String account="";
			account = din.readUTF();

			
			menuMain(s, balance, account);

			din.close();
			s.close();
			ss.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}