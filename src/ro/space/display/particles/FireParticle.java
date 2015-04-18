
package ro.space.display.particles;

import static javax.media.opengl.GL.GL_TRIANGLE_STRIP;

import java.util.Random;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

public class FireParticle extends Particle {

  private GL2 gl;

  private Texture texture;

  private float left;
  private float right;
  private float top;
  private float bottom;

  private float radius = 0.5f;

  private float fadeUnit;

  public FireParticle(GL2 gl, Trio location, Trio speed, Trio acceleration, Texture texture, double angle) {
    super(location, speed, acceleration);
    this.angle = angle;
    this.gl = gl;
    this.texture = texture;

    TextureCoords textureCoords = this.texture.getImageTexCoords();

    top = textureCoords.top();
    bottom = textureCoords.bottom();
    left = textureCoords.left();
    right = textureCoords.right();

    fadeUnit = new Random().nextInt(100) / 1000.0f + 0.003f;
  }

  public void update() {
    speed.add(acceleration);
    location.add(speed);
    lifespan -= fadeUnit;
  }

  @Override
  public void draw() {
    gl.glColor4f(1.0f, 0.0f, 0.0f, lifespan);
    drawBillboard(location, radius);
  }

  public Texture getTexture() {
    return texture;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  Trio leftBottom = new Trio(0.0f, 0.0f, 0.0f);
  Trio rightBottom = new Trio(0.0f, 0.0f, 0.0f);
  Trio rightTop = new Trio(0.0f, 0.0f, 0.0f);
  Trio leftTop = new Trio(0.0f, 0.0f, 0.0f);

  private void drawBillboard(Trio position, float particleSize) {

    leftBottom = new Trio(position.getX(), position.getY(), position.getZ());
    rightBottom = new Trio(position.getX(), position.getY(), position.getZ());
    rightTop = new Trio(position.getX(), position.getY(), position.getZ());
    leftTop = new Trio(position.getX(), position.getY(), position.getZ());

    float sinSize = particleSize * (float) Math.sin(angle);
    float cosSize = particleSize * (float) Math.cos(angle);

    Trio positiveSin = new Trio(0.0f, 0.0f, sinSize);
    rightBottom.add(positiveSin);
    rightTop.add(positiveSin);
    Trio negativeSin = new Trio(0.0f, 0.0f, -1 * sinSize);
    leftBottom.add(negativeSin);
    leftTop.add(negativeSin);

    Trio positiveCos = new Trio(cosSize, 0.0f, 0.0f);
    rightBottom.add(positiveCos);
    rightTop.add(positiveCos);
    Trio negativeCos = new Trio(-1 * cosSize, 0.0f, 0.0f);
    leftBottom.add(negativeCos);
    leftTop.add(negativeCos);

    Trio negativeSize = new Trio(0.0f, -1 * particleSize, 0.0f);
    leftBottom.add(negativeSize);
    rightBottom.add(negativeSize);
    Trio positiveSize = new Trio(0.0f, particleSize, 0.0f);
    leftTop.add(positiveSize);
    rightTop.add(positiveSize);

    // texture.bind(gl);
    // gl.glDisable(GL_BLEND);

    gl.glPushMatrix();

    gl.glBegin(GL_TRIANGLE_STRIP);

    gl.glTexCoord2d(right, bottom);
    gl.glVertex3f(rightBottom.getX(), rightBottom.getY(), rightBottom.getZ());

    gl.glTexCoord2d(right, top);
    gl.glVertex3f(rightTop.getX(), rightTop.getY(), rightTop.getZ());

    gl.glTexCoord2d(left, bottom);
    gl.glVertex3f(leftBottom.getX(), leftBottom.getY(), leftBottom.getZ());

    gl.glTexCoord2d(left, top);
    gl.glVertex3f(leftTop.getX(), leftTop.getY(), leftTop.getZ());

    gl.glEnd();

    gl.glPopMatrix();
  }
}