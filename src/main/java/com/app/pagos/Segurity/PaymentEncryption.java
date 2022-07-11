package com.app.pagos.Segurity;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class PaymentEncryption {
	
	private BigInteger p,q,a,b,n;
	
	private static String ENCRYPT_KEY="Cl4v3-De-S3gUr1Dad-Pr1V4d4";
	static Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");
	
	
	public SecretKeySpec makeKey(String key) {
		try {
			byte[] cad = key.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			cad = md.digest(cad);
			cad = Arrays.copyOf(cad, 16);
			SecretKeySpec keySpec = new SecretKeySpec(cad, "AES");
			return keySpec;
		}catch (Exception e) {
			return null;
		}
	}
	
	public String encript(String text){
		
		try {
			SecretKeySpec spec = makeKey(ENCRYPT_KEY);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, spec);
			
			byte[] cade = text.getBytes("UTF-8");
			byte[] encripted = cipher.doFinal(cade);
			String encrypted = Base64.encodeBase64String(encripted);
			return encrypted;
		}catch(Exception e) {
			return "";
		}
	}
	
	public String decrypt(String text) {
		
		try {
			SecretKeySpec spec = makeKey(ENCRYPT_KEY);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, spec);
			
			byte[] cade = Base64.decodeBase64(text);
			byte[] encripted = cipher.doFinal(cade);
			String decrypted = new String(encripted);
			return decrypted;
		}catch(Exception e) {
			return "";
		}
	}
	
	public String cifrar(LinkedList<BigInteger> input) {
		String ret = "";
		for (BigInteger aux : input) {
			BigInteger cip = aux.modPow(b, n);
			ret += cip.toString() + ", ";
		}
		return ret;
	}
	
	public String decifrar(LinkedList<BigInteger> input) {
		String ret = "";
		for (BigInteger aux : input) {
			BigInteger dip = aux.modPow(a, n);
			ret += dip.toString()+", ";
		}
		return ret;
	}
	
	private void buidKey() {
		SecureRandom rand = new SecureRandom();
		BigInteger tmp = BigInteger.probablePrime(256, rand);
		while (!isPrime(tmp)) {
			tmp = BigInteger.probablePrime(256, rand);
		}
		p=tmp;
		tmp = BigInteger.probablePrime(256, rand);
		while(!isPrime(tmp)) {
			tmp=BigInteger.probablePrime(256, rand);
		}
		q=tmp;
		n=q.multiply(p);
		tmp = new BigInteger(128,rand);
		BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger inv = inverseModule(tmp, phiN);
		while (inv.compareTo(new BigInteger("-1")) == 0) {
			tmp = new BigInteger(128, rand);
			inv = inverseModule(inv, phiN);
		}
		a = tmp;
		b = inv;
	}

	private boolean isPrime(BigInteger p) {
		return p.isProbablePrime(30);
	}
	
	private BigInteger inverseModule(BigInteger a, BigInteger p) {
		a = a.mod(p);
		BigInteger[] sol = eucldgcd(a, p);
		if (sol[0].compareTo(BigInteger.ONE) != 0) {
			return new BigInteger("-1");
		}
		return (sol[1].compareTo(BigInteger.ZERO) < 0 )? p.add(sol[1]) : sol[1];
	}
	
	private BigInteger[] eucldgcd(BigInteger p, BigInteger q) {
		if(q.compareTo(BigInteger.ZERO)==0) {
			return new BigInteger[] {p,BigInteger.ONE,BigInteger.ZERO};
		}
		BigInteger[] vals = eucldgcd(q, p.mod(q));
		BigInteger d = vals[0];
		BigInteger a = vals[2];
		BigInteger b = vals[1].subtract((p.divide(q).multiply(vals[2])));
		return new BigInteger[] {d,a,b};
	}
}
