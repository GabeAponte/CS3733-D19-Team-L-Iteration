package Object;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class SceneGestures {

    PanAndZoomPane panAndZoomPane;

    ImageView imageView;
    double width;
    double height;

    private Circle startCircle;
    private Circle endCircle;
    private ArrayList<Line> lines;

    ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

    private static final int MIN_PIXELS = 235;


    public SceneGestures( PanAndZoomPane canvas, ImageView i) {
        this.panAndZoomPane = canvas;
        imageView = i;
        height = imageView.getImage().getHeight();
        width = imageView.getImage().getWidth();

        imageView.setPreserveRatio(true);
        reset(imageView, width, height);
    }

    public EventHandler<MouseEvent> getOnMouseClickedEventHandler() {
        return onMouseClickedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {
            Point2D mousePress = imageViewToImage(imageView, new Point2D(event.getX(), event.getY()));
            mouseDown.set(mousePress);
        }

    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
            Point2D oldPoint = getImageLocation();


            Point2D dragPoint = imageViewToImage(imageView, new Point2D(event.getX(), event.getY()));
            shift(imageView, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(imageView, new Point2D(event.getX(), event.getY())));
            redrawPath(oldPoint, getImageScale());
        }
    };

    // reset to the top left:
    public void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    // shift the viewport of the imageView by the specified delta, clamping so
    // the viewport does not move off the actual imageView:
    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    // convert mouse coordinates in the imageView to coordinates in the actual imageView:
    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }

    /**
     * Mouse wheel handler: zoom to pivot point
     */
    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent event) {
            double oldScale = getImageScale();
            Point2D oldPoint = getImageLocation();

            double delta = -event.getDeltaY();
            Rectangle2D viewport = imageView.getViewport();

            double scale = clamp(Math.pow(1.01, delta),

                    // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),

                    // don't scale so that we're bigger than imageView dimensions:
                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())
            );

            Point2D mouse = imageViewToImage(imageView, new Point2D(event.getX(), event.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;

            // To keep the visual point under the mouse from moving, we need
            // (x - newViewportMinX) / (x - currentViewportMinX) = scale
            // where x is the mouse X coordinate in the imageView

            // solving this for newViewportMinX gives

            // newViewportMinX = x - (x - currentViewportMinX) * scale

            // we then clamp this value so the imageView never scrolls out
            // of the imageview:

            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));

            redrawPath(oldPoint, oldScale);
        }
    };



    /**
     * Mouse click handler
     */
    private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getClickCount() == 2) {
                Point2D oldPoint = getImageLocation();
                double oldScale = getImageScale();
                reset(imageView, width, height);
                redrawPath(oldPoint, oldScale);
            }
        }
    };

    public double getImageScale(){
        return imageView.getImage().getWidth()/imageView.getViewport().getWidth();
    }

    public Point2D getImageLocation(){
        return new Point2D(imageView.getViewport().getMinX(), imageView.getViewport().getMinY());
    }


    public void setDrawPath(Circle sCircle, Circle eCircle, ArrayList<Line> l){
        startCircle = sCircle;
        endCircle = eCircle;
        lines = l;
    }

    @SuppressWarnings("Duplicates")
    private void redrawPath(Point2D oldPointUpper, double oldScale){
        if(startCircle != null) {

            startCircle.setCenterX(((startCircle.getCenterX()/(0.137*oldScale)+oldPointUpper.getX())-getImageLocation().getX())*0.137*getImageScale());
            startCircle.setCenterY(((startCircle.getCenterY()/(0.137*oldScale)+oldPointUpper.getY())-getImageLocation().getY())*0.137*getImageScale());
            startCircle.setRadius(Math.max(2.5,2.5f*getImageScale()/5));

            endCircle.setCenterX(((endCircle.getCenterX()/(0.137*oldScale)+oldPointUpper.getX())-getImageLocation().getX())*0.137*getImageScale());
            endCircle.setCenterY(((endCircle.getCenterY()/(0.137*oldScale)+oldPointUpper.getY())-getImageLocation().getY())*0.137*getImageScale());
            endCircle.setRadius(Math.max(2.5,2.5f*getImageScale()/5));

            for (int i = 0; i < lines.size(); i++) {
                Line line = lines.get(i);

                line.setStartX(((line.getStartX()/(0.137*oldScale)+oldPointUpper.getX())-getImageLocation().getX())*0.137*getImageScale());
                line.setStartY(((line.getStartY()/(0.137*oldScale)+oldPointUpper.getY())-getImageLocation().getY())*0.137*getImageScale());
                line.setEndX(((line.getEndX()/(0.137*oldScale)+oldPointUpper.getX())-getImageLocation().getX())*0.137*getImageScale());
                line.setEndY(((line.getEndY()/(0.137*oldScale)+oldPointUpper.getY())-getImageLocation().getY())*0.137*getImageScale());

                line.setStrokeWidth(Math.max(1,getImageScale()/8));
            }
        }
    }
}