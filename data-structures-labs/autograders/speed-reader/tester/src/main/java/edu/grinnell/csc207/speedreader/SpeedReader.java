package edu.grinnell.csc207.speedreader;

import java.awt.*;
import java.io.FileNotFoundException;

/**
 *
 * @author osera
 */
public class SpeedReader {

	private static void printUsageAndExit() {
		System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
		System.exit(1);
	}

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		if (args.length != 5) {
			printUsageAndExit();
		}

		String filename = args[0];
		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2]);
		int fontSize = Integer.parseInt(args[3]);
		int wpm = Integer.parseInt(args[4]);

		WordGenerator gen = new WordGenerator(filename);

		DrawingPanel panel = new DrawingPanel(width, height);
		Graphics g = panel.getGraphics();
		Font f = new Font("Courier", Font.BOLD, fontSize);
		g.setFont(f);

		int delay = 60000 / wpm;

		while (gen.hasNext()) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.BLACK);
			g.drawString(gen.next(), width / 4, height / 2);
			Thread.sleep(delay);
		}

		System.out.println("Total words: " + gen.getWordCount());
		System.out.println("Total sentences: " + gen.getSentenceCount());
	}
}
