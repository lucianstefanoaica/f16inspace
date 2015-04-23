
package ro.space.display.particles;

import java.util.Random;

import javax.media.opengl.GL2;

import ro.space.read.textures.TextureReader;
import ro.space.util.constants.Numbers;

import com.jogamp.opengl.util.texture.Texture;

public class FireSystem extends ParticleSystem {

  private TextureReader reader;

  private Random generator = new Random();

  public FireSystem(GL2 gl, Trio eye, double cameraAngle) {
    super(eye, cameraAngle);

    this.gl = gl;
    reader = new TextureReader(this.gl, "res/");
  }

  protected void spawnParticles() {

    Texture texture = reader.readTexture("fireParticle.png", ".png");

    for (int i = 0; i < Numbers.NUMBER_OF_PARTICLES.getValue(); ++i) {

      Trio loc = new Trio(0.0f, 0.0f, 0.0f);
      Trio speed = new Trio(0.0f, 0.0f, 0.0f);
      Trio acceleration = new Trio(1.0f / generator.nextInt(1000), 1.0f / generator.nextInt(1000), 1.0f / generator.nextInt(1000));

      FireParticle particle = new FireParticle(gl, loc, speed, acceleration, texture, eye, cameraAngle);

      particles.add(particle);
    }
  }
}