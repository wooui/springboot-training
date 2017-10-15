package cn.waka.framework.util;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import cn.waka.framework.exception.ZhhrUtilException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * RSA密码算法 ,非对称<br>
 * 
 * @author jiqijun <br>
 * @version 1.0.0 2014年4月20日 上午9:45:22 <br>
 * @see http://snowolf.iteye.com/blog/381767
 * @since JDK 1.4.2.6
 */
class RSACoder {


	public static final String KEY_ALGORITHM = "RSA";

	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";

	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/** 加密与解密要使用相同的RSA/ECB/PKCS1Padding或RSA/ECB/NoPadding */
	private static final String RSA_java = "RSA/ECB/PKCS1Padding", RSA_android = "RSA/ECB/NoPadding";

	/*public static void main(String[] args) {
		System.err.println("公钥加密——私钥解密");
		String inputStr = "{\"cardno\":\"YG-2102\",\"cid\":\"20140421161939039cezg1e9\",\"ctid\":\"20140419192154054ml7k92f\",\"km\":\"社保卡\",\"name\":\"张三\",\"phone\":\"18666935916\",\"sfid\":\"430280198807078319\",\"ver\":\"V2\",\"xh\":1,\"zm\":\"机构\",\"zzid\":\"20140419171913013s8881s0\"}";
		
		 * Map<String,Key> keys = RSACoder.initKey(); String publicKey =
		 * RSACoder.getPublicKey(keys),privateKey =
		 * RSACoder.getPrivateKey(keys);
		 
		String localKey, publicKey, privateKey;
		localKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIF9Hi+/flDnEOkKF60NL2FzEwEPjBijruzD0USQSjyCrRTpLc6qA7XQbJvYrgGHDqsss5gk750BgugRhnoHqIfS4vnl296gZFwJrFnWMRyNWxFsQ1WMmrdLVpTgSsw3VcN+0WPRFjpS66ioRg7cHeDKz2Mvmfg9M8hszPOgyvBDAgMBAAECgYAU+qp7XfEfNnCc8Q+o7+vvQ++eSmCOVWav4YH0M9RzPVDBhI3xX8Y+HMib710xzinoPdPTIJjZvTbyVnSNLNR3AF5gvzdlB2p9Esg262GldMhSdGxoz6I+g31zOd5igQlptrMz4wbd9mj2bH+Glhg/IQzsO0C7uqTCvRckKF+j0QJBAOblvDnQE8f9ZrV0Hc7c7jmQ7WIODEPpS2QCLklzVxhnwLZbCmYehbkC5bQbUKkHmZvBDFGo0BobL6pS7waJTb8CQQCPkQIhUE6cbZnTMzoPtJnMomCuwx6oC/mg7ep8w5T1wHkcupECjZj9E1HXt+eti7ZlZhGeabs5Hu2tvrOOcoZ9AkEA4gdE4idSJfVmxPx9bZKS1eGMr+9mjFhlh+omm5qASRDP8vNeU6dPalRuvof6xuz8rx8kyw+BX/z7BTYYbQKeCwJAezjg9Srybfz8PHQJb5V5NFRiW12JoH62wVOzIWbVIaET0n6nG+Pg3caJRnfIWAJEiP90O91QHpTXN3sorijKGQJAHPTl7ZW7O49itYI40S0/rdioda8LNU9kxbwr6CESkuXBUSLuhblBwlJGhh4+q1NO00vi+yQFnwp1/fL+RoeH6w==";
		publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBfR4vv35Q5xDpChetDS9hcxMBD4wYo67sw9FEkEo8gq0U6S3OqgO10Gyb2K4Bhw6rLLOYJO+dAYLoEYZ6B6iH0uL55dveoGRcCaxZ1jEcjVsRbENVjJq3S1aU4ErMN1XDftFj0RY6UuuoqEYO3B3gys9jL5n4PTPIbMzzoMrwQwIDAQAB";

		privateKey = localKey;
		byte[] data = inputStr.getBytes(Charset.forName("UTF-8"));
		byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
		String miniWen = encryptBASE64(encodedData);
		miniWen = miniWen.replaceAll("\r\n", "");

		System.out.println("密文:" + miniWen);
		byte[] miniByte = decryptBASE64(miniWen);
		byte[] decodedData = RSACoder.decryptByPublicKey(miniByte, publicKey);

		String outputStr = new String(decodedData, Charset.forName("UTF-8"));
		System.err.println("加密前: " + inputStr + "\r\n" + "解密后: " + outputStr);
		System.err.println("publicKey:" + publicKey + "\r\n" + "privateKey:" + privateKey);
		
		 * String str=
		 * "w3k0xcZS1lVzChnlmHSk1tvceGGYX/dtyO+UzBshgUDm/ZPqv3x6oQD8+OKEqwv8QxSic+zh4kFt\r\n"
		 * +
		 * "M8/4e1zcjuRsz4EUABMvn4DapTNYdVNpd5pUp9WQIXgtKWcMysZcAcOazdz0J5ADiX9PAhGI9YNm\r\n"
		 * +
		 * "aXZdvZPluOwrgwdE12dxiysGt1NGZy9zFldLm8rJRo4e4SI8DqoEGSTpCj5jzh5/vAAHcyTTDRqg\r\n"
		 * +
		 * "8INO+8xCFqsUt8xoB0u+Y6dq4w5w/SsZXXj4sxLUhld8quGST1uT5rxsFsWsSdi1yk58Pr8GLjv3\r\n"
		 * +
		 * "oo+YfyzlYw4ghMEbDmSuvmwwhkkPJfIZ2fWpR3D+YIWGvx5Mr/qlAogIekWnhFVmwqALh4GiEJXW\r\n"
		 * +
		 * "lRUbc6UE4wLCq4o4uOaknS5seDtwRfXXHc4lrxAadkpa9O9OQc2V4SZkoP653MDgunX950EuV7zb\r\n"
		 * +
		 * "maVvHiFcFB9GWpS7SnDY3WqXjrLjH0UVoaPdUbW49MOHwsWBVxMJYEEhhgb5GGHaz7UR1hc9cFKF\r\n"
		 * +
		 * "u6oeBC++mABYCtTU1zIqYURzy7WIRdw/23u330QylP22YgZU5lo6qkaP5D7UCJmyDppyTlNj4XtD\r\n"
		 * +
		 * "atEMWiD5AvZDlMYbWJ4bQ6Yge4IdZMDIlxFrQ/ZWjgv+eYF9JLepmFzJjqUJenI9zu9HFP7Pg/mS\r\n"
		 * +
		 * "dgXF0z0OpPTmtyzGDvVpbstgFGKG0QILoaTYQpqeXVdFeMi2mpqKOvZHUVnazUsCANyXGy2tyUHj\r\n"
		 * +
		 * "pskJMIy4Pjko/yIRQbn7AxH6nn8e/SgLd0D43Gqpe/JeOc3waR8Ncz6mFiCCJ9LNv09GMBz2+VtZ\r\n"
		 * + "QglglSR+4BDGY+kB9W1cMshYfk22"; byte[] bys = decryptBASE64(str);
		 * byte[] b2 =DESedeCoder.decrypt(bys, "012345678901234567890123");
		 * byte[] b3 =decryptBASE64( encryptBASE64(b2)); String s1 = new
		 * String(b3,Charset.forName("utf-8")); JSONObject json =
		 * JSONObject.parseObject(s1);
		 * 
		 * String rsa = json.getString("data"); byte[] b4
		 * =RSACoder.decryptBASE64(rsa); String publicKey=
		 * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmPD7Dq7+UhkkIHMicBhn6Fj7etsntBrwBHXiLm2NCO5OYZqn1c7zhTzFJ5Tf0yjqF5dNQVmb+fnO3NQlVqTJxm92v4p76fpC7e00qP4AtJ7pUAchzP8rgBCx16ojzrl7Gu83d4CzfbfZWQD22XqyL5nQNKyR2dWRUloqoq57CswIDAQAB"
		 * ;//测试 byte[] b5 = RSACoder.decryptByPublicKey(b4, publicKey); String
		 * rsaStr = new String(b5,Charset.forName("utf-8"));
		 * System.out.println(rsaStr);
		 
	}*/

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = decryptBASE64(privateKey);

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return encryptBASE64(signature.sign());
	}

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) {
		try {
			BASE64Decoder b64 = new BASE64Decoder();
			return b64.decodeBuffer(key);
			// return Base64.decode(key);
		} catch (Exception e) {
			throw new RuntimeException("BASE64解密", e);
		}

	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) {
		BASE64Encoder b64 = new BASE64Encoder();
		String rs = b64.encodeBuffer(key);
		rs = rs.replaceAll("\r\n", "");
		return rs;
		// return Base64.encode(key);
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

		// 解密由base64编码的公钥
		byte[] keyBytes = decryptBASE64(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(decryptBASE64(sign));
	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	protected static byte[] decryptByPrivateKey(byte[] data, String key) {
		try {
			// 对密钥解密
			byte[] keyBytes = decryptBASE64(key);
			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// 对数据解密
			// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			Cipher cipher = Cipher.getInstance(RSA_java);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new ZhhrUtilException("用私钥解密" + e.getMessage());
		}
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) {
		try {
			// 对密钥解密
			byte[] keyBytes = decryptBASE64(key);

			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			// 对数据解密
			// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			Cipher cipher = Cipher.getInstance(RSA_java);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);

			byte[] result = {};
			for (int i = 0; i < data.length; i += 128) {
				byte[] split = splitBytes(data, i, 128);
				byte[] doFinal = cipher.doFinal(split);
				result = appBytes(result, doFinal);
			}

			return result;
		} catch (Exception e) {
			throw new ZhhrUtilException("用公钥解密出错" + e.getMessage());
		}
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	protected static byte[] encryptByPublicKey(byte[] data, String key) {
		try {
			// 对公钥解密
			byte[] keyBytes = decryptBASE64(key);

			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(RSA_java);// Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException("用公钥加密出错" + e.getMessage());
		}
	}

	/**
	 * 加密<br>
	 * 用私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key) {
		try {
			// 对密钥解密
			byte[] keyBytes = decryptBASE64(key);

			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(RSA_java);// Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			/*
			 * byte[] bytes = encodeString.getBytes(); byte[] encodedByteArray =
			 * new byte[] {}; for (int i = 0; i < bytes.length; i += 100) {
			 * byte[] subarray = ArrayUtils.subarray(bytes, i, i + 100); byte[]
			 * doFinal = cipher.doFinal(subarray); encodedByteArray =
			 * ArrayUtils.addAll(encodedByteArray, doFinal); } return
			 * encodedByteArray;
			 */
			byte[] result = {};
			for (int i = 0; i < data.length; i += 100) {
				byte[] split = splitBytes(data, i, 100);
				byte[] arys = cipher.doFinal(split);
				result = appBytes(result, arys);
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException("用私钥加密出错" + e.getMessage());
		}
	}

	private static byte[] splitBytes(byte[] bys, int start, int count) {
		int l = (bys.length - start);
		if (l < count) {
			count = l;
		}
		byte[] rs = new byte[count];
		System.arraycopy(bys, start, rs, 0, count);
		return rs;
	}

	private static byte[] appBytes(byte[] master, byte[] sub) {
		int ml = (master == null ? 0 : master.length), sl = sub.length;
		byte[] rs = new byte[ml + sl];
		if (ml > 0) {
			System.arraycopy(master, 0, rs, 0, ml);
		}
		if (sl > 0) {
			System.arraycopy(sub, 0, rs, ml, sl);
		}
		return rs;
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Key> keyMap) {
		Key key = keyMap.get(PRIVATE_KEY);
		String rs = encryptBASE64(key.getEncoded());
		rs = rs.replaceAll("\r\n", "");
		return rs;
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Key> keyMap) {
		Key key = keyMap.get(PUBLIC_KEY);
		String rs = encryptBASE64(key.getEncoded());
		rs = rs.replaceAll("\r\n", "");
		return rs;
	}

	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Key> initKey() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(1024);

			KeyPair keyPair = keyPairGen.generateKeyPair();

			// 公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

			// 私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

			Map<String, Key> keyMap = new HashMap<String, Key>(2);

			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);
			return keyMap;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("初始化密钥出错 " + e.getMessage());
		}
	}
}
