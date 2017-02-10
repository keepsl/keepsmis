package com.keeps.tools.utils;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {

	public static boolean isImplOf(Class c1, Class interface1) {
		Assert.isTrue(c1 != null, "传值错误", true);
		if (interface1 == null) {
			return false;
		}
		if (c1.getName().equals(interface1.getName())) {
			return true;
		}
		Class[] faces = c1.getInterfaces();
		if ((faces == null) || (faces.length <= 0)) {
			return false;
		}
		for (Class face : faces) {
			if (interface1.getName().equals(face.getName())) {
				return true;
			}

		}

		if (c1.getSuperclass() != null) {
			return isImplOf(c1.getSuperclass(), interface1);
		}
		return false;
	}

	public static Object newInstance(Class clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Class getCurrentClassActualType(Class clazz) {
		Type t = clazz.getGenericSuperclass();
		Type[] params = ((ParameterizedType) t).getActualTypeArguments();
		return ((Class) params[0]);
	}

	public static ClassLoader getClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Exception localException) {
		}
		if (cl == null) {
			cl = ClassUtils.class.getClassLoader();
		}
		return cl;
	}

	public static Set<Class> getClasses(String packages) {
		Set classes = new LinkedHashSet();
		String packageDirName = packages.replace(".", "/");

		ClassLoader classLoader = getClassLoader();
		try {
			Enumeration dirs = classLoader.getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = (URL) dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol))
					findAndAddClassesInpackageFile(packages, URLDecoder.decode(url.getFile(), "UTF-8"), classes,
							classLoader);
				else if ("jar".equals(protocol))
					findAndAddClassesInpackageJar(url, packages, packageDirName, classes, classLoader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	private static void findAndAddClassesInpackageJar(URL url, String packagesName, String packageDirName,
			Set<Class> classes, ClassLoader classLoader) {
		try {
			JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
			Enumeration entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = (JarEntry) entries.nextElement();
				String name = entry.getName();
				if (name.charAt(0) == '/') {
					name = name.substring(1);
				}
				if (name.startsWith(packageDirName)) {
					int idx = name.lastIndexOf(47);
					if (idx != -1) {
						packagesName = name.substring(0, idx).replace('/', '.');
						if ((!(entry.isDirectory())) && (name.endsWith(".class"))) {
							String className = name.substring(packagesName.length() + 1, name.length() - 6);
							try {
								classes.add(classLoader.loadClass(packagesName + "." + className));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void findAndAddClassesInpackageFile(String packageName, String packagePath, Set<Class> classes,
			ClassLoader classLoader) {
		File dir = new File(packagePath);
		if ((!(dir.exists())) || (!(dir.isDirectory()))) {
			return;
		}
		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return ((file.isDirectory()) || (file.getName().endsWith(".class")));
			}
		});
		for (File file : dirfiles)
			if (file.isDirectory()) {
				findAndAddClassesInpackageFile(packageName + "." + file.getName(), file.getAbsolutePath(), classes,
						classLoader);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					classes.add(classLoader.loadClass(packageName + "." + className));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}

}
