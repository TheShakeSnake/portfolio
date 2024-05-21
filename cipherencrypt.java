import java.util.*;
import java.io.*;
public class cipherencrypt {

	public static void main(String[] args) {
		String infilename = " ", outfilename = " ", password = "",filetext = "", key = "", etext = "" ;
		int option = 0, option2, shift, bite, t=0;
		boolean loop = true;
		Scanner input = new Scanner(System.in);

		try {
			System.out.println("Enter an input file name");										//collects input file name and uses it to create a DataInputStream for Caesar ciper,
			infilename = input.next();															// Scanner for Vigenère(pronounced vision-air) cipher
			Scanner infile = new Scanner(new FileInputStream(infilename));
			DataInputStream instream = new DataInputStream(new FileInputStream(infilename));
			System.out.println("Enter an output file name");
			outfilename = input.next();
			PrintWriter pw = new PrintWriter(outfilename);
			DataOutputStream bio = new DataOutputStream (new FileOutputStream(outfilename));	//collects output file name and uses it to create a DataOutputStream
			System.out.println("Do you want to:\n1.Encode a file\n2.decode a file");
			option2 = input.nextInt();
			if(option2 == 1) {
			System.out.println("Which ciper do you want to use?\n" + "1. Ceasar cipher\n" + "2. Vigenère cipher(makes message uppercase) ");
			option = input.nextInt();
			switch(option) {
			case 1:
				System.out.println("Enter the encryption key: ");
				shift = input.nextInt();							//collects the key for the cipher
				while(loop == true) {			//loops until no more bytes are available to read, then triggers an exception to signal the file is encrypted
				bite = instream.readByte() + shift;		//reads the byte from the input file and adds the key to it
				bio.writeByte(bite);				//writes the new bite to the output file
				}
				break;
			case 2:
				System.out.println("Enter a password to encrypt your file with:");
				password = input.next();												//takes password
				password = password.toUpperCase();
				while(infile.hasNextLine()) {											//goes as long as there are lines in the file
					filetext = infile.nextLine();										//takes first line of file text
					filetext = filetext.toUpperCase();									//forces to uppercase for encryption
					key = createKey(filetext,password);									//creates key used for encryption 
					System.out.print("key is: " +key);									//prints key to console in case user wants to know the key
					
					for(int i = 0; i < filetext.length();i++) {							//encrypting text
						int echar;
						if(filetext.charAt(i) == ' '  || filetext.charAt(i) == '\n') {	//if current char in file is a blank space or new line character, set it as the encrypted character
							echar = filetext.charAt(i);
						}
						else {
						echar = (filetext.charAt(i) + key.charAt(i)) %26;				//otherwise, encrypt the current character based on the key generated earlier
						echar += 'A';
						}
						etext+=(char)echar;												//adds encrypted character to the final string to be printed to the file
					}
					pw.print(etext + '\n');												//prints out encrypted text with a new line character to preserve structure
					etext = "";															//resets etext
					key="";																//resets key
				}
				pw.flush();																//flushes encrypted text to file
				
				break;

			default: System.out.println("Invalid option, try again");
			}
			}
			
			else if(option2==2) {
				System.out.println("Which ciper do you want to use?\n" + "1. Ceasar ciper\n" + "2. Vigenère ciper");
				option = input.nextInt();																			//takes option from user
				switch(option) {
				case 1: {
					System.out.println("Enter the key for the cipher");			
					shift = input.nextInt();			//collects cipher key
					do {
						bite = instream.readByte() - shift;	//reads the current byte in the file and subtracts the key
						bio.writeByte(bite);			// writes the new bite to the output file
					}while(loop==true);			//loops until there is no new data left in the file, then triggers an exception
					break;
				}
				case 2: {
					System.out.println("Enter the password that was used to encrypt the file");
					password = input.next();														//takes password from user
					password = password.toUpperCase();												//forces to upper case
					while(infile.hasNextLine()) {													//goes until file has no more encrypted text
						etext = infile.nextLine();													//grabs line of encrypted text from file
						key = createKey(etext, password);											//generates the key that was used to encrypt the file based on encrypted text and password
						for(int i = 0; i <etext.length() && i < key.length(); i++) {
							if(etext.charAt(i) == ' '  || etext.charAt(i) == '\n') {				//blank spaces and new lines are added to filetext
								filetext+= etext.charAt(i);
								continue;
							}
							int echar = (etext.charAt(i) - key.charAt(i) +26) %26;					//otherwise, character is decrypted based on key and encrypted text
							echar += 'A';
							filetext += (char)echar;												//now decrypted character is added to filetext
						}
						pw.print(filetext + '\n');													//prints current line of decrypted text
						filetext = "";																//clears filetext to be used again
						
					}
					pw.flush();																		//flushes printwriter
					break;	
				}
				default: System.out.println("Invalid option, try again");
				}
			}
			pw.close();																				//closes everything
			infile.close();
			input.close();
			bio.close();
			instream.close();
		} catch (IOException e) {
			System.out.println("Process done");														//when Caesar cipher is done, displays message									
		}
		}
		public static String createKey(String text, String password) {								//creates key by repeating characters in password until it matches the length of the original text
			String key="";
			int t = 0;
			for ( int i = 0;i<text.length();i++) {												
				if(t == password.length())
					t = 0;
				if(text.charAt(i) == ' '  || text.charAt(i) == '\n') {							
					key+= text.charAt(i);
					continue;
				}
				key += password.charAt(t);
				t++;
			}
			return key;
		}
	}


