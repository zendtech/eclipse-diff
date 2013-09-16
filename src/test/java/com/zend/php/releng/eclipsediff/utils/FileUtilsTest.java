package com.zend.php.releng.eclipsediff.utils;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class FileUtilsTest {

	/**
	 * Directory /root_dir is not a plugin.
	 */
	@Test
	public void testIsPlugin_RootDir() {
		File mock = createMock_RootDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * File .../plugins/test.jar is a plugin.
	 */
	@Test
	public void testIsPlugin_JarInsidePluginsDir() {
		File mock = createMock_JarInsidePluginsDir();
		assertTrue(FileUtils.isPlugin(mock));
	}

	/**
	 * File /root_dir/test.jar is not a plugin.
	 */
	@Test
	public void testIsPlugin_JarOutsidePluginsDir() {
		File mock = createMock_JarOutsidePluginsDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * File .../plugins/sub_dir/test.jar is not a plugin.
	 */
	@Test
	public void testIsPlugin_JarInsidePluginsSubDir() {
		File mock = createMock_JarInsidePluginsSubDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * File .../plugins/sub_dir/plugins/test.jar is not a plugin.
	 */
	@Test
	public void testIsPlugin_JarInsideInnerPluginsDir() {
		File mock = createMock_JarInsideInnerPluginsDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * File .../plugins/test.txt is not a plugin.
	 */
	@Test
	public void testIsPlugin_TextFileInsidePluginsDir() {
		File mock = createMock_TextFileInsidePluginsDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * Directory .../plugins/test_dir is a plugin.
	 */
	@Test
	public void testIsPlugin_DirInsidePluginsDir() {
		File mock = createMock_DirInsidePluginsDir();
		assertTrue(FileUtils.isPlugin(mock));
	}

	/**
	 * Directory /root_dir/test_dir is not a plugin.
	 */
	@Test
	public void testIsPlugin_DirOutsidePluginsDir() {
		File mock = createMock_DirOutsidePluginsDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * Directory .../plugins/sub_dir/test_dir is not a plugin.
	 */
	@Test
	public void testIsPlugin_DirInsidePluginsSubDir() {
		File mock = createMock_DirInsidePluginsSubDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	/**
	 * Directory .../plugins/sub_dir/plugins/test_dir is not a plugin.
	 */
	@Test
	public void testIsPlugin_DirInsideInnerPluginsDir() {
		File mock = createMock_DirInsideInnerPluginsDir();
		assertFalse(FileUtils.isPlugin(mock));
	}

	private File createMock_RootDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("root_dir");
		expect(mock.getParentFile()).andReturn(null);
		replay(mock);
		return mock;
	}

	private File createMock_PluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("plugins");
		expect(mock.getParentFile()).andReturn(createMock_RootDir());
		replay(mock);
		return mock;
	}

	private File createMock_PluginsSubDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("sub_dir");
		expect(mock.getParentFile()).andReturn(createMock_PluginsDir());
		replay(mock);
		return mock;
	}

	private File createMock_InnerPluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("plugins");
		expect(mock.getParentFile()).andReturn(createMock_PluginsSubDir());
		replay(mock);
		return mock;
	}

	private File createMock_JarInsidePluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(true);
		expect(mock.isDirectory()).andReturn(false);
		expect(mock.getName()).andReturn("test.jar");
		expect(mock.getParentFile()).andReturn(createMock_PluginsDir());
		replay(mock);
		return mock;
	}

	private File createMock_JarOutsidePluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(true);
		expect(mock.isDirectory()).andReturn(false);
		expect(mock.getName()).andReturn("test.jar");
		expect(mock.getParentFile()).andReturn(createMock_RootDir());
		replay(mock);
		return mock;
	}

	private File createMock_JarInsidePluginsSubDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(true);
		expect(mock.isDirectory()).andReturn(false);
		expect(mock.getName()).andReturn("test.jar");
		expect(mock.getParentFile()).andReturn(createMock_PluginsSubDir());
		replay(mock);
		return mock;
	}

	private File createMock_JarInsideInnerPluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(true);
		expect(mock.isDirectory()).andReturn(false);
		expect(mock.getName()).andReturn("test.jar");
		expect(mock.getParentFile()).andReturn(createMock_InnerPluginsDir());
		replay(mock);
		return mock;
	}

	private File createMock_TextFileInsidePluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(true);
		expect(mock.isDirectory()).andReturn(false);
		expect(mock.getName()).andReturn("test.txt");
		expect(mock.getParentFile()).andReturn(createMock_PluginsDir());
		replay(mock);
		return mock;
	}

	private File createMock_DirInsidePluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("test_dir");
		expect(mock.getParentFile()).andReturn(createMock_PluginsDir());
		replay(mock);
		return mock;
	}

	private File createMock_DirOutsidePluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("test_dir");
		expect(mock.getParentFile()).andReturn(createMock_RootDir());
		replay(mock);
		return mock;
	}

	private File createMock_DirInsidePluginsSubDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("test_dir");
		expect(mock.getParentFile()).andReturn(createMock_PluginsSubDir());
		replay(mock);
		return mock;
	}

	private File createMock_DirInsideInnerPluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("test_dir");
		expect(mock.getParentFile()).andReturn(createMock_InnerPluginsDir());
		replay(mock);
		return mock;
	}

}
