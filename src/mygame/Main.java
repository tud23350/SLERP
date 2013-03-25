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

    SpotLight spot = new SpotLight();
    PssmShadowRenderer pssm;
    BasicShadowRenderer bsr;
    DirectionalLight sun;
    Quaternion q[];
    Quaternion q_buffer1;
    Quaternion q_buffer2;
    Spatial g[];
    Node n[];
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

    public Spatial initBox(Vector3f pos, ColorRGBA color) {
//        Box b = new Box(pos, 0.5f, 0.5f, 3);
//        Geometry geom = new Geometry("Box", b);
//
//        geom.setShadowMode(ShadowMode.CastAndReceive);
//
//        TangentBinormalGenerator.generate(geom.getMesh(), true);
//        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
//        mat.setBoolean("UseMaterialColors", true);
//        mat.setColor("Ambient", color);
//        mat.setColor("Diffuse", color);
//        mat.setColor("Specular", color);
//        mat.setFloat("Shininess", 1);
//        geom.setMaterial(mat);
//        //Node n = new Node();
//        //n.attachChild(geom);
//        //rootNode.attachChild(n);
//        //return n;
//        //rootNode.attachChild(geom);
//        geom.setCullHint(Spatial.CullHint.Never);
        
        Spatial elephant = getAssetManager().loadModel("Models/Elephant/Elephant.mesh.xml");
        elephant.scale(0.05f, 0.05f, 0.05f);
        elephant.rotate(0.0f, -3.0f, 0.0f);
        elephant.setLocalTranslation(pos);
        
        return elephant;
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        lightInit();
        initKeys();
        g = new Spatial[3];
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

        flyCam.setMoveSpeed(10);
        //models m=new models(this);

        q = new Quaternion[3];

        q[0] = n[0].getLocalRotation();
        q[2] = n[2].getLocalRotation();


    }

    @Override
    public void simpleUpdate(float tpf) {

        q[0] = n[0].getLocalRotation();
        q[2] = n[2].getLocalRotation();
        
        counter = countUp ? counter + tpf / 2.0f : counter - tpf / 2.0f;
        counter = counter > 1.0f ? 1.0f : counter;
        counter = counter < 0.0f ? 0.0f : counter;
        countUp = counter == 1.0f ? false : countUp;
        countUp = counter == 0.0f ? true : countUp;

        n[1].setLocalRotation((new Quaternion()).slerp(q[0], q[2], counter));
        
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
        inputManager.addMapping("Move_start_Up", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("Move_start_Down", new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("Move_start_Left", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("Move_start_Right", new KeyTrigger(KeyInput.KEY_V));
        inputManager.addMapping("Move_start_hiddenIn", new KeyTrigger(KeyInput.KEY_LBRACKET));
        inputManager.addMapping("Move_start_hiddenOut", new KeyTrigger(KeyInput.KEY_RBRACKET));
        inputManager.addMapping("Rotate_start_hiddenZ", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("Rotate_start_hidden-Z", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addMapping("Rotate_end_Up", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addMapping("Rotate_end_Down", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("Rotate_end_Left", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addMapping("Rotate_end_Right", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("Rotate_end_hiddenZ", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("Rotate_end_hidden-Z", new KeyTrigger(KeyInput.KEY_Y));
        inputManager.addMapping("Move_end_Up", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addMapping("Move_end_Down", new KeyTrigger(KeyInput.KEY_N));
        inputManager.addMapping("Move_end_Left", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addMapping("Move_end_Right", new KeyTrigger(KeyInput.KEY_COMMA));
        inputManager.addMapping("Move_end_hiddenIn", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("Move_end_hiddenOut", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addListener(analogListener, new String[]{"Move_start_hiddenIn","Move_start_hiddenOut","Move_end_hiddenIn","Move_end_hiddenOut","Move_end_Up","Move_end_Down","Move_end_Left","Move_end_Right","Move_start_Up", "Move_start_Down", "Move_start_Left", "Move_start_Right", "Rotate_start_Up", "Rotate_start_Down", "Rotate_start_Left", "Rotate_start_Right", "Rotate_start_hiddenZ", "Rotate_start_hidden-Z", "Rotate_end_Up", "Rotate_end_Down", "Rotate_end_Left", "Rotate_end_Right", "Rotate_end_hiddenZ", "Rotate_end_hidden-Z"});
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
            if (name.equals("Move_start_Up")) {
                Vector3f v = g[0].getLocalTranslation();
                g[0].setLocalTranslation(v.x, v.y + value * speed, v.z);
            }
            if (name.equals("Move_start_Down")) {
                Vector3f v = g[0].getLocalTranslation();
                g[0].setLocalTranslation(v.x, v.y - value * speed, v.z);
            }
            if (name.equals("Move_start_Right")) {
                Vector3f v = g[0].getLocalTranslation();
                g[0].setLocalTranslation(v.x + value * speed, v.y, v.z);
            }
            if (name.equals("Move_start_Left")) {
                Vector3f v = g[0].getLocalTranslation();
                g[0].setLocalTranslation(v.x - value * speed, v.y, v.z);
            }
            if(name.equals("Move_start_hiddenIn")){
                Vector3f v = g[0].getLocalTranslation();
                g[0].setLocalTranslation(v.x,v.y,v.z - value*speed);
            }
            if(name.equals("Move_start_hiddenOut")){
                Vector3f v = g[0].getLocalTranslation();
                g[0].setLocalTranslation(v.x,v.y,v.z + value *speed);
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
            if (name.equals("Move_end_Up")) {
                Vector3f v = g[2].getLocalTranslation();
                g[2].setLocalTranslation(v.x, v.y + value * speed, v.z);
            }
            if (name.equals("Move_end_Down")) {
                Vector3f v = g[2].getLocalTranslation();
                g[2].setLocalTranslation(v.x, v.y - value * speed, v.z);
            }
            if (name.equals("Move_end_Right")) {
                Vector3f v = g[2].getLocalTranslation();
                g[2].setLocalTranslation(v.x + value * speed, v.y, v.z);
            }
            if (name.equals("Move_end_Left")) {
                Vector3f v = g[2].getLocalTranslation();
                g[2].setLocalTranslation(v.x - value * speed, v.y, v.z);
            }
            if(name.equals("Move_end_hiddenIn")){
                Vector3f v = g[2].getLocalTranslation();
                g[2].setLocalTranslation(v.x,v.y,v.z - value*speed);
            }
            if(name.equals("Move_end_hiddenOut")){
                Vector3f v = g[2].getLocalTranslation();
                g[2].setLocalTranslation(v.x,v.y,v.z + value *speed);
            }
        }
    };

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
