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

import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
		if (!equal(original, other)) {
			if (isJar(original)) {
				new NestedJarDiff(originalJar, original, otherJar, other)
						.execute(report);
			} else {
				report.add(MODIFIED, originalPath);
			}
		}
	}

}
