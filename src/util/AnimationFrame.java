package util;

import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Transparency;

public class AnimationFrame extends BufferedImage {
    /**
     * This class is used to store the frames of an animation
     * It is used in the AnimatedSprite class
     */

    private int frameDelay = 1;

    /**
     * This constructor is used to create a frame of an Animated Sprite.
     * The frame of the AnimationFrame is set to frame
     */

    public AnimationFrame(BufferedImage frame) {
        super(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = this.getGraphics();
        g.drawImage(frame, 0, 0, null);
        g.dispose();
    }

    public AnimationFrame copy() {
        BufferedImage imageCopy = new BufferedImage(this.getWidth(), this.getHeight(), this.getType());
        Graphics g = imageCopy.getGraphics();
        g.drawImage(this, 0, 0, null);
        g.dispose();
        return new AnimationFrame(imageCopy, frameDelay);
    }

    public AnimationFrame(BufferedImage frame, int frameDelay) {
        super(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.frameDelay = frameDelay;
        Graphics g = this.getGraphics();
        g.drawImage(frame, 0, 0, null);
        g.dispose();
    }

    /** This method is used to set the image of the frame */
    public void setFrame(BufferedImage frame) {
        Graphics2D g = this.createGraphics();

        // Set the composite to AlphaComposite.Clear to clear the area
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Set the composite to AlphaComposite.Src to draw the new image
        g.setComposite(AlphaComposite.Src);
        g.drawImage(frame, 0, 0, null);
        g.dispose();
    }

    /** This method is used to get the delay of the frame */
    public int getFrameDelay() {
        return frameDelay;
    }

    /** This method is used to set the delay of the frame */
    public void setFrameDelay(int frameDelay) {
        this.frameDelay = frameDelay;
    }

    private BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();

        // Create a new BufferedImage with an alpha channel
        BufferedImage transparentImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics gTransparent = transparentImage.getGraphics();
        gTransparent.drawImage(image, 0, 0, null);
        gTransparent.dispose();

        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(transparentImage, null);
        g.dispose();
        return result;
    }

    private GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

    public void rotate(double angle) {
        setFrame(rotate(this, angle));
    }
}
