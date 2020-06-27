package magtape;

/***
 * An exception named after the sound a tape makes when it hits either end. 
 * This class is to make sure that any program can identify that the exception is a type of thunk.
 * @author igtampe
 */
public class thunk extends Exception{
	private static final long serialVersionUID = -1486875433190965066L;

	public static enum thunktype{front,back}
	
	/**
	 * Creates a thunk exception. Also sysouts the sound "THUNK!"
	 * @param type type of thunk being made (either the front/end of the tape, or the back/beginning of the tap)
	 */
	public thunk(thunktype type) {
		super("You've reached the " + thunktypeToString(type) + "of this tape!");
		System.out.println("THUNK!");
	}
	
	public static String thunktypeToString(thunktype type) {
		switch (type) {
		case front:
			return "end ";
		case back:
			return "beginning ";
		default:
			return "";
		}
	}
	
}


