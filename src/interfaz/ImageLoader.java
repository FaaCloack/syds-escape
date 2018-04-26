package interfaz;

import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	private BufferedImage image;
	private BufferedImage[] imagenes;
	
	public ImageLoader(){
		imagenes = new BufferedImage[7];
	}
	
	public BufferedImage loadImage(String path) throws IOException{
		image = ImageIO.read(getClass().getResource(path));
		return image;
	}
	
	public void setImage(BufferedImage image, int pos){
		imagenes[pos]= image;
	}
	
	public BufferedImage getImage(int pos){
		return imagenes[pos];
	}
	
	
	
}