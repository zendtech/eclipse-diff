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

import static com.zend.php.releng.eclipsediff.report.ReportEntryType.ADDED;
import static com.zend.php.releng.eclipsediff.report.ReportEntryType.REMOVED;
import static com.zend.php.releng.eclipsediff.utils.FileUtils.hasExtension;
import static com.zend.php.releng.eclipsediff.utils.JarUtils.getJarEntryFileName;
import static com.zend.php.releng.eclipsediff.utils.JarUtils.getJarEntryPath;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.zend.php.releng.eclipsediff.report.Report;

public class JarDiff extends AbstractDiff {

	protected JarDiff(File original, File other) {
		super(original.getAbsolutePath(), other.getAbsolutePath());
	}

	@Override
	public void execute(Report report) throws Exception {
		JarFile jar1 = new JarFile(originalPath);
		JarFile jar2 = new JarFile(otherPath);

		new ManifestDiff(jar1, jar2).execute(report);

		List<JarEntry> entries1 = getJarEntriesList(jar1);
		List<JarEntry> entries2 = getJarEntriesList(jar2);

		Iterator<JarEntry> iter = entries1.iterator();
		while (iter.hasNext()) {
			JarEntry e1 = iter.next();
			JarEntry e2 = findCorrespondingJarEntry(e1, entries2);
			if (e2 == null) {
				report.add(REMOVED, getJarEntryPath(jar1, e1));
			} else {
				new JarEntryDiff(jar1, e1, jar2, e2).execute(report);
				iter.remove();
				entries2.remove(e2);
			}
		}

		for (JarEntry e : entries2) {
			report.add(ADDED, getJarEntryPath(jar2, e));
		}
	}

	protected List<JarEntry> getJarEntriesList(JarFile jar) {
		List<JarEntry> result = new ArrayList<>();

		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (!SKIP_LIST.contains(getJarEntryFileName(entry))
					&& !hasExtension(entry.getName(), IGNORE_FILE_EXT)
					&& !entry.getName().endsWith(JarFile.MANIFEST_NAME)) {
				result.add(entry);
			}
		}

		return result;
	}

	protected JarEntry findCorrespondingJarEntry(JarEntry entry,
			List<JarEntry> entries) {
		for (JarEntry e : entries) {
			if (e.getName().equals(entry.getName())) {
				return e;
			}
		}
		return null;
	}

}
