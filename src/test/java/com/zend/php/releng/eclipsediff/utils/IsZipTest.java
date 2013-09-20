package com.zend.php.releng.eclipsediff.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class IsZipTest {

	/**
	 * File <code>null</code> is not a ZIP file.
	 */
	@Test
	public void testIsZip_Null() {
		assertFalse(FileUtils.isZip(null));
	}

	/**
	 * File "test.zip" is a ZIP file.
	 */
	@Test
	public void testIsZip_TestZip() {
		File mock = Mock.fileWithName("test.zip");
		assertTrue(FileUtils.isZip(mock));
	}

	/**
	 * File "test.ZIP" is a ZIP file.
	 */
	@Test
	public void testIsZip_TestZIP() {
		File mock = Mock.fileWithName("test.ZIP");
		assertTrue(FileUtils.isZip(mock));
	}

	/**
	 * File "test.txt" is not a ZIP file.
	 */
	@Test
	public void testIsZip_TestTxt() {
		File mock = Mock.fileWithName("test.txt");
		assertFalse(FileUtils.isZip(mock));
	}

	/**
	 * Directory "test.zip" is not a ZIP file.
	 */
	@Test
	public void testIsZip_Directory() {
		File mock = Mock.dirWithName("test.zip");
		FileUtils.isZip(mock);
	}

}
