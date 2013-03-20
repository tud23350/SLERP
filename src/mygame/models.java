/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Cylinder;

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
        
         Matrix3f m=new Matrix3f(1, 1, 1, 2, 2, 2, 2, 2, 2);
        cylinder1.setLocalRotation(m);
        



    }
}
