package com.zend.php.releng.eclipsediff.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HasExtensionStringStringArrayTest {

	/**
	 * <code>null</code> file name has not a </code>null</code> extension.
	 */
	@Test
	public void testHasExtensionStringStringAray_NullFile_NullExtension() {
		assertFalse(FileUtils.hasExtension((String) null, (String[]) null));
	}

	/**
	 * <code>null</code> file name has not an empty extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_NullFile() {
		assertFalse(FileUtils.hasExtension((String) null, new String[] { "" }));
	}

	/**
	 * File "test" has a <code>null</code> extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_TestFile_NullExtension() {
		assertTrue(FileUtils.hasExtension("test", (String[]) null));
	}

	/**
	 * File "test." has a <code>null</code> extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_TestDotFile_NullExtension() {
		assertTrue(FileUtils.hasExtension("test.", (String[]) null));
	}

	/**
	 * File "test.txt" has not a <code>null</code> extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_TestTxtFile_NullExtension() {
		assertFalse(FileUtils.hasExtension("test.txt", (String[]) null));
	}

	/**
	 * File "test" has an empty extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_TestFile_EmptyExtension() {
		assertTrue(FileUtils.hasExtension("test", new String[] { "" }));
	}

	/**
	 * File "test." has an empty extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_TestDotFile_EmptyExtension() {
		assertTrue(FileUtils.hasExtension("test.", new String[] { "" }));
	}

	/**
	 * File "test.txt" has not an empty extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_TestTxtFile_EmptyExtension() {
		assertFalse(FileUtils.hasExtension("test.txt", new String[] { "" }));
	}

	/**
	 * File "test.txt" has a "txt" extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_TestTxtFile_TxtExtension() {
		assertTrue(FileUtils.hasExtension("test.txt", new String[] { "txt" }));
	}

	/**
	 * File "test.txt" has a "TXT" extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_TestTxtFile_TXTExtension() {
		assertTrue(FileUtils.hasExtension("test.txt", new String[] { "TXT" }));
	}

	/**
	 * File "test.TXT" has a "txt" extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_TestTXTFile_TxtExtension() {
		assertTrue(FileUtils.hasExtension("test.TXT", new String[] { "txt" }));
	}

	/**
	 * File "test.txt" has not an "atxt" extension.
	 */
	@Test
	public void testHasExtensionStringStringArray_TestTxtFile_AtxtExtension() {
		assertFalse(FileUtils.hasExtension("text.txt", new String[] { "atxt" }));
	}

	/**
	 * File "test.txt" has an extension that matches "txt" or "doc".
	 */
	@Test
	public void testHasExtensionStringStringArray_TestTxtFile_TxtAtxtExtensions() {
		assertTrue(FileUtils.hasExtension("text.txt", new String[] { "txt",
				"doc" }));
	}

	/**
	 * File "test.txt" has an extension that does not match "atxt" and "doc".
	 */
	@Test
	public void testHasExtensionStringStringArray_TestTxtFile_AtxtDocExtensions() {
		assertFalse(FileUtils.hasExtension("text.txt", new String[] { "atxt",
				"doc" }));
	}

}
