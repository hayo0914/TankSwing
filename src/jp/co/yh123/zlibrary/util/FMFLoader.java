package src.jp.co.yh123.zlibrary.util;

import src.jp.co.yh123.zlibrary.io.ResourceFileReader;

public class FMFLoader {
	/**
	 * ���ʕ����uFMF_�v
	 */
	public char[] identifier;
	/**
	 * �w�b�_���������f�[�^�T�C�Y
	 */
	public int datasize;
	/**
	 * �}�b�v�̕�
	 */
	public int width;
	/**
	 * �}�b�v�̍���
	 */
	public int height;
	/**
	 * �`�b�v�̕�
	 */
	public int chipwidth;
	/**
	 * �`�b�v�̍���
	 */
	public int chipheight;
	/**
	 * ���C���[��
	 */
	public int layercount;
	/**
	 * �f�[�^��bit��(8/16)
	 */
	public int bitcount;
	/**
	 * ���C���[�z��
	 */
	FMFLayer2D[] layerArray;
	private byte[] bytes;
	private int _ptr;

	/**
	 * �R���X�g���N�^<br>
	 * TODO: �}�b�v�f�[�^��8bit�̂�
	 * 
	 * @param filepath
	 *            FMF�t�@�C��
	 * @throw �t�@�C���t�H�[�}�b�g��O
	 */
	public FMFLoader(String filepath) throws Exception {

		bytes = ResourceFileReader.readFile(filepath).getBData();

		_ptr = 0;
		_ptr += 4;

		datasize = readLong(); // �w�b�_���������T�C�Y [4byte]
		width = readLong(); // �}�b�v�̕� [4byte]
		height = readLong(); // �}�b�v�̍��� [4byte]
		chipwidth = readByte(); // �`�b�v�̕� [1byte]
		chipheight = readByte(); // �`�b�v�̍��� [1byte]
		layercount = readByte(); // ���C���[�� [1byte]
		bitcount = readByte(); // �f�[�^��bit��(8/16) [1byte]
		layerArray = new FMFLayer2D[layercount];

		for (int i = 0; i < layercount; i++) {
			FMFLayer2D layer = new FMFLayer2D(width, height, bytes, _ptr, _ptr
					+ width * height);
			layerArray[i] = layer;
			_ptr += width * height;
		}

	}

	/**
	 * �w�背�C���[���擾����
	 * 
	 * @param id
	 *            ���C���[ID
	 * @return 2�����}�b�v
	 */
	public FMFLayer2D getLayer(int id) {
		if (id < 0 || id >= layercount)
			return null;
		return layerArray[id];
	}

	/**
	 * 1byte�ǂݍ���
	 */
	private int readByte() {
		if (_ptr - 1 >= bytes.length)
			return -1;
		int result = bytes[_ptr];
		_ptr += 1;
		return result;
	}

	/**
	 * 4byte�ǂݍ���
	 */
	private int readLong() {
		if (_ptr - 4 >= bytes.length)
			return -1;
		int result = bytes[_ptr] + (bytes[_ptr + 1] << 8)
				+ (bytes[_ptr + 2] << 16) + (bytes[_ptr + 3] << 24);
		_ptr += 4;
		return result;
	}
}
