package id1212.NWProgramming;

import javax.swing.JFrame;

public class Customer2 {
	public static void main(String[] args) {
		Customer Rick;
		Rick = new Customer("127.0.0.1");
		Rick.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Rick.startRunning();
	}
}