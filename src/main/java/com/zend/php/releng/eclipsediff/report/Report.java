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
package com.zend.php.releng.eclipsediff.report;

import static com.zend.php.releng.eclipsediff.report.ReportEntryType.ADDED;
import static com.zend.php.releng.eclipsediff.report.ReportEntryType.MODIFIED;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Report {

	private String originalRoot;
	private String otherRoot;

	private List<ReportEntry> entries = new ArrayList<>();

	private boolean optimized = false;

	public Report(String originalRoot, String otherRoot) {
		this.originalRoot = originalRoot;
		this.otherRoot = otherRoot;
	}

	public void add(ReportEntryType type, File file) {
		this.add(type, file.getAbsolutePath());
	}

	public void add(ReportEntryType type, String absolutePath) {
		entries.add(new ReportEntry(type, absolutePath));
	}

	public void print() {
		print(System.out);
	}

	public void print(PrintStream out) {
		if (!optimized) {
			optimize();
		}

		for (ReportEntry entry : entries) {
			out.println(entry);
		}
	}

	private void optimize() {
		makePathsRelative();
		stripQualifiers();
		sort();
		removeInnerClasses();
	}

	private void makePathsRelative() {
		for (ReportEntry entry : entries) {
			String absolutePath = entry.getPath();
			String root = (entry.getType() == ADDED) ? otherRoot : originalRoot;
			entry.setPath(getRelativePath(absolutePath, root));
		}
	}

	private String getRelativePath(String path, String root) {
		int index = root.length();
		// skip any leading path separator character
		if (path.length() > index && path.charAt(index) == File.separatorChar) {
			index++;
		}
		return path.substring(index);
	}

	private void stripQualifiers() {
		Pattern pattern = Pattern.compile("_(\\d+\\.){3}[\\w\\-]+");

		for (ReportEntry entry : entries) {
			String path = entry.getPath();
			Matcher matcher = pattern.matcher(path);
			while (matcher.find()) {
				String version = matcher.group();
				String qualifier = version
						.substring(version.lastIndexOf('.') + 1);
				path = path.replace(qualifier, "<qualifier>");
			}
			entry.setPath(path);
		}
	}

	private void sort() {
		Collections.sort(entries);
	}

	private void removeInnerClasses() {
		Iterator<ReportEntry> iter = entries.iterator();
		while (iter.hasNext()) {
			ReportEntry entry = iter.next();
			String path = entry.getPath();
			if (path.contains("$")) {
				String mainClassPath = getMainClassPath(path);
				if (isPathInReport(mainClassPath)) {
					iter.remove();
				} else {
					entry.setType(MODIFIED);
					entry.setPath(mainClassPath);
				}
			}
		}
	}

	private String getMainClassPath(String path) {
		assert (path.endsWith(".class"));
		int index = path.indexOf('$');
		return path.substring(0, index) + ".class";
	}

	private boolean isPathInReport(String path) {
		for (ReportEntry entry : entries) {
			if (entry.getPath().equals(path)) {
				return true;
			}
		}
		return false;
	}

}
