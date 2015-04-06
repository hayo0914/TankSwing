package src.jp.co.yh123.zlibrary.anime;

import src.jp.co.yh123.zlibrary.collision.HitBox;

public class AnimeFrame {

	private int sprId = 0;
	private int frameId = 0;
	private HitBox[] hitbox = null;
	
	private AnimeFrame(){
		
	}
	
	public static AnimeFrame newAnimeFrame(int sprId,int frameId,HitBox[] hitbox){
		AnimeFrame frame = new AnimeFrame();
		frame.sprId = sprId;
		frame.frameId = frameId;
		frame.hitbox = hitbox;
		return frame;
	}

	public void dump() {
		System.out.print("ÅysprId:" + sprId + "|" + "frameId:" + frameId
				+ "|Hits:" + String.valueOf(hitbox) + "Åz");
	}

	public int getFrameId() {
		return frameId;
	}


	public HitBox[] getHitbox() {
		return hitbox;
	}


	public int getSprId() {
		return sprId;
	}

}
