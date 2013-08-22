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
package com.zend.php.releng.eclipsediff;

import static com.zend.php.releng.eclipsediff.report.ReportEntryType.MODIFIED;
import static com.zend.php.releng.eclipsediff.utils.JarUtils.equal;
import static com.zend.php.releng.eclipsediff.utils.JarUtils.getJarEntryPath;
import static com.zend.php.releng.eclipsediff.utils.JarUtils.isJar;

import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.IOUtils;

import com.zend.php.releng.eclipsediff.report.Report;

public class JarEntryDiff extends AbstractDiff {

	private JarEntry original;
	private JarEntry other;

	private JarFile originalJar;
	private JarFile otherJar;

	protected JarEntryDiff(JarFile originalJar, JarEntry originalEntry,
			JarFile otherJar, JarEntry otherEntry) {
		super(getJarEntryPath(originalJar, originalEntry), getJarEntryPath(
				otherJar, otherEntry));
		this.original = originalEntry;
		this.other = otherEntry;
		this.originalJar = originalJar;
		this.otherJar = otherJar;
	}

	@Override
	public void execute(Report report) throws Exception {
		if (!equal(original, other) && !equalIgnoringClassVersion()) {
			if (isJar(original)) {
				new NestedJarDiff(originalJar, original, otherJar, other)
						.execute(report);
			} else {
				report.add(MODIFIED, originalPath);
			}
		}
	}

	private boolean equalIgnoringClassVersion() throws IOException {
		if (original == null) {
			// if both are null then they are equal
			return other == null;
		} else if (other == null) {
			// other is not null, so they are not equal
			return false;
		} else {
			byte[] originalBytes = IOUtils.toByteArray(originalJar
					.getInputStream(original));
			byte[] otherBytes = IOUtils.toByteArray(otherJar
					.getInputStream(other));

			if (originalBytes.length != otherBytes.length)
				return false;

			for (int i = 0; i < originalBytes.length; i++) {
				if (i != 7 && originalBytes[i] != otherBytes[i]) {
					return false;
				}
			}
			return true;
		}
	}

}
