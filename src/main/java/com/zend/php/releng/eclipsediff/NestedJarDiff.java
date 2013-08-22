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

import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import com.zend.php.releng.eclipsediff.report.Report;

public class NestedJarDiff extends AbstractDiff {

	private JarInputStream original;
	private JarInputStream other;

	protected NestedJarDiff(JarFile originalJar, JarEntry originalEntry,
			JarFile otherJar, JarEntry otherEntry) throws IOException {
		super(getJarEntryPath(originalJar, originalEntry), getJarEntryPath(
				otherJar, otherEntry));
		this.original = new JarInputStream(
				originalJar.getInputStream(originalEntry));
		this.other = new JarInputStream(otherJar.getInputStream(otherEntry));
	}

	@Override
	public void execute(Report report) throws Exception {
		JarEntry e1 = null;
		JarEntry e2 = null;

		while ((e1 = original.getNextJarEntry()) != null) {
			e2 = other.getNextJarEntry();

			if (!equal(e1, e2)) {
				report.add(MODIFIED, originalPath);
				return;
			}
		}

	}

}
