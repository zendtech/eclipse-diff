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
import static com.zend.php.releng.eclipsediff.utils.JarUtils.getManifestPath;
import static com.zend.php.releng.eclipsediff.utils.MapUtils.equal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.zend.php.releng.eclipsediff.report.Report;

public class ManifestDiff extends AbstractDiff {

	protected static final String[] IGNORED_HEADERS = { "Bundle-Version",
			"Built-By", "Created-By", "Build-Jdk", "Archiver-Version" };

	private Manifest originalManifest;
	private Manifest otherManifest;

	protected ManifestDiff(JarFile original, JarFile other) throws IOException {
		super(getManifestPath(original), getManifestPath(other));
		this.originalManifest = original.getManifest();
		this.otherManifest = other.getManifest();
	}

	protected ManifestDiff(File original, File other) throws IOException {
		super(original.getAbsolutePath(), other.getAbsolutePath());
		this.originalManifest = new Manifest(new FileInputStream(original));
		this.otherManifest = new Manifest(new FileInputStream(other));
	}

	@Override
	public void execute(Report report) throws Exception {
		if (originalManifest == null)
			return;

		Attributes attr1 = originalManifest.getMainAttributes();
		Attributes attr2 = otherManifest.getMainAttributes();

		// ignore some headers when comparing the manifests
		for (String header : IGNORED_HEADERS) {
			attr1.remove(new Attributes.Name(header));
			attr2.remove(new Attributes.Name(header));
		}

		if (!equal(attr1, attr2)) {
			report.add(MODIFIED, originalPath);
		}
	}

}
