/*
 * Appending to a OutputStream will make a duplicate header and reading from the file will fail.
 * To be able to write one at a time to a ObjectOutputStream, we need to override writeStreamHeader
 * and call reset.
 */
package lindacare.com.helper;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendingObjectOutputStream extends ObjectOutputStream {

	  public AppendingObjectOutputStream(OutputStream out) throws IOException {
	    super(out);
	  }

	  @Override
	  protected void writeStreamHeader() throws IOException {
	    reset();
	  }

}