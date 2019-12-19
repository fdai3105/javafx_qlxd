package sample.models;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class ImageData implements Serializable, Cloneable {
    private static final long serialVersionUID = -8851026854074051816L;

    private byte []  imagedata;

    public ImageData(Image image, String format) throws IOException {
        BufferedImage bufferedimage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedimage, format, bos );
        imagedata = bos.toByteArray();
    }

    public Image readImageFromArray() throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imagedata);
        BufferedImage bufferedimage = ImageIO.read(bis);
        return SwingFXUtils.toFXImage(bufferedimage, null);
    }

    @Override
    protected ImageData clone() throws CloneNotSupportedException {
        return (ImageData)super.clone();
    }
}
