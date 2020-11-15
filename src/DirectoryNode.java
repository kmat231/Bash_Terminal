//name: Kevin Mathew
//id number: 112167040
//recitation: 02

/**
 * DirectoryNode Class to insert new node Objects
 * @author Kevin
 *
 */
public class DirectoryNode {
	public String name;
	public DirectoryNode parent; 
	public DirectoryNode[] children;
	public Boolean isFile = false; 
	
	/**
	 * Constructor class, initializes children to an array of 10 objects
	 */
	public DirectoryNode () {
		this.isFile = false; 
		this.children = new DirectoryNode[10];
	}
	/**
	 * 
	 * @return
	 * returns the name of the node
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name
	 * sets the name of the node
	 */
	public void setName(String name) {
		this.name = name; 
	}
	
	/**
	 * 
	 * @param parent
	 * sets the parent node of the node
	 */
	public void setParent(DirectoryNode parent) {
		this.parent = parent;
	}
	/**
	 * 
	 * @return
	 * returns the parent of the node
	 */
	public DirectoryNode getParent() {
		return this.parent; 
	}
	
	/**
	 * 
	 * @param isFile
	 * sets whether the node is a file or not
	 */
	public void setFile(Boolean isFile) {
		this.isFile = isFile;
	}
	
	/**
	 * 
	 * @return
	 * return if the node is a file or not
	 */
	public Boolean getFile() {
		return this.isFile;
	}
	
	/**
	 * 
	 * @return
	 * returns the children of the node
	 */
	public DirectoryNode[] getChildren() {
		return this.children;
	}

	/**
	 * 
	 * @param node
	 * node that is being added as a child to calling node
	 * @throws NotADirectoryException
	 * throw exception if you are attempting to add a child to a file
	 * @throws FullDirectoryException
	 * throws exception of calling node has no available child
	 */
	public void addChild(DirectoryNode node) throws NotADirectoryException, FullDirectoryException {
		boolean fullDirectory = true; 
		int index = 0; 
		if (this.isFile) {
			throw new NotADirectoryException();
		}
		
		for(int i = 0; i < this.getChildren().length; i++) {
			if(this.getChildren()[i] == null) {
				fullDirectory = false;
				index = i;
				break;
			}
		}
		
		if (fullDirectory) {
			throw new FullDirectoryException();
		}
		else { //add a child
			this.children[index] = node;
		}
	}
	

}
