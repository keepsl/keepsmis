package com.keeps.tools.utils;

import com.keeps.tools.common.SoftUtils;
import com.keeps.tools.exception.CapecException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import org.hibernate.cache.CacheException;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
/**
 * <p>
 * Title: FileUtils.java
 * </p>
 * <p>
 * Description: @TODO
 * </p>
 * <p>
 * Copyright: Copyright (c) KEEPS
 * </p>
 * 
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月8日 修改日期： 修改人： 复审人：
 */
public class FileUtils implements SoftUtils {

	public static final String FILE_SYSTEM_NAME = "TheFileNameIsSystemName";
	public static final String FILE_EXPAND_RAR = ".rar";

	private static Properties readPropertiesFromFile(File file) {
		Properties p = null;
		InputStream is = null;
		try {
			p = new Properties();
			is = new FileInputStream(file);
			p.load(is);
		} catch (IOException e) {
			throw new CapecException(e.getMessage());
		} finally {
			IoUtils.close(is);
		}
		return p;
	}

	private static String[] getKeysFromFile(File file, String[] keys) {
		Properties pr = readPropertiesFromFile(file);
		List list = new ArrayList();
		for (String key : keys) {
			list.add(pr.getProperty(key));
		}
		return ((String[]) list.toArray(new String[0]));
	}

	public static String[] getKeysFromPath(String path, String[] keys) {
		return getKeysFromFile(getFile(path), keys);
	}

