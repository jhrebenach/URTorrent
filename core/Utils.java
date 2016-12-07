package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import ttorrent_stuff.BEValue;
import ttorrent_stuff.BEncoder;

public class Utils {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		createMetaInfoFile("/Users/Herbie/Desktop/temp.txt", "http://127.0.0.1:6969/announce");
		// createMetaInfoFile("/Users/Herbie/Desktop/volcano.jpg", "http://127.0.0.1:6969/announce");
	}
	
	public static void addByteArrToByteArrList(ArrayList<Byte> al, byte[] bytes) {
		for(byte b:bytes) {
			al.add(b);
		}
	}
	
	public static byte[] byteArrToByteArr(ArrayList<Byte> al) {
		byte[] toReturn = new byte[al.size()];
		for(int i = 0; i < al.size(); i++) {
			toReturn[i] = al.get(i).byteValue();
		}
		return toReturn;
	}
	
	public static void createMetaInfoFile(String pathToFile, String announce) throws IOException, NoSuchAlgorithmException {
		
		File file;
		Path filePath;
		String fileName; //==name
		int fileSize; //==length (in bytes)
		// TODO: is this okay for the piece length?
		// int defaultPieceLength = 512 * 1024; // (512 kB)
		int defaultPieceLength = 32768;
		
		Path path = Paths.get(pathToFile);
		if(Files.exists(path)) {
			file = path.toFile();
			filePath = path.getParent();
			fileName = path.getFileName().toString();
			fileSize = (int) file.length();
			int numberOfPieces = (int) Math.ceil((double) fileSize / (double) defaultPieceLength);
			
			byte[] fileBytes = Files.readAllBytes(path);
			
			int pieceArrIndex = 0;
			ArrayList<Byte> piecesBytes = new ArrayList<Byte>();
			for(int i = 0; i < numberOfPieces; i++) {
				
				int currentFileBytesLength = defaultPieceLength;
				if(i == (numberOfPieces-1) ) {
					currentFileBytesLength = fileSize - i*defaultPieceLength;
				} 
				
				byte[] currentFileBytes = new byte[currentFileBytesLength];
				System.arraycopy(fileBytes, pieceArrIndex, currentFileBytes, 0, currentFileBytesLength);
				MessageDigest messageDigest;
				messageDigest = MessageDigest.getInstance("SHA-1");
				messageDigest.reset();
				messageDigest.update(currentFileBytes);
				
				addByteArrToByteArrList(piecesBytes, messageDigest.digest());
				pieceArrIndex += defaultPieceLength;
			}
			
			Map<String, BEValue> info = new LinkedHashMap<String, BEValue>();
			info.put("length", new BEValue(fileSize));
			info.put("name", new BEValue(fileName));
			info.put("piece length", new BEValue(defaultPieceLength));
			info.put("pieces", new BEValue(byteArrToByteArr(piecesBytes)));
			
			Map<String, Object> metainfo = new LinkedHashMap<String, Object>();
			metainfo.put("announce", announce);
			metainfo.put("creation date", System.currentTimeMillis()); // TODO: fix (3 extra decimal places present)
			metainfo.put("info", info);
			
			File metainfoFile = new File(filePath + "/" + fileName + ".torrent");
			metainfoFile.createNewFile();
			OutputStream BEncOutputStream = new FileOutputStream(metainfoFile);
			BEncoder.bencode(metainfo, BEncOutputStream);
			BEncOutputStream.close();
			
		} else{
			throw new FileNotFoundException("pathToFile");
		}
	}
	
}
