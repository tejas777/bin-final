package b9;

//public class HuffmanLeaf {
//
//}
import java.util.*;
public class HuffmanLeaf extends HuffmanTree {
public final char value; // the character this leaf represents
public HuffmanLeaf(int freq, char val) {
super(freq);
value = val;
}
}