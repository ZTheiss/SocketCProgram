package Program;
import java.io.*;
import java.net.*;

public class MyClient {
	
	public static void main(String[] args) {
		try {
			Socket s=new Socket("localhost",3333);  
			DataInputStream din=new DataInputStream(s.getInputStream());  
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
			String str="",str2="";  
			
			dout.flush();  
			str2=din.readUTF();  
			System.out.println("Server: "+str2);  
			
			//Read through till User or Server stops with a special string. 
			//Imagining an ATM that has a number pad
			while(!str.equals("HowDoYouCatchAnEtherBunny?WithAnEthernet!")){  //Easter Egg
				str2=din.readUTF();
				System.out.println("Server: "+str2);
				int index = str2.indexOf("enter");
				if (index != -1) {
					str=br.readLine();  
					dout.writeUTF(str);  
					dout.flush();
				}
				int endIndex = str2.indexOf("Bye");
				if (endIndex != -1) {
					System.out.println("Client is disconnecting.");
				}
				 
			}  
			dout.close();  
			s.close();  
		}
		catch (Exception e) {
			
		}
	}
}