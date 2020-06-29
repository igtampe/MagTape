import java.util.Random;

import magtape.magtape;
import magtape.thunk;
public class magtapeTest {

	public static void main(String[] args) throws thunk {
		
		//OK time to get a magtape of string.
		magtape<String> Tape1 = new magtape<String>(true);
		System.out.println("Lenght="+Tape1.getLength());
		System.out.println("is Empty:" + Tape1.isEmpty());
		
		System.out.println("TEST ADD");
		
		Tape1.add("Bonjour");
		Tape1.add("Hello");
		Tape1.add("Hola");
		Tape1.add("Konichiwa");
		Tape1.add("Gutten Tag");
		Tape1.add("Salue");
		
		System.out.println("\n Current Data: " + Tape1.getCurrent());
		
		printTape(Tape1);
		
		System.out.println("Test remove 3:");
		Tape1.remove(3); 
		
		printTape(Tape1);
		
		System.out.println("Test Splice-IN at position" + Tape1.getCurrentPos());
		Tape1.spliceIn("Nihao");
		
		printTape(Tape1);
		
		System.out.println("Test remove Hello, splice in Good Morning");
		Tape1.remove("Hello");
		Tape1.spliceIn("Good Morning");
		printTape(Tape1);
		
		System.out.println("Test add at position 0, Bonjiorno");
		Tape1.add(0,"Bonjiorno");
		printTape(Tape1);
		
		System.out.println("Test find Hola, replace it with Buenos Dias");
		Tape1.find("Hola");
		Tape1.setCurrent("Buenos Dias");
		
		printTape(Tape1);
		
		System.out.println("Test Set last index");
		Tape1.set(Tape1.getLength()-1, "Bonum Manne");
		
		printTape(Tape1);
				
		System.out.println("Position 0: "+Tape1.get(0));

		System.out.println("Ensure tape is at position 0: "+Tape1.getCurrentPos());
		
		System.out.println("Ensure tape is not empty (Should return false): "+Tape1.isEmpty());
		
		System.out.println("Test find element that doesn't exist: ");
		System.out.println(Tape1.find("Woopsie"));
		System.out.println("Test Remove element that doesn't exist: ");
		System.out.println(Tape1.remove("Woopsie"));
		
		System.out.println("Move tape to position 1");
		Tape1.moveToPos(3);
		
		System.out.println("Test Thunk protection (End): ");
		try{while(true) {Tape1.moveForward();}} catch(thunk e) {}
		
		System.out.println("Test Footer Write-Prevent: ");
		try {Tape1.setCurrent("Hola");} catch(Exception e) {System.out.println(e.getMessage());}
		
		System.out.println("Test Thunk protection (Beginning): ");
		try{while(true) {Tape1.moveBack();}} catch(thunk e) {}
		
		System.out.println("Test Header Write-Prevent");
		try {Tape1.setCurrent("Hola");} catch(Exception e) {System.out.println(e.getMessage());}
		
		System.out.println("\n\n Now its time to test something that I just added in this revision wow que lindo");
		magtape<Integer> Tape2 = new magtape<Integer>(true);
		Random rand = new Random();
		
		System.out.println("Let's add a bunch of random numbers");
		while(Tape2.getLength()<30) {Tape2.add(rand.nextInt());}
		
		System.out.println("Now, the tape should be at its last position. Let me check: " + Tape2.getCurrentPos());
		System.out.println("Now, let's see what happens if I ask it to go to position 3");
		Tape2.moveToPos(3);
		
		System.out.println("We should've at least seen one whirr. Let's see if it happens.");
		
		System.out.println("OK! looks like it worked. Now, let's go all the way back to the end of the tape. Let's say... Position 20");
		Tape2.moveToPos(20);
		
		System.out.println("We should've at least seen one whirr. Let's see if it happens.");
		System.out.println("ok good to go with that. Now one last thing. If we ask it to move to position 27, for instance, it should trigger the fast forward.");
		System.out.println("It doesn't really make sense in a physical interpretation, but becuase this magical tape drive can move all the way to the end instantly, this is faster.");
		
		Tape2.moveToPos(27);
		
		System.out.println("\n\n OK! Good! \n\n Actually thinking about it, this probably does make sense in a physical interpretation. Moving one by one would take confirming that you actually moved one spot"
				+ "\n while fast-forwarding would probably be able to be done a lot faster since it stops once the tape hits the edge, or just before a real life THUNK! moment. Huh.");
		
		System.out.println("\n\n Anyways this has been my cosito thanks for watching adiositooooooo");
		
		
		
	}
	
	
	
	public static void printTape(magtape<String> Tape) {
		System.out.println("");
		System.out.println("Tape of length " + Tape.getLength());
		System.out.println("");
		System.out.print("{");
		for (String string : Tape) {System.out.print(string+",");}
		System.out.println("}");
		System.out.println("");
	}
	
}
