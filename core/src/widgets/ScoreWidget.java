/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Assets;
import components.PlayerComponent;

/**
 *
 * @author david
 */
public class ScoreWidget extends Actor{
   
   Label label; 
   public ScoreWidget() { 
       label = new Label("", Assets.skin); 
   }
   
   @Override 
   public void act(float delta) { 
       label.act(delta); 
       label.setText("Score : " + PlayerComponent.score); 
   }
   
   @Override 
   public void draw(Batch batch, float parentAlpha) { 
       label.draw(batch, parentAlpha); 
   }
   
   @Override 
   public void setPosition(float x, float y) { 
       super.setPosition(x, y); 
       label.setPosition(x, y); 
   }
   
   @Override 
   public void setSize(float width, float height) { 
       super.setSize(width, height); 
       label.setSize(width, height); 
   } 
}
