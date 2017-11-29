package ts.graphics.dao;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public interface ImageWriterDao {

	public void writeImage(String filePath, BufferedImage image) throws IOException;
	
	public void writeImage(OutputStream outputStream, BufferedImage image) throws IOException;
}
