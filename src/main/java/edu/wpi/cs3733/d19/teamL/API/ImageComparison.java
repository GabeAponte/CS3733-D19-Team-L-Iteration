package edu.wpi.cs3733.d19.teamL.API;

import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImageComparison {

    public double doIT(String username) {
        try {
            EmployeeAccess ea = new EmployeeAccess();
            BufferedImage img1 = ea.getEmpImg(username);
            if(img1 == null){
                return 100.0;
            }

            File outputfile = new File("DBInput.jpg");
            ImageIO.write(img1, "jpg", outputfile);

            BufferedImage img2 = ImageIO.read(new File("TempOutput.jpg"));

            if(img2 == null){
                return 100.0;
            }
            double p = getDifferencePercent(img1, img2);
            //System.out.println("diff percent: " + p);
            return p;
        } catch (Exception e){
            e.printStackTrace();
        }
        return 100.0;
    }

    public ArrayList<String> doWithAll(){
        ArrayList<String> result = new ArrayList<>();
        try {
            EmployeeAccess ea = new EmployeeAccess();
            ArrayList<BufferedImage> img1 = ea.getEmpImgs();
            if(img1.size() == 0){
                result.add("100.0");
                result.add("");
                return result;
            }

            BufferedImage img2 = ImageIO.read(new File("TempOutput.jpg"));

            if(img2 == null){
                result.add("100.0");
                result.add("");
                return result;
            }
            ArrayList<String> unames = ea.getEmpsWithImg();

            double bestDiff = 100;
            String bestUname = "";
            for(int i = 0; i < img1.size(); i++){
                double p = getDifferencePercent(img1.get(i), img2);
                if(p < bestDiff){
                    bestDiff = p;
                    bestUname = unames.get(i);
                }
            }
            result.add("" + bestDiff);
            result.add(bestUname);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private static double getDifferencePercent(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();
        if (width != width2 || height != height2) {
            return 100;
        }

        long diff = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
            }
        }
        long maxDiff = 3L * 255 * width * height;

        return 100.0 * diff / maxDiff;
    }

    private static int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >>  8) & 0xff;
        int b1 =  rgb1        & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >>  8) & 0xff;
        int b2 =  rgb2        & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }
}