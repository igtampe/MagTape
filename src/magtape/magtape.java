package magtape;
import java.util.Iterator;
import magtape.thunk.thunktype;

/**
 * Holds data almost like a magtape. Just made for some fun, with some cute little names.
 * its basically a doubly linked list, that's easier to itterate through without an itterator.
 * 
 * Perhaps it could be a neat way for someone to introduce linkedlists to their students. IDK.
 * @author igtampe
 */
public class magtape<E> implements Iterable<E>{
	
	//---------------------[Internal Classes]----------------
	
	/***
	 * Holds data in the chain. Essentially a node.
	 * @author igtampe
	 */
	private class tapebit {
		
		private E value;
		private tapebit next;
		private tapebit prev;
		
		public tapebit(E value) {this.value=value;}
		public tapebit(E value, tapebit prev, tapebit next) {
			this.value=value;
			this.prev=prev;
			this.next=next;
		}
		
		public E getValue() {return value;}
		public void setValue(E value) {this.value = value;}
		public tapebit getNext() {return next;}
		public void setNext(tapebit next) {this.next = next;}
		public tapebit getPrev() {return prev;}
		public void setPrev(tapebit prev) {this.prev = prev;}
		
	}
	public class tapeiterator implements Iterator<E>{
		
		private tapebit currentiteratorbit;
		public tapeiterator() {currentiteratorbit = head;}
		
		//if the next one isn't the footer, we have a next one.
		public boolean hasNext() {return currentiteratorbit.next!=tail;}

		public E next() {
			//move forward one position
			currentiteratorbit=currentiteratorbit.next;
			
			//return the value of the next one.
			return currentiteratorbit.getValue();
		}
		
	}
	
	//---------------------[Variables]----------------
	
	/***
	 * Dummy nodes at the beginning and end.
	 * these will be used by the itterator, and the rewind/FFToEnd functions
	 */
	private tapebit head;
	private tapebit tail;
	
	/**
	 * length of the tape in tapebits.
	 */
	private int length;
	
	/***
	 * Current tapebit being accessed.
	 */
	private tapebit current;
	private int currentpos;
	
	/**
	 * Specifies wether or not to make sysout "sounds" when moving about the tape.
	 */
	private boolean verboseMode=false;
	
	//---------------------[Constructor]----------------
	
	public magtape() {
		
		//Create the header and footer
		head=new tapebit(null);
		tail=new tapebit(null);
		
		//remember to link the header and footer.
		head.setNext(tail);
		tail.setPrev(head);
		
		//This tape is currently empty, so the length is 0.
		length=0;
		
		//Rewind sets the current position back to where it needs to be.
		rewind();
	}
	
	public magtape(boolean Verbose) {
		this();
		verboseMode=Verbose;
	}
	
	//---------------------[Element Interaction]----------------
	
	/**
	 * Adds the specified value to the end of the tape.
	 * Will move the tape to the new value.
	 * @param value
	 */
	public void add(E value) {
		//Since we have to add it at the end, let's fast forward to it.
		FFToEnd();
		
		//Now the currently selected bit is the footer. However, we have to move back once, since we cannot splice in a value ahead of the footer.
		try {moveBack();} catch (Exception e) {} //Honestly there's no way for this exception to occur, as moving back from the footer will always at worse move us back to the header. This is handle well enough for me.
		
		//Now we're ready to splice in the value
		spliceIn(value);
		
	}
	
	/**
	 * Adds the specified value to the tape at the specified position.
	 * Will move the tape to the new value.
	 * @param pos
	 * @param value
	 * @throws thunk if on our way to the position, we hit either end of the tape.
	 */
	public void add(int pos, E value) throws thunk{
		moveToPos(pos-1); //get to the position right behind to the one we want to add in.
		
		//now we're ready to splice in the value
		spliceIn(value);
	}
	
	/**
	 * Removes the specified value, by first searching for it, and then removing it.
	 * The search procedure involves rewinding the tape, scouring through it, and then removing it when it finds it.
	 * 
	 * Leaves the tape at the position before where the element was.
	 * 
	 * @param value
	 * @return True if it managed to find the value and remove it, false otherwise.
	 */
	public boolean remove(E value) {
		//if we can find the value, remove it, and return true
		if(find(value)) {spliceOut(); return true;}
		
		//else return false.
		return false;
	}
	
	/**
	 * Removes the data at that position. Leaves the tape at the position behind it.
	 * @param pos
	 * @throws thunk if on our way to the position, we hit either end of the tape.
	 */
	public void remove(int pos) throws thunk{
		//get to the position
		moveToPos(pos);
		
		//now splice out
		spliceOut();
	}

	/**
	 * Finds the first instance of the specified value in the tape, and leaves the tape at the position of the element.
	 * If it does not find it, the tape will be left at its end.
	 * @param value 
	 * @return True if it found the value, false otherwise.
	 */
	public boolean find(E value) {
		//Rewind the tape
		rewind();
		
		//While we haven't found it, try to move forward, and if we thunk, return false.
		while(!value.equals(current.getValue())) {try {moveForward();} catch (thunk e) {return false;}}
		
		//if we made it this far, it means we haven't thunk-ed, and we found it. Return true.
		return true;
	}
	
