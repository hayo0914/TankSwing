package src.jp.co.yh123.zlibrary.scene;

import java.util.Vector;

import src.jp.co.yh123.zlibrary.platform.GameGraphic;

public class SceneManager {

	private Vector scenes = new Vector();

	private Vector scenesToAdd = new Vector();

	SceneManager() {
		// do nothing
	}

	public synchronized void add(ISceneInterface scene) throws Exception {
		scenesToAdd.addElement(scene);
	}

	// public synchronized void remove(ISceneInterface scene) throws Exception
	// {
	// scene.setEnd();
	// }

	public void update() throws Exception {
		ISceneInterface scene;

		if (scenesToAdd.size() > 0) {
			synchronized (this) {
				int addSize = scenesToAdd.size();
				for (int i = 0; i < addSize; i++) {
					scene = (ISceneInterface) scenesToAdd.elementAt(i);
					scenes.addElement(scene);
					scene.onInit();
				}
				for (int i = 0; i < addSize; i++) {
					scenesToAdd.removeElementAt(0);
				}
			}
		} else {
			// do nothing
		}

		int size = scenes.size();
		int position = size - 1;
		// DebugUtil.debug("--------------mediators--------------");
		for (int i = 0; i < size; i++) {
			scene = (ISceneInterface) scenes.elementAt(i);
			// DebugUtil.debug(con.toString());
			if (scene.isEnd()) {
				// do notshing
			} else {
				scene.update(position);
			}
			position--;
		}
		// DebugUtil.debug("-------------------------------------");

		// remove end
		synchronized (this) {
			for (int i = 0; i < size; i++) {
				scene = (ISceneInterface) scenes.elementAt(i);
				if (scene.isEnd()) {
					scene.onExit();
					scenes.removeElement(scene);
					i--;
					size--;
				} else {
					// do notshing
				}
			}
		}
	}

	// public void removeAll() throws Exception {
	// ISceneInterface scene;
	// int size = scenes.size();
	// for (int i = 0; i < size; i++) {
	// scene = (ISceneInterface) scenes.elementAt(i);
	// scene.setEnd();
	// }
	// }

	public synchronized void draw(GameGraphic g) throws Exception {
		ISceneInterface scene;
		int size = scenes.size();
		for (int i = 0; i < size; i++) {
			scene = (ISceneInterface) scenes.elementAt(i);
			if (scene.isEnd()) {
				// do nothing
			} else {
				scene.draw(g);
			}
		}
	}
}
