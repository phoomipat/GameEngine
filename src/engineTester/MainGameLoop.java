package engineTester;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.RoadTerrain;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;



public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//TREE Model
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		
		List<Entity> entities = new ArrayList<Entity>();
		//Create TREE
		Random random = new Random();
		for(int i=0;i<800;i++){
			float ranX;
			ranX = random.nextFloat()*800 - 400;
			while( (-5<ranX && ranX<70) || (90<ranX && ranX<-25) ){
				ranX = random.nextFloat()*800 - 400;
			}
			entities.add(new Entity(staticModel, new Vector3f(ranX,0,random.nextFloat() * -600),0,0,0,3));
		}
		

		//Create CAR
		RawModel botCarModel = OBJLoader.loadObjModel("stall", loader);
		TexturedModel botCarTexture = new TexturedModel(botCarModel,new ModelTexture(loader.loadTexture("stallTexture")));
		List<Entity> autoCar = new ArrayList<Entity>();
		
		for(int i=0;i<10;i++){
			int ranX = ((int)(Math.random()*4))+1;
			float ranZ = (float)(Math.random()*0.5)+0.1f;
			float zPos = random.nextFloat() * -600;
			System.out.println("RANDOM X : "+ranX+"\t"+ranZ+"px/s");
			switch(ranX){
			case 1 : autoCar.add(new Entity(botCarTexture, new Vector3f(7,0,zPos),0,0,0,1,-ranZ));
			case 2 : autoCar.add(new Entity(botCarTexture, new Vector3f(23,0,zPos),0,0,0,1,-ranZ));
			case 3 : autoCar.add(new Entity(botCarTexture, new Vector3f(41,0,zPos),0,0,0,1,-ranZ));
			case 4 : autoCar.add(new Entity(botCarTexture, new Vector3f(57,0,zPos),0,0,0,1,-ranZ));
			}
		}
		
		System.out.println("-- TREE --");
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		//Road
		RoadTerrain terrainRoad = new RoadTerrain(0f,-1f,loader,new ModelTexture(loader.loadTexture("road")));
		RoadTerrain terrainRoad2 = new RoadTerrain(0f,-2f,loader,new ModelTexture(loader.loadTexture("road")));
		
		//Right
		Terrain terrainGrassRight = new Terrain(0.08f,-1f,loader,new ModelTexture(loader.loadTexture("grass")));
		//Left
		Terrain terrainGrassLeft = new Terrain(-1f,-1f,loader,new ModelTexture(loader.loadTexture("grass")));
		
		//Grass2
		Terrain terrainGrassRight2 = new Terrain(0.08f,-2f,loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrainGrassLeft2 = new Terrain(-1f,-2f,loader,new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();
		MasterRenderer renderer = new MasterRenderer();
		
		//carHUD Model
		RawModel carHUDmodel = OBJLoader.loadObjModel("tempHUD", loader);
		TexturedModel carModel = new TexturedModel(carHUDmodel,new ModelTexture(loader.loadTexture("tempHUD")));
		Entity carHUD = new Entity(carModel, new Vector3f(camera.getPosition().x-2,camera.getPosition().y+3,camera.getPosition().z-13) ,90,-40,0,4);
		System.out.println("-- HUD --");
		
		RoadTerrain terrainRoadArray[] = new RoadTerrain[10];
		Terrain terrainGrassLeftArray[] = new Terrain[35];
		Terrain terrainGrassRightArray[] = new Terrain[35];
		int amt=0;
		int amg=0;
		
    	for(float i = 0;i>-10;i--){
    		terrainRoadArray[amt] = new RoadTerrain(0f, i,loader,new ModelTexture(loader.loadTexture("road")));
    		System.out.println((((-1*i)/45)*100)+"%");
    		amt++;
    	}
		System.out.println("-- ROAD --");
    	
    	for(float i = 0;i>-35;i--){
    		terrainGrassRightArray[amg] = new Terrain(0.08f,i,loader,new ModelTexture(loader.loadTexture("grass")));
    		terrainGrassLeftArray[amg] = new Terrain(-1f,i,loader,new ModelTexture(loader.loadTexture("grass")));
    		System.out.println(((((-1*i)+10)/45)*100)+"%");
    		amg++;
    	}
		System.out.println("-- GRASS --");
    	
    	amt=3;
    	amg=3;
    	boolean swapRoad=true;
    	boolean swapGrass=true;
    	
    	java.util.Date dateStart = new java.util.Date();
    	String startTime = dateStart.toString();
    	int tempClash=0;
    	
		while(!Display.isCloseRequested()){
			
			if(camera.getPosition().z < -1500){
		    	java.util.Date dateEnd = new java.util.Date();
		    	String endTime = dateEnd.toString();
		    	
		    	String strTemp=startTime.substring( startTime.indexOf(":")+2, startTime.lastIndexOf(":")+3 );
		    	String endTemp=endTime.substring( endTime.indexOf(":")+2, endTime.lastIndexOf(":")+3 );
		    	
		    	int minStr = Integer.parseInt(strTemp.substring(0,strTemp.indexOf(":")));
		    	int minEnd = Integer.parseInt(endTemp.substring(0,strTemp.indexOf(":")));

		    	int secStr = Integer.parseInt(strTemp.substring(strTemp.indexOf(":")+1));
		    	int secEnd = Integer.parseInt(endTemp.substring(strTemp.indexOf(":")+1));
		    	
		    	secStr = secStr+(minStr*60);
		    	secEnd = secEnd+(minEnd*60);
		    	int score = secEnd-secStr+tempClash;
		    	
		    	System.out.println(strTemp+" "+minStr+" "+secStr+"\n"+endTemp+" "+minEnd+" "+secEnd+"\n"+score+" "+tempClash);
		    	
		    	JOptionPane.showConfirmDialog(null, score, "Your Score",JOptionPane.CLOSED_OPTION);
				renderer.cleanUp();
				loader.cleanUp();
				DisplayManager.closeDisplay();
			}
			
			if(camera.getPosition().z < -(2700*(amt-1)-1000)){
				System.out.println("===============amt "+amt+"==============");
				System.out.println("===============Create ROAD==============");
				if(swapRoad){
					terrainRoad=terrainRoadArray[amt];
					swapRoad=false;
				}else{
					terrainRoad2=terrainRoadArray[amt];
					swapRoad=true;
				}
				amt++;
				System.out.println("=================================");
				System.out.println("");
			}
			
			if(camera.getPosition().z < -(750*(amg-1)-400)){
				System.out.println("===============amg "+amg+"==============");
				System.out.println("===============Create GRASS==============");
				
				if(swapGrass){
					terrainGrassLeft=terrainGrassLeftArray[amg];
					terrainGrassRight=terrainGrassRightArray[amg];
					swapGrass=false;
				}else{
					terrainGrassLeft2=terrainGrassLeftArray[amg];
					terrainGrassRight2=terrainGrassRightArray[amg];
					swapGrass=true;
				}
				amg++;
				System.out.println("=================================");
				System.out.println("");
			}
			
			System.out.println("Position(-1 int) = "+camera.getPosition()+"\t\tSpeed : "+camera.getSpeed());
			
			camera.move();
			camera.drive();
			for(Iterator<Entity> treeList = entities.iterator(); treeList.hasNext();){
				Entity tree = treeList.next();
				if(tree.collide(camera.getPosition())){
					System.out.println("COLLIDE DETECTED!");
					tempClash=tempClash+5;
					camera.collide();
				}
			}
			
			for(Iterator<Entity> car = autoCar.iterator(); car.hasNext();){
				Entity movinCar = car.next();
				if(movinCar.collide(camera.getPosition())){
					System.out.println("COLLIDE DETECTED!");
					tempClash=tempClash+5;
					camera.collide();
				}
			}
			
			carHUD.setPosition(new Vector3f(camera.getPosition().x-2,camera.getPosition().y+3,camera.getPosition().z-13));
			renderer.processEntity(carHUD);
			renderer.processTerrain(terrainGrassLeft);
			renderer.processTerrain(terrainGrassRight);
			renderer.processTerrain(terrainGrassLeft2);
			renderer.processTerrain(terrainGrassRight2);
			renderer.processTerrain(terrainRoad);
			renderer.processTerrain(terrainRoad2);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			for(Entity entity:autoCar){
				entity.increasePosition(0, 0, entity.getSpeed());
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
