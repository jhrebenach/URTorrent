package ttorrent_stuff;

import java.util.ArrayList;
import java.util.Arrays;

public class TTorrent_Test {
	
	public static void main(String[] args) {
		/////////////////////////////
		// Example Encodes/Decodes //
		/////////////////////////////
		
		String hw = "hello world!";
		
		// String
		String hwString = hw;
		
		// byte[]
		byte[] hwByteArr = hw.getBytes();
		
		// Number
		Number hwNumber = 6969;
		
		// List
		String[] hwStringArr = new String[] {"hello", "world", "?"};
		ArrayList<String> hwArrList = new ArrayList<String>(Arrays.asList(hwStringArr));
		// hwArrList.add(e)
		
		// Map
		
		// Other (not accepted)
		
	}
	
}
