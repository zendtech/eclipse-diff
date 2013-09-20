package com.zend.php.releng.eclipsediff.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class HasExtensionFileStringTest {

	/**
	 * <code>null</code> file has not a </code>null</code> extension.
	 */
	@Test
	public void testHasExtensionFileString_NullFile_NullExtension() {
		assertFalse(FileUtils.hasExtension((File) null, null));
	}

	/**
	 * <code>null</code> file has not an empty extension.
	 */
	@Test
	public void testHasExtensionFileString_NullFile() {
		assertFalse(FileUtils.hasExtension((File) null, ""));
	}

	/**
	 * File "test" has a <code>null</code> extension.
	 */
	@Test
	public void testHasExtensionFileString_TestFile_NullExtension() {
		File mock = Mock.testFile();
		assertTrue(FileUtils.hasExtension(mock, null));
	}

	/**
	 * File "test." has a <code>null</code> extension.
	 */
	@Test
	public void testHasExtensionFileString_TestDotFile_NullExtension() {
		File mock = Mock.testDotFile();
		assertTrue(FileUtils.hasExtension(mock, null));
	}

	/**
	 * File "test.txt" has not a <code>null</code> extension.
	 */
	@Test
	public void testHasExtensionFileString_TestTxtFile_NullExtension() {
		File mock = Mock.textFileInsidePluginsDir();
		assertFalse(FileUtils.hasExtension(mock, null));
	}

	/**
	 * File "test" has an empty extension.
	 */
	@Test
	public void testHasExtensionFileString_TestFile_EmptyExtension() {
		File mock = Mock.testFile();
		assertTrue(FileUtils.hasExtension(mock, ""));
	}

	/**
	 * File "test." has an empty extension.
	 */
	@Test
	public void testHasExtensionFileString_TestDotFile_EmptyExtension() {
		File mock = Mock.testDotFile();
		assertTrue(FileUtils.hasExtension(mock, ""));
	}

	/**
	 * File "test.txt" has not an empty extension.
	 */
	@Test
	public void testHasExtensionFileString_TestTxtFile_EmptyExtension() {
		File mock = Mock.textFileInsidePluginsDir();
		assertFalse(FileUtils.hasExtension(mock, ""));
	}

	/**
	 * File "test.txt" has a "txt" extension.
	 */
	@Test
	public void testHasExtensionFileString_TestTxtFile_TxtExtension() {
		File mock = Mock.textFileInsidePluginsDir();
		assertTrue(FileUtils.hasExtension(mock, "txt"));
	}

	/**
	 * File "test.txt" has not an "atxt" extension.
	 */
	@Test
	public void testHasExtensionFileString_TestTxtFile_AtxtExtension() {
		File mock = Mock.textFileInsidePluginsDir();
		assertFalse(FileUtils.hasExtension(mock, "atxt"));
	}

	/**
	 * Passing directory throws an <code>IllegalArgumentException</code>.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testHasExtensionFileString_Directory() {
		File mock = Mock.rootDir();
		FileUtils.hasExtension(mock, null);
	}

}
