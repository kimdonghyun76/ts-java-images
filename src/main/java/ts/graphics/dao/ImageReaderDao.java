package ts.graphics.dao;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageReaderDao {

	public BufferedImage getImage(String filePath) throws IOException;
	
	public BufferedImage getImageByNio(String filePath) throws IOException;
	
	public BufferedImage getImageByBuffered(String filePath) throws IOException;
}
