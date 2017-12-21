<%@ page import="b9.*,java.io.*" 
    language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Client Side</title>

	<style>
	table, td, th{
	border: 1px solid black;
	border-collapse: collapse;
	}
	th{
	background: #222;
	color: white;
	}
	</style>
 

</head>
<body>
<center>
<h1>Huffman code generation</h1>
<p>
 <table style="width: 40%;">
 <tr>
  <th>SYMBOL</th>
  <th>WEIGHT</th>
  <th>HUFFMAN CODE</th>
 </tr>
 <tr>
  <td>-</td>
  <td>-</td>
  <td>-</td>
 </tr>
	<%
	String str = request.getParameter("text");
	int[] charFreqs = new int[256];
	// read each character and record the frequencies
	for (char c : str.toCharArray())
	charFreqs[c]++;
	// build tree
	HuffmanCode temp = new HuffmanCode();
	HuffmanTree tree = temp.buildTree(charFreqs);
	PrintWriter pw = new PrintWriter(out, true);
	temp.printCodes(tree, new StringBuffer(), pw);
	%>
 </table>
 </p>
 </center>
</body>
</html>