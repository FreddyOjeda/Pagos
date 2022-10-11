package com.app.pagos.Segurity;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class PaymentEncryption {

	private final static String PUBLICKEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAquoIWPEHGhz6jlsTSmo0\n" +
			"ndqyu4ysjk1PoloiQlR91TJ3zGwL9BinDPyw6+TFE56rgfSLb+TfFQvkWTTqPhzF\n" +
			"2gqu0fUAWhM6i8rd5Aw/OsfrIoGPHVco0hcAfcsBwh5cVZDHq+hpI3WlJdLFOxXt\n" +
			"sdPeH4O4b7JcXF23nM/FT+sY446cVTRFucbbPBDeT9ftIN+AtQTPKCe3NPQ1vkTM\n" +
			"8YHmx0STD0i+awF0goNGLncFVrflvYx4sRfktTmdla6fAPaHaiCgHUftZUlgAFE9\n" +
			"4HIUNO8vjNrdVd/22rKLDoHYeI4mk/Zok42wf2RzCWA21drslOsISCkxFGZmX4BH\n" +
			"yQIDAQAB";

	private final static String PRIVATEKEY="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCq6ghY8QcaHPqO\n" +
			"WxNKajSd2rK7jKyOTU+iWiJCVH3VMnfMbAv0GKcM/LDr5MUTnquB9Itv5N8VC+RZ\n" +
			"NOo+HMXaCq7R9QBaEzqLyt3kDD86x+sigY8dVyjSFwB9ywHCHlxVkMer6GkjdaUl\n" +
			"0sU7Fe2x094fg7hvslxcXbecz8VP6xjjjpxVNEW5xts8EN5P1+0g34C1BM8oJ7c0\n" +
			"9DW+RMzxgebHRJMPSL5rAXSCg0YudwVWt+W9jHixF+S1OZ2Vrp8A9odqIKAdR+1l\n" +
			"SWAAUT3gchQ07y+M2t1V3/basosOgdh4jiaT9miTjbB/ZHMJYDbV2uyU6whIKTEU\n" +
			"ZmZfgEfJAgMBAAECggEAEjgET8bRpPz10Hrw4XvUxAbuCoQCWODw3jORd1SeXT2V\n" +
			"+5qA47JFDGNbUKoAQAkn6yBwRKs3BH1SCBhV5+0VQhrla8gcE339Pa2QXaDYlKF+\n" +
			"9Ryubsg9t7dv+/jJ5TFrUkuunSqXGoqN4ixHjfbWcaBOCzn1TSLMKaRr0DFtrQkL\n" +
			"3ed8xnstlq19QPry/8FtJEmMYANaRzXEhIdYAyGxbb40x0nU1bUUXtPaXK3lzh/k\n" +
			"vzdKx2V8+rOPxiJeE19mZZMGy3ZoEwOEVNZYNgm3EeChsfqRqj/0lXuk189WD/bW\n" +
			"nHUbXmeM0O0mzEGvFf7d+V52dKBgknFkqNrMU4XlyQKBgQDeiI63f8GfwIw4FVyV\n" +
			"dXkGRDJ9C5rHnRwTMt0Jlc/UzGsmizgKtH015nSgVaWjDQ7Ifs0iz3omiUXhiE60\n" +
			"7ph386s3fc95sRYmW5+0VPmq+J2OPSd4ZE5Jik4j8wwttr0n+wxQ2+ArkOgn7ZaJ\n" +
			"NM9v+SbsxGTMAFb0Mf9WoLUkrwKBgQDEnigIFk+KRoKJaM3KQZ60OsESQy915Xuj\n" +
			"C2dCDd0X05q54Hz0QO1RHUbwvRcBTn3AuUfW1ub+O3Et/NWlrPgJ/l1BLIw9BFu5\n" +
			"GYq0Ous2ItbPmfXBGIM9MAqFBKp5N+19lHUD+42CQFuFcJYqCyr6mIcnDso0Qw8H\n" +
			"FLLyMqfpBwKBgGMjuVk83HkcYhu2QJoBTNqi1U9qNRmK/+/5sAnOKVone/EfAuea\n" +
			"A1JZCkpH/dQHRDfEbgV9I3E5pN1WSaJHzbta3kOJlD4ly7VpClxfox8tRZwR2JzF\n" +
			"Ky+OI0PWyu0uxVSjfpgjbNpb42GucmtMEMJU4KeI01MjL5n5EEV6sVjXAoGAL4QB\n" +
			"LKMnqcUMeuSnBwAAuJGSYzV1XMqOrIJu+9mZ2s46kD0eNXETaQzoPfmUe65aWZ7t\n" +
			"K5BX1ozK91Ao2ZcJgnDEXKHFamGJQRxZFuE+QqD8xMLyTy37WWgowqi0AgNwE28z\n" +
			"MDk0o4umEoN45sQ4KMN/tgKu/x5NnWp1LG7tgTUCgYBXWuFJ7tRau2bnpcK89ULX\n" +
			"55l1WyP03vmx9p5J4E9jHEtiODdqM+K4JzRcfeIm40SGvuXr6PS56AkkFriGDTI2\n" +
			"rb5PXDo4ZPNw3f+6IJPI07/NfwV2KCj9iC3lQWk3haPwMB6mMrCOfQZCBiDEnE5S\n" +
			"cMmsJzK6mbutmjtBtw8mVA==";

	public String encryptByPublicKey(String text) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(PUBLICKEY));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] result = cipher.doFinal(text.getBytes());
		return Base64.encodeBase64String(result);
	}

	public String decryptByPrivateKey(String text) throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(PRIVATEKEY));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] result = cipher.doFinal(Base64.decodeBase64(text));
		return new String(result);
	}

}


