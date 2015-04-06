package src.jp.co.yh123.swing;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import src.jp.co.yh123.zlibrary.platform.ActionEventAdapter;

public class SwingPanel extends JPanel {

	private Game _game = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingPanel(Game game, int width, int height) throws Exception {
		setSize(width, height);
		_game = game;
		addKeyListener(new ActionEventAdapter.PojoKeyListner());
		setOpaque(true);
		setFocusable(true);
		requestFocus();
		setVisible(true);
		setMinimumSize(new Dimension(width, height));
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		_game.setGraphic(g);
		_game.draw();
	}

}
