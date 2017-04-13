package com.keeps.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.keeps.tools.exception.CapecException;

/** 
 * <p>Title: ImageUtil.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月7日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class ImageUtil {

	
	private static Log log = LogFactory.getLog(ImageUtil.class);

	public static void createThumbnail(String src, String dist, float width,
            float height) {
        try {
            File srcfile = new File(src);
            
            if (!srcfile.exists()) {
                throw new CapecException(src+":图片文件不存在");
            }
            
            BufferedImage image = ImageIO.read(srcfile);

            // 获得缩放的比例
            double ratio = 1.0;
            // 判断如果高、宽都不大于设定值，则不处理
            if (image.getHeight() > height || image.getWidth() > width) {
                if (image.getHeight() > image.getWidth()) {
                    ratio = height / image.getHeight();
                } else {
                    ratio = width / image.getWidth();
                }
            }
            // 计算新的图面宽度和高度
            int newWidth = (int) (image.getWidth() * ratio);
            int newHeight = (int) (image.getHeight() * ratio);

            BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
                    BufferedImage.TYPE_INT_RGB);
            bfImage.getGraphics().drawImage(
                    image.getScaledInstance(newWidth, newHeight,
                            Image.SCALE_SMOOTH), 0, 0, null);

            File newFile=new File(dist);
            if (!newFile.exists()) {
            	newFile.mkdirs();
            }
            ImageIO.write(bfImage, "png", newFile);
        } catch (Exception e) {
            throw new CapecException(e);
        }
    }
	
	 /**  
     * 图像切割（改）     *  
     * @param srcImageFile           源图像地址 
     * @param dirImageFile           新图像地址 
     * @param x                      目标切片起点x坐标 
     * @param y                      目标切片起点y坐标 
     * @param destWidth              目标切片宽度 
     * @param destHeight             目标切片高度 
     */ 
    public static void absCut(String srcImageFile,String dirImageFile, int x, int y, int destWidth, int destHeight, String imageType) {  
        try {  
            Image img;  
            ImageFilter cropFilter;
            BufferedImage bi = ImageIO.read(new File(srcImageFile));  
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();   
            if (srcWidth >= destWidth && srcHeight >= destHeight) {  
                Image image = bi.getScaledInstance(srcWidth, srcHeight,  
                        Image.SCALE_DEFAULT);
                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);  
                img = Toolkit.getDefaultToolkit().createImage(  
                        new FilteredImageSource(image.getSource(), cropFilter));  
                BufferedImage tag = new BufferedImage(destWidth, destHeight,  
                        BufferedImage.TYPE_INT_RGB);  
                Graphics g = tag.getGraphics();  
                g.drawImage(img, 0, 0, null);
                g.dispose();
                File file=new File(dirImageFile);
                if(!file.exists()){
                	file.mkdirs();  
                } 
                // 输出为文件   
                ImageIO.write(tag, imageType.toLowerCase(), file);   
            //    ImageIO.write(tag, imageType.toLowerCase(), new File(dirImageFile)); 
            }  
        } catch (Exception e) {  
        	log.error("剪切图片发生异常" + e.getMessage());
        	 throw new CapecException("剪切图片发生异常" + e.getMessage());
        }  
    }
    
}
