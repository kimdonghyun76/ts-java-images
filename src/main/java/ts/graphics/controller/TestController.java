package ts.graphics.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ts.graphics.service.ImageService;
import ts.graphics.type.ImageReaderType;
import ts.graphics.type.ImageWriterType;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private ImageService imageService;
	
	@Value("${image.write.path}")
	private String imageWritePath;
	
	private Map<Integer, BufferedImage> imageMap;
	
	@PostConstruct
	public void init() {
		ImageIO.setUseCache(false);
		imageMap = new HashMap<Integer, BufferedImage>();
		
		for(int i=1;i<10;i++) {
			
			BufferedImage image = createNumber(i);
			imageService.writeImage(ImageWriterType.ImageIO, imageWritePath + i + ".png", image);
			
			imageMap.put(i, image);
		}
	}
	
	@RequestMapping("/readImages")
	public String readImages(HttpServletRequest request,
			HttpServletResponse response){
		
		ImageReaderType readerType = null;
		String read = request.getParameter("read");
		
		if(StringUtils.isEmpty(read) || read.equals("1")) {
			readerType = ImageReaderType.NORMAL;
		}else if(read.equals("2")){
			readerType = ImageReaderType.NIO;
		}else {
			readerType = ImageReaderType.BUFFERED;
		}
		
		for(int i=1;i<10;i++) {
			
			getNumberImage(readerType, i);

		}
		
		return "success";
	}
	
	@RequestMapping("/combineImages")
	public ResponseEntity<byte[]> combineNumbers(HttpServletRequest request,
			HttpServletResponse response){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		
		ImageWriterType writerType = null;
		String write = request.getParameter("write");
		
		if(StringUtils.isEmpty(write) || write.equals("1")) {
			writerType = ImageWriterType.ImageIO;
		}else if(write.equals("2")) {
			writerType = ImageWriterType.JDELI;
		}else {
			writerType = ImageWriterType.OBJECTPLANET;
		}
		
		BufferedImage image = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		
		int x = 0;
		int y = 0;
		
		for(int i=1;i<10;i++) {
			
			BufferedImage numberImage = imageMap.get(i);
			graphics.drawImage(numberImage, x, y, null);
			
			x += 50;
			if(i%3 == 0) {
				x = 0;
				y += 50;
			}
		}
		
		graphics.dispose();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		imageService.writeImage(writerType, baos, image);
		
		return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.OK);
	}
	
	
	@RequestMapping("/createNumbers")
	public String createNumberImage(HttpServletRequest request,
			HttpServletResponse response){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
	
		for(int i=1;i<10;i++) {
			BufferedImage image = createNumber(i);
			imageService.writeImage(ImageWriterType.ImageIO, imageWritePath + i + ".png", image);
		}
		
		return "success";
	}
	
	private BufferedImage getNumberImage(ImageReaderType readerType, int number) {
		return imageService.getImage(readerType, imageWritePath + number + ".png");
	}
	
	private BufferedImage createNumber(int number) {
		BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		
		graphics.setPaint(Color.BLACK);
		graphics.setFont(new Font("SansSerif",Font.BOLD,40));
		graphics.drawString(number + "", 10, 40);
		graphics.dispose();
		
		return image;
	}

}
