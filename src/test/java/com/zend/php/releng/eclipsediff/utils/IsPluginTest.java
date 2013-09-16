package com.zend.php.releng.eclipsediff.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class IsPluginTest {

	/**
	 * <code>null</code> is not a plugin.
	 */
	@Test
	public void testIsPlugin_Null() {
		assertFalse(FileUtils.isPlugin(null));
	}

	/**
	 * Directory /root_dir is not a plugin.
	 */
	@Test
	public void testIsPlugin_RootDir() {
		File mock = Mock.rootDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * File .../plugins/test.jar is a plugin.
	 */
	@Test
	public void testIsPlugin_JarInsidePluginsDir() {
		File mock = Mock.jarInsidePluginsDir();
		assertTrue(FileUtils.isPlugin(mock));
	}

	/**
	 * File /root_dir/test.jar is not a plugin.
	 */
	@Test
	public void testIsPlugin_JarOutsidePluginsDir() {
		File mock = Mock.jarOutsidePluginsDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * File .../plugins/sub_dir/test.jar is not a plugin.
	 */
	@Test
	public void testIsPlugin_JarInsidePluginsSubDir() {
		File mock = Mock.jarInsidePluginsSubDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * File .../plugins/sub_dir/plugins/test.jar is not a plugin.
	 */
	@Test
	public void testIsPlugin_JarInsideInnerPluginsDir() {
		File mock = Mock.jarInsideInnerPluginsDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * File .../plugins/test.txt is not a plugin.
	 */
	@Test
	public void testIsPlugin_TextFileInsidePluginsDir() {
		File mock = Mock.textFileInsidePluginsDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * Directory .../plugins/test_dir is a plugin.
	 */
	@Test
	public void testIsPlugin_DirInsidePluginsDir() {
		File mock = Mock.dirInsidePluginsDir();
		assertTrue(FileUtils.isPlugin(mock));
	}

	/**
	 * Directory /root_dir/test_dir is not a plugin.
	 */
	@Test
	public void testIsPlugin_DirOutsidePluginsDir() {
		File mock = Mock.dirOutsidePluginsDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * Directory .../plugins/sub_dir/test_dir is not a plugin.
	 */
	@Test
	public void testIsPlugin_DirInsidePluginsSubDir() {
		File mock = Mock.dirInsidePluginsSubDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * Directory .../plugins/sub_dir/plugins/test_dir is not a plugin.
	 */
	@Test
	public void testIsPlugin_DirInsideInnerPluginsDir() {
		File mock = Mock.dirInsideInnerPluginsDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

}
