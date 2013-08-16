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

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.zend.php.releng.eclipsediff.report.Report;

public class ZipDiff extends AbstractDiff {

	protected ZipDiff(File original, File other) {
		super(original.getAbsolutePath(), other.getAbsolutePath());
	}

	@Override
	public void execute(Report report) throws Exception {
		try (ZipInputStream zis1 = new ZipInputStream(new FileInputStream(
				originalPath));
				ZipInputStream zis2 = new ZipInputStream(new FileInputStream(
						otherPath))) {
			ZipEntry e1 = null;
			ZipEntry e2 = null;

			while ((e1 = zis1.getNextEntry()) != null) {
				e2 = zis2.getNextEntry();

				if (!equal(e1, e2)) {
					report.add(MODIFIED, originalPath);
				}
			}
		}
	}
}
