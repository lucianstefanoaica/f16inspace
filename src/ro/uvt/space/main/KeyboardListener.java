
package ro.uvt.space.main;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ro.uvt.api.particles.Trio;
import ro.uvt.api.util.Observer;
import ro.uvt.api.util.Subject;

public class KeyboardListener implements KeyListener, Subject {

  private Trio cameraPosition = new Trio(0.0f, 1.0f, 0.0f);
  private Trio targetPosition = new Trio(0.0f, 1.0f, -1.0f);
  private double cameraAngle;

  private double angleStep = 0.1f;
  private double fraction = 0.1f;

  private List<Observer> observers = new ArrayList<>();

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case VK_LEFT:
        cameraAngle -= angleStep;
        targetPosition.setX((float) Math.sin(cameraAngle));
        targetPosition.setZ((float) -Math.cos(cameraAngle));
        notifyObservers();
        break;

      case VK_RIGHT:
        cameraAngle += angleStep;
        targetPosition.setX((float) Math.sin(cameraAngle));
        targetPosition.setZ((float) -Math.cos(cameraAngle));
        notifyObservers();
        break;

      case VK_UP:
        cameraPosition.setX(cameraPosition.getX() + (float) (targetPosition.getX() * fraction));
        cameraPosition.setZ(cameraPosition.getZ() + (float) (targetPosition.getZ() * fraction));
        notifyObservers();
        break;

      case VK_DOWN:
        cameraPosition.setX(cameraPosition.getX() - (float) (targetPosition.getX() * fraction));
        cameraPosition.setZ(cameraPosition.getZ() - (float) (targetPosition.getZ() * fraction));
        notifyObservers();
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void registerObserver(Observer toRegister) {
    observers.add(toRegister);
  }

  @Override
  public void removeObserver(Observer toRemove) {
    observers.remove(toRemove);
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public HashMap<String, Object> getState() {
    HashMap<String, Object> state = new HashMap<>();

    state.put("camera_position", cameraPosition);
    state.put("target_position", targetPosition);
    state.put("camera_angle", cameraAngle);

    return state;
  }
}