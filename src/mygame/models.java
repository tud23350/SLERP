/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;
import com.jme3.util.TangentBinormalGenerator;

/**
 *
 * @author Michael
 */
public class models {

    Main main;
    Cylinder c1;
    Cylinder c2;
    Geometry cylinder1;
    Geometry cylinder2;

    public models(Main main) {

        Spatial elephant1 = main.getAssetManager().loadModel("Models/Elephant/Elephant.mesh.xml");
        elephant1.scale(0.05f, 0.05f, 0.05f);
        elephant1.rotate(0.0f, -3.0f, 0.0f);
        elephant1.setLocalTranslation(0.0f, 0f, -2.0f);
        main.getRootNode().attachChild(elephant1);

        TangentBinormalGenerator.generate(((Geometry) elephant1).getMesh(), true);
        Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.White);
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 1);

        Spatial elephant2 = main.getAssetManager().loadModel("Models/Elephant/Elephant.mesh.xml");
        elephant2.scale(0.05f, 0.05f, 0.05f);
        elephant2.rotate(3.0f, -3.0f, 0.0f);
        elephant2.setLocalTranslation(0.0f, -5.0f, -2.0f);
        elephant2.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        elephant1.setMaterial(mat);
        elephant2.setMaterial(mat);
        main.getRootNode().attachChild(elephant1);
        main.getRootNode().attachChild(elephant2);
        /*
         c1 = new Cylinder(20, 10, 1f, 25f);
         c2 = new Cylinder(20, 10, 1f, 25f);
         cylinder1 = new Geometry("Cylinder1", c1);
         cylinder2 = new Geometry("Cylinder2", c2);

         Material mat_cylinder1 = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
         Material mat_cylinder2 = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");

         mat_cylinder1.setColor("color1", ColorRGBA.Blue);
         mat_cylinder2.setColor("color2", ColorRGBA.Green);

         cylinder1.setMaterial(mat_cylinder1);
         cylinder2.setMaterial(mat_cylinder2);

         main.getRootNode().attachChild(cylinder1);
         main.getRootNode().attachChild(cylinder2);

         cylinder1.move(1, 1, 1);
         cylinder2.move(-1, -1, -1);

         Matrix3f m = new Matrix3f(1, 1, 1, 2, 2, 2, 2, 2, 2);
         cylinder1.setLocalRotation(m);
         */



    }
}
