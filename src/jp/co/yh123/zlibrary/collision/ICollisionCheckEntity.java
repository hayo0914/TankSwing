package src.jp.co.yh123.zlibrary.collision;

import src.jp.co.yh123.zlibrary.anime.Animation;

public interface ICollisionCheckEntity {

	public int getWidth();

	public int getHeight();

	public int getX();

	public int getY();

	public void addCollisionSegment(CollisionSegment segment);

	public CollisionSegment[] getCollisionSegmentsList();

	public void initCollisionSegmentsList();

	public int getCollisionSegmentsLength();

	public Animation getAnime();

	public void onHit(ICollisionCheckEntity ent);

}
