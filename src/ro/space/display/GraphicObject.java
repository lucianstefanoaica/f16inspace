
package ro.space.display;

import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL.GL_TRIANGLES;
import static javax.media.opengl.GL.GL_UNSIGNED_INT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;

import java.util.ArrayList;

import javax.media.opengl.GL2;

import ro.space.contents.MtlContent;

public class GraphicObject {

  private GL2 gl;

  private ArrayList<GraphicComponent> components = new ArrayList<>();

  public GraphicObject(GL2 gl) {
    this.gl = gl;
  }

  public void addComponent(GraphicComponent component) {
    components.add(component);
  }

  public void draw() {
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, 0.2f, 0.0f);
    gl.glRotatef(180, 0.0f, 1.0f, 0.0f);

    for (GraphicComponent component : components) {
      enableMaterial(component.getMaterial());
      component.getTexture().bind(gl);
      gl.glBindVertexArray(component.getVertexArrayObjectId());
      gl.glDrawElements(GL_TRIANGLES, component.getTotalElements(), GL_UNSIGNED_INT, 0);
    }
    gl.glPopMatrix();
  }

  protected void enableMaterial(MtlContent theMaterial) {

    gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, theMaterial.getKd(), 0);
    gl.glMaterialfv(GL_FRONT, GL_SPECULAR, theMaterial.getKs(), 0);
    gl.glMaterialfv(GL_FRONT, GL_AMBIENT, theMaterial.getKa(), 0);
    gl.glMaterialfv(GL_FRONT, GL_SHININESS, theMaterial.getNs(), 0);
  }
}