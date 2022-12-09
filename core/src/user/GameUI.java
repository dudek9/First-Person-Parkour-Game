package user;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Core;
import widgets.CrosshairWidget;
import widgets.GameOverWidget;
import widgets.HealthWidget;
import widgets.PauseWidget;
import widgets.ScoreWidget;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package UI;
//
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.mygdx.game.Core;
//
///**
// *
// * @author david
// */
public class GameUI {
   private Core game; 
   public Stage stage; 
   public HealthWidget healthWidget; 
   private ScoreWidget scoreWidget; 
   private PauseWidget pauseWidget; 
   private CrosshairWidget crosshairWidget; 
   public GameOverWidget gameOverWidget; 
   
   public GameUI(Core game) { 
       this.game = game; 
       stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH,
       Core.VIRTUAL_HEIGHT)); 
       setWidgets(); 
       configureWidgets(); 
   }
   
   public void setWidgets() { 
       healthWidget = new HealthWidget(); 
       scoreWidget = new ScoreWidget(); 
       pauseWidget = new PauseWidget(game, stage); 
       crosshairWidget = new CrosshairWidget(); 
       gameOverWidget = new GameOverWidget(game, stage); 
   }
   
   public void configureWidgets() { 
       healthWidget.setSize(140, 25); 
       healthWidget.setPosition(Core.VIRTUAL_WIDTH / 2 -
       healthWidget.getWidth() / 2, 0); 
       scoreWidget.setSize(140, 25); 
       scoreWidget.setPosition(0, Core.VIRTUAL_HEIGHT -
       scoreWidget.getHeight()); 
       pauseWidget.setSize(64, 64); 
       pauseWidget.setPosition(Core.VIRTUAL_WIDTH -
       pauseWidget.getWidth(),
       Core.VIRTUAL_HEIGHT - pauseWidget.getHeight()); 
       gameOverWidget.setSize(280, 100); 
       gameOverWidget.setPosition(Core.VIRTUAL_WIDTH / 2 - 280 / 2, 
       Core.VIRTUAL_HEIGHT / 2); 
       crosshairWidget.setPosition(Core.VIRTUAL_WIDTH / 2 - 16,
       Core.VIRTUAL_HEIGHT / 2 - 16); 
       crosshairWidget.setSize(32, 32); 
       stage.addActor(healthWidget); 
       stage.addActor(scoreWidget); 
       stage.addActor(crosshairWidget); 
       stage.setKeyboardFocus(pauseWidget); 
   } 
   public void update(float delta) { 
       stage.act(delta); 
   } 
   public void render() { 
       stage.draw(); 
   } 
   public void resize(int width, int height) { 
       stage.getViewport().update(width, height); 
   } 
   public void dispose() { 
       stage.dispose(); 
   } 
}
