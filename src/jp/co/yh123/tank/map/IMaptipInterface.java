package src.jp.co.yh123.tank.map;

public interface IMaptipInterface extends IActorInterface {

	public boolean isWallOrBarrier() throws Exception;

	public boolean hasChara() throws Exception;

	public boolean isAbleToSetChara() throws Exception;

	public ICharaInterface getChara() throws Exception;

	public boolean hasItem() throws Exception;

	public boolean hasMapObject() throws Exception;

	public void setMapTile(int mapTileId) throws Exception;

	public void setMapObject(IMapObjectInterface mapObject) throws Exception;

	public void setChara(ICharaInterface chara) throws Exception;

	public void setItem(IItemInterface item) throws Exception;

	public void removeChara(ICharaInterface chara) throws Exception;

	// public void setTrap(ITrapInterface trap) throws Exception;
}
