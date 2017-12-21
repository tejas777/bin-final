import java.util.*;
import java.io.*;
import java.nio.*;
/*
Defines interfaces and classes for the Java virtual machine to access files, file attributes, and file systems.
The java.nio.file package defines classes to access files and file systems.
*/
import java.nio.channels.*;
/*
Defines channels, which represent connections to entities that are capable of performing I/O operations, such as files and sockets; defines selectors, for multiplexed, non-blocking I/O operations.
A channel represents an open connection to an entity such as a hardware device, a file, a network socket, or a program component that is capable of performing one or more distinct I/O operations, for example reading or writing.
*/

class a6_final
{
	//converting string to digits

	private static byte[] stringToDigits(String num) 
	{
		byte[] result = new byte[num.length()];
		for (int i = 0; i < num.length(); i++) 
		{
			char c = num.charAt(i);
			if (c < '0' || c > '9') 
			{
				throw new IllegalArgumentException("Invalid digit " + c + " found at position " + i);
			}
			result[num.length() - 1 - i] = (byte) (c - '0');
		}
		return result;
	}

	// Deinition of longmult()

	public static String longMult(String num1, String num2) 
	{
		byte[] left = stringToDigits(num1);
		byte[] right = stringToDigits(num2);
		byte[] result = new byte[left.length + right.length];

		for (int rightPos = 0; rightPos < right.length; rightPos++) 
		{
			byte rightDigit = right[rightPos];
			byte temp = 0;
			for (int leftPos = 0; leftPos < left.length; leftPos++) 
			{
				temp += result[leftPos + rightPos];
				temp += rightDigit * left[leftPos];
				result[leftPos + rightPos] = (byte) (temp % 10);
				temp /= 10;
			}
			int destPos = rightPos + left.length;
			while (temp != 0) 
			{
				temp += result[destPos];// & 0xFFFFFFFFL;
				result[destPos] = (byte) (temp % 10);
				temp /= 10;
				destPos++;
			}
		}
		
		StringBuilder str = new StringBuilder(result.length);
		for (int i = result.length - 1; i >= 0; i--) 
		{
			byte digit = result[i];
			if (digit != 0 || str.length() > 0) 
			{
				str.append((char) (digit + '0'));
			}
		}
		return str.toString();
	}
	
	
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter First Multiplier ");
		String input = sc.nextLine();

		//write the input in the input file
		BufferedWriter writer = new BufferedWriter(new FileWriter("InputFile")); // InputFile is file which holds multiplier
		writer.write(input);
		writer.close();

		// Reading from memorymapped files
		File file = new File("InputFile");
		FileChannel fc = new RandomAccessFile(file, "rw").getChannel();
/*File channel : A channel for reading, writing, mapping, and manipulating a file. A file channel can also be obtained from an existing FileInputStream, FileOutputStream, or RandomAccessFile object by invoking that object's getChannel method, which returns a file channel that is connected to the same underlying file.

Random access file : Instances of this class support both reading and writing to a random access file. A random access file behaves like a large array of bytes stored in the file system. There is a kind of cursor, or index into the implied array, called the file pointer; input operations read bytes starting at the file pointer and advance the file pointer past the bytes read. If the random access file is created in read/write mode, then output operations are also available; output operations write bytes starting at the file pointer and advance the file pointer past the bytes written. 
*/


		ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int) fc.size());
/*MapMode : A typesafe enumeration for file-mapping modes. It can be PRIVATE,READ-ONLY,READ-WRITE
FileChannel.map(FileChannel.MapMode mode, long position, long size) 
*/

		String num1 = null;
		String token, multiplier1, multiplier2;
		char ch;
	
		while (bb.hasRemaining()) 
		{
			ch = (char) bb.get();
			num1 = num1 + ch;
		}

		token = num1.substring(4);	//value of num1 : null12345678

		multiplier1 = multiplier2 = token;
		System.out.println("Multiplier1: " + multiplier1);
		System.out.println("Multiplier2: " + multiplier2);
		System.out.println("Final Result: " +longMult(multiplier1,multiplier2)); //calling longmult function
	}
}
