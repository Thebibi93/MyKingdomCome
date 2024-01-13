package util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Image;

public class AnimatedSprite {
    /*
     * Implements an animated sprite using sprite methods
     * The sprite is animated by changing the image of the sprite
     * at a given interval
     */

    private int animationFrameCount = 0;
    private double rotation = 0;
    private ArrayList<AnimationFrame> spriteSheet = new ArrayList<AnimationFrame>();
    private boolean stopped = false;
    private int currentFrame = 0;
    private AnimationFinishedListener finishedListener;

    /**
     * This constructor is used to create an AnimatedSprite
     */
    public AnimatedSprite() {

    }

    public int getAnimationFrameCount() {
        return animationFrameCount;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public AnimatedSprite(AnimatedSprite a){
        this.animationFrameCount = a.animationFrameCount;
        this.rotation = a.rotation;
        this.spriteSheet = a.spriteSheet;
        this.stopped = a.stopped;
        this.currentFrame = a.currentFrame;
        this.finishedListener = a.finishedListener;
    }

    /**
     * This constructor is used to create an AnimatedSprite using a BufferedImage
     */
    public AnimatedSprite(int width, int height, int x, int y, BufferedImage img) {
        img.getSubimage(x, y, width, height);
        spriteSheet.add(new AnimationFrame(img));
    }

    /**
     * This constructor is used to create an AnimatedSprite using an Array of
     * AnimationFrame
     */
    public AnimatedSprite(ArrayList<AnimationFrame> spriteSheet) {
        this.spriteSheet = spriteSheet;
        animationFrameCount = 0;
        currentFrame = 0;
        stopped = true;
    }

    public AnimatedSprite resize(int newWidth, int newHeight) {
        AnimatedSprite res = new AnimatedSprite();
        
        for (int i = 0; i < spriteSheet.size(); i++) {
            AnimationFrame originalFrame = spriteSheet.get(i);
            BufferedImage originalImage = originalFrame;
    
            // Use getScaledInstance to resize the image
            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_REPLICATE);
    
            // Convert the scaled Image back to a BufferedImage
            BufferedImage bufferedResizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedResizedImage.createGraphics();
            g2d.drawImage(resizedImage, 0, 0, null);
            g2d.dispose();
    
            // Create a new AnimationFrame with the resized BufferedImage
            AnimationFrame resizedFrame = new AnimationFrame(bufferedResizedImage, originalFrame.getFrameDelay());
    
            // Add the resized frame to the result AnimatedSprite
            res.addFrame(resizedFrame, originalFrame.getFrameDelay());
        }
        
        return res;
    }

    /** This method is used to get the next frame of the animated sprite */
    private void nextFrame() {
        if (!stopped) {
            animationFrameCount++;
            if (animationFrameCount >= spriteSheet.get(currentFrame).getFrameDelay()) {
                currentFrame++;
                animationFrameCount = 0;
            }
            if (currentFrame >= spriteSheet.size()) {
                currentFrame = 0;
                if (finishedListener != null) {
                    finishedListener.onAnimationFinished();
                }
                animationFrameCount = 0;
            }
        }
    }

    public boolean isFinished() {
        return currentFrame == spriteSheet.size() - 1 && animationFrameCount == 0;
    }

    public static AnimatedSprite resize(AnimatedSprite originalImage, int cellSize) {
        AnimatedSprite res = new AnimatedSprite();
        for (int i = 0; i < originalImage.getNumberOfFrames(); i++) {
            AnimationFrame frame = (AnimationFrame) originalImage.getFrame(i);
            int originalDelay = frame.getFrameDelay();
            Image tmp = originalImage.getFrame(i).getScaledInstance(cellSize, cellSize, Image.SCALE_AREA_AVERAGING);
            BufferedImage resizedImage = new BufferedImage(cellSize, cellSize, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            res.addFrame(resizedImage, originalDelay);
        }
        return res;
    }

    public void rotate(double angle){
        for (AnimationFrame frame : spriteSheet) {
            frame.rotate(angle);
        }
        this.rotation += angle;
    }

    public double getRotation() {
        return rotation;
    }

    public double setRotation(double angle) {
        return rotation = angle;
    }

    /** This method is used to display the next frame */
    public synchronized BufferedImage displayNextFrame() {
        nextFrame();
        return spriteSheet.get(currentFrame);
    }

    /** This method is used to display the current frame */
    public BufferedImage displayCurrentFrame() {
        return spriteSheet.get(currentFrame);
    }

    /** This method is used to add a frame to the animation */
    public void addFrame(BufferedImage frame) {
        spriteSheet.add(new AnimationFrame(frame));
    }

    /**
     * This method is used to add a frame to the spriteSheet with a given frameDelay
     */
    public void addFrame(BufferedImage frame, int frameDelay) {
        spriteSheet.add(new AnimationFrame(frame));
        spriteSheet.get(spriteSheet.size() - 1).setFrameDelay(frameDelay);
    }

    /** This method is used to stop the animation */
    public void stop() {
        stopped = true;
    }

    /** This method is used to check if the animation is stopped */
    public boolean isStopped() {
        return stopped;
    }

    /** This method is used to reset the animation */
    public void reset() {
        currentFrame = 0;
        animationFrameCount = 0;
    }

    /** This method is used to set the position of the animated sprite */
    public void setPosition(int x, int y) {
        for (AnimationFrame frame : spriteSheet) {
            frame.getSubimage(x, y, frame.getWidth(), frame.getHeight());
        }
    }

    /** This method is used to play the animation */
    public void play() {
        stopped = false;
        nextFrame();
    }

    public interface AnimationFinishedListener {
        void onAnimationFinished();
    }

    public void setOnFinished(AnimationFinishedListener listener) {
        finishedListener = listener;
    }

    public int getWidth() {
        return spriteSheet.get(currentFrame).getWidth();
    }

    public int getHeight() {
        return spriteSheet.get(currentFrame).getHeight();
    }

    public void setFrame(int frame, BufferedImage image) {
        spriteSheet.get(frame).setFrame(image);
    }

    public BufferedImage getFrame(int frame) {
        return spriteSheet.get(frame);
    }

    public int getNumberOfFrames() {
        return spriteSheet.size();
    }
}