	public static File getFile(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
		} catch (Exception e) {
			throw new CapecException("文件读取失败,文件不存在![001]");
		}
		return file;
	}

	public static synchronized File uploadFile(MultipartFile mulFile, String filePath) {
		File file = null;
		try {
			validatePath(filePath);

			String mulFileName = mulFile.getOriginalFilename();
			String expand = mulFileName.substring(mulFileName.lastIndexOf("."));
			String fullName = RandomUtils.randomUUID() + expand;

			mulFile.transferTo(new File(filePath, fullName));
		} catch (CapecException e1) {
			throw e1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CacheException("文件上传失败!");
		}
		return file;
	}

	public static File getFile(String filePath, boolean creator) {
		File file = null;
		try {
			file = new File(filePath);
			if ((creator) && (!(file.exists())))
				file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CapecException("文件读取失败,文件不存在![001]");
		}
		return file;
	}

	public static void validatePath(String path) {
		File file = getFile(path);
		if (!(file.exists()))
			file.mkdirs();
	}

	public static File validatePath(String path, String fileName, String expand) {
		if ("TheFileNameIsSystemName".equals(fileName)) {
			fileName = getSystemFileName();
		}

		validatePath(path);

		String newFileName = path + "\\" + fileName;
		if (expand != null) {
			newFileName = newFileName + expand;
		}

		File toFile = getFile(newFileName, true);

		if (!(toFile.exists())) {
			try {
				toFile.createNewFile();
			} catch (IOException e) {
				throw new CapecException("创建文件错误!请检查路径!");
			}
		}

		return toFile;
	}

	private static String getSystemFileName() {
		return RandomUtils.randomUUID();
	}

	public static File validateFile(String comFilePath, String toPath) {
		return validateFile(comFilePath, toPath, "TheFileNameIsSystemName");
	}

	public static File validateFile(String comFilePath, String toPath, String newFileName) {
		Assert.isTrue(StringUtils.hasText(comFilePath), "无来源路径!");
		Assert.isTrue(StringUtils.hasText(toPath), "无保存路径");
		File comFile = getFile(comFilePath);

		String comFileName = comFile.getName();
		File toFilePath = getFile(toPath);
		if (!(toFilePath.exists())) {
			toFilePath.mkdirs();
		}

		String toFileAbsPath = "";
		if ("TheFileNameIsSystemName".equals(newFileName)) {
			toFileAbsPath = toPath + "\\" + getSystemFileName();
		} else if (StringUtils.hasText(newFileName))
			toFileAbsPath = toPath + "\\" + newFileName;
		else {
			toFileAbsPath = toPath + "\\" + comFileName;
		}

		File toFile = getFile(toFileAbsPath);
		if (!(toFile.exists())) {
			try {
				toFile.createNewFile();
			} catch (IOException e) {
				throw new CapecException("文件传输出错!创建文件错误!请检查路径!");
			}
		}

		return toFile;
	}

	public static File copyFile(String comFilePath, String toPath) {
		return copyFile(comFilePath, toPath, "TheFileNameIsSystemName");
	}

	public static File copyFile(String comFilePath, String toPath, String fileName) {
		InputStream is = null;
		OutputStream os = null;
		File toFile = null;
		try {
			if ("TheFileNameIsSystemName".equals(fileName)) {
				fileName = getSystemFileName();
			}
			toFile = validateFile(comFilePath, toPath, fileName);

			is = new FileInputStream(comFilePath);

			os = new FileOutputStream(toFile);

			byte[] b = new byte[is.available()];
			while (is.read(b) != -1)
				os.write(b);
		} catch (Exception e) {
			throw new CapecException("系统找不到指定文件，原因如下:  1,当前文件不在所运行的服务器上.");
		} finally {
			IoUtils.flush(os);
			IoUtils.close(os);
			IoUtils.close(is);
		}
		return toFile;
	}

	public static File validateFileAndExpand(File file, String toPath, String newFileName, String expand) {
		Assert.isTrue(file != null, "无来源路径!");
		Assert.isTrue(StringUtils.hasText(toPath), "无保存路径");

		File toFilePath = getFile(toPath);
		if (!(toFilePath.exists())) {
			toFilePath.mkdirs();
		}

		String toFileAbsPath = "";
		if ("TheFileNameIsSystemName".equals(newFileName)) {
			toFileAbsPath = toPath + "/" + getSystemFileName();
		} else if (StringUtils.hasText(newFileName))
			toFileAbsPath = toPath + "/" + newFileName;
		else {
			toFileAbsPath = toPath + "/" + file.getName();
		}

		return getFile(toFileAbsPath + expand, true);
	}

	public static File uploadFull(File file, String toPathFull, String fileName, String expand) {
		InputStream is = null;
		OutputStream os = null;
		File toFile = null;
		try {
			toFile = validateFileAndExpand(file, toPathFull, fileName, expand);

			is = new FileInputStream(file);
			os = new FileOutputStream(toFile);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = is.read(b)) != -1)
				os.write(b, 0, i);
		} catch (Exception e) {
			throw new CapecException(e.getMessage());
		} finally {
			IoUtils.flush(os);
			IoUtils.close(os);
			IoUtils.close(is);
		}
		return toFile;
	}

	public static String getExpandName(String imgName) {
		if (imgName == null) {
			return ".unknow";
		}
		String in = imgName.toLowerCase();
		if (in.endsWith("jpeg"))
			return ".jpg";
		if (in.endsWith("png"))
			return ".png";
		if (in.endsWith("bmp"))
			return ".bmp";
		if (in.endsWith("gif")) {
			return ".gif";
		}

		return ".jpeg";
	}

	public static byte[] downloadFile(String downFilePath) {
		try {
			return downloadFile(getFile(downFilePath));
		} catch (Exception e) {
			throw new CapecException(e.getMessage());
		}
	}

	public static byte[] downloadFile(File file) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(file);
			os = new ByteArrayOutputStream();

			int len = 0;
			byte[] b = new byte[is.available()];
			while ((len = is.read(b)) > 0) {
				os.write(b, 0, len);
				IoUtils.flush(os);
			}

			return b;
		} catch (Exception e) {
		} finally {
			IoUtils.close(os);
			IoUtils.close(is);
		}
		return null;
	}

	public static void removeFile(String path) {
		Assert.isTrue(StringUtils.hasText(path), "路径为空!");
		try {
			File file = getFile(path);

			if (!(file.isDirectory())) {
				file.delete();
				return;
			}
			if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; ++i) {
					File delfile = new File(path + "\\" + filelist[i]);
					if (!(delfile.isDirectory())) {
						delfile.delete();
					} else if (delfile.isDirectory()) {
						removeFile(path + "\\" + filelist[i]);
					}
				}
				file.delete();
			}
		} catch (Exception e) {
			throw new CapecException("删除文件失败!");
		}
	}

	public static void removeInnerFile(String path) {
		Assert.isTrue(StringUtils.hasText(path), "路径为空!");
		try {
			File file = getFile(path);

			if ((!(file.isDirectory())) || (!(file.isDirectory())))
				return;
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; ++i) {
				File delfile = new File(path + "\\" + filelist[i]);
				if (!(delfile.isDirectory()))
					delfile.delete();
				else if (delfile.isDirectory())
					removeFile(path + "\\" + filelist[i]);
			}
		} catch (Exception e) {
			throw new CapecException("删除文件失败!");
		}
	}

	public static File createImage(String path, String name, String expand, byte[] b) throws Exception {
		File file = validatePath(path, name, expand);
		ImageOutputStream ios = null;
		try {
			ios = new FileImageOutputStream(file);
			ios.write(b);
		} catch (Exception localException) {
		} finally {
			if (ios != null) {
				ios.flush();
				ios.close();
			}
		}
		return file;
	}

	public static void createFile(String path,String name,String content){
		File file = validatePath(path, name, null);
		OutputStreamWriter osw = null;
		PrintWriter pw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			pw = new PrintWriter(osw);
			pw.write(content.toCharArray());
			osw.flush();
		} catch (Exception localException) {
		} finally {
			IoUtils.close(osw);
			if(pw!=null)
				pw.close();
		}
	}
	public static void main(String[] s) {
		String as = "aaab.jgp";
		System.out.println(".".intern());
		createFile("F:\\testupdatefile", "load.vm", "dddd");
	}
}
