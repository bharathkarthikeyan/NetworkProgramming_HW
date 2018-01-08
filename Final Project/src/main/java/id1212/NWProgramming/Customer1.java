package id1212.NWProgramming;

import javax.swing.JFrame;

public class Customer1 {
	public static void main(String[] args) {
		Customer Deckard;
		Deckard = new Customer("127.0.0.1");
		Deckard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Deckard.startRunning();
	}
}