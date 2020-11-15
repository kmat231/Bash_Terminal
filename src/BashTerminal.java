//name: Kevin Mathew
//id number: 112167040
//recitation: 02

import java.util.Scanner;

/**
 * Main method to run the bash terminal
 * @author Kevin
 *
 */
public class BashTerminal {
	public static void main (String args[]) {
		Scanner stdin = new Scanner(System.in);
		DirectoryTree bash = new DirectoryTree();
		boolean running = true;
		String input = "";
		System.out.println("Starting bash terminal.");
		
		while(running) {
			System.out.print("[112167040@host]: $ ");
			input = stdin.nextLine();
		
		switch(input) {
			case "pwd": //where the cursor is located
				System.out.println(bash.presentWorkingDirectory());
				break;
			case "ls": 
			case "ls ": //list all children nodes
				System.out.println(bash.listDirectory());
				break;
			case "ls -R": 
			case "ls -R "://print out tree directory
				DirectoryNode originalCursor = bash.cursor;
				bash.printDirectoryTree(originalCursor, 0);
				break;
			case "cd ..": //back to parent node
				if (bash.cursor.getParent() == null) {
					System.out.println("ERROR: Already at root directory");
				}
				else {
					bash.cursor = bash.cursor.getParent();
				}
				break;
			case "cd /" : //back to root directory reset cursor
				bash.resetCursor();
				break;
			case "exit":
					System.out.println("Program terminating normally");
					running = false; 
					break;
			default: 
				if (input.contains("cd")) {
					try {
							String adjusted = input.substring(3);
							String adjustedM = adjusted.trim();
							bash.changeDirectory(adjustedM); 
					}
					catch(NotADirectoryException error) {
						System.out.println("ERROR: Cannot change directory into a file.");
					}
					catch(StringIndexOutOfBoundsException error) {
						System.out.println("ERROR: Invalid input entered");
					}
					break; 
				}
				
				
				if (input.contains("mkdir")) {	
					try{ 
						String adjusted = input.substring(6);
						String adjustedM = adjusted.trim();
						bash.makeDirectory(adjustedM);
					}
					catch(FullDirectoryException error) {
						System.out.println("ERROR: Present directory is full.");
					}
					catch(NotADirectoryException error) {
						System.out.println("ERROR: Cannot change directory into a file.");
					}
					
					catch(IllegalArgumentException error) {
						System.out.println("Illegal Argument Entered");
					}
					catch(StringIndexOutOfBoundsException error) {
						System.out.println("ERROR: Invalid input entered");
					}
					break;
				
				}
				
				if(input.contains("touch")) {
					try{ 
						String adjusted = input.substring(6);
						String adjustedM = adjusted.trim();
						bash.makeFile(adjustedM);
					}
					catch(FullDirectoryException error) {
						System.out.println("ERROR: Present directory is full.");
					}
					catch(NotADirectoryException error) {
						System.out.println("ERROR: Cannot change directory into a file.");
					}
					
					catch(IllegalArgumentException error) {
						System.out.println("Illegal Argument Entered");
					}
					catch(StringIndexOutOfBoundsException error) {
						System.out.println("ERROR: Invalid input entered");
					}
					break;
					
					
				}
				
				if (input.contains("find")) {
				try {
					String adjusted = input.substring(5);
					String adjustedM = adjusted.trim();
					bash.setfind(adjustedM, bash.root, "");
					if (bash.getFound()) {
						bash.find(adjustedM, bash.root, "");
						bash.setFound(false); 
					}
					else {
						System.out.println("ERROR: No such file exists.");
					}
				}
					catch (StringIndexOutOfBoundsException error) {
						System.out.println("ERROR: Invalid input entered");
					}
					break;
				}
				
				if(input.contains("mv")) {
				try {
					String adjusted = input.substring(3);
					String adjustedM = adjusted.trim();
					bash.moveCommand(adjustedM);
				}
				catch(NotADirectoryException error) {
					System.out.println("ERROR: Cannot change directory into a file.");
				}
				catch(FullDirectoryException error) {
					System.out.println("ERROR: Present directory is full.");
				}
				catch(IllegalArgumentException error) {
					System.out.println("ERROR: Invalid input entered");
				}
				catch(StringIndexOutOfBoundsException error) {
					System.out.println("ERROR: Invalid input entered");
				}
					break;
				}
				
				else {
					System.out.println("Invalid command");
					break;
				}
				
			}
		}
			stdin.close(); 
	}
}
