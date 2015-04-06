package src.jp.co.yh123.tank.collabo;

import src.jp.co.yh123.tank.chara.Chara;
import src.jp.co.yh123.tank.item.Item;
import src.jp.co.yh123.tank.map.ICharaInterface;
import src.jp.co.yh123.tank.map.IEntityFactoryInterface;
import src.jp.co.yh123.tank.map.IItemInterface;
import src.jp.co.yh123.tank.map.IMapObjectInterface;
import src.jp.co.yh123.tank.map.IMaptipInterface;
import src.jp.co.yh123.tank.mapobject.MapObject;
import src.jp.co.yh123.tank.maptip.MapTip;

public class EntityFactory implements IEntityFactoryInterface {

	public EntityFactory() {
	}

	public IMaptipInterface makeMapTip() throws Exception {
		return new MapTip();
	}

	public IMapObjectInterface makeMapObject() throws Exception {
		return new MapObject();
	}

	public ICharaInterface makeChara() throws Exception {
		return new Chara();
	}

	public IItemInterface makeItem() throws Exception {
		return new Item();
	}

}
