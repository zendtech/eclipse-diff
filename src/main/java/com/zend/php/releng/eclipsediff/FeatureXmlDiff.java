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

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.Node;

import com.zend.php.releng.eclipsediff.report.Report;

public class FeatureXmlDiff extends FileDiff {

	private static final List<String> IGNORE_ATTRS = Arrays
			.asList(new String[] { "version", "download-size", "install-size" });

	protected FeatureXmlDiff(File original, File other) {
		super(original, other);
	}

	@Override
	public void execute(Report report) throws Exception {
		Diff xmlDiff = new Diff(new FileReader(originalPath), new FileReader(
				otherPath));
		xmlDiff.overrideDifferenceListener(new IgnoreVariableAttributesDifferenceListener());
		if (!xmlDiff.similar()) {
			report.add(MODIFIED, originalPath);
		}
	}

	private class IgnoreVariableAttributesDifferenceListener implements
			DifferenceListener {

		@Override
		public int differenceFound(Difference difference) {
			if (isIgnoredAttributeDifference(difference)) {
				return RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
			} else {
				return RETURN_ACCEPT_DIFFERENCE;
			}
		}

		@Override
		public void skippedComparison(Node control, Node test) {
			// nothing to do
		}

		private boolean isIgnoredAttributeDifference(Difference difference) {
			return difference.getId() == DifferenceConstants.ATTR_VALUE_ID
					&& IGNORE_ATTRS.contains(difference.getControlNodeDetail()
							.getNode().getNodeName());
		}
	}

}
