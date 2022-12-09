/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;

/**
 *
 * @author david
 */
public class HealthWidget extends Actor{
   private ProgressBar healthBar; 
   private ProgressBar.ProgressBarStyle progressBarStyle; 
   private Label label;
   
   public HealthWidget() { 
       progressBarStyle = new ProgressBar.ProgressBarStyle( 
               Assets.skin.newDrawable("white", Color.RED), 
               Assets.skin.newDrawable("white", Color.GREEN)); 
               progressBarStyle.knobBefore = progressBarStyle.knob; 
               healthBar = new ProgressBar(0, 100, 1, false, 
               progressBarStyle); 
               label = new Label("Health", Assets.skin); 
               label.setAlignment(Align.center); 
   }
   
   @Override 
   public void act(float delta) { 
       healthBar.act(delta); 
       label.act(delta); 
   }
   
   @Override 
   public void draw(Batch batch, float parentAlpha) { 
       healthBar.draw(batch, parentAlpha); 
       label.draw(batch, parentAlpha); 
   }
   
   @Override 
   public void setPosition(float x, float y) { 
       super.setPosition(x, y); 
       healthBar.setPosition(x, y); 
       label.setPosition(x, y); 
   }
   
   @Override 
   public void setSize(float width, float height) { 
       super.setSize(width, height); 
       healthBar.setSize(width, height); 
       progressBarStyle.background.setMinWidth(width); 
       progressBarStyle.background.setMinHeight(height); 
       progressBarStyle.knob.setMinWidth(healthBar.getValue()); 
       progressBarStyle.knob.setMinHeight(height); 
       label.setSize(width, height); 
   }
   
   public void setValue(float value) { 
       healthBar.setValue(value); 
   } 
}
