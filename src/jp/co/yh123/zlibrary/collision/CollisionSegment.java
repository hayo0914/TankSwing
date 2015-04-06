package src.jp.co.yh123.zlibrary.collision;

public class CollisionSegment {

	ICollisionCheckEntity[] entityList = null;

	int entityNum = 0;

	CollisionSegment(int maxObjNum) {
		entityList = new ICollisionCheckEntity[maxObjNum];
	}

	ICollisionCheckEntity[] getObjList() {
		return entityList;
	}

	void addShObject(ICollisionCheckEntity ent) {
		try {
			if (entityNum >= entityList.length) {
				return;
			}
			entityList[entityNum] = ent;
			entityNum++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void initObjList() {
		entityNum = 0;
	}

}
