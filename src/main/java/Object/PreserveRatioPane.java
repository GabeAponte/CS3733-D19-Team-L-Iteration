package Object;

import javafx.scene.layout.Pane;

import java.awt.*;

public class PreserveRatioPane extends Pane {

    private double aspectRatio;

    public PreserveRatioPane(double ar){
        super();
        aspectRatio = ar;
    }

    public void rescale(Dimension imgSize, Dimension boundary){
        double original_width = imgSize.width;
        double original_height = imgSize.height;
        double bound_width = boundary.width;
        double bound_height = boundary.height;
        double new_width = original_width;
        double new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        this.setMaxSize(new_width, new_height);
    }
}
