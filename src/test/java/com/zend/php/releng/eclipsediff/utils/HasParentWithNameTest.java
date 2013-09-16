package com.zend.php.releng.eclipsediff.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class HasParentWithNameTest {

	/**
	 * <code>null</code> has not a "plugins" parent directory.
	 */
	@Test
	public void testHasPluginsParent_NullFile() {
		assertFalse(FileUtils.hasParentWithName(null, "plugins"));
	}

	/**
	 * <code>null</code> for parent name throws IllegalArgumentException.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testHasPluginsParent_NullParentName() {
		File mock = Mock.rootDir();
		FileUtils.hasParentWithName(mock, null);
	}

	/**
	 * Directory /root_dir has not a "plugins" parent directory.
	 */
	@Test
	public void testHasPluginsParent_RootDir() {
		File mock = Mock.rootDir();
		assertFalse(FileUtils.hasParentWithName(mock, "plugins"));
	}

	/**
	 * Directory .../plugins has not a "plugins" parent directory.
	 */
	@Test
	public void testHasPluginsParent_PluginsDir() {
		File mock = Mock.pluginsDir();
		assertFalse(FileUtils.hasParentWithName(mock, "plugins"));
	}

	/**
	 * Directory .../plugins/sub_dir has a "plugins" parent directory.
	 */
	@Test
	public void testHasPluginsParent_PluginsSubDir() {
		File mock = Mock.pluginsSubDir();
		assertTrue(FileUtils.hasParentWithName(mock, "plugins"));
	}

	/**
	 * Directory .../plugins/sub_dir/plugins has a "plugins" parent directory.
	 */
	@Test
	public void testHasPluginsParent_InnerPluginsDir() {
		File mock = Mock.innerPluginsDir();
		assertTrue(FileUtils.hasParentWithName(mock, "plugins"));
	}

	/**
	 * File .../plugins/test.txt has a "plugins" parent directory.
	 */
	@Test
	public void testHasPluginsParent_TextFileInsidePluginsDir() {
		File mock = Mock.textFileInsidePluginsDir();
		assertTrue(FileUtils.hasParentWithName(mock, "plugins"));
	}

	/**
	 * File /root_dir/test.jar has not a "plugins" parent directory.
	 */
	@Test
	public void testHasPluginsParent_JarFileOutsidePluginsDir() {
		File mock = Mock.jarOutsidePluginsDir();
		assertFalse(FileUtils.hasParentWithName(mock, "plugins"));
	}

}
