import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class grayscaleTest{
	public grayscaleTest() {
		BufferedImage OriginalImage = null;
		BufferedImage grayscaleImage1;
		BufferedImage grayscaleImage2 = null;
		
		String path = "test.jpg";
		try {
			OriginalImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println(path);
		}
		Long time1 = System.currentTimeMillis();
		
		grayscaleImage1 = grayscale(OriginalImage);
		
		Long time2 =  System.currentTimeMillis();
		
		ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		grayscaleImage2=op.filter(OriginalImage,null);
		Long time3 =  System.currentTimeMillis();
		//showFrame(grayscaleImage1);
		//showFrame(grayscaleImage2);


		System.out.println("�g�k�ҿ�: �ϥΤF"+(time2-time1)+"�@��");
		System.out.println("�禡�w��: �ϥΤF"+(time3-time2)+"�@��");
	}
	double redPro = 0.3, greenPro=0.59,bluePro=0.11;
	public static void main (String [] args){
		new grayscaleTest();
	}
	
	BufferedImage grayscale (BufferedImage bi){
			BufferedImage bo = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
			for(int x = 0;x < bi.getHeight(); x++){
				for(int y = 0; y < bi.getWidth() ; y++){
					int rgb = bi.getRGB(y, x);
					int gray = (int)(redPro * getRed(rgb) +
									greenPro * getGreen(rgb)+
									bluePro * getBlue(rgb)
									);
					bo.setRGB(y,x,newGrayRGB(getAlpha(rgb),gray));
				}
			}
			return bo;		
}
	int newGrayRGB(int alpha , int gray){
		int newPixel = 0; 
		newPixel += (alpha<<24); 
		newPixel = newPixel << 8; 
		newPixel += gray; 
		newPixel = newPixel << 8; 
		newPixel += gray; 
		newPixel = newPixel << 8; 
		newPixel += gray; 
		return newPixel; 
	}
	
	// ���o��pixel��ARGB�U�� 
	int getAlpha(int rgb){
		return (rgb & 0xff000000)>>24;
	}
	int getRed(int rgb){
		return (rgb & 0xff0000)>>16;
	}
	int getGreen(int rgb){
		return (rgb & 0xff00)>>8;
	}
	int getBlue(int rgb){
		return rgb & 0xff;
	}
	// ���m�ؤo
	void resizeImg (JLabel iiLabel ,BufferedImage fi){
		ImageIcon ii  = new ImageIcon(fi);
		int in_width = ii.getIconWidth();
		int in_height = ii.getIconHeight();
		int label_width = iiLabel.getWidth();
		int label_height = iiLabel.getHeight();
		System.out.println("��s�e�Ϥ��j�p"+ii.getIconWidth()+":"+ii.getIconHeight());
		if(in_width >= in_height){
			ii.setImage(ii.getImage().getScaledInstance(label_width, -1,16));
		}else{
			ii.setImage(ii.getImage().getScaledInstance(-1, label_height,16));
		}		
		iiLabel.setIcon(ii);
		System.out.println("��s��Label�j�p"+iiLabel.getIcon().getIconWidth()+":"+iiLabel.getIcon().getIconHeight());
	}
	// �u�X��ܭ��
	void showFrame (BufferedImage bi){
		JFrame showFrame = new JFrame();
		JLabel showLabel = new JLabel();
		showLabel.setBounds(0,0, bi.getWidth(), bi.getHeight());
		resizeImg(showLabel, bi);
		showFrame.getContentPane().add(showLabel);
		showFrame.setSize(showLabel.getWidth(), showLabel.getHeight());
		showFrame.setVisible(true);
		showFrame.setResizable(false);
		showFrame.setLocationRelativeTo(null);
		showFrame.setTitle("Preview");
	}
}


