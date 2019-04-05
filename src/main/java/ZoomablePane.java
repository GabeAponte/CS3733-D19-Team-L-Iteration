import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
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

            System.out.println(content.getChildren().get(0));

            System.out.println(this.getParent().getParent().getParent().getParent().getParent());
            if(this.getParent().getParent().getParent().getParent().getParent() instanceof JFXScrollPane){
                if(content.getChildren().get(0) instanceof ImageView) {
                    ImageView map = (ImageView) content.getChildren().get(0);
                    JFXScrollPane parent = (JFXScrollPane) this.getParent().getParent().getParent().getParent().getParent();
                    parent.setScrollBarSize((content.getScaleX()-1)*685, currentZoom*464);
                }
            }


//            System.out.println(currentZoom);
        });
    }


    public void addChildren(Node n){
        content.getChildren().add(n);
    }
}
