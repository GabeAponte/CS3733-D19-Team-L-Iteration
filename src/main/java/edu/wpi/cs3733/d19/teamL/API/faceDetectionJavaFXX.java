package edu.wpi.cs3733.d19.teamL.API;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.FileNotFoundException;
import java.io.IOException;

//TAKES PICTURE USING WEBCAM AND DETECTS FACE IN IT
public class faceDetectionJavaFXX extends Application {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    Mat matrix = null;

    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
        System.out.println("going");
        // Capturing the snapshot from the camera
        faceDetectionJavaFXX obj = new faceDetectionJavaFXX();
        WritableImage writableImage = obj.capureFrame();

        // Saving the image
        obj.saveImage();


        /*/ Setting the image view
        ImageView imageView = new ImageView(writableImage);

        // setting the fit height and width of the image view
        imageView.setFitHeight(400);
        imageView.setFitWidth(600);

        // Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        // Creating a Group object
        Group root = new Group(imageView);

        // Creating a scene object
        Scene scene = new Scene(root, 600, 400);

        // Setting title to the Stage
        stage.setTitle("Capturing an image");

        // Adding scene to the stage
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.show();*/
    }
    public WritableImage capureFrame() {
        System.out.println("capturing");
        WritableImage writableImage = null;

        // Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // Instantiating the VideoCapture class (camera:: 0)
        VideoCapture capture = new VideoCapture(0);

        // Reading the next video frame from the camera
        Mat matrix = new Mat();
        capture.read(matrix);

        // If camera is opened
        if(!capture.isOpened()) {
            System.out.println("camera not detected");
        } else
            System.out.println("Camera detected ");

        // If there is next video frame
        if (capture.read(matrix)) {
            /////// Detecting the face in the snap /////
            System.out.println("Test");
            String file = "C:/Users/NWALZER/Desktop/SoftEng/EvenMoreFacialRecognition/src/lbpcascade_frontalface.xml";
            //String file = getClass().getResource("/haarcascade_frontalface_alt.xml").getPath();
            CascadeClassifier classifier = new CascadeClassifier(file);
            System.out.println("Test 2");

            MatOfRect faceDetections = new MatOfRect();
            classifier.detectMultiScale(matrix, faceDetections);
            System.out.println(String.format("Detected %s faces",
                    faceDetections.toArray().length));
            if(faceDetections.toArray().length == 0){
                return null;
            }

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
            capture.release();
        }
        System.out.println("DONE");
        saveImage();
        return writableImage;
    }
    public void saveImage() {
        // Saving the Image
        String file = "C:/Users/NWALZER/Desktop/SoftEng/EvenMoreFacialRecognition/output2.jpg";

        // Instantiating the imagecodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        // Saving it again
        imageCodecs.imwrite(file, matrix);
    }
    public static void main(String args[]) {
        launch(args);
    }
}