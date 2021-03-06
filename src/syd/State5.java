package syd;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import interfaz.SpriteSheet;

public class State5 implements SydState{
	Syd context;
	
	private BufferedImage image;

	private SpriteSheet ss;
	
	public State5(SpriteSheet ss, Syd syd) {
		this.ss = ss;
		this.context = syd;
		
		image = ss.grabImage(1, 5, 100, 100);
	}
	
	public void tick(){
		if(Syd.score>=(5*1500)){
			state6();
		}
	}
	
	public void pinturitas(Graphics dbg) {
		context.cont++;
		if(context.cont%10 == 0){
			if(context.getSuperSyd()){
				image= ss.grabImage(4+Syd.contador, 10, 100, 100);
			}else{
				image= ss.grabImage(Syd.contador, 5, 100, 100);
			}
		}
		if(context.invulnerableTick%2 == 0){
			dbg.drawImage(image, context.x-30, context.y-40, null);
		}
	}

	public void keyPressed(int key) {
		
	}

	public void keyReleased(int key) {
		
	}

	public void state1() {
		
	}

	@Override
	public void state2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void state3() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void state4() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void state5() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void state6() {
		context.setState(context.getState6());
		
	}

	@Override
	public void state7() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void state8() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void state9() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void state10() {
		// TODO Auto-generated method stub
		
	}
}
