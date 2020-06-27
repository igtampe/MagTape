import magtape.magtape;
import magtape.thunk;
public class magtapeTest {

	public static void main(String[] args) throws thunk {
		
		//OK time to get a magtape of string.
		magtape<String> Tape1 = new magtape<String>(true);
		System.out.println(Tape1.getLength());
		System.out.println(Tape1.isEmpty());
		
		Tape1.add("Bonjour");
		Tape1.add("Hello");
		Tape1.add("Hola");
		Tape1.add("Konichiwa");
		Tape1.add("Gutten Tag");
		Tape1.add("Salue");
		
		System.out.println(Tape1.getCurrent());
		
		printTape(Tape1);
		
		Tape1.remove(3); 
		
		printTape(Tape1);
		
		Tape1.spliceIn("Nihao");
		
		printTape(Tape1);
		
		Tape1.remove("Hello");
		Tape1.spliceIn("Good Morning");
		printTape(Tape1);
		
		Tape1.add(0,"Bonjiorno");
		printTape(Tape1);
		
		Tape1.find("Hola");
		Tape1.setCurrent("Buenos Dias");
		
		printTape(Tape1);
		
		Tape1.set(Tape1.getLength()-1, "Bonum Manne");
		
		printTape(Tape1);
		
		System.out.println(Tape1.get(0));

		System.out.println(Tape1.getCurrentPos());
		
		System.out.println(Tape1.isEmpty());
		
		System.out.println(Tape1.find("Woopsie"));
		System.out.println(Tape1.remove("Woopsie"));
		
		Tape1.moveToPos(3);
		
		try{while(true) {Tape1.moveForward();}} catch(thunk e) {}
		try {Tape1.setCurrent("Hola");} catch(Exception e) {System.out.println(e.getMessage());}
		try{while(true) {Tape1.moveBack();}} catch(thunk e) {}
		try {Tape1.setCurrent("Hola");} catch(Exception e) {System.out.println(e.getMessage());}
		
	}
	
	public static void printTape(magtape<String> Tape) {
		System.out.println("");
		System.out.print("{");
		for (String string : Tape) {System.out.print(string+",");}
		System.out.println("}");
		System.out.println("");
	}
	
}
