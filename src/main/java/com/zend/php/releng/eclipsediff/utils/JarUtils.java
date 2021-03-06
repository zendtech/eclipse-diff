/*****************************************************************************
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
 ****************************************************************************/
package com.zend.php.releng.eclipsediff.utils;

import static com.zend.php.releng.eclipsediff.utils.FileUtils.hasExtension;

import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class JarUtils {

	public static final String JAR_EXT = "jar";

	public static boolean equal(ZipEntry e1, ZipEntry e2) {
		if (e1 == null) {
			// if both are null then they are equal
			return e2 == null;
		} else if (e2 == null) {
			// e1 is not null, so they are not equal
			return false;
		} else {
			// both are not null, so compare their checksums
			return e1.getCrc() == e2.getCrc();
		}
	}

	public static String getJarEntryPath(JarFile jar, JarEntry entry) {
		return jar.getName() + "!" + entry.getName();
	}

	public static String getManifestPath(JarFile jar) {
		return jar.getName() + "!" + JarFile.MANIFEST_NAME;
	}

	public static String getJarEntryFileName(JarEntry entry) {
		String name = entry.getName();
		int index = name.lastIndexOf('/');
		if (index != -1) {
			name = name.substring(index + 1);
		}
		return name;
	}

	public static boolean isJar(JarEntry entry) {
		return isJar(entry.getName());
	}

	public static boolean isJar(File file) {
		return file.isFile() && isJar(file.getName());
	}

	private static boolean isJar(String name) {
		return hasExtension(name, JAR_EXT);
	}
}
