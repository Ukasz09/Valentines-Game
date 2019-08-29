package com.Ukasz09.ValentineGame.gameModules.sprites.creatures;

import com.Ukasz09.ValentineGame.gameModules.sprites.effects.imageByPositionEffect.ProperImageSet;
import com.Ukasz09.ValentineGame.gameModules.utilitis.ViewManager;
import com.Ukasz09.ValentineGame.soundsModule.soundsPath.SoundsPlayer;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.beans.Transient;

public abstract class Sprite {

    public enum YAxisDirection {
        LEFT, RIGHT;
    }

    private Image actualImage;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    protected double lives;
    protected double maxLives;
    private int protectionTime;
    private double lastRotate;
    private double actualRotate;
    private ProperImageSet imageSetWay;

    private SoundsPlayer hitSound;
    private SoundsPlayer deathSound;
    private SoundsPlayer missSound;

    private ViewManager manager;
    private YAxisDirection imageDirection;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Sprite(Image actualImage, ViewManager manager) {
        this.manager = manager;
        this.actualImage = actualImage;
        width = actualImage.getWidth();
        height = actualImage.getHeight();
        lives = 3;
        maxLives = lives;
        missSound = null;
        protectionTime = 0;
        actualRotate = 0;
        lastRotate = actualRotate;
        imageSetWay = new ProperImageSet();
        imageDirection = YAxisDirection.RIGHT;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean shieldIsActive() {
        if (getProtectionTime() <= 0) return false;

        return true;
    }

    public void update(double time) {
        updatePosition(time);
    }

    public void updatePosition(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }


    public void render() {
        drawActualImage(actualRotate, imageDirection);
        drawBoundaryForTests();
    }

    public void drawActualImage(double rotate, YAxisDirection direction) {
        if (direction.equals(YAxisDirection.LEFT) || (rotate != 0)) {
            ImageView iv = new ImageView(actualImage);

            if (rotate != 0 && direction.equals(YAxisDirection.LEFT)) {
                iv.setScaleX(-1);
                iv.setRotate(rotate);
            } else if (rotate != 0)
                iv.setRotate(rotate);
            else iv.setScaleX(-1);

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            Image rotatedImage = iv.snapshot(params, null);
            manager.getGraphicContext().drawImage(rotatedImage, positionX, positionY);
        } else manager.getGraphicContext().drawImage(actualImage, positionX, positionY);
    }

    public float getAngleToTarget(Sprite target) {
        double dx = this.getPositionX();
        double dy = this.getPositionY();
        double diffX = target.getPositionX() - dx;
        double diffY = target.getPositionY() - dy;
        float angle = (float) Math.atan2(diffY, diffX);

        return angle;
    }

    //TEMP
    public void drawBoundaryForTests() {
        double tmpPosX = getBoundary().getMinX();
        double tmpPosY = getBoundary().getMinY();
        double tmpWidth = getBoundary().getWidth();
        double tmpHeight = getBoundary().getHeight();
        Paint p = new Color(0.6, 0.6, 0.6, 0.3);
        manager.getGraphicContext().setFill(p);
        manager.getGraphicContext().fillRect(tmpPosX, tmpPosY, tmpWidth, tmpHeight);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Sprite s) {
        return (s.getBoundary().intersects(this.getBoundary()));
    }

    public void addPositionX(double offset) {
        positionX += offset;
    }

    public void addPositionY(double offset) {
        positionY += offset;
    }

    public void removeLives(double howMany) {
        lives -= howMany;
    }

    public void removeProtectionTime(int value) {
        protectionTime -= value;
    }

    public boolean boundaryCollisionFromLeftSide(double atLeftBorder) {
        if ((this.getBoundary().getMinX()) <= atLeftBorder) return true;
        else return false;
    }

    public boolean boundaryCollisionFromRightSide(double atRightBorder) {
        if ((this.getBoundary().getMaxX()) >= atRightBorder) return true;
        else return false;
    }

    public boolean boundaryCollisionFromBottom(double atBottomBorder) {
        if ((this.getBoundary().getMaxY()) >= atBottomBorder) return true;
        else return false;

    }

    public boolean boundaryCollisionFromTop(double atTopBorder) {
        if ((this.getBoundary().getMinY()) <= atTopBorder) return true;
        else return false;
    }

    public void updateLastRotate() {
        lastRotate = actualRotate;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setActualImage(Image actualImage) {
        this.actualImage = actualImage;
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    public void setLives(double lives) {
        this.lives = lives;
    }

    public void setHitSound(SoundsPlayer hitSound) {
        this.hitSound = hitSound;
    }

    public void setDeathSound(SoundsPlayer deathSound) {
        this.deathSound = deathSound;
    }

    public void setMissSound(SoundsPlayer missSound) {
        this.missSound = missSound;
    }

    public void setProtectionTime(int protectionTime) {
        this.protectionTime = protectionTime;
    }

    public ViewManager getManager() {
        return manager;
    }

    public Image getActualImage() {
        return actualImage;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getLives() {
        return lives;
    }

    public SoundsPlayer getHitSound() {
        return hitSound;
    }

    public SoundsPlayer getDeathSound() {
        return deathSound;
    }

    public SoundsPlayer getMissSound() {
        return missSound;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getMaxLives() {
        return maxLives;
    }

    public int getProtectionTime() {
        return protectionTime;
    }

    public double getActualRotate() {
        return actualRotate;
    }

    public void setActualRotate(double actualRotate) {
        this.actualRotate = actualRotate;
    }

    public double getLastRotate() {
        return lastRotate;
    }

    public ProperImageSet getImageSetWay() {
        return imageSetWay;
    }

    public void setImageDirection(YAxisDirection imageDirection) {
        this.imageDirection = imageDirection;
    }

    public YAxisDirection getImageDirection() {
        return imageDirection;
    }
}
