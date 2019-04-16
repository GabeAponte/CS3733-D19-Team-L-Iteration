package edu.wpi.cs3733.d19.teamL.API;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;

//TAKES PICTURE AND DETECTS FACE IN IT
public class FaceDetector {

    public void doIt() {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("\nRunning FaceDetector");

        File f = new File("haarcascade_frontalface_alt.xml");
        String file = f.getAbsolutePath();
        file = file.replace('\\', '/');
        CascadeClassifier faceDetector = new CascadeClassifier(file);
        //faceDetector.load("C:/Users/NWALZER/Desktop/SoftEng/EvenMoreFacialRecognition/src/haarcascade_frontalface_alt.xml");

        f = new File("TempOutput.jpg");
        file = f.getAbsolutePath();
        file = file.replace('\\', '/');
        Mat image = Imgcodecs.imread(file);
        System.out.println("GOT PICTURE");
        //GIVE IT A PICTURE OF A FACE AND IT WILL GET THE FACE OUT OF THE PICTURE
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        try {
            System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        } catch (Exception e) {
            System.err.println("ERROR IS HERE");
            e.printStackTrace();
        }

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }

        String filename = "TempOutput.png";
        System.out.println(String.format("Writing %s", filename));
        Imgcodecs.imwrite(filename, image);
    }
}