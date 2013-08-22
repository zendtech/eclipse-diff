/**
 * Copyright (c) 2013, Zend Technologies Ltd.
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package com.zend.php.releng.eclipsediff.utils;

import static com.zend.php.releng.eclipsediff.utils.JarUtils.isJar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class FileUtils {

	public static long crc32(File file) throws IOException {
		Checksum checksum = new CRC32();
		try (FileInputStream fis = new FileInputStream(file)) {
			byte[] bytes = new byte[1024];
			int len = 0;

			while ((len = fis.read(bytes)) != -1) {
				checksum.update(bytes, 0, len);
			}
		}
		return checksum.getValue();
	}

	public static boolean isFeature(File folder) {
		return folder.isDirectory() && folder.getParent().endsWith("features");
	}

	public static boolean isPlugin(File file) {
		return file.isFile() && isJar(file) || file.isDirectory()
				&& file.getParent().endsWith("plugins");
	}

	public static boolean isManifest(File file) {
		return file.isFile() && file.getPath().endsWith(JarFile.MANIFEST_NAME);
	}

	public static boolean isFeatureXml(File file) {
		return "feature.xml".equals(file.getName());
	}

	public static boolean isZip(File file) {
		return hasExtension(file, ".zip");
	}

	public static String getId(File file) {
		String name = file.getName();
		String jarId = name;
		int index = name.lastIndexOf('_');
		if (index != -1) {
			jarId = name.substring(0, index);
		}
		return jarId;
	}

	public static boolean hasExtension(File file, String extension) {
		return hasExtension(file.getName(), extension);
	}

	public static boolean hasExtension(String name, String extension) {
		return name.toLowerCase().endsWith("." + extension.toLowerCase());
	}

	public static boolean hasExtension(String name, String[] extensions) {
		for (String extension : extensions) {
			if (hasExtension(name, extension)) {
				return true;
			}
		}
		return false;
	}

}
