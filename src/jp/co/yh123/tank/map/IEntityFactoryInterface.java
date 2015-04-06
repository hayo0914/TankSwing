package src.jp.co.yh123.tank.map;

public interface IEntityFactoryInterface {

	public IMaptipInterface makeMapTip() throws Exception;

	public IMapObjectInterface makeMapObject() throws Exception;

	public ICharaInterface makeChara() throws Exception;

	public IItemInterface makeItem() throws Exception;

}
