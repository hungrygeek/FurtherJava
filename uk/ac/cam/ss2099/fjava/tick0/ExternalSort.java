package uk.ac.cam.ss2099.fjava.tick0;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ExternalSort {

	public static void sort(String f1, String f2) throws FileNotFoundException, IOException, Exception {
		
		RandomAccessFile file1 = new RandomAccessFile(f1, "rw");
		RandomAccessFile file2 = new RandomAccessFile(f2, "rw");
		int int_numbers = (int) (file1.length() / 4);

		int chunk = 1000000;

		if (int_numbers<=1000000) {chunk = int_numbers;}
		if (int_numbers>=10000000) {chunk = 500000;}
		int size = Math.min(chunk, int_numbers);

		if (size==0) {
			file1.close(); 
			file2.close();
		} else {
			external_sort(f1, f2, size); 

			boolean data_in_file1 = true;
			DataOutputStream dos = null;

			while (size <= int_numbers) { 

				if (data_in_file1) {
					file1.seek(0);
					dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file1.getFD())));						
				} else {
					file2.seek(0);
					dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file2.getFD())));						
				}

				for (int i = 0; i < int_numbers; i = i + size * 2) {
					if (data_in_file1) { 
						dos = merge(f2, dos, i, size);
						dos.flush();
					} else { 
						dos = merge(f1, dos, i, size);
						dos.flush();
					}
				}

				data_in_file1 = !data_in_file1; 
				size = size * 2; 

			}

			if (data_in_file1) {
				file1.seek(0);
				file2.seek(0);
				dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file1.getFD())));
				DataInputStream dis2 = new DataInputStream(new BufferedInputStream(new FileInputStream(file2.getFD()))); 

				int[] storage = new int[int_numbers];

				for (int i=0; i<int_numbers; i++) {
					storage[i]=dis2.readInt();
				}

				for (int i=0; i<int_numbers; i++) {
					dos.writeInt(i);
				}

				dos.flush();
			}

			dos.close();
			file1.close();
			file2.close();
		}
	}

	public static void external_sort(String f1, String f2, int size) throws Exception {

		RandomAccessFile file1 = new RandomAccessFile(f1, "rw");
		RandomAccessFile file2 = new RandomAccessFile(f2, "rw");

		int NumInts = (int) (file1.length() / 4);

		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file1.getFD())));			
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file2.getFD())));
		int[] array = new int[size];
		int NumberSorted = 0;
		try {	
			while (NumberSorted < NumInts){
				for (int i = 0; i < size; ++i){
					array[i] = dis.readInt();
				}
				NumberSorted = NumberSorted + size;

				Arrays.sort(array);

				for (int i = 0; i < size; i++){
					dos.writeInt(array[i]);
				}
			}
		} catch (EOFException e) {
		}

		dos.flush();	
		file1.close();
		file2.close();
		dis.close();
		dos.close();
	}

	public static DataOutputStream merge(String file, DataOutputStream dos, int begin, int size) throws IOException {
		RandomAccessFile file1 = new RandomAccessFile(file, "rw"); 
		RandomAccessFile file2 = new RandomAccessFile(file, "rw"); 

		int int_numbers = (int) (file1.length() / 4);
		file1.seek(begin * 4); 
		file2.seek(begin * 4); 

		DataInputStream dis1 = new DataInputStream(new BufferedInputStream(new FileInputStream(file1.getFD()))); 
		DataInputStream dis2 = new DataInputStream(new BufferedInputStream(new FileInputStream(file2.getFD()))); 

		int m = 0; 
		int n = 0; 
		int range = size * 2;
		int a = dis1.readInt();
		int b = 0;

		if (begin + size < int_numbers) {
			dis2.skip(4 * size); 
			b = dis2.readInt(); 
		} else { 
			n = size;
		}

		while (m + n < range) {

			if (n == size || (a <= b && m != size)) {
				dos.writeInt(a); 
				m++;
				if (begin + m == int_numbers) {
					m = size; 
				} else if (m != size) {
					a = dis1.readInt();
				}
			} else {
				dos.writeInt(b); 
				n++;
				if (begin + size + n == int_numbers) {
					n = size; 
				} else if (n != size) { 
					b = dis2.readInt();
				}
			}
		}

		dis1.close();
		dis2.close();
		file1.close();
		file2.close();
		return dos;
	}

	private static String byteToHex(byte b) {
		String r = Integer.toHexString(b);
		if (r.length() == 8) {
			return r.substring(6);
		}
		return r;
	}

	public static String checkSum(String f) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			DigestInputStream ds = new DigestInputStream(
					new FileInputStream(f), md);
			byte[] b = new byte[512];
			while (ds.read(b) != -1)
				;

			String computed = "";
			for(byte v : md.digest()) 
				computed += byteToHex(v);

			return computed;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "<error computing checksum>";
	}

	public static void main(String[] args) throws Exception {
		String f1 = args[0];
		String f2 = args[1];
		sort(f1, f2);
		System.out.println("The checksum is: "+checkSum(f1));
	}
}