package edu.wpi.cs3733.d19.teamL.API;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//TAKES PICTURE USING WEBCAM AND DETECTS FACE IN IT
public class faceDetectionJavaFXX {
    //static{
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    //}
    Mat matrix = null;

    public WritableImage capureFrame() {
        WritableImage writableImage = null;

        // Loading the OpenCV core library
        //System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // Instantiating the VideoCapture class (camera:: 0)
        VideoCapture capture = new VideoCapture(0);

        // Reading the next video frame from the camera
        Mat matrix = new Mat();
        capture.read(matrix);

        // If there is next video frame
        if (capture.read(matrix)) {
            /////// Detecting the face in the snap /////
            File f = new File("lbpcascade_frontalface.xml");
            String file = f.getAbsolutePath();
            file = file.replace('\\', '/');
            CascadeClassifier classifier = new CascadeClassifier(file);

            MatOfRect faceDetections = new MatOfRect();
            classifier.detectMultiScale(matrix, faceDetections);
            System.out.println(String.format("Detected %s faces",
                    faceDetections.toArray().length));

            // Drawing boxes
            for (Rect rect : faceDetections.toArray()) {
                Imgproc.rectangle(
                        matrix,                                   //where to draw the box
                        new Point(rect.x, rect.y),                            //bottom left
                        new Point(rect.x + rect.width, rect.y + rect.height), //top right
                        new Scalar(0, 0, 255)                                 //RGB colour
                );
            }
            // Creating BuffredImage from the matrix
            BufferedImage image = new BufferedImage(matrix.width(), matrix.height(),
                    BufferedImage.TYPE_3BYTE_BGR);

            WritableRaster raster = image.getRaster();
            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
            byte[] data = dataBuffer.getData();
            matrix.get(0, 0, data);

            this.matrix = matrix;

            // Creating the Writable Image
            writableImage = SwingFXUtils.toFXImage(image, null);
            if(faceDetections.toArray().length == 0){
                saveImage();
                return null;
            }
            capture.release();
        }
        saveImage();

        return writableImage;
    }
    public void saveImage() {
        // Saving the Image
        File f = new File("TempOutput.jpg");
        String file = f.getAbsolutePath();
        file = file.replace('\\', '/');

        // Instantiating the imagecodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        // Saving it again
        imageCodecs.imwrite(file, matrix);
    }
    /*public static void main(String args[]) {
        launch(args);
    }*/
}