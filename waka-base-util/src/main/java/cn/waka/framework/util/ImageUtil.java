package cn.waka.framework.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;

import cn.waka.framework.exception.ZhhrUtilException;


/**
 * 图片工具类
 * 
 * @author Administrator
 * 
 */
public class ImageUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	ImageUtil() {

	}

	// ===========图片验证码============

	/*private final ValidateCodeImage validCodeImage = new ValidateCodeImage();

	public String createValidateImage(OutputStream os, int vlength) {
		try {
			return validCodeImage.createValidateImage(os, vlength);
		} catch (IOException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	public void createValidateImage(OutputStream os, char[] vcode) {
		try {
			validCodeImage.createValidateImage(os, vcode);
		} catch (IOException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}*/

	// ============图片质量压缩============

	/**
	 * 根据质量压缩图片，不改变图片的尺寸
	 * 
	 * @param imgContent
	 *            图片内容
	 * @param quality
	 *            缩放比例
	 * @param extName
	 *            后缀名
	 * @return
	 */
	public byte[] compress(byte[] imgContent, float quality, String extName) {
		if (quality > 1 || quality <= 0 || imgContent == null || WakaUtils.string.isEmpty(extName)) {
			throw new ZhhrUtilException("压缩文件失败！入参为空");
		}
		try (InputStream is = new ByteArrayInputStream(imgContent);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageOutputStream ios = ImageIO.createImageOutputStream(os);) {
			BufferedImage image = ImageIO.read(is);

			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extName);
			ImageWriter writer = writers.next();
			writer.setOutput(ios);

			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);

			writer.write(null, new IIOImage(image, null, null), param);
			writer.dispose();

			return os.toByteArray();
		} catch (IOException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	// ============图片尺寸及缩放============

	/**
	 * 获取图片的尺寸
	 * 
	 * @param extName
	 *            后缀名
	 * @param imgContent
	 *            图片内容
	 * @return 数组:[宽,高]
	 */
	public int[] getSize(String extName, byte[] imgContent) {
		if (imgContent == null || WakaUtils.string.isEmpty(extName)) {
			throw new ZhhrUtilException("获取文件大小失败！入参为空");
		}
		// 字节转图片流
		try (ImageInputStream imgInputStream = byteArrayStreamToImageInputStream(imgContent)) {
			// 取得图片读取器
			ImageReader imgReader = getImageReader(extName);

			// 把文件流加载到图片读取器中
			imgReader.setInput(imgInputStream, true);

			return new int[] { imgReader.getWidth(0), imgReader.getHeight(0) };
		} catch (IOException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	private ImageReader getImageReader(final String imgType) {
		// 建立图片读取器
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imgType);
		return readers.next();
	}

	private ImageInputStream byteArrayStreamToImageInputStream(final byte[] bytes) throws IOException {
		// 取得文件流
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);

		// 把字节流转换为图片流
		return ImageIO.createImageInputStream(byteInputStream);
	}

	/**
	 * 缩放图片（宽度或高度不能大于maxSize），不改变图片的质量
	 * 
	 * @author lujiaxi 2014年12月5日 上午8:43:00
	 * @param bytes
	 *            图片内容
	 * @param maxSize
	 *            竖版图则高度缩至maxSize，横版图则宽度缩至maxSize
	 * @param imgType
	 *            图片后缀名
	 * @return
	 */
	public byte[] resizeMax(byte[] bytes, int maxSize, String imgType) {
		return resize(bytes, maxSize, 0, imgType);
	}

	/**
	 * 缩放图片（宽度不能大于maxWidth），不改变图片的质量
	 * 
	 * @author lujiaxi 2014年12月5日 上午8:43:00
	 * @param bytes
	 *            图片内容
	 * @param maxWidth
	 *            宽度缩至maxWidth
	 * @param imgType
	 *            图片后缀名
	 * @return
	 */
	public byte[] resizeWidth(byte[] bytes, int maxWidth, String imgType) {
		return resize(bytes, maxWidth, 1, imgType);
	}

	/**
	 * 缩放图片（高度不能大于maxHeight），不改变图片的质量
	 * 
	 * @author lujiaxi 2014年12月5日 上午8:43:00
	 * @param bytes
	 *            图片内容
	 * @param maxHeight
	 *            高度缩至maxHeight
	 * @param imgType
	 *            图片后缀名
	 * @return
	 */
	public byte[] resizeHeight(byte[] bytes, int maxHeight, String imgType) {
		return resize(bytes, maxHeight, 2, imgType);
	}

	private byte[] resize(byte[] bytes, int newSize, int operation, String imgType) {
		if (bytes == null || WakaUtils.string.isEmpty(imgType) || newSize <= 0) {
			throw new ZhhrUtilException("获取文件大小失败！入参为空");
		}
		Image image = new ImageIcon(bytes).getImage();
		Image resizedImage = getResizedImage(image, newSize, operation);

		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();

		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();

		// Clear background and paint the image.
		// g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();

		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray = { 0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);

		// 输出
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			ImageIO.write(bufferedImage, imgType, os);
			return os.toByteArray();
		} catch (IOException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	private Image getResizedImage(Image image, int newSize, int operation) {
		int iWidth = image.getWidth(null);
		int iHeight = image.getHeight(null);
		int hints = Image.SCALE_SMOOTH;
		switch (operation) {
		case 1:// 按宽度缩放
			return image.getScaledInstance(newSize, (newSize * iHeight) / iWidth, hints);
		case 2:// 按高度缩放
			return image.getScaledInstance((newSize * iWidth) / iHeight, newSize, hints);
		default:// 哪边大按哪边缩放
			if (iWidth > iHeight) {
				return image.getScaledInstance(newSize, (newSize * iHeight) / iWidth, hints);
			} else {
				return image.getScaledInstance((newSize * iWidth) / iHeight, newSize, hints);
			}
		}
	}

	/**
	 * 缩放图片（按比例缩放），不改变图片的质量
	 * 
	 * @author lujiaxi 2014年12月5日 上午9:45:00
	 * @param bytes
	 *            图片内容
	 * @param scale
	 *            缩放比例
	 * @param imgType
	 *            图片后缀名
	 * @return
	 */
	public byte[] resizeScale(byte[] bytes, float scale, String imgType) {
		if (bytes == null || WakaUtils.string.isEmpty(imgType) || scale <= 0) {
			throw new ZhhrUtilException("缩放图片失败！入参为空");
		}
		if (scale == 1.0f) {
			return bytes;
		}

		Image image = new ImageIcon(bytes).getImage();
		int width = (int) (image.getWidth(null) * scale);

		return resizeWidth(bytes, width, imgType);
	}

	public static String generateImage(String imgStr, String fileName) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return "";
		// BASE64Decoder decoder = new BASE64Decoder();
		imgStr = imgStr.substring(imgStr.indexOf(",") + 1, imgStr.length());
		Base64.decodeBase64(imgStr.getBytes());
		OutputStream out = null;
		try {
			// Base64解码
			byte[] b = Base64.decodeBase64(imgStr.getBytes());
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			// ByteArrayInputStream in = new ByteArrayInputStream(b);
			String imgFilePath = "/statics/company/mobile/images/" + fileName + ".png";// 新生成的图片
			out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			return imgFilePath;
		} catch (Exception e) {
			return "";
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static InputStream generateImage(String imgStr) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return null;
		imgStr = imgStr.substring(imgStr.indexOf(",") + 1, imgStr.length());
		Base64.decodeBase64(imgStr.getBytes());
		OutputStream out = null;
		try {
			// Base64解码
			byte[] b = Base64.decodeBase64(imgStr.getBytes());
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			InputStream in = new ByteArrayInputStream(b);
			return in;
		} catch (Exception e) {
			return null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static InputStream generateImage2(String imgStr) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return null;
		Base64.decodeBase64(imgStr.getBytes());
		OutputStream out = null;
		try {
			// Base64解码
			byte[] b = Base64.decodeBase64(imgStr.getBytes());
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			InputStream in = new ByteArrayInputStream(b);
			return in;
		} catch (Exception e) {
			return null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*public static void main(String[] args) {
		String img = "data:image/jpeg;base64,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";
		img = img.substring(img.indexOf(",") + 1, img.length());
		ImageUtil.generateImage(img, "adsad");
	}*/

}
