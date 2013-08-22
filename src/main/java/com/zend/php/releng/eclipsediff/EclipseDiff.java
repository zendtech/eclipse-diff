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
import static com.zend.php.releng.eclipsediff.report.ReportEntryType.MODIFIED;
import static com.zend.php.releng.eclipsediff.report.ReportEntryType.REMOVED;

import java.io.File;

import com.zend.php.releng.eclipsediff.report.Report;

public class EclipseDiff extends FolderDiff {

	private static final String HORIZONTAL_LINE = "=============================================================";

	public EclipseDiff(File original, File other) {
		super(original, other);
	}

	public void execute() throws Exception {
		this.execute(new Report(originalPath, otherPath));
	}

	@Override
	public void execute(Report report) throws Exception {
		printTitle();
		printLegend();
		printSkippedResources();

		super.execute(report);

		report.print();
	}

	private void printTitle() {
		System.out.println("Comparing Eclipse installations:");
		System.out.println("\t" + originalPath);
		System.out.println("\t" + otherPath);
		System.out.println(HORIZONTAL_LINE);
	}

	private void printLegend() {
		System.out.println("Legend:");
		System.out.println("\t" + ADDED
				+ " File or folder added in second build");
		System.out.println("\t" + REMOVED
				+ " File or folder removed in second build");
		System.out.println("\t" + MODIFIED + " File modified in second build");
		System.out.println(HORIZONTAL_LINE);
	}

	private void printSkippedResources() {
		System.out.println("Resources with the following names are skipped:");
		for (String name : SKIP) {
			System.out.println("\t" + name);
		}
		for (String name : IGNORE_FILE_EXT) {
			System.out.println("\t*." + name);
		}
		System.out.println(HORIZONTAL_LINE);
	}

	public static void main(String[] args) throws Exception {

		File root1 = new File("/home/raev/Downloads/ZendStudio-10.1.0-715/");
		File root2 = new File(
				"/home/raev/git/studio-core/studio-product/target/products/ZendStudio");

		new EclipseDiff(root1, root2).execute();
	}
}
