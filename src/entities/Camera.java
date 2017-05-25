package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0,5,0);
	private float pitch = 10;
	private float yaw;
	private float roll;
	private float speed=0;
	
	public Camera(){}
	
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			if(speed>-2.5){
				speed-=0.01f;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			if(speed<-1){
				speed+=0.02f;	
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x+=0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x-=0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			position.y+=0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			position.y-=0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_P)){
			speed=0;
		}
	}
	public void drive(){
		position.z+=getSpeed();
	}
	public void collide(){
		if(speed>0){
			setSpeed(-0.4f);
		}
		else{
			setSpeed(0.4f);
		}
	}
	public void setSpeed(float speed){
		this.speed=speed;
	}
	public float getSpeed(){
		return speed;
	}

	public Vector3f getPosition() {
		return position;
	}
	

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	

}
