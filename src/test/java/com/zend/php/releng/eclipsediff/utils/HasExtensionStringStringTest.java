package com.zend.php.releng.eclipsediff.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HasExtensionStringStringTest {

	/**
	 * <code>null</code> file name has not a </code>null</code> extension.
	 */
	@Test
	public void testHasExtensionStringString_NullFile_NullExtension() {
		assertFalse(FileUtils.hasExtension((String) null, (String) null));
	}

	/**
	 * <code>null</code> file name has not an empty extension.
	 */
	@Test
	public void testHasExtensionStringString_NullFile() {
		assertFalse(FileUtils.hasExtension((String) null, ""));
	}

	/**
	 * File "test" has a <code>null</code> extension.
	 */
	@Test
	public void testHasExtensionStringString_TestFile_NullExtension() {
		assertTrue(FileUtils.hasExtension("test", (String) null));
	}

	/**
	 * File "test." has a <code>null</code> extension.
	 */
	@Test
	public void testHasExtensionStringString_TestDotFile_NullExtension() {
		assertTrue(FileUtils.hasExtension("test.", (String) null));
	}

	/**
	 * File "test.txt" has not a <code>null</code> extension.
	 */
	@Test
	public void testHasExtensionStringString_TestTxtFile_NullExtension() {
		assertFalse(FileUtils.hasExtension("test.txt", (String) null));
	}

	/**
	 * File "test" has an empty extension.
	 */
	@Test
	public void testHasExtensionStringString_TestFile_EmptyExtension() {
		assertTrue(FileUtils.hasExtension("test", ""));
	}

	/**
	 * File "test." has an empty extension.
	 */
	@Test
	public void testHasExtensionStringString_TestDotFile_EmptyExtension() {
		assertTrue(FileUtils.hasExtension("test.", ""));
	}

	/**
	 * File "test.txt" has not an empty extension.
	 */
	@Test
	public void testHasExtensionStringString_TestTxtFile_EmptyExtension() {
		assertFalse(FileUtils.hasExtension("test.txt", ""));
	}

	/**
	 * File "test.txt" has a "txt" extension.
	 */
	@Test
	public void testHasExtensionStringString_TestTxtFile_TxtExtension() {
		assertTrue(FileUtils.hasExtension("test.txt", "txt"));
	}

	/**
	 * File "test.txt" has not an "atxt" extension.
	 */
	@Test
	public void testHasExtensionStringString_TestTxtFile_AtxtExtension() {
		assertFalse(FileUtils.hasExtension("text.txt", "atxt"));
	}

}
