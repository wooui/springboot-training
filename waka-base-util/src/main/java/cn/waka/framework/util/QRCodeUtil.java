package cn.waka.framework.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import cn.waka.framework.exception.ZhhrUtilException;

/**
 * 生成二维码
 * 
 * @author sai
 * 
 */
public class QRCodeUtil {

	QRCodeUtil() {

	}

	private final int BLACK = 0xFF000000;

	private final int WHITE = 0xFFFFFFFF;

	/**
	 * 生成二维码
	 * 
	 * @return
	 */
	private BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * 获取二维码的流
	 * 
	 * @param html5Url
	 *            扫描后访问的HTML5地址
	 * @return
	 */
	public InputStream getQRCodeURL(String html5Url) {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			BitMatrix bitMatrix = multiFormatWriter.encode(html5Url, BarcodeFormat.QR_CODE, 400, 400, hints);
			BufferedImage image = toBufferedImage(bitMatrix);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(image, "jpeg", outputStream);
			ByteArrayInputStream in = new ByteArrayInputStream(outputStream.toByteArray());
			return in;
		} catch (WriterException e) {
			throw new ZhhrUtilException("创建二维码错误！" + e.getMessage());
		} catch (IOException e) {
			throw new ZhhrUtilException("IO读写错误" + e.getMessage());
		}
	}

}
