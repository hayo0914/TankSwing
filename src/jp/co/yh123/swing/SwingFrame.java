package src.jp.co.yh123.swing;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class SwingFrame extends JFrame {

	private SwingPanel mp = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingFrame(Game game, int width, int height) throws Exception {
		setSize(width + 10, height + 30);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(1, 1));
		// setAlwaysOnTop(true);

		mp = new SwingPanel(game, width, height);
		getContentPane().add(mp);

		setVisible(true);
	}

	Graphics getPanelGraphic() {
		return mp.getGraphics();
	}

	public void repaintAll() {
		mp.repaint();
		mp.invalidate();
	}

}
