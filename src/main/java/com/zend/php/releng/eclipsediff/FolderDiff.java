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
import static com.zend.php.releng.eclipsediff.utils.FileUtils.getId;
import static com.zend.php.releng.eclipsediff.utils.FileUtils.hasExtension;
import static com.zend.php.releng.eclipsediff.utils.FileUtils.isFeature;
import static com.zend.php.releng.eclipsediff.utils.FileUtils.isPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.zend.php.releng.eclipsediff.report.Report;

public class FolderDiff extends AbstractDiff {

	protected static FilenameFilter FILTER = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return !SKIP_LIST.contains(name)
					&& !hasExtension(name, IGNORE_FILE_EXT);
		}
	};

	private File original;
	private File other;

	protected FolderDiff(File original, File other) {
		super(original.getAbsolutePath(), other.getAbsolutePath());

		this.original = original;
		this.other = other;

		if (!original.isDirectory())
			throw new IllegalArgumentException(original.getName()
					+ " is not a folder");

		if (!other.isDirectory())
			throw new IllegalArgumentException(other.getName()
					+ " is not a folder");
	}

	@Override
	public void execute(Report report) throws Exception {
		List<File> files1 = getFiles(original);
		List<File> files2 = getFiles(other);

		Iterator<File> iter = files1.iterator();
		while (iter.hasNext()) {
			File f1 = iter.next();
			File f2 = findCorrespondingFile(f1, files2);
			if (f2 == null) {
				report.add(REMOVED, f1);
			} else {
				new FileDiff(f1, f2).execute(report);
				iter.remove();
				files2.remove(f2);
			}

		}

		for (File f : files2) {
			report.add(ADDED, f);
		}

		List<File> folders1 = getFolders(original);
		List<File> folders2 = getFolders(other);

		iter = folders1.iterator();
		while (iter.hasNext()) {
			File f1 = iter.next();
			File f2 = findCorrespondingFile(f1, folders2);
			if (f2 == null) {
				report.add(REMOVED, f1);
			} else {
				new FolderDiff(f1, f2).execute(report);
				iter.remove();
				folders2.remove(f2);
			}
		}

		for (File f : folders2) {
			report.add(ADDED, f);
		}
	}

	protected List<File> getFiles(File folder) {
		List<File> result = new ArrayList<>();

		for (File f : folder.listFiles(FILTER)) {
			if (f.isFile()) {
				result.add(f);
			}
		}

		return result;
	}

	protected List<File> getFolders(File folder) {
		List<File> result = new ArrayList<>();

		for (File f : folder.listFiles(FILTER)) {
			if (f.isDirectory()) {
				result.add(f);
			}
		}

		return result;
	}

	protected File findCorrespondingFile(File file, List<File> files) {
		if (isFeature(file) || isPlugin(file)) {
			String id = getId(file);

			for (File f : files) {
				if (getId(f).equals(id)) {
					return f;
				}
			}
		} else {
			for (File f : files) {
				if (file.getName().equals(f.getName())) {
					return f;
				}
			}
		}
		return null;
	}

}
