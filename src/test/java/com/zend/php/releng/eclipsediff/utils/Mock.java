package com.zend.php.releng.eclipsediff.utils;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.io.File;

public class Mock {

	public static File rootDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("root_dir");
		expect(mock.getParentFile()).andReturn(null);
		replay(mock);
		return mock;
	}

	public static File pluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("plugins");
		expect(mock.getParentFile()).andReturn(rootDir());
		replay(mock);
		return mock;
	}

	public static File pluginsSubDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("sub_dir");
		expect(mock.getParentFile()).andReturn(pluginsDir());
		replay(mock);
		return mock;
	}

	public static File innerPluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("plugins");
		expect(mock.getParentFile()).andReturn(pluginsSubDir());
		replay(mock);
		return mock;
	}

	public static File jarInsidePluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(true);
		expect(mock.isDirectory()).andReturn(false);
		expect(mock.getName()).andReturn("test.jar");
		expect(mock.getParentFile()).andReturn(pluginsDir());
		replay(mock);
		return mock;
	}

	public static File jarOutsidePluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(true);
		expect(mock.isDirectory()).andReturn(false);
		expect(mock.getName()).andReturn("test.jar");
		expect(mock.getParentFile()).andReturn(rootDir());
		replay(mock);
		return mock;
	}

	public static File jarInsidePluginsSubDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(true);
		expect(mock.isDirectory()).andReturn(false);
		expect(mock.getName()).andReturn("test.jar");
		expect(mock.getParentFile()).andReturn(pluginsSubDir());
		replay(mock);
		return mock;
	}

	public static File jarInsideInnerPluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(true);
		expect(mock.isDirectory()).andReturn(false);
		expect(mock.getName()).andReturn("test.jar");
		expect(mock.getParentFile()).andReturn(innerPluginsDir());
		replay(mock);
		return mock;
	}

	public static File textFileInsidePluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(true);
		expect(mock.isDirectory()).andReturn(false);
		expect(mock.getName()).andReturn("test.txt");
		expect(mock.getParentFile()).andReturn(pluginsDir());
		replay(mock);
		return mock;
	}

	public static File dirInsidePluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("test_dir");
		expect(mock.getParentFile()).andReturn(pluginsDir());
		replay(mock);
		return mock;
	}

	public static File dirOutsidePluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("test_dir");
		expect(mock.getParentFile()).andReturn(rootDir());
		replay(mock);
		return mock;
	}

	public static File dirInsidePluginsSubDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("test_dir");
		expect(mock.getParentFile()).andReturn(pluginsSubDir());
		replay(mock);
		return mock;
	}

	public static File dirInsideInnerPluginsDir() {
		File mock = createMock(File.class);
		expect(mock.isFile()).andReturn(false);
		expect(mock.isDirectory()).andReturn(true);
		expect(mock.getName()).andReturn("test_dir");
		expect(mock.getParentFile()).andReturn(innerPluginsDir());
		replay(mock);
		return mock;
	}

}
