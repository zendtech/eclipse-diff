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
		File mock = Mock.fileWithName("test");
		assertTrue(FileUtils.hasExtension(mock, null));
	}

	/**
	 * File "test." has a <code>null</code> extension.
	 */
	@Test
	public void testHasExtensionFileString_TestDotFile_NullExtension() {
		File mock = Mock.fileWithName("test.");
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
		File mock = Mock.fileWithName("test");
		assertTrue(FileUtils.hasExtension(mock, ""));
	}

	/**
	 * File "test." has an empty extension.
	 */
	@Test
	public void testHasExtensionFileString_TestDotFile_EmptyExtension() {
		File mock = Mock.fileWithName("test.");
		assertTrue(FileUtils.hasExtension(mock, ""));
	}

	/**
	 * File "test.txt" has not an empty extension.
	 */
	@Test
	public void testHasExtensionFileString_TestTxtFile_EmptyExtension() {
		File mock = Mock.fileWithName("test.txt");
		assertFalse(FileUtils.hasExtension(mock, ""));
	}

	/**
	 * File "test.txt" has a "txt" extension.
	 */
	@Test
	public void testHasExtensionFileString_TestTxtFile_TxtExtension() {
		File mock = Mock.fileWithName("test.txt");
		assertTrue(FileUtils.hasExtension(mock, "txt"));
	}

	/**
	 * File "test.txt" has a "TXT" extension.
	 */
	@Test
	public void testHasExtensionFileString_TestTxtFile_TXTExtension() {
		File mock = Mock.fileWithName("test.txt");
		assertTrue(FileUtils.hasExtension(mock, "TXT"));
	}

	/**
	 * File "test.TXT" has a "txt" extension.
	 */
	@Test
	public void testHasExtensionFileString_TestTXTFile_TxtExtension() {
		File mock = Mock.fileWithName("test.TXT");
		assertTrue(FileUtils.hasExtension(mock, "txt"));
	}

	/**
	 * File "test.txt" has not an "atxt" extension.
	 */
	@Test
	public void testHasExtensionFileString_TestTxtFile_AtxtExtension() {
		File mock = Mock.fileWithName("test.txt");
		assertFalse(FileUtils.hasExtension(mock, "atxt"));
	}

	/**
	 * Directory "test.txt" has not a "txt" extension.
	 */
	@Test
	public void testHasExtensionFileString_Directory() {
		File mock = Mock.dirWithName("test.txt");
		assertFalse(FileUtils.hasExtension(mock, "txt"));
	}

}
