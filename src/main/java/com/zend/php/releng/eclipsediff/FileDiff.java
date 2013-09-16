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
import static com.zend.php.releng.eclipsediff.utils.FileUtils.crc32;
import static com.zend.php.releng.eclipsediff.utils.FileUtils.isFeatureXml;
import static com.zend.php.releng.eclipsediff.utils.FileUtils.isManifest;
import static com.zend.php.releng.eclipsediff.utils.FileUtils.isZip;
import static com.zend.php.releng.eclipsediff.utils.JarUtils.isJar;

import java.io.File;

import com.zend.php.releng.eclipsediff.report.Report;

public class FileDiff extends AbstractDiff {

	private File original;
	private File other;

	protected FileDiff(File original, File other) {
		super(original.getAbsolutePath(), other.getAbsolutePath());

		this.original = original;
		this.other = other;

		if (!original.isFile())
			throw new IllegalArgumentException(original.getName()
					+ " is not file");

		if (!other.isFile())
			throw new IllegalArgumentException(other.getName() + " is not file");
	}

	@Override
	public void execute(Report report) throws Exception {
		if (isManifest(original)) {
			new ManifestDiff(original, other).execute(report);
		} else if (isFeatureXml(original)) {
			new FeatureXmlDiff(original, other).execute(report);
		} else if (isJar(original)) {
			new JarDiff(original, other).execute(report);
		} else if (isZip(original)) {
			new ZipDiff(original, other).execute(report);
		} else {
			if (original.length() != other.length()
					|| crc32(original) != crc32(other)) {
				report.add(MODIFIED, originalPath);
			}
		}
	}

}
