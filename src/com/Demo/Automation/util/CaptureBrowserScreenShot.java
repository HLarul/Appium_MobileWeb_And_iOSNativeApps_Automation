package com.Demo.Automation.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

public class CaptureBrowserScreenShot {
	List<ArrayList<BufferedImage>> imageslist;

	public void seleniumCaptureBrowserScreenShot(WebDriver driver,
			String ImgPath) throws InterruptedException, IOException {
		int height_covered = 0, width_covered = 0, pageHeightLeft = 0, pageWidthLeft = 0;
		JavascriptExecutor js = (JavascriptExecutor) driver;

		Long pageheight1 = (Long) js.executeScript("return window.innerHeight");
		Long maxPageHeight1 = (Long) js
				.executeScript("return Math.max(document.documentElement.scrollHeight, document.body.scrollHeight,"
						+ "document.documentElement.clientHeight, window.innerHeight)");
		float sections = (float) maxPageHeight1 / pageheight1;
		int numberOfRows = (int) Math.ceil(sections); // denotes the number of
														// rows.

		imageslist = new ArrayList<ArrayList<BufferedImage>>(numberOfRows);

		Long pageWidth1 = (Long) js.executeScript("return window.innerWidth");
		Long maxPageWidth1 = (Long) js
				.executeScript("return Math.max(document.documentElement.scrollWidth, document.body.scrollWidth,"
						+ "document.documentElement.clientWidth, window.innerWidth)");
		sections = (float) maxPageWidth1 / pageWidth1;
		int numberOfCols = (int) Math.ceil(sections); // denotes the number of
														// columns.

		int pageheight = pageheight1.intValue();
		int maxPageHeight = maxPageHeight1.intValue();
		int pageWidth = pageWidth1.intValue();
		int maxPageWidth = maxPageWidth1.intValue();

		System.out.println("HTML Page height=" + pageheight
				+ "   Max Page Height=" + maxPageHeight + "   Rows="
				+ numberOfRows);
		System.out.println("HTML Page Width=" + pageWidth
				+ "   Max Page Width=" + maxPageWidth + "   Columns="
				+ numberOfCols);

		for (int row = 0; row < numberOfRows; row++) {

			ArrayList<BufferedImage> rowArr = new ArrayList<BufferedImage>(
					numberOfCols);
			imageslist.add(rowArr);
			width_covered = 0;
			pageHeightLeft = maxPageHeight - height_covered;

			for (int col = 0; col < numberOfCols; col++) {
				pageWidthLeft = maxPageWidth - width_covered;
				takeScreenshot(driver, row, col, ImgPath);
				System.out.println("\nRow=" + row + "  Col=" + col
						+ "  PageWidthLeft=" + pageWidthLeft
						+ "  PageHeightLeft=" + pageHeightLeft);

				if ((pageHeightLeft < pageheight)
						&& (pageWidthLeft < pageWidth)) {
					System.out
							.println("Page Width and Height both are at extreme!");
				} else if (pageHeightLeft < pageheight) {

					System.out.println("....Page Height needs cropping...");
					/* Crops the Image */
					BufferedImage lastScreenShot = rowArr
							.get(rowArr.size() - 1);
					int lastScreenShotHeight = lastScreenShot.getHeight();
					System.out.println("Last Image height="
							+ lastScreenShotHeight);

					double pagePercent = ((double) (pageheight - pageHeightLeft) / pageheight) * 100;
					System.out.println("Page Remove Percent from top="
							+ pagePercent);

					double imageToBeCropped = Math
							.round(((double) (pagePercent / 100))
									* lastScreenShotHeight);
					System.out
							.println("Height of Image to be cropped from top="
									+ imageToBeCropped);
					System.out.println("Height of resultant image="
							+ (lastScreenShotHeight - imageToBeCropped));

					BufferedImage subImage = lastScreenShot.getSubimage(0,
							(int) imageToBeCropped, lastScreenShot.getWidth(),
							(int) (lastScreenShotHeight - imageToBeCropped));
					// ImageIO.write(subImage, "jpg", new File(ImgPath +
					// row+"_"+col+"_1.jpg"));
					rowArr.set(rowArr.size() - 1, subImage);

				} else if (pageWidthLeft < pageWidth) {

					System.out
							.println("Page Width needs cropping...Size of Images array: "
									+ rowArr.size());
					/* Crops the Image */
					BufferedImage lastScreenShot = rowArr
							.get(rowArr.size() - 1);
					int lastScreenShotWidth = lastScreenShot.getWidth();
					System.out.println("Last Image Width="
							+ lastScreenShotWidth);

					double pagePercent = ((double) (pageWidth - pageWidthLeft) / pageWidth) * 100;
					System.out.println("Percent of UnUseful Page="
							+ pagePercent);

					double imageToBeCropped = Math
							.round(((double) (pagePercent / 100))
									* lastScreenShotWidth);
					System.out
							.println("Width of Image to be cropped from Left="
									+ imageToBeCropped);
					System.out.println("Width of resultant image="
							+ (lastScreenShotWidth - imageToBeCropped));

					BufferedImage subImage = lastScreenShot.getSubimage(
							(int) imageToBeCropped, 0,
							(int) (lastScreenShotWidth - imageToBeCropped),
							lastScreenShot.getHeight());
					// ImageIO.write(subImage, "jpg", new File(ImgPath +
					// row+"_"+col+".jpg"));
					rowArr.set(rowArr.size() - 1, subImage);

				} else {
					if (maxPageWidth == pageWidth) { // No action on width has
														// to be taken.
						break;
					}
					js.executeScript("window.scrollBy(" + pageWidth + ",0)");
					width_covered = width_covered + pageWidth;

					System.out.println("Horizontally Scrolled window by now "
							+ pageWidth + "  pixels. Row=" + row);
				}
				Thread.sleep(1000);
			}

			// js.executeScript("window.scrollBy(0,"+pageheight+")");
			height_covered = height_covered + pageheight;
			js.executeScript("window.scrollTo(0," + height_covered + ")");

			System.out.println("Vertically Scrolled window by now "
					+ height_covered + "  pixels.");
			Thread.sleep(1000);
		}
		try {
			joinImages(ImgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void takeScreenshot(WebDriver driver, int row, int col,
			String ImgPath) {
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		TakesScreenshot driver_secreenshot = (TakesScreenshot) augmentedDriver;

		// If you are executing the test case using only ChromeDriver then use
		// the below code instead. You dont need Augmenter.
		// TakesScreenshot driver_secreenshot=(TakesScreenshot)driver;

		File file = driver_secreenshot.getScreenshotAs(OutputType.FILE);
		if (file != null)
			try {
				BufferedImage image = ImageIO.read(file);
				imageslist.get(row).add(image);
				// ImageIO.write(image, "jpg", new
				// File("D:\\Github\\QACompetencyCenter\\AppiumAndroidTest\\snapshots\\screenshot"+row+"_"+col+".jpg"));
				// ImageIO.write(image, "jpg", new File(ImgPath +
				// row+"_"+col+".jpg"));

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void joinImages(String ImgPath) throws IOException {

		int resultImageHeight = 0, resultImageWidth = 0;

		System.out.println("Height of all Images are:");
		for (ArrayList<BufferedImage> arr : imageslist) {
			BufferedImage image = arr.get(0);
			System.out.println(image.getHeight());
			resultImageHeight = resultImageHeight + image.getHeight();
		}
		System.out.println("Width of all Images are:");
		ArrayList<BufferedImage> arrImage = imageslist.get(0);
		for (BufferedImage image : arrImage) {
			System.out.println(image.getWidth());
			resultImageWidth = resultImageWidth + image.getWidth();
		}
		System.out.println("Final Image Height=" + resultImageHeight
				+ "  Width=" + resultImageWidth);

		BufferedImage resultImage = new BufferedImage(resultImageWidth,
				resultImageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics g = resultImage.createGraphics();

		int imageBelow = 0;
		for (ArrayList<BufferedImage> arr : imageslist) {
			int imageSide = 0;
			for (BufferedImage screenshot : arr) {

				g.drawImage(screenshot, imageSide, imageBelow, null);
				imageSide = imageSide + screenshot.getWidth();
			}
			imageBelow = imageBelow + arr.get(0).getHeight();
		}
		ImageIO.write(resultImage, "jpg", new File(ImgPath));
	}


}
