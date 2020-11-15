//name: Kevin Mathew
//id number: 112167040
//recitation: 02

/**
 * Class for DirectoryNode that runs the bash terminal
 * @author Kevin
 *
 */
public class DirectoryTree {
	public DirectoryNode root;
	public DirectoryNode cursor;
	public boolean isFound = false;
	
	public DirectoryTree() {
		this.root = new DirectoryNode(); 
		this.root.setName("root");
		this.root.setFile(false);
		this.root.setParent(null);
		this.cursor = this.root;
	}
	
	/**
	 * 
	 * @return
	 * if the directory is found
	 */
	public boolean getFound() {
		return this.isFound;
	}
	
	/**
	 *
	 * @param isFound
	 * setter method for if a directory is found
	 */
	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}
	
	/**
	 * method to reset the cursor when using cd /
	 */
	public void resetCursor() {
		this.cursor = this.root; 
	}
	
	/**
	 * 
	 * @param name
	 *  name of the directory you are attempting to change the cursor to
	 * @throws NotADirectoryException
	 * if destination is a file throw this error
	 */
	public void changeDirectory(String name) throws NotADirectoryException {
		DirectoryNode newCursor = new DirectoryNode();
		String [] values = name.split("/");
		boolean directoryFound = false;
		int correctVals = 0;
		
		if (values[0].isEmpty()) {
			throw new StringIndexOutOfBoundsException();
		}
		if (values.length == 1) { //cd [children] of node
			newCursor = this.cursor;	
			for(int i = 0; i < newCursor.getChildren().length; i++) {
				if(newCursor.getChildren()[i] != null) {
					if(newCursor.getChildren()[i].isFile == false) {
						if(newCursor.getChildren()[i].getName().equals(values[0])) {
							this.cursor = newCursor.getChildren()[i];
							directoryFound = true;
							break;
						}
					}
					else if (newCursor.getChildren()[i].isFile == true) {
						if(newCursor.getChildren()[i].getName().equals(values[0])) {
							throw new NotADirectoryException();
						}
					}
				}		
			}
		}
		
		else {				//cd [direct destination] 
			boolean atRoot = true;
			if (values[0].equals("root")) {//absolute path
				newCursor = this.root;
			}
			else {
				newCursor = this.cursor;
				atRoot = false; 			//relative path
			}
			int val = values.length - 1;
			for(int i = 0; i < values.length; i++) {
				for(int j = 0; j < newCursor.getChildren().length; j++) {
					if(newCursor.getChildren()[j] != null) {
						if(newCursor.getChildren()[j].isFile == false) {
							if(newCursor.getChildren()[j].getName().equals(values[i])) {
								if(newCursor == this.root && atRoot) {
									correctVals++;
								}
									correctVals++;
									newCursor = newCursor.getChildren()[j];
									break;
							}
						}
						else if (newCursor.getChildren()[j].isFile == true) {
							if(newCursor.getChildren()[j].getName().equals(values[val])) {
								throw new NotADirectoryException();
							}
						}
					}
				}
			}
			if(correctVals == values.length) {
				directoryFound = true;
				this.cursor = newCursor;
			}
		}
		
		if(directoryFound == false) {
			if(values.length == 1) {
				System.out.println("ERROR: No such directory named '" +values[0] + "'." );
			}
			else {
				System.out.println("ERROR: No such directory named '" + values[values.length-1] + "'");
			}
		}
	}
	
	/**
	 * 
	 * @return
	 * returns the location of the cursor 
	 */
	public String presentWorkingDirectory() {
		DirectoryNode originalCursor = this.cursor;
		String result = "";
		String resultArr[] = new String[100]; 
		int index = 0;
	
		while(originalCursor.getParent() != null) {
				resultArr[index] = originalCursor.getName();
				originalCursor = originalCursor.getParent();
				index++;
		}	
		
		if (originalCursor.getParent() == null)  {//if at root
			resultArr[index] = originalCursor.getName();
		}
		
		for (int i = index; i >= 0; i--) {
			if(i == 0) {
				result += resultArr[i];
			}
			else {
				result += resultArr[i] + "/";
			}
		}
				return result;
	}
	
	/**
	 * 
	 * @return
	 * return a String representation of the all the direct children of the cursor node
	 */
	public String listDirectory() {
		DirectoryNode originalCursor = this.cursor;
		String result = "";
		for(int i = 0; i < originalCursor.getChildren().length; i++) {
			if (originalCursor.getChildren()[i] != null) {
				result += originalCursor.getChildren()[i].getName() + " ";
			}
		}
		if (result.equals("")) {
			result += "Directory has no children";
		}
		return result; 
	}
	
	/**
	 * 
	 * @param name
	 * name of the directory 
	 * @throws FullDirectoryException
	 * throws exception when cursor point has 10 children already
	 * @throws IllegalArgumentException
	 * throws exception when illegal argument is entered
	 * @throws NotADirectoryException
	 * throws not a directory if you're attempting to pass a child to a file
	 */
	public void makeDirectory(String name) throws FullDirectoryException, IllegalArgumentException, NotADirectoryException {
		String [] values = name.split(" ");
		if (values.length > 1 || values[0].isEmpty() || values[0].contains("/")) {
			throw new IllegalArgumentException();
		}
		DirectoryNode originalCursor = this.cursor;
		DirectoryNode newDirectory = new DirectoryNode();
		newDirectory.setName(values[0]);
		newDirectory.setFile(false);
		newDirectory.setParent(originalCursor);
		originalCursor.addChild(newDirectory);
	}
	
	/**
	 * 
	 * @param name
	 * name of the file you are making
	 * @throws FullDirectoryException
	 * throws exception when cursor point has 10 children already
	 * @throws IllegalArgumentException
	 * throws exception when illegal argument is entered
	 * @throws NotADirectoryException
	 * throws not a directory if you're attempting to pass a child to a file
	 */
	public void makeFile(String name) throws FullDirectoryException, IllegalArgumentException, NotADirectoryException {
		String [] values = name.split(" ");
		if (values.length > 1 || values[0].isEmpty() || values[0].contains("/")) {
			throw new IllegalArgumentException();
		}
		DirectoryNode originalCursor = this.cursor;
		DirectoryNode newDirectory = new DirectoryNode();
		newDirectory.setName(values[0]);
		newDirectory.setFile(true);
		newDirectory.setParent(originalCursor);
		originalCursor.addChild(newDirectory);
	}
	
	/**
	 * 
	 * @param originalCursor
	 * parameter to show where the cursor is located
	 * @param depth
	 * depth of the each node, starts at a depth of 0
	 */
	public void printDirectoryTree(DirectoryNode originalCursor, int depth) {
		String indents = "";
		for (int i = 0; i < depth; i++) {
			indents += "    ";
		}
		
		if(originalCursor.isFile == true) {
			System.out.println(indents + "-" + originalCursor.getName());
			return;
		}
		
		if(this.childrenCounter(originalCursor) == 0) {
			System.out.println(indents + "|-" + originalCursor.getName());
			return;
		}
		
		System.out.println(indents + "|-" + originalCursor.getName());
		for(int i = 0; i < this.childrenCounter(originalCursor); i++) {
			if(originalCursor.getChildren()[i] != null) {		
				printDirectoryTree(originalCursor.getChildren()[i], (depth + 1));
			}
			else {
				System.out.println(indents + "-" + originalCursor.getName());
			}
		}
			
}
	/**
	 * 
	 * @param originalCursor
	 * pass the position of the cursor
	 * @return
	 * returns the amount of children that cursor point has
	 */
	public int childrenCounter(DirectoryNode originalCursor) {
		int counter = 0;
		for(int i = 0; i < originalCursor.getChildren().length; i++) {
			if(originalCursor.getChildren()[i] != null) {
				counter++; 
			}
		}
			return counter;
	}
	/**
	 * 
	 * @param name
	 * name of the directory/file you are trying to find
	 * @param originalCursor
	 * setting the cursor to the root
	 * @param result
	 * return the result of the find
	 */
	public void setfind(String name, DirectoryNode originalCursor, String result) {
		if(originalCursor.getName().equals(name)) {
			this.setFound(true);
		}
		for(int i = 0; i < this.childrenCounter(originalCursor); i++) {
				setfind(name, originalCursor.getChildren()[i], result + "/" + originalCursor.getChildren()[i].getName());		
		}		
	}
	/**
	 * 
	 * @param name
	 * name of the directory/file you are trying to find
	 * @param originalCursor
	 * setting the cursor to the root
	 * @param result
	 * return the result of the find
	 */
	public void find(String name, DirectoryNode originalCursor, String result) {
		if(originalCursor.getName().equals(name)) {
			System.out.println(this.root.getName() + result);
		}
		for(int i = 0; i < this.childrenCounter(originalCursor); i++) {
				find(name, originalCursor.getChildren()[i], result + "/" + originalCursor.getChildren()[i].getName());		
		}		
	}
	
	/**
	 * 
	 * @param value
	 * the 2 target and destination sources to move
	 * @throws NotADirectoryException
	 * if you are attempting to move to a file throw exception
	 * @throws FullDirectoryException
	 * if that directory, has too many children throw exception
	 */
	public void moveCommand(String value) throws NotADirectoryException, FullDirectoryException {
		DirectoryNode newNode = new DirectoryNode(); 
		String [] destination = value.split(" ");
		if(destination.length != 2) {
			System.out.println("Invalid input entered");
			return; 
		}
		String [] firstLocation = destination[0].split("/"); 
		String [] secondLocation = destination[1].split("/");
		
		DirectoryNode newCursor = this.root;  
		int correctVals = 0;
		for(int i = 0; i < firstLocation.length; i++) {
			for(int j = 0; j < newCursor.getChildren().length; j++) {
				if(newCursor.getChildren()[j] != null) {
						if(newCursor.getChildren()[j].getName().equals(firstLocation[i])) {
							if (newCursor == this.root) {
								correctVals++;
							}
							correctVals++;
							newCursor = newCursor.getChildren()[j];
							break;
								}
							}
						}
					}
		if(correctVals == firstLocation.length) {
			newNode = newCursor;
			boolean looped = true;
			for(int i = 0; i < newCursor.getParent().getChildren().length; i++) {
				if(newCursor.getParent().getChildren()[i] != null) {
					if(newCursor.getParent().getChildren()[i] == newCursor) {
						looped = false;
						newCursor.getParent().getChildren()[i] = null;
						break;
					}
				}
			}
			if(newCursor != null && looped) {
				newCursor = null;
			}
				correctVals = 0; 
		}
		else {
			System.out.println("Source Directory was not found");
			return;
		}
		
		DirectoryNode newCursor2 = this.root; 
		for(int i = 0; i < secondLocation.length; i++) {
			for(int j = 0; j < newCursor2.getChildren().length; j++) {
				if(newCursor2.getChildren()[j] != null) {
						if(newCursor2.getChildren()[j].getName().equals(secondLocation[i])) {
							if (newCursor2 == this.root) {
								correctVals++;
							}
							correctVals++;
							newCursor2 = newCursor2.getChildren()[j];
							break;
						}
					}
				}
			}
		
		if(correctVals == secondLocation.length) {
			if(newCursor2.isFile == true) {
				newCursor2.addChild(newNode);
				newCursor = newNode; 
			}
			else {
				newCursor2.addChild(newNode);
			}
		}
		else {
			System.out.println("Target Directory was not found");
			return;
		}
	
	}
}


