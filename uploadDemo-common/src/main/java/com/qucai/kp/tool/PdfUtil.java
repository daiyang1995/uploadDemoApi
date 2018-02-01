package com.qucai.kp.tool;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class PdfUtil {

	/**
	 * 多个模板合并生成一个pdf<br>
	 * 第一个页面正常生成，从第二个页面起进行循环输出
	 * 
	 * @param templateDir
	 *            freemarker模板目录
	 * @param ftlName
	 *            freemarker模板文件名，多个
	 * @param shareBaseUrl
	 *            共享目录
	 * @param paramMap
	 *            freemarker参数
	 * @param destFilePath
	 *            生成的pdf文件全路径
	 * @return
	 * @throws Exception
	 */
	public static File genCombinePdfFile(String templateDir, String[] ftlName,
			String shareBaseUrl, Map<String, Object> paramMap,
			String destFilePath) throws Exception {
		File file = new File(destFilePath);
		FileOutputStream os = new FileOutputStream(file);

		ITextRenderer renderer = new ITextRenderer();
		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		// 需要保证覆盖freemarker模板页面中的字体font-family
		// 默认使用字体为宋体
		fontResolver.addFont(getResourceUri("/simsun.ttf"),
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		// 第一个页面正常生成
		String firstHtmlStr = useTemplate(templateDir, ftlName[0], paramMap);
		renderer.setDocumentFromString(firstHtmlStr);
		
		// 设置图片的相对路径，必须在文档生成之后设置
		if (StringUtils.isNotBlank(shareBaseUrl)) {
			renderer.getSharedContext().setBaseURL(shareBaseUrl);
		}

		renderer.layout();
		renderer.createPDF(os, false);

		// 从第二个页面起进行循环输出
		for (int i = 1; i < ftlName.length; i++) {
			String htmlStr = useTemplate(templateDir, ftlName[i], paramMap);
			renderer.setDocumentFromString(htmlStr);
			// 设置图片的相对路径，必须在文档生成之后设置
			if (StringUtils.isNotBlank(shareBaseUrl)) {
				renderer.getSharedContext().setBaseURL(shareBaseUrl);
			}
			renderer.layout();
			renderer.writeNextDocument();
		}
		renderer.finishPDF();

		return file;
	}

	/**
	 * 生成pdf
	 * 
	 * @param templateDir
	 *            freemarker模板目录
	 * @param ftlName
	 *            freemarker模板文件名
	 * @param shareBaseUrl
	 *            共享目录
	 * @param paramMap
	 *            freemarker参数
	 * @return
	 * @throws Exception
	 */
	public static byte[] genPdfFile(String templateDir, String ftlName,
			String shareBaseUrl, Map<String, Object> paramMap) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ITextRenderer renderer = new ITextRenderer();
		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		// 需要保证覆盖freemarker模板页面中的字体font-family
		// 默认使用字体为宋体
		fontResolver.addFont(getResourceUri("/simsun.ttf"),
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		// 读取模板，生成文档信息
		String htmlStr = useTemplate(templateDir, ftlName, paramMap);
		renderer.setDocumentFromString(htmlStr);
		
		// 设置图片的相对路径，必须在文档生成之后设置
		if (StringUtils.isNotBlank(shareBaseUrl)) {
			renderer.getSharedContext().setBaseURL(shareBaseUrl);
		}

		renderer.layout();
		renderer.createPDF(os);
		renderer.finishPDF();

		return os.toByteArray();
	}
	
	/**
	 * 生成pdf
	 * 
	 * @param templateDir
	 *            freemarker模板目录
	 * @param ftlName
	 *            freemarker模板文件名
	 * @param shareBaseUrl
	 *            共享目录
	 * @param paramMap
	 *            freemarker参数
	 * @param destFilePath
	 *            生成的pdf文件全路径
	 * @return
	 * @throws Exception
	 */
	public static File genPdfFile(String templateDir, String ftlName,
			String shareBaseUrl, Map<String, Object> paramMap,
			String destFilePath) throws Exception {
		File file = new File(destFilePath);
		FileOutputStream os = new FileOutputStream(file);

		ITextRenderer renderer = new ITextRenderer();
		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		// 需要保证覆盖freemarker模板页面中的字体font-family
		// 默认使用字体为宋体
		fontResolver.addFont(getResourceUri("/simsun.ttf"),
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		// 读取模板，生成文档信息
		String htmlStr = useTemplate(templateDir, ftlName, paramMap);
		renderer.setDocumentFromString(htmlStr);
		
		// 设置图片的相对路径，必须在文档生成之后设置
		if (StringUtils.isNotBlank(shareBaseUrl)) {
			renderer.getSharedContext().setBaseURL(shareBaseUrl);
		}

		renderer.layout();
		renderer.createPDF(os);
		renderer.finishPDF();

		return file;
	}

	/**
	 * 生成带有水印的pdf
	 * 
	 * @param orginPdfFilePath
	 *            待处理pdf
	 * @param destFilePath
	 *            生成的pdf文件全路径
	 * @return
	 * @throws Exception
	 */
	public static File genPdfWatermark4Sample(String orginPdfFilePath,
			String destFilePath) throws Exception {
		File file = new File(destFilePath);
		FileOutputStream os = new FileOutputStream(file);

		PdfReader reader = new PdfReader(orginPdfFilePath);
		PdfStamper stamper = new PdfStamper(reader, os);
		int pageNum = reader.getNumberOfPages() + 1;

		// 默认使用字体为宋体
		BaseFont font = BaseFont.createFont(getResourceUri("/simsun.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		// 透明度0.4
		PdfGState gs = new PdfGState();
		gs.setFillOpacity(0.4f);

		PdfContentByte content;
		for (int i = 1; i < pageNum; i++) {
			// 在内容上方加水印
			content = stamper.getOverContent(i);
			// 在内容下方加水印
			// content = stamper.getUnderContent(i);
			content.setGState(gs);

			content.beginText();

			// 设置水印文字
			content.setColorFill(Color.LIGHT_GRAY);
			content.setFontAndSize(font, 80);
			content.setTextMatrix(70, 200);
			content.showTextAligned(Element.ALIGN_CENTER, "内部文件 注意保密", 300,
					500, 45);

			// 设置图片
			Image image = Image.getInstance(getResourceUri("/jobDone.png"));
			image.setRotationDegrees(-30);
			image.setAbsolutePosition(360, 30);
			image.scaleToFit(200, 200);
			content.addImage(image);

			// 设置其他内容，如下载时间：yyyy-MM-dd HH:m:ss，下载人：xx
			content.setColorFill(Color.BLACK);
			content.setFontAndSize(font, 8);
			content.showTextAligned(Element.ALIGN_CENTER, "其他内容", 300, 10, 0);

			content.endText();
		}
		stamper.close();
		os.close();

		return file;
	}

	/**
	 * 获取freemarker模板
	 * 
	 * @param templateDir
	 *            freemarker模板目录
	 * @param ftlName
	 *            freemarker模板文件名
	 * @param paramMap
	 *            freemarker参数
	 * @return
	 * @throws Exception
	 */
	public static String useTemplate(String templateDir, String ftlName,
			Map<String, Object> paramMap) throws Exception {
		Template tpl = getFreemarkerConfig(templateDir).getTemplate(ftlName);
		StringWriter writer = new StringWriter();
		tpl.process(paramMap, writer);
		writer.flush();
		return writer.toString();
	}

	/**
	 * 获取Freemarker配置
	 * 
	 * @param templateDir
	 * @return
	 * @throws IOException
	 */
	private static Configuration getFreemarkerConfig(String templateDir)
			throws IOException {
		Configuration config = new Configuration(Configuration.VERSION_2_3_23);
		config.setDirectoryForTemplateLoading(new File(templateDir));
		config.setEncoding(Locale.CHINA, "utf-8");
		return config;
	}

	public static String getRootPath() {
		return PdfUtil.class.getResource("/").getPath();
	}

	public static String getRootUri() {
		return PdfUtil.class.getResource("/").toString();
	}

	public static String getClassLoadPath() {
		return PdfUtil.class.getResource("").getPath();
	}

	public static String getClassLoadUri() {
		return PdfUtil.class.getResource("").toString();
	}
	
	public static String getResourcePath(String resource) {
		return PdfUtil.class.getResource(resource).getPath();
	}
	
	public static String getResourceUri(String resource) {
		return PdfUtil.class.getResource(resource).toString();
	}

	/**
	 * 提取pdf中的图片，并存储，提取的图片名称格式为：destImgPrefix+index.suffix
	 * 
	 * @param pdfFile 待提取的pdf文件
	 * @param destDir 目标文件夹，必须以"/"结尾
	 * @param destImgPrefix 提取的图片前缀
	 * @throws IOException
	 */
	public static void extractImage(String pdfFile, String destDir, String destImgPrefix)
			throws IOException {
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(pdfFile));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		PDPageTree pages = document.getDocumentCatalog().getPages();

		Iterator<PDPage> iter = pages.iterator();
		int i = 1;
		while (iter.hasNext()) {
			PDPage page = (PDPage) iter.next();
			PDResources resources = page.getResources();
			Iterator<COSName> y = resources.getXObjectNames().iterator();
			while (y.hasNext()) {
				COSName u = y.next();
				if (resources.isImageXObject(u)) {
					PDImageXObject image = (PDImageXObject) resources.getXObject(u);
					String suffix = image.getSuffix();

					FileOutputStream output = new FileOutputStream(String.format(destDir + destImgPrefix + "%d.%s", i, suffix));
					BufferedImage bi = image.getImage();
					ImageIO.write(bi, suffix, output);
					i++;
				}
			}
		}
	}
	
	/**
	 * 提取pdf中的图片，以List<byte[]>返回图片数据
	 * 
	 * @param pdfFile 待提取的pdf文件
	 * @return
	 * @throws IOException
	 */
	public static List<byte[]> extractImage(String pdfFile)
			throws IOException {
		List<byte[]> rs = new ArrayList<byte[]>();
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(pdfFile));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		PDPageTree pages = document.getDocumentCatalog().getPages();

		Iterator<PDPage> iter = pages.iterator();
		while (iter.hasNext()) {
			PDPage page = (PDPage) iter.next();
			PDResources resources = page.getResources();
			Iterator<COSName> y = resources.getXObjectNames().iterator();
			while (y.hasNext()) {
				COSName u = y.next();
				if (resources.isImageXObject(u)) {
					PDImageXObject image = (PDImageXObject) resources.getXObject(u);
					String suffix = image.getSuffix();

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					BufferedImage bi = image.getImage();
					ImageIO.write(bi, suffix, baos);
					rs.add(baos.toByteArray());
				}
			}
		}
		return rs;
	}
	
	/**
	 * 将pdf按页全部转换为图片，以List<byte[]>返回图片数据
	 * 
	 * @param pdfFile 待转换的pdf文件
	 * @param destDir 目标文件夹，必须以"/"结尾
	 * @param destImgPrefix 转换的图片前缀
	 * @param destImgType 转换的图片类型，如："jpg"、"png"
	 * @param dpi 转换的图片dpi，数值越大、越清晰、速度越慢
	 */
	public static void convertPdf2Image(String pdfFile, String destDir,
			String destImgPrefix, String destImgType, int dpi) {
		PDDocument document;
		try {
			document = PDDocument.load(new File(pdfFile));
			int totalPage = document.getNumberOfPages();

			PDFRenderer renderer = new PDFRenderer(document);
			for (int i = 0; i < totalPage; i++) {
				File dstFile = new File(destDir + destImgPrefix + i + "." + destImgType);
				BufferedImage bi = renderer.renderImageWithDPI(i, dpi);
				ImageIO.write(bi, destImgType, dstFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将pdf按页全部转换为图片，转换的图片名称格式为：destImgPrefix+index.destImgType
	 * 
	 * @param pdfFile 待转换的pdf文件
	 * @param destImgType 转换的图片类型，如："jpg"、"png"
	 * @param dpi 转换的图片dpi，数值越大、越清晰、速度越慢
	 */
	public static List<byte[]> convertPdf2Image(String pdfFile, String destImgType, int dpi) {
		List<byte[]> rs = new ArrayList<byte[]>();
			
		PDDocument document;
		try {
			document = PDDocument.load(new File(pdfFile));
			int totalPage = document.getNumberOfPages();

			PDFRenderer renderer = new PDFRenderer(document);
			for (int i = 0; i < totalPage; i++) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedImage bi = renderer.renderImageWithDPI(i, dpi);
				
				ImageIO.write(bi, destImgType, baos);
				rs.add(baos.toByteArray());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void main(String[] args) throws Exception {
		
//		System.out.println(getRootPath());
//		System.out.println(getRootUri());
//		System.out.println(getClassLoadPath());
//		System.out.println(getClassLoadUri());
//		
//		PdfUtil.genPdfFile(getRootPath(), "pdfTemplate.html",
//				getRootUri(), null, "D:/testPdf.pdf");
//
//		PdfUtil.genPdfWatermark4Sample("D:/testPdf.pdf",
//				"D:/testPdfWatermark.pdf");
//
//		PdfUtil.genCombinePdfFile(getRootPath(), new String[] {
//				"pdfTemplate.html", "pdfTemplate.html" }, getRootUri(),
//				null, "D:/testCombinePdf.pdf");
		
//		PdfUtil.extractImage("D:/FreeMarker+中文手册.pdf", "D:/pdf/", "output");
//		PdfUtil.convertPdf2Image("D:/FreeMarker+中文手册.pdf", "D:/pdf/", "output", "png", 120);
		
	}

}
