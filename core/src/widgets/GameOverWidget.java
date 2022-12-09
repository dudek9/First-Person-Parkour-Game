/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;
import com.mygdx.game.Core;
import screens.GameScreen;

/**
 *
 * @author david
 */
public class GameOverWidget extends Actor{
   private Core game; 
   private Stage stage; 
   private Image image; 
   private TextButton retryB, quitB;
   
   public GameOverWidget(Core game, Stage stage) { 
       this.game = game; 
       this.stage = stage; 
       setWidgets(); 
       setListeners(); 
   }
   
   private void setWidgets() { 
       image = new Image(new
       Texture(Gdx.files.internal("gameOver.png"))); 
       retryB = new TextButton("Retry", Assets.skin); 
       //leaderB = new TextButton("Leaderboards", Assets.skin); 
       quitB = new TextButton("Quit", Assets.skin); 
   } 
 
   private void setListeners() { 
       retryB.addListener(new ClickListener() { 
           @Override 
           public void clicked(InputEvent event, float x, float y)
           { 
               game.setScreen(new GameScreen(game)); 
           } 
       }); 
//       leaderB.addListener(new ClickListener() { 
//           @Override 
//           public void clicked(InputEvent event, float x, float y)
//           { 
//               //game.setScreen(new LeaderboardsScreen(game)); 
//           } 
//       }); 

       quitB.addListener(new ClickListener() { 
           @Override 
           public void clicked(InputEvent event, float x, float y)
           { 
               Gdx.app.exit(); 
           } 
       }); 
   }
   
   @Override 
   public void setPosition(float x, float y) { 
       super.setPosition(0, 0); 
       image.setPosition(x, y + 32); 
       retryB.setPosition(Core.VIRTUAL_WIDTH/2 - 45 - retryB.getWidth(), y - 96); 
       //leaderB.setPosition(x + retryB.getWidth() - 25, y - 96); 
       quitB.setPosition(Core.VIRTUAL_WIDTH/2 + 45, y - 96);
       //leaderB.getWidth(), y - 96); 
   }
   
   @Override 
   public void setSize(float width, float height) { 
       super.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT); 
       image.setSize(width, height); 
       retryB.setSize(width / 2.5f, height / 2); 
       //leaderB.setSize(width / 2.5f, height / 2); 
       quitB.setSize(width / 2.5f, height / 2); 
   }
   
   public void gameOver() { 
       stage.addActor(image); 
       stage.addActor(retryB); 
       //stage.addActor(leaderB); 
       stage.addActor(quitB); 
       stage.unfocus(stage.getKeyboardFocus()); 
       Gdx.input.setCursorCatched(false); 
   } 
}
