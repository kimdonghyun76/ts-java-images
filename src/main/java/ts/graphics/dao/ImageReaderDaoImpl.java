package ts.graphics.dao;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Repository;

@Repository
public class ImageReaderDaoImpl implements ImageReaderDao {

	@Override
	public BufferedImage getImage(String filePath) throws IOException{
		BufferedImage image = null;	
		image = ImageIO.read(new File(filePath));
		return image;
	}

	@Override
	public BufferedImage getImageByNio(String filePath) throws IOException {
		
		BufferedImage image = null;
		
		Path path = Paths.get(filePath);
		ByteArrayInputStream bais  = null;
		
		try {
			if(Files.exists(path)) {
				byte[] imageBytes = Files.readAllBytes(path);
				bais = new ByteArrayInputStream(imageBytes);
				image = ImageIO.read(bais);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(bais != null) bais.close();
		}
		
		
		return image;
	}

	@Override
	public BufferedImage getImageByBuffered(String filePath) throws IOException {
		
		BufferedImage image = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		
		try {
			fis = new FileInputStream(new File(filePath));
			bis = new BufferedInputStream(fis);
			image = ImageIO.read(bis);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(bis != null) bis.close();
			if(fis != null) fis.close();
		}
		
		return image;
	}

	

}
