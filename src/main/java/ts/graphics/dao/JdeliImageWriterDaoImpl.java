package ts.graphics.dao;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Repository;

import com.idrsolutions.image.png.PngEncoder;

@Repository("jdeliWriterDao")
public class JdeliImageWriterDaoImpl implements ImageWriterDao {

	@Override
	public void writeImage(String filePath, BufferedImage image) throws IOException {
		BufferedOutputStream outputStream = null;
		try {
			outputStream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			PngEncoder encoder = new PngEncoder();
			
			encoder.setCompressed(true);
			encoder.write(image, outputStream);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(outputStream != null) outputStream.close();
		}
	}

	@Override
	public void writeImage(OutputStream outputStream, BufferedImage image) throws IOException {
		
		try {
			
			PngEncoder encoder = new PngEncoder();
			
			encoder.setCompressed(true);
			encoder.write(image, outputStream);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(outputStream != null) outputStream.close();

		}
	}

}