	/**
	 * Function to splice in a value in front of the current value.
	 * @param newbitVal the value of the bit you want to insert between the current bit, and the bit that follows it.
	 */
	public void spliceIn(E newbitVal) {
		
		//Essentially we're going to "cut" the magtape, and "splice" in a new bit of tape with the data we want.
		if(verboseMode) {System.out.print("Snip-");}
		
		//Create a new bit which will be between prevbit and prevbit's next bit.
		tapebit newbit = new tapebit(newbitVal,current,current.getNext());
		
		//link newbit's surrounding entries to itself.
		newbit.getPrev().setNext(newbit);
		newbit.getNext().setPrev(newbit);
		
		//Now let's update current
		current=newbit;
		currentpos++;
		
		//lastly let's update length.
		length++;
	}
	
	/**
	 * Function to splice out the current value.
	 */
	public void spliceOut() {
		
		//essentially, we're going to "cut" out the current bit, and "splice" the surorunding bits together.
		if(verboseMode) {System.out.print("Snip-");}
		
		//Connect the bit's surrounding bits together.
		current.getPrev().setNext(current.getNext()); //Set Current's previous bit's next entry to Current's next.
		current.getNext().setPrev(current.getPrev()); //Set Current's next bit's previous entry to Current's previous.
		
		//The Current bit has essentially been "cut" out of the magtape. Now all we have to do is clear it.
		current.value=null;
		
		//now all we have to do is update it.
		current=current.getPrev(); //move our position back.
		currentpos--;
		
		//lastly, update length.
		length--;
		
	}
	
	//---------------------[Geters/setters]----------------
	
	public boolean isEmpty() {return length==0;}
	public int getLength() {return length;}
	
	/**
	 * Gets the value of the current tapebit
	 * @return the value of the current tapebit
	 */
	public E getCurrent() {return current.getValue();}
	
	/**
	 * sets the current node's value 
	 * @param value
	 */
	public void setCurrent(E value) {
		if(current==head||current==tail) {throw new IllegalStateException("This magtape is in a position that cannot be modified.");}
		current.setValue(value);
	}
	
	/**
	 * gets the value of the current position of the magtape
	 * @return the current position of the magtape.
	 */
	public int getCurrentPos() {return currentpos;}
	
	/**
	 * Moves the tape to the specified position, then reads the data at that position.
	 * @param pos Position of the data you want to read
	 * @return the data at that position.
	 * @throws thunk if in getting to that position, we hit either end of the tape.
	 */
	public E get(int pos) throws thunk {
		moveToPos(pos); //move to that position
		return getCurrent(); //now that we're in that position, return the current tapebit's value.
	}
	
	/**
	 * Moves the tape to the specified position, then sets the data at that position.
	 * @param pos Position of the data to replace
	 * @param value The data that will replace the data that is currently at that position
	 * @throws thunk if in getting to that position, we hit either end of the tape.
	 */
	public void set(int pos, E value) throws thunk{
		moveToPos(pos); //move to the position
		setCurrent(value); //now that we're in that position, set current's to the value.
	}	
	
	//---------------------[Tape Movement Functions]----------------
	
	/**
	 * moves the tape to the specified position
	 * @throws thunk Tape shouldn't thunk due to our checks before moving forward or backward, but just in case, this is here.
	 */
	public void moveToPos(int pos) throws thunk {
		//if(pos>length || pos<-1) {throw new IndexOutOfBoundsException();} 
		
		//the tape should probably thunk instead of just doing this. That way at least we have a reason to have this thing throw it. Plus, it lets the user know of the tape's position beign at either end.
		//also, this would waste a little time, but this DS should probably not be used for anything other than demonstrational purposes. I doubt anyone would use it professionally.
		
		//bueno if someone does, then I guess remove that check.
		
		//these checks could be removed, but they save time.
		if(pos==length) {FFToEnd(); return;}
		if(pos==-1) {rewind(); return;}
		
		//decide whether we need to rewind or fast-forward to get to the current position
		if(currentpos>pos) {while (currentpos!=pos) {moveBack();}}
		if(currentpos<pos) {while (currentpos!=pos) {moveForward();}}
		
		//if neither of these were true then nice we're already at that position we don't have to do anything.
	}
	
	/**
	 * move the tape forward one position.
	 * @throws thunk if we reached the end of the tape. As in, you cannot move any more forward.
	 */
	public void moveForward() throws thunk {
		if(verboseMode) {System.out.print("Tuck"+currentpos+"+-");}
		if(current==tail) {throw new thunk(thunktype.front);}
		current=current.getNext();
		currentpos++;
	}
	
	/**
	 * Move the tape back one position.
	 * @throws thunk if we reached the beginning of the tape. As in, you cannot move any more back.
	 */
	public void moveBack() throws thunk {
		if(verboseMode) {System.out.print("Tuck"+currentpos+"--");}
		if(current==head) {throw new thunk(thunktype.back);}
		current=current.getPrev();
		currentpos--;
	}
	
	/**
	 * "Fast-Forwards" the tape to the end. Essentially sets the current position to the footer.
	 */
	public void FFToEnd() {
		if(verboseMode) {System.out.print("Whirr+-");}
		current=tail;
		currentpos=length; //length also represents the position of the footer. We'll set it to that. 
	}
	
	/**
	 * "rewinds" the tape to its beginning. Moves the tape's current position back to the header.
	 */
	public void rewind() {
		if(verboseMode) {System.out.print("Whirr--");}
		current=head; //The current tape will be at the header.
		currentpos=-1; //use negative one, because the header is behind the first entry in this tape, which is entry 0.
	}

	//---------------------[Other]----------------
	
	/**
	 * returns a new tapeIterator
	 */
	public Iterator<E> iterator() {return new tapeiterator();}	
}
