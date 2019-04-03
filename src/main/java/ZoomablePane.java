import javafx.scene.Group;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;

public class ZoomablePane extends AnchorPane {

    final double SCALE_DELTA = 1.1;
    double currentZoom;

    public Group content = new Group();

    public ZoomablePane() {
        super();
        currentZoom = 1.0;
        getChildren().add(content);
        content.setAutoSizeChildren(true);
        setOnScroll((ScrollEvent event) -> {
            event.consume();
            if (event.getDeltaY() == 0 || (event.getDeltaY() < 0 && currentZoom <= 1.0001)) {
                return;
            }

            double scaleFactor
                    = (event.getDeltaY() > 0)
                    ? SCALE_DELTA
                    : 1 / SCALE_DELTA;

            content.setScaleX(content.getScaleX() * scaleFactor);
            content.setScaleY(content.getScaleY() * scaleFactor);
            currentZoom = currentZoom * scaleFactor;

            System.out.println(currentZoom);
        });
    }

}
