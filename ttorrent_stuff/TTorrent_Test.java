package ttorrent_stuff;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TTorrent_Test {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		/////////////////////////////
		// Example Encodes/Decodes //
		/////////////////////////////
		
		String hw = "hello world!"; 
		
		// String
		String hwString = hw; // produces - 12:hello world!
		
		// byte[]
		byte[] hwByteArr = hw.getBytes(); // produces - 12:hello world!
		
		// Number
		Number hwNumber = 6969; // produces - i6969e
		
		// BEValues (for nesting in Maps + Lists)
		BEValue helloBE = new BEValue("hello");
		BEValue spaceBE = new BEValue(" ");
		BEValue worldBE = new BEValue("world");
		BEValue exclamationBE = new BEValue("!");
		
		// List
		List<BEValue> hwList = new ArrayList<BEValue>(); // produces - l5:hello1: 5:world1:!e
		hwList.add(helloBE);
		hwList.add(spaceBE);
		hwList.add(worldBE);
		hwList.add(exclamationBE);
		
		// Map
		Map<String, BEValue> hwMap = new LinkedHashMap<String, BEValue>(); // produces - d1: 1: 1:!1:!5:hello5:hello5:world5:worlde
		hwMap.put("hello", helloBE);
		hwMap.put(" ", spaceBE);
		hwMap.put("world", worldBE);
		hwMap.put("!", exclamationBE);
		
		
		OutputStream BEncOutputStream = null;
		InputStream BDecInputStream = null;
		try {
			// Encode
			BEncOutputStream = new FileOutputStream("src/ttorrent_stuff/Output.txt");
			BEncoder.bencode(hwString, BEncOutputStream); // Bencode String
			// BEncoder.bencode(hwByteArr, BEncOutputStream); // Bencode Byte Array
			// BEncoder.bencode(hwNumber, BEncOutputStream); // Bencode Number
			// BEncoder.bencode(hwList, BEncOutputStream); // Bencode List
			// BEncoder.bencode(hwMap, BEncOutputStream); // Bencode Map
			BEncOutputStream.close();
			
			// Decode
			BDecInputStream = new FileInputStream("src/ttorrent_stuff/Output.txt");
			BEValue decoded = BDecoder.bdecode(BDecInputStream);
			System.out.println(decoded.getString()); // only use on type String 
			// System.out.println(decoded.getBytes()); // only use on type Bytes
			// System.out.println(decoded.getList()); // only use on type List
			// System.out.println(decoded.getMap()); // only use on type Map
			BDecInputStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("Done!");
		}
		
	}
	
}
