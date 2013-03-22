package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
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
 * test test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static final Quaternion PITCH045 = new Quaternion().fromAngleAxis(FastMath.PI / 3, new Vector3f(1, 0, 0));
    public static final Quaternion ROLL045 = new Quaternion().fromAngleAxis(FastMath.PI / 4, new Vector3f(0, 0, 1));
    SpotLight spot = new SpotLight();
    PssmShadowRenderer pssm;
    BasicShadowRenderer bsr;
    DirectionalLight sun;
    Quaternion q[];
    Quaternion q_buffer1;
    Quaternion q_buffer2;
    Geometry g[];
    Node n[];
    float XYZW[];
    float counter = 0;
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
        //rootNode.attachChild(geom);
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
        initKeys();
        g = new Geometry[3];
        g[0] = initBox(new Vector3f(0, 0, 0), ColorRGBA.Red);
        g[1] = initBox(new Vector3f(0, 0, 0), ColorRGBA.Green);
        g[2] = initBox(new Vector3f(0, 0, 0), ColorRGBA.Blue);
        
        n = new Node[3];
        n[0] = new Node();
        n[1] = new Node();
        n[2] = new Node();
        n[0].attachChild(g[0]);
        n[1].attachChild(g[1]);
        n[2].attachChild(g[2]);
        rootNode.attachChild(n[0]);
        rootNode.attachChild(n[1]);
        rootNode.attachChild(n[2]);

        //g[0].rotate(ROLL045);
        //g[0].rotate(PITCH045);

        flyCam.setMoveSpeed(10);
        //models m=new models(this);

        q = new Quaternion[3];
        XYZW = new float[4];

        q[0] = n[0].getLocalRotation();
        XYZW[0] = q[0].getX();
        XYZW[1] = q[0].getY();
        XYZW[2] = q[0].getZ();
        XYZW[3] = q[0].getW();
        q_buffer1 = (new Quaternion()).fromAngleNormalAxis(XYZW[3], new Vector3f(XYZW[0], XYZW[1], XYZW[2]));

        q[2] = n[2].getLocalRotation();
        XYZW[0] = q[2].getX();
        XYZW[1] = q[2].getY();
        XYZW[2] = q[2].getZ();
        XYZW[3] = q[2].getW();
        q_buffer2 = (new Quaternion()).fromAngleNormalAxis(XYZW[3], new Vector3f(XYZW[0], XYZW[1], XYZW[2]));
//        System.out.println("Data: "+q[0].getX()+"  "+q[0].getY()+"  "+q[0].getZ());


    }

    @Override
    public void simpleUpdate(float tpf) {

        q[0] = n[0].getLocalRotation();
        XYZW[0] = q[0].getX();
        XYZW[1] = q[0].getY();
        XYZW[2] = q[0].getZ();
        XYZW[3] = q[0].getW();
        q_buffer1 = (new Quaternion()).fromAngleNormalAxis(XYZW[3], new Vector3f(XYZW[0], XYZW[1], XYZW[2]));

        q[2] = n[2].getLocalRotation();
        XYZW[0] = q[2].getX();
        XYZW[1] = q[2].getY();
        XYZW[2] = q[2].getZ();
        XYZW[3] = q[2].getW();
        q_buffer2 = (new Quaternion()).fromAngleNormalAxis(XYZW[3], new Vector3f(XYZW[0], XYZW[1], XYZW[2]));
//        System.out.println("Data: "+q[0].getX()+"  "+q[0].getY()+"  "+q[0].getZ());

        
        counter = countUp ? counter + tpf / 2.0f : counter - tpf / 2.0f;
        counter = counter > 1.0f ? 1.0f : counter;
        counter = counter < 0.0f ? 0.0f : counter;
        countUp = counter == 1.0f ? false : countUp;
        countUp = counter == 0.0f ? true : countUp;


        n[1].setLocalRotation((new Quaternion()).slerp(q_buffer1, q_buffer2, counter));

        //pssm.setDirection(new Vector3f(-.5f+counter, -.5f, -.5f).normalizeLocal());
        // sun.setDirection((new Vector3f(-0.5f+counter, -0.5f, -0.5f)).normalizeLocal());
    }

    private void initKeys() { //geometry 0 [start] is Red, geometry 1 [interpol] is Green, geometry 2 [end] is Blue
        inputManager.addMapping("Rotate_start_Up", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("Rotate_start_Down", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Rotate_start_Left", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Rotate_start_Right", new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("Rotate_start_hiddenZ", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("Rotate_start_hidden-Z", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addMapping("Move_start_Up", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addMapping("Move_start_Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Move_start_Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Move_start_Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Rotate_start_hiddenZ", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("Rotate_start_hidden-Z", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addMapping("Rotate_end_Up", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addMapping("Rotate_end_Down", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("Rotate_end_Left", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addMapping("Rotate_end_Right", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("Rotate_end_hiddenZ", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("Rotate_end_hidden-Z", new KeyTrigger(KeyInput.KEY_Y));
        inputManager.addListener(analogListener, new String[]{"Move_start_Up", "Move_start_Down", "Move_start_Left", "Move_start_Right", "Rotate_start_Up", "Rotate_start_Down", "Rotate_start_Left", "Rotate_start_Right", "Rotate_start_hiddenZ", "Rotate_start_hidden-Z", "Rotate_end_Up", "Rotate_end_Down", "Rotate_end_Left", "Rotate_end_Right", "Rotate_end_hiddenZ", "Rotate_end_hidden-Z"});
    }
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if (name.contains("Rotate_start_Up")) {
                n[0].rotate(-value * speed, 0, 0);
            }
            if (name.equals("Rotate_start_Down")) {
                n[0].rotate(value * speed, 0, 0);
            }
            if (name.equals("Rotate_start_Left")) {
                n[0].rotate(0, -value * speed, 0);
            }
            if (name.equals("Rotate_start_Right")) {
                n[0].rotate(0, value * speed, 0);
            }
            if (name.equals("Rotate_start_hiddenZ")) {
                n[0].rotate(0, 0, value * speed);
            }
            if (name.equals("Rotate_start_hidden-Z")) {
                n[0].rotate(0, 0, -value * speed);
            }
            if (name.equals("Move_start_Right")) {
                Vector3f v = n[0].getLocalTranslation();
                n[0].setLocalTranslation(v.x + value * speed, v.y, v.z);
            }
            if (name.equals("Rotate_end_Up")) {
                n[2].rotate(-value * speed, 0, 0);
            }
            if (name.equals("Rotate_end_Down")) {
                n[2].rotate(value * speed, 0, 0);
            }
            if (name.equals("Rotate_end_Left")) {
                n[2].rotate(0, -value * speed, 0);
            }
            if (name.equals("Rotate_end_Right")) {
                n[2].rotate(0, value * speed, 0);
            }
            if (name.equals("Rotate_end_hiddenZ")) {
                n[2].rotate(0, 0, value * speed);
            }
            if (name.equals("Rotate_end_hidden-Z")) {
                n[2].rotate(0, 0, -value * speed);
            }
        }
    };

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
