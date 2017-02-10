package com.keeps.tools.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtils {
	public static final void scale(String srcImageFile, String result, int scale) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));

			int width = src.getWidth();

			int height = src.getHeight();

			boolean flag = scale > 0;

			scale = Math.abs(scale);

			if (flag) {
				width *= scale;

				height *= scale;
			} else {
				width /= scale;

				height /= scale;
			}

			Image image = src.getScaledInstance(width, height, 1);

			BufferedImage tag = new BufferedImage(width, height, 1);

			Graphics g = tag.getGraphics();

			g.drawImage(image, 0, 0, null);

			g.dispose();

			ImageIO.write(tag, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final void scale2(String srcImageFile, String result, int height, int width, boolean bb) {
		try {
			double ratio = 0.0D;

			File f = new File(srcImageFile);

			BufferedImage bi = ImageIO.read(f);

			Image itemp = bi.getScaledInstance(width, height, 4);

			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = new Integer(height).doubleValue() / bi.getHeight();
				} else {
					ratio = new Integer(width).doubleValue() / bi.getWidth();
				}

				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);

				itemp = op.filter(bi, null);
			}

			if (bb) {
				BufferedImage image = new BufferedImage(width, height, 1);

				Graphics2D g = image.createGraphics();

				g.setColor(Color.white);

				g.fillRect(0, 0, width, height);

				if (width == itemp.getWidth(null)) {
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				} else {
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				}
				g.dispose();

				itemp = image;
			}

			ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final void cut(String srcImageFile, String result, int x, int y, int width, int height) {
		try {
			BufferedImage bi = ImageIO.read(new File(srcImageFile));

			int srcWidth = bi.getHeight();

			int srcHeight = bi.getWidth();

			if ((srcWidth <= 0) || (srcHeight <= 0))
				return;
			Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);

			ImageFilter cropFilter = new CropImageFilter(x, y, width, height);

			Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));

			BufferedImage tag = new BufferedImage(width, height, 1);

			Graphics g = tag.getGraphics();

			g.drawImage(img, 0, 0, width, height, null);

			g.dispose();

			ImageIO.write(tag, "JPEG", new File(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void cut2(String srcImageFile, String descDir, int rows, int cols) {
		try {
			if ((rows <= 0) || (rows > 20))
				rows = 2;

			if ((cols <= 0) || (cols > 20))
				cols = 2;

			BufferedImage bi = ImageIO.read(new File(srcImageFile));

			int srcWidth = bi.getHeight();

			int srcHeight = bi.getWidth();

			if ((srcWidth <= 0) || (srcHeight <= 0)) {
				return;
			}

			Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);

			int destWidth = srcWidth;

			int destHeight = srcHeight;

			if (srcWidth % cols == 0) {
				destWidth = srcWidth / cols;
			} else {
				destWidth = (int) Math.floor(srcWidth / cols) + 1;
			}

			if (srcHeight % rows == 0) {
				destHeight = srcHeight / rows;
			} else {
				destHeight = (int) Math.floor(srcWidth / rows) + 1;
			}

			for (int i = 0; i < rows; ++i) {
				for (int j = 0; j < cols; ++j) {
					ImageFilter cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);

					Image img = Toolkit.getDefaultToolkit()
							.createImage(new FilteredImageSource(image.getSource(), cropFilter));

					BufferedImage tag = new BufferedImage(destWidth, destHeight, 1);

					Graphics g = tag.getGraphics();

					g.drawImage(img, 0, 0, null);

					g.dispose();

					ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void cut3(String srcImageFile, String descDir, int destWidth, int destHeight) {
		try {
			if (destWidth <= 0)
				destWidth = 200;

			if (destHeight <= 0)
				destHeight = 150;

			BufferedImage bi = ImageIO.read(new File(srcImageFile));

			int srcWidth = bi.getHeight();

			int srcHeight = bi.getWidth();

			if ((srcWidth <= destWidth) || (srcHeight <= destHeight)) {
				return;
			}

			Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);

			int cols = 0;

			int rows = 0;

			if (srcWidth % destWidth == 0) {
				cols = srcWidth / destWidth;
			} else {
				cols = (int) Math.floor(srcWidth / destWidth) + 1;
			}

			if (srcHeight % destHeight == 0) {
				rows = srcHeight / destHeight;
			} else {
				rows = (int) Math.floor(srcHeight / destHeight) + 1;
			}

			for (int i = 0; i < rows; ++i) {
				for (int j = 0; j < cols; ++j) {
					ImageFilter cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);

					Image img = Toolkit.getDefaultToolkit()
							.createImage(new FilteredImageSource(image.getSource(), cropFilter));

					BufferedImage tag = new BufferedImage(destWidth, destHeight, 1);

					Graphics g = tag.getGraphics();

					g.drawImage(img, 0, 0, null);

					g.dispose();

					ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void convert(String srcImageFile, String formatName, String destImageFile) {
		try {
			File f = new File(srcImageFile);

			f.canRead();

			f.canWrite();

			BufferedImage src = ImageIO.read(f);

			ImageIO.write(src, formatName, new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void gray(String srcImageFile, String destImageFile) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));

			ColorSpace cs = ColorSpace.getInstance(1003);

			ColorConvertOp op = new ColorConvertOp(cs, null);

			src = op.filter(src, null);

			ImageIO.write(src, "JPEG", new File(destImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final void pressText(String pressText, String srcImageFile, String destImageFile, String fontName,
			int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);

			Image src = ImageIO.read(img);

			int width = src.getWidth(null);

			int height = src.getHeight(null);

			BufferedImage image = new BufferedImage(width, height, 1);

			Graphics2D g = image.createGraphics();

			g.drawImage(src, 0, 0, width, height, null);

			g.setColor(color);

			g.setFont(new Font(fontName, fontStyle, fontSize));

			g.setComposite(AlphaComposite.getInstance(10, alpha));

			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);

			g.dispose();

			ImageIO.write(image, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void pressText2(String pressText, String srcImageFile, String destImageFile, String fontName,
			int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);

			Image src = ImageIO.read(img);

			int width = src.getWidth(null);

			int height = src.getHeight(null);

			BufferedImage image = new BufferedImage(width, height, 1);

			Graphics2D g = image.createGraphics();

			g.drawImage(src, 0, 0, width, height, null);

			g.setColor(color);

			g.setFont(new Font(fontName, fontStyle, fontSize));

			g.setComposite(AlphaComposite.getInstance(10, alpha));

			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);

			g.dispose();

			ImageIO.write(image, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void pressImage(String pressImg, String srcImageFile, String destImageFile, int x, int y,
			float alpha) {
		try {
			File img = new File(srcImageFile);

			Image src = ImageIO.read(img);

			int wideth = src.getWidth(null);

			int height = src.getHeight(null);

			BufferedImage image = new BufferedImage(wideth, height, 1);

			Graphics2D g = image.createGraphics();

			g.drawImage(src, 0, 0, wideth, height, null);

			Image src_biao = ImageIO.read(new File(pressImg));

			int wideth_biao = src_biao.getWidth(null);

			int height_biao = src_biao.getHeight(null);

			g.setComposite(AlphaComposite.getInstance(10, alpha));

			g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao,
					null);

			g.dispose();

			ImageIO.write(image, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final int getLength(String text) {
		int length = 0;

		for (int i = 0; i < text.length(); ++i) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length += 2;
			} else {
				++length;
			}

		}

		return (length / 2);
	}

	public static void main(String[] args) {
		scale2("c:/QR/IMG_0004.JPG", "c:/QR/IMG_0004_SCALE.JPG", 300, 300, true);
	}
}