package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
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
    public static final Quaternion PITCH045 = new Quaternion().fromAngleAxis(FastMath.PI/3,   new Vector3f(1,0,0));
    public static final Quaternion ROLL045  = new Quaternion().fromAngleAxis(FastMath.PI/4,   new Vector3f(0,0,1));
    SpotLight spot = new SpotLight();
    PssmShadowRenderer pssm;
    BasicShadowRenderer bsr;
    DirectionalLight sun;
    
    Quaternion q[];
    Quaternion q_buffer1;
    Quaternion q_buffer2;
    Geometry g[];
    float XYZW[];
    float counter=0;
    boolean countUp = true;
    
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
        sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        /**
         * Advanced shadows for uneven surfaces
         */
        pssm = new PssmShadowRenderer(assetManager, 1024, 3);
        pssm.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        viewPort.addProcessor(pssm);

    }

    public Geometry initBox(Vector3f pos, ColorRGBA color) {
        Box b = new Box(pos, 0.5f, 0.5f, 3);
        Geometry geom = new Geometry("Box", b);
        
        geom.setShadowMode(ShadowMode.CastAndReceive);

        TangentBinormalGenerator.generate(geom.getMesh(), true);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", color);
        mat.setColor("Diffuse", color);
        mat.setColor("Specular", color);
        mat.setFloat("Shininess", 1);
        geom.setMaterial(mat);
        //Node n = new Node();
        //n.attachChild(geom);
        //rootNode.attachChild(n);
        //return n;
        rootNode.attachChild(geom);
        geom.setCullHint(Spatial.CullHint.Never);
        return geom;
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        lightInit();
        g = new Geometry[3];
        g[0] = initBox(new Vector3f(0, 0, 0), ColorRGBA.Red);
        g[1] = initBox(new Vector3f(0, 0, 0), ColorRGBA.Green);
        g[2] = initBox(new Vector3f(0, 0, 0), ColorRGBA.Blue);
        
        g[0].rotate(ROLL045);
        g[0].rotate(PITCH045);
        
        flyCam.setMoveSpeed(100);
        //models m=new models(this);
        
        q = new Quaternion[3];
        XYZW = new float[4];
        
        q[0] = g[0].getLocalRotation();
        XYZW[0] = q[0].getX();
        XYZW[1] = q[0].getY();
        XYZW[2] = q[0].getZ();
        XYZW[3] = q[0].getW();
        q_buffer1 =  (new Quaternion()).fromAngleNormalAxis(XYZW[3], new Vector3f(XYZW[0], XYZW[1], XYZW[2]));
        
        q[2] = g[2].getLocalRotation();
        XYZW[0] = q[2].getX();
        XYZW[1] = q[2].getY();
        XYZW[2] = q[2].getZ();
        XYZW[3] = q[2].getW();
        q_buffer2 =  (new Quaternion()).fromAngleNormalAxis(XYZW[3], new Vector3f(XYZW[0], XYZW[1], XYZW[2]));
//        System.out.println("Data: "+q[0].getX()+"  "+q[0].getY()+"  "+q[0].getZ());
        
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        
        counter = countUp ? counter+tpf/2.0f : counter-tpf/2.0f;
        counter = counter>1.0f ? 1.0f : counter;
        counter = counter<0.0f ? 0.0f : counter;
        countUp = counter==1.0f ? false : countUp;
        countUp = counter==0.0f ? true : countUp;
        
        
        g[1].setLocalRotation((new Quaternion()).slerp(q_buffer1, q_buffer2, counter));
        
        //pssm.setDirection(new Vector3f(-.5f+counter, -.5f, -.5f).normalizeLocal());
       // sun.setDirection((new Vector3f(-0.5f+counter, -0.5f, -0.5f)).normalizeLocal());
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
