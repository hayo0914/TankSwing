package src.jp.co.yh123.zlibrary.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ResourceFileReader {

	public static ResourceFile readFile(String name) throws Exception {
		int size;
		ByteArrayOutputStream out = null;
		InputStream in = null;
		byte[] w = new byte[10240];

		try {
			in = ResourceFileReader.class.getResourceAsStream(name);
			out = new ByteArrayOutputStream();

			if (in == null) {
				throw new Exception("InputStream�擾���s�F" + name);
			}

			// �t�@�C������̓ǂݍ���
			while (true) {
				w = new byte[10240];
				size = in.read(w);
				if (size <= 0)
					break;
				out.write(w, 0, size);
			}

			// �t�@�C���Ƃ̐ؒf
			out.close();
			in.close();
			ResourceFile file = new ResourceFile(name, out.toByteArray());
			return file;
		} catch (Exception e) {
			throw e;
		}
	}

}
