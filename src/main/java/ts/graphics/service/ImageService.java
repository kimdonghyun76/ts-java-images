package ts.graphics.service;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ts.graphics.dao.ImageReaderDao;
import ts.graphics.dao.ImageWriterDao;
import ts.graphics.type.ImageReaderType;
import ts.graphics.type.ImageWriterType;

@Service
public class ImageService {
	
	@Autowired
	private ImageReaderDao imageReaderDao;
	@Autowired
	private ImageWriterDao imageIOWriterDao;
	@Autowired
	private ImageWriterDao jdeliWriterDao;
	@Autowired
	private ImageWriterDao objectPlanetWriterDao;
	
	public BufferedImage getImage(ImageReaderType readerType, String filePath) {
		
		BufferedImage image = null;
		
		try {
			if(readerType.equals(ImageReaderType.NORMAL)) {
				image = imageReaderDao.getImage(filePath);
			}else if(readerType.equals(ImageReaderType.NIO)) {
				image = imageReaderDao.getImageByNio(filePath);
			}else {
				image = imageReaderDao.getImageByBuffered(filePath);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public void writeImage(ImageWriterType writeType, OutputStream outputStream, BufferedImage image) {
		
		ImageWriterDao writerDao = getWriterDao(writeType);
		try {
			writerDao.writeImage(outputStream, image);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void writeImage(ImageWriterType writeType, String filePath, BufferedImage image) {
		
		ImageWriterDao writerDao = getWriterDao(writeType);
		try {
			writerDao.writeImage(filePath, image);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private ImageWriterDao getWriterDao(ImageWriterType writeType) {
		
		if(writeType.equals(ImageWriterType.ImageIO)) {
			return imageIOWriterDao;
		}else if(writeType.equals(ImageWriterType.JDELI)){
			return jdeliWriterDao;
		}else {
			return objectPlanetWriterDao;
		}
	}
	
}
