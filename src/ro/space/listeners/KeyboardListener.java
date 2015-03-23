
package ro.space.listeners;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

  private GraphicListener sceneHandler;

  private double eyeX;
  private double eyeY;
  private double eyeZ;

  private double targetX;
  private double targetY;
  private double targetZ;

  private double angle;
  private double angleStep;
  private double fraction;

  public KeyboardListener() {
    eyeX = 0.0f;
    eyeY = 1.0f;
    eyeZ = 5.0f;

    targetX = 0.0f;
    targetY = 1.0f;
    targetZ = -1.0f;

    angleStep = 0.1f;
    fraction = 0.1f;
  }

  @Override
  public void keyPressed(KeyEvent e) {

    switch (e.getKeyCode()) {

      case VK_LEFT:
        angle -= angleStep;
        targetX = Math.sin(angle);
        targetZ = -Math.cos(angle);
        notifySceneHandler();
        break;

      case VK_RIGHT:
        angle += angleStep;
        targetX = Math.sin(angle);
        targetZ = -Math.cos(angle);
        notifySceneHandler();
        break;

      case VK_UP:
        eyeX += (targetX * fraction);
        eyeZ += (targetZ * fraction);
        notifySceneHandler();
        break;

      case VK_DOWN:
        eyeX -= (targetX * fraction);
        eyeZ -= (targetZ * fraction);
        notifySceneHandler();
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // unused
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // unused
  }

  public double getEyeX() {
    return eyeX;
  }

  public double getEyeY() {
    return eyeY;
  }

  public double getEyeZ() {
    return eyeZ;
  }

  public double getTargetX() {
    return targetX;
  }

  public double getTargetY() {
    return targetY;
  }

  public double getTargetZ() {
    return targetZ;
  }

  public void setSceneHandler(GraphicListener sceneHandler) {
    this.sceneHandler = sceneHandler;
  }

  private void notifySceneHandler() {
    sceneHandler.updateKeyboardInputs(this);
  }
}