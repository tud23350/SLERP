package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.util.TangentBinormalGenerator;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    SpotLight spot = new SpotLight();
    PssmShadowRenderer pssm;
    BasicShadowRenderer bsr;

    public void lightInit() {
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        /**
         * Advanced shadows for uneven surfaces
         */
        pssm = new PssmShadowRenderer(assetManager, 1024, 3);
        pssm.setDirection(new Vector3f(-.5f, -.5f, -.6f).normalizeLocal());
        viewPort.addProcessor(pssm);

    }

    public void initBox(Vector3f pos) {
        Box b = new Box(pos, 1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        geom.setShadowMode(ShadowMode.CastAndReceive);

        TangentBinormalGenerator.generate(geom.getMesh(), true);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.White);
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 1);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        lightInit();
        initBox(new Vector3f(0, 6, 0));
        initBox(new Vector3f(2, 3, 2));
        initBox(new Vector3f(-2, 3, 2));
        initBox(new Vector3f(-2, 3, -2));

        flyCam.setMoveSpeed(100);
    }

    @Override
    public void simpleUpdate(float tpf) {
        pssm.preFrame(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
