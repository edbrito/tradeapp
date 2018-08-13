package lindacare.com.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import lindacare.com.Trading.Trade;
import lindacare.com.helper.AppendingObjectOutputStream;

public class DAOManager {

	private static String fileName = "tradesFile.dat";
	
	public static void setFile(String dest){
		fileName = dest;
	}
	
	public static boolean saveTradeObject(Trade t){
		try{
			//Tries to open and write to the file the trade sent as parameter.
			File fn = new File(fileName);
			boolean exists = fn.exists();
			FileOutputStream f = new FileOutputStream(fn, true);
			ObjectOutputStream out;
			//If the file already existed, we need to append without an header;
			//Otherwise, just write normally with ObjectOuputStream.
			//Normally we cannot append to ObjectOutputStreams as it will generate an exception
			//caused by an unexpected header.
			if(exists)
				out = new AppendingObjectOutputStream(f);
			else
				out = new ObjectOutputStream(f);
			out.writeObject(t);
			out.flush();
			out.close();
			f.close();
			return true;
		}catch(IOException ioe){
			return false;	
		}
	}
	
	public static ArrayList<Trade> loadSavedTrades(){
		ArrayList<Trade> collection = new ArrayList<Trade>();
		ObjectInputStream o=null;
		FileInputStream f=null;
		//Tries to read the file where the Trade objects are stored.
		//If it fails mid-file, it returns the Trades it was able to read
		//up until that point.
		try{
			f = new FileInputStream(new File(fileName));
			o = new ObjectInputStream(f);
			Trade t;
			do{
				t = (Trade) o.readObject();
				if(t!=null)
					collection.add(t);
			}while(t!=null);
		}catch(IOException | ClassNotFoundException ioe){
			System.err.println(ioe.getMessage());
		}finally{
			try{
				if(o!=null) o.close();
				if(f!=null) f.close();
			}catch(Exception e){ System.err.println(e.getMessage()); }
		}
		//Returns the Trade objects it was able to read. Returns an empty list if the file was
		//not found.
		//It will always return a non-null ArrayList even if it is empty.
		return collection;
	}
}
