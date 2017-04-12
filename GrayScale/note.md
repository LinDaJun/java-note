2017 java - 灰階化 土法煉鋼及函式庫 對比

起因從這篇文章開始 : http://ppt.cc/2QQfM

在搜尋灰階化範例的時候，發現java有函式庫可以執行灰階化，但不確定其RGB的比例是多少，因此有此測試並順便對比效率


土法煉鋼程式碼 : 

     BufferedImage grayscale (BufferedImage bi){
          BufferedImage bo = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
          
          for(int x = 0;x < bi.getHeight(); x++){
               for(int y = 0; y < bi.getWidth() ; y++){
                    int rgb = bi.getRGB(y, x);
                    int gray = (int)(redPro * getRed(rgb) + greenPro * getGreen(rgb)+bluePro * getBlue(rgb));
		    // redPro = 0.3, greenPro=0.59,bluePro=0.11;
                    bo.setRGB(y,x,newGrayRGB(getAlpha(rgb),gray));
               }
          }
          return bo;
     }
     
     // 輸出轉換後灰階值之RGB
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
     
     // 取得該pixel之ARGB各值 
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
     
java 自帶package:

     ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
          grayscaleImage2=op.filter(OriginalImage,null);

至於結果其實還滿明顯的..


我的秒數從 140~ 100都有
但是函式庫基本上都保持在60 +- 5毫秒

對java的理解還是不夠
想知道哪裡還可以改善變得更快?

而RGB轉換灰度的比例
土法煉鋼的是紅 = 0.3 , 綠 = 0.59 , 藍 = 0.11
package自帶的根據計算推估是 紅 = 0.3 , 綠 = 0.6 , 藍 = 0.1
