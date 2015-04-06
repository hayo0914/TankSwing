package src.jp.co.yh123.zlibrary.io;

public class ResourceFile {

	private String name = "";
	private byte[] bData = null;

	protected ResourceFile(String name, byte[] bData) {
		this.name = name;
		this.bData = bData;
	}
	
	public String getName() {
		return name;
	}

	public byte[] getBData() {
		return bData;
	}

}
