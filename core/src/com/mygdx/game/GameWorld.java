/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;
import components.BulletComponent;
import components.CharacterComponent;
import components.InteractableComponent;
import components.ModelComponent;
import managers.EntityFactory;
import user.GameUI;

/**
 *
 * @author david
 */
public class GameWorld {
    private static final float FOV = 67F; 
    private ModelBatch batch;
    
    private float[][][] coords = new float[5][5][3];
    private boolean[][] collectibles = new boolean[5][5];
    private int collectibleNum = 0;
    
    private ModelBuilder mb;
    private Environment environment; 
    private PerspectiveCamera camera;
    private Entity character;
    private Engine engine;
    
    public BulletSystem bulletSystem;
    public PlayerSystem playerSystem;
    public ModelBuilder modelBuilder = new ModelBuilder();
    
    //public Array<Entity> objects;
    
    Model wallHorizontal = modelBuilder.createBox(65, 20, 1, 
           new Material(ColorAttribute.createDiffuse(Color.WHITE),
           ColorAttribute.createSpecular(Color.RED), FloatAttribute 
           .createShininess(16f)), VertexAttributes.Usage.Position 
           | VertexAttributes.Usage.Normal); 
    
    Model wallVertical = modelBuilder.createBox(1, 20, 65, 
           new Material(ColorAttribute.createDiffuse(Color.WHITE), 
           ColorAttribute.createSpecular(Color.GREEN), 
           FloatAttribute.createShininess(16f)),
           VertexAttributes.Usage.Position |  
           VertexAttributes.Usage.Normal); 
    
    Model groundModel = modelBuilder.createBox(65, 1, 65, 
           new Material(ColorAttribute.createDiffuse(Color.RED),
           ColorAttribute.createSpecular(Color.WHITE), 
           FloatAttribute.createShininess(16f)),
           VertexAttributes.Usage.Position
           | VertexAttributes.Usage.Normal);
    
    Model platformLow = modelBuilder.createBox(5, 5, 5, 
           new Material(ColorAttribute.createDiffuse(Color.BLUE),
           ColorAttribute.createSpecular(Color.BLUE), 
           FloatAttribute.createShininess(16f)),
           VertexAttributes.Usage.Position
           | VertexAttributes.Usage.Normal);
    
    Model platformHigh = modelBuilder.createBox(5, 12, 5, 
           new Material(ColorAttribute.createDiffuse(Color.BLUE),
           ColorAttribute.createSpecular(Color.BLUE), 
           FloatAttribute.createShininess(16f)),
           VertexAttributes.Usage.Position
           | VertexAttributes.Usage.Normal);
    
    Model ladderHorizontal = modelBuilder.createBox(3, 6, 0.3f, 
           new Material(ColorAttribute.createDiffuse(Color.GREEN),
           ColorAttribute.createSpecular(Color.GREEN), 
           FloatAttribute.createShininess(16f)),
           VertexAttributes.Usage.Position
           | VertexAttributes.Usage.Normal);
    
    Model ladderVertical = modelBuilder.createBox(0.3f, 6, 3, 
           new Material(ColorAttribute.createDiffuse(Color.GREEN),
           ColorAttribute.createSpecular(Color.GREEN), 
           FloatAttribute.createShininess(16f)),
           VertexAttributes.Usage.Position
           | VertexAttributes.Usage.Normal);
    
    Model collectible = modelBuilder.createBox(1, 1, 1, 
           new Material(ColorAttribute.createDiffuse(Color.YELLOW),
           ColorAttribute.createSpecular(Color.YELLOW), 
           FloatAttribute.createShininess(16f)),
           VertexAttributes.Usage.Position
           | VertexAttributes.Usage.Normal);
    
    public GameWorld(GameUI gameUI) { 
       Bullet.init();
        
       generateLevel();
       initPersCamera(); 
       initEnvironment(); 
       initModelBatch();
       addSystems(gameUI);
       
       addEntities();
    } 
    
    private void generateLevel() {
        for (int i = 0; i < coords.length; i++) {
            for (int j = 0; j < coords.length; j++) {
                coords[i][j][0] = (j * 15) + 2.5f + -32.5f; //X
                coords[i][j][1] = (i * 15) + 2.5f + + -32.5f; //Z
                
                if (i != 0 && i != 4 && j != 0 && j != 4) {
                    coords[i][j][2] = (int)(2*Math.random());
                }
                else {
                    coords[i][j][2] = 0;
                }
                
                collectibles[i][j] = false;
            }
        }
        
        while(collectibleNum < 10) {
            int x = (int)(5*Math.random());
            int y = (int)(5*Math.random());
            
            if (!collectibles[x][y]) {
                collectibles[x][y] = true;
                collectibleNum++;
            }
        }
    }
    
