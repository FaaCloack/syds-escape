package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import entidades.*;
import syd.Syd;

public class GameStateNivel implements GameState{
	GameStateContext director;
	
	private Syd syd;
	
	private ArrayList<Entidad> obstacle = new ArrayList<Entidad>();
	
	private BufferedImage fondodia, fondonoche, arboles;
	private ImageLoader loader;
	private SpriteSheet ss; //contiene un objeto encargado de hacer las operaciones para las animaciones de sprites
	private BackgroundOp bk; //contiene un objeto encargado de hacer las operaciones para la animacion del fondo
	
	private EntityGenerator entities; //objeto encargado de generar entidades al azar
	
	private int generateTime;
	
	private int difficulty;
	private int spawnRate;
	
	public static int score;
	private int scoreTick;
	
	private boolean isMoving;
	
	private boolean isNight;
	private int nightTime;

	public GameStateNivel(GameStateContext dir, ImageLoader loader) {
		this.director = dir;		
		this.loader = loader;
		
		ss = new SpriteSheet(loader.getImage(0));
		bk = new BackgroundOp(loader.getImage(3));
		fondodia = loader.getImage(1);
		fondonoche = loader.getImage(2);
		syd = new Syd(ss);
		
		entities = new EntityGenerator(ss);
		
		arboles = bk.grabBackground(); //asigna un fondo inicial
		
		generateTime = 0;
		
		difficulty = 0;
		spawnRate = 170;
		
		score = 0;
		scoreTick = 0;
		
		isNight = false;
		nightTime = 0;
	}

	public void ready(){
		
	}
	
	public void nivel(){

	}
	
	public void pause(){
		director.setState(StateFactory.getState(3, director, loader)); //crea los estados a partir de un Factory
	}
	
	public void over() {
		director.setState(StateFactory.getState(4, director, loader));
	}
	
	public void  detectCollision(){		
		Iterator<Entidad> obstacleIt = obstacle.iterator();
		
		if(obstacle.isEmpty() != true){
			while(obstacleIt.hasNext()){
				Entidad obstaculo = obstacleIt.next();
				
				if(syd.collision(obstaculo.getHitbox()) || obstaculo.collision(syd.getHitbox()))
				{
					if(obstaculo.getType() == "agua"){ //se regenera la vida cada que consume agua
						syd.regenLife();
						obstacleIt.remove();
					}else{
						if(syd.getSuperSyd()){
							obstacleIt.remove();
							score += 10;
						}else{
							syd.hit();
						}
					}
				}
			}
		}
	}
	
	public void  clearUnused(){
		Iterator<Entidad> obstacleIt = obstacle.iterator();
		
		if(obstacle.isEmpty() != true){
			while(obstacleIt.hasNext()){
				Entidad obstaculo = obstacleIt.next();
				
				if(obstaculo.getOutOfBounds()){
					obstacleIt.remove();
				}
			}
		}
	}
	
	public void addScore(){
		if(scoreTick >= 50){
			score += 5;
			scoreTick = 0;
		}
		scoreTick++;
	}
	
	public void tick() {		
		syd.tick();
		
		detectCollision();
		clearUnused();
		
		isMoving = syd.getRight();
		if(isMoving){
			addScore();
	 		arboles = bk.grabBackground();
			
			Iterator<Entidad> obstacleIt = obstacle.iterator();
			if(obstacle.isEmpty() != true){
				while(obstacleIt.hasNext()){
					Entidad obstaculo = obstacleIt.next();
					
					obstaculo.move();
				}
			}
			
		}
		
		if(obstacle.isEmpty() != true){
			int len = obstacle.size();
			
			for (int i = 0; i < len; i++) {
				Entidad obstaculo = obstacle.get(i);
				obstaculo.tick();
			}
		}
		
		if(difficulty > 500){
			spawnRate -= 10;
			if(spawnRate < 50){
				spawnRate = 30;
			}
			difficulty = 0;
		}
		difficulty++;
		
		if(generateTime > spawnRate){
			obstacle.add(entities.createEntity());
			generateTime = 0;
		}
		generateTime++;
		
		if(syd.getLife() == 0){
			over();
		}
		
		if(nightTime > 3500){
			if(isNight){
				isNight = false;
				syd.sunGrab = true;
			}else{
				isNight = true;
				entities.setNight(isNight);
				syd.sunGrab = false;
			}
			nightTime = 0;
		}
		nightTime++;
	}

	public void pinturitas(Graphics dgb) {
		if(isNight){
			dgb.drawImage(fondonoche, 0, 0, null);
		}else{
			dgb.drawImage(fondodia, 0, 0, null);
		}
		
		dgb.drawImage(arboles, 0, 0, null);
		syd.pinturitas(dgb);
	
		if(obstacle.isEmpty() != true){
			int len = obstacle.size();
			
			for (int i = 0; i < len; i++) {
				Entidad obstaculo = obstacle.get(i);
				obstaculo.pinturita(dgb);
			}
		}
		
		dgb.setFont(new Font("Arial", Font.PLAIN, 80));
		dgb.setColor(Color.white);
		dgb.drawString(String.valueOf(score), 330, 575);
	}
	
	public void keyPressed(int key) {
		if(key == KeyEvent.VK_ESCAPE ) {
			pause();
		}
		syd.keyPressed(key);
	}

	public void keyReleased(int key) {
		syd.keyReleased(key);
	}
}

