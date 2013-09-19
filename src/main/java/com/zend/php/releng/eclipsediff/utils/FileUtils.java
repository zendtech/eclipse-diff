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

	/**
	 * Checks if the provided file system resource is an Eclipse plugin.
	 * 
	 * <p>
	 * Eclipse plugins are the directories and the JAR files that are directly
	 * under the "plugins" directory of the Eclipse installation.
	 * </p>
	 * 
	 * @param file
	 *            the file or directory to check
	 * @return <code>true</code> if the file or directory is an Eclipse plugin,
	 *         <code>false</code> - otherwise.
	 */
	public static boolean isPlugin(File file) {
		if (file == null)
			return false;

		File parent = file.getParentFile();

		return (isJar(file) || file.isDirectory()) && parent != null
				&& "plugins".equals(parent.getName())
				&& !hasParentWithName(parent, "plugins");
	}

	public static boolean hasParentWithName(File file, String parentName) {
		if (file == null)
			return false;

		if (parentName == null)
			throw new IllegalArgumentException("parentName is null");

		File parent = file.getParentFile();

		if (parent == null) {
			return false;
		} else if (parentName.equals(parent.getName())) {
			return true;
		} else {
			return hasParentWithName(parent, parentName);
		}
	}

	public static boolean isManifest(File file) {
		return file.isFile() && file.getPath().endsWith(JarFile.MANIFEST_NAME);
	}

	public static boolean isFeatureXml(File file) {
		return "feature.xml".equals(file.getName());
	}

	public static boolean isZip(File file) {
		return file.isFile() && hasExtension(file, ".zip");
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

	/**
	 * Checks if the provided file name has the given extension.
	 * 
	 * <p>
	 * The check is case-insensitive.
	 * </p>
	 * 
	 * <p>
	 * If <code>extension</code> parameter is <code>null</code> then it is
	 * treated as empty string.
	 * </p>
	 * 
	 * @param fileName
	 *            the file name to check
	 * @param extension
	 *            the extension to test
	 * @return <code>true</code> if the <code>extension</code> argument matches
	 *         the extension of the provided file name, <code>false</code> -
	 *         otherwise.
	 */
	public static boolean hasExtension(String fileName, String extension) {
		if (fileName == null)
			return false;

		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex == -1) {
			return extension == null || extension.isEmpty();
		}

		String ext = fileName.substring(dotIndex + 1);
		if (ext.isEmpty()) {
			return extension == null || extension.isEmpty();
		} else {
			return ext.equalsIgnoreCase(extension);
		}
	}

	/**
	 * Checks if the provided file name has any of the given extensions.
	 * 
	 * <p>
	 * The check is case-insensitive.
	 * </p>
	 * 
	 * <p>
	 * If <code>extensions</code> array is <code>null</code> then it is treated
	 * as a single extension of empty string.
	 * </p>
	 * 
	 * <p>
	 * This method calls {@link #hasExtension(String, String)} for each of the
	 * items in the <code>extensions</code> array.
	 * </p>
	 * 
	 * @param fileName
	 *            the file name to check
	 * @param extensions
	 *            the list of extension to test
	 * @return <code>true</code> if the extension of the provided file name
	 *         matches at least one of items in the <code>extensions</code>
	 *         array, <code>false</code> - otherwise.
	 * 
	 * @see #hasExtension(String, String)
	 */
	public static boolean hasExtension(String fileName, String[] extensions) {
		if (extensions == null) {
			extensions = new String[] { null };
		}

		for (String extension : extensions) {
			if (hasExtension(fileName, extension)) {
				return true;
			}
		}

		return false;
	}

}