    private void createLevel() {
        
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Model realModel;
                
                float x = coords[i][j][0];
                float z = coords[i][j][1];
                
                float y;
                
                //check if tall
                if(coords[i][j][2] == 1) {
                    realModel = platformHigh;
                    y = 6;
                    engine.addEntity(EntityFactory.createStaticEntity(realModel, x, y, z, false, 0));
                    
                    int rand = (int)(4*Math.random());
                    
                    //making ladders
                    switch(rand) {
                        case 0:
                            //left side
                            engine.addEntity(EntityFactory.createStaticEntity(ladderVertical, (x - 2.5f - 0.15f), 9, z, true, 3));
                            break;
                        case 1:
                            //front side
                            engine.addEntity(EntityFactory.createStaticEntity(ladderHorizontal, x, 9, (z - 2.5f - 0.15f), true, 3));
                            break;
                        case 2:
                            //right side
                            engine.addEntity(EntityFactory.createStaticEntity(ladderVertical, (x + 2.5f + 0.15f), 9, z, true, 3));
                            break;
                        case 3:
                            //back side
                            engine.addEntity(EntityFactory.createStaticEntity(ladderHorizontal, x, 9, (z + 2.5f + 0.15f), true, 3));
                            break;
                        default:
                            break;       
                    }
                }
                else {
                    realModel = platformLow;
                    y = 2.5f;
                    engine.addEntity(EntityFactory.createStaticEntity(realModel, x, y, z, false, 0));
                }
                
                if (collectibles[i][j]) {
                    engine.addEntity(EntityFactory.createStaticEntity(collectible, x, (2*y + 1.5f), z, true, 4));
                }
                
                
            }
        }
    }
    
    private void addEntities() {
        createPlayer(0, 9, -30);
        createBase();        
    }
    
    private void createBase() {
       
        //create floor and walls
       engine.addEntity(EntityFactory.createStaticEntity(groundModel, 0, 0, 0, true, 2));
       engine.addEntity(EntityFactory.createStaticEntity(wallHorizontal, 0, 10, -32.5f, false, 0)); 
       engine.addEntity(EntityFactory.createStaticEntity(wallHorizontal, 0, 10, 32.5f, false, 0)); 
       engine.addEntity(EntityFactory.createStaticEntity(wallVertical, 32.5f, 10, 0, false, 0)); 
       engine.addEntity(EntityFactory.createStaticEntity(wallVertical, -32.5f, 10, 0, false, 0)); 
       
       //create platforms, collectibles and ladders
       createLevel();
//       engine.addEntity(EntityFactory.createStaticEntity(platform, -25, 3, 0, false, 0));
//       engine.addEntity(EntityFactory.createStaticEntity(platform, -20, 3, 0, false, 0));
//       engine.addEntity(EntityFactory.createStaticEntity(platform, -10, 3, 0, false, 0));
//       engine.addEntity(EntityFactory.createStaticEntity(platform, -5, 3, 0, false, 0));
//       
//       engine.addEntity(EntityFactory.createStaticEntity(ladderHorizontal, 0, 3, 10, true, 3));
//       
//       engine.addEntity(EntityFactory.createStaticEntity(collectible, 3, 3, 10, true, 4));
       
   }
    
    private void createPlayer(float x, float y, float z) { 
       character = EntityFactory.createPlayer(bulletSystem, x, y, z); 
       engine.addEntity(character);
       bulletSystem.setContactListener(character);
    } 
    
    private void initPersCamera() { 
       camera = new PerspectiveCamera(FOV, 
       Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT); 
//       camera.position.set(30f, 40f, 30f); 
//       camera.lookAt(0f, 0f, 0f); 
//       camera.near = 1f; 
//       camera.far = 300f; 
//       camera.update(); 
    } 
    
    private void initEnvironment() { 
       environment = new Environment(); 
       environment.set(new ColorAttribute(ColorAttribute.AmbientLight,
       0.3f, 0.3f, 0.3f, 1f)); 
    } 
    
    private void initModelBatch() { 
       batch = new ModelBatch(); 
    }
    
    private void addSystems(GameUI gameUI) {
       engine = new Engine();
       bulletSystem = new BulletSystem();
       playerSystem = new PlayerSystem(this, gameUI, camera);
       engine.addSystem(new RenderSystem(batch, environment)); 
       engine.addSystem(bulletSystem);
       engine.addSystem(playerSystem);
       engine.addSystem(new StatusSystem(this));
    }
    
    private void checkPause() { 
       if (Settings.Paused) { 
           playerSystem.setProcessing(false); 
           playerSystem.setProcessing(false); 
           bulletSystem.setProcessing(false); 
       } 
       
       else { 
           playerSystem.setProcessing(true); 
           playerSystem.setProcessing(true); 
           bulletSystem.setProcessing(true); 
       } 
   } 

    public void dispose() { 
      
       bulletSystem.collisionWorld.removeAction(character.getComponent(CharacterComponent.class).characterController);
       bulletSystem.collisionWorld.removeCollisionObject(character.getComponent(CharacterComponent.class).ghostObject);
       
       bulletSystem.dispose(); 
       bulletSystem = null;
       
       character.getComponent(CharacterComponent.class).characterController.dispose(); 
       character.getComponent(CharacterComponent.class).ghostObject.dispose();
       character.getComponent(CharacterComponent.class).ghostShape.dispose();
       
       wallHorizontal.dispose(); 
       wallVertical.dispose(); 
       groundModel.dispose(); 
       batch.dispose(); 
       batch = null; 
    } 
    
    public void remove(Entity entity) {
        engine.removeEntity(entity);
        bulletSystem.removeBody(entity);
    }
    
    public void resize(int width, int height) { 
       camera.viewportHeight = height; 
       camera.viewportWidth = width; 
    } 

    public void render(float delta) { 
       batch.begin(camera); 
       engine.update(delta);
       batch.end();
       checkPause();
    } 
}
