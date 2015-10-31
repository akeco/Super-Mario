import org.newdawn.slick.*;

import java.util.*;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.newdawn.slick.state.*;
import org.lwjgl.Sys;
import org.lwjgl.input.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import javax.swing.Timer;

import java.util.*;

public class Menu extends BasicGameState  {


Animation marioAnim, desno, lijevo, defaultR, defaultF, crouchAnimL, crouchAnimR;
Animation coinAnim;
int [] trajanje = {200, 200};
int [] coinTrajanje={200, 200, 200, 200};
int ID=0;
int x=0, y=0;
static int mX=320, mY=350;
long lastFrame;
int score=0;
Image mapa1;
Image mario;
Image enemy;
int enemX=1220;
Thread skok, skokLong, enemyMove;
int pom=0;
List<Integer> walkTXT= new ArrayList<Integer>();
List<Integer> skokTXT= new ArrayList<Integer>();
List<Integer> specialTXT = new ArrayList<Integer>();
File jumpFile = new File("JumpFile");
File walkFile = new File("WalkFile");
File specialCase = new File("SpecialCase");
Scanner scanJUMP, scanWALK, specialScan;
Boolean pomocna=false;
boolean enemyCounter=false;
String beast [] ={"res/beast/caveman1.png", "res/beast/caveman2.png", "res/beast/caveman3.png",
		"res/beast/caveman4.png", "res/beast/caveman5.png", "res/beast/caveman6.png",
		"res/beast/caveman7.png", "res/beast/caveman8.png", "res/beast/caveman9.png"};

Image coin1, coin2, coin3, coin4;
Image newCoin1, newCoin2, newCoin3, newCoin4;
Image crouchR, crouchL;



public long getTime(){
 return	(System.currentTimeMillis()*1000)/ Sys.getTimerResolution();
}


public int getDelta() {
    long time = getTime();
    int delta = (int) (time - lastFrame);
    lastFrame = time;
 
    return delta;
}


public Menu(int state) {
}


public synchronized void jumping(){
	
	skok=new Thread(){
		
		public void run(){
			if(pom==0){
				pomocna=true;
			while(mY>=170){
				mY-=5;
				try {
					sleep(7);
				} catch (InterruptedException e) {	
					e.printStackTrace();
				}
				
			}
			
			while(mY<350){
				mY+=5;
				try {
					sleep(7);
				} catch (InterruptedException e) {	
					e.printStackTrace();
				}
				
			}
			pomocna=false;
		  }
		}
	};
	if(pom==0){ skok.start(); pom=1; }
}


public synchronized void jumpingShort(){
	
	skok=new Thread(){
		
		public void run(){
			if(pom==0){
				
			while(mY>=310){
				mY-=5;
				try {
					sleep(7);
				} catch (InterruptedException e) {	
					e.printStackTrace();
				}
				
			}
			
			while(mY<350){
				mY+=5;
				try {
					sleep(7);
				} catch (InterruptedException e) {	
					e.printStackTrace();
				}
				
			}
			
		}
		}
	};
	if(pom==0){ skok.start(); pom=1; }
	
}


public synchronized void jumpingLong(){
    
	skokLong=new Thread(){
		public void run(){
			if(pom==0){
			while(mY>=150){
				mY-=5;
				try {
					sleep(8);
				} catch (InterruptedException e) {	
					e.printStackTrace();
				}
				
			}
			
			while(mY<350){
				mY+=5;
				try {
					sleep(8);
				} catch (InterruptedException e) {	
					e.printStackTrace();
				}
				
			}
		}
		}
	};
	if(pom==0){ skokLong.start(); pom=1; }
		
}


public void EnemyMove(){
	
	enemyMove = new Thread(){
		public void run(){
			//while(true){
			if(!enemyCounter){
				enemyCounter=true;
				while(enemX>=1000){		
					enemX-=5;
					try{
						enemyMove.sleep(80);
						System.out.println("enemX je:"+enemX);
						System.out.println("x je:"+ x);
						System.out.println("enemX-x je: "+ (enemX-x));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				while(enemX<=1220){	
					enemX+=5;
					try{
						enemyMove.sleep(80);
						System.out.println("enemX je:"+enemX);
						System.out.println("x je:"+ x);
						System.out.println("enemX-x je: "+ (enemX-x));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			//}
				enemyCounter=false;
			}
		}	
	};
	enemyMove.start();
}


	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		{
			coin1= new Image("res/coins/coin1.png");
			coin2= new Image("res/coins/coin2.png");
			coin3= new Image("res/coins/coin3.png");
			coin4= new Image("res/coins/coin4.png");
			
			
		    newCoin1 = coin1.getScaledCopy(45, 45);
		    newCoin2 = coin2.getScaledCopy(45, 45);
		    newCoin3 = coin3.getScaledCopy(45, 45);
		    newCoin4 = coin4.getScaledCopy(45, 45);
		}
		
		
		try {
			scanJUMP = new Scanner(new FileReader(jumpFile));
			scanWALK = new Scanner(new FileReader(walkFile));
			specialScan = new Scanner(new FileReader(specialCase));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		while(scanJUMP.hasNext()){
			skokTXT.add(Integer.parseInt(scanJUMP.next()));
			
		}
		while(scanWALK.hasNext()){
			walkTXT.add(Integer.parseInt(scanWALK.next()));
		}
		
		while(specialScan.hasNext()){
			specialTXT.add(Integer.parseInt(specialScan.next()));
		}
		scanJUMP.close();
		scanWALK.close();
		specialScan.close();
		
		
	
		crouchL= new Image("res/marioCrouchL.png");
		crouchR= new Image("res/marioCrouchR.png");
		mapa1= new Image("res/dioPrvi.jpg");
		mario = new Image("res/MarioSmallRight.png");
		enemy= new Image("enemy1.png");
		
		Image [] right = {new Image("res/MarioSmallRight.png"), new Image("res/MarioSmallRight2.png")};
		Image [] left = {new Image("res/MarioSmallLeft.png"), new Image("res/MarioSmallLeft2.png")};
		Image [] coinRotate = {newCoin1, newCoin2, newCoin3, newCoin4 };
		Image [] spustenR = {crouchR, crouchL};
		Image [] spustenL = {crouchL, crouchR};
		
		
		coinAnim = new Animation(coinRotate, coinTrajanje, true);
		desno= new Animation(right, trajanje, true);
		lijevo= new Animation(left, trajanje, true);
		defaultR= new Animation(right, trajanje, false);
		defaultF= new Animation(left, trajanje, false);
		
		crouchAnimR = new Animation(spustenR, trajanje, false);
		crouchAnimL = new Animation(spustenL, trajanje, false);
		
		marioAnim=desno;
		//marioAnim.setLooping(false);
		
		
		//EnemyMove();
	}

	public void render(GameContainer gc, StateBasedGame SBG, Graphics g)
			throws SlickException {
		
		
		mapa1.draw(x, 0);
		marioAnim.draw(mX, mY);
		enemy.draw(enemX+x, 367);
		
	//	if(x<=-700)
	//	enemy.draw(500, EnemY);
	
		g.drawString("x: "+x, 0, 20);
		g.drawString("Mario X: "+mX + "\nMario Y: "+ mY, 0, 40);
		g.drawString("Score: "+score, 550, 0);
		
		
	}
	
	
	/*
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}*/
	
	
	
	public void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	

	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		if(!enemyCounter) EnemyMove();
		
		int delta= getDelta();
		
		Input input= gc.getInput();
		
		
	    if(input.isKeyPressed(0)==false){
	    	if(marioAnim==desno) marioAnim=defaultR;
	    	else if(marioAnim==lijevo) marioAnim=defaultF;
	    }
	
	    if(input.isKeyDown(Keyboard.KEY_RIGHT)){
			if(marioAnim!=desno) marioAnim=desno;
			
			if(x==-160 && specialTXT.contains(mY)){
				return;
			}
			
			else if(mY!=350){
				x-=5;
			}	
			else if(walkTXT.contains(x)){
				marioAnim=defaultR;
				return;
			}
			else{
				x-=5; 
			}
		}
		
		if(input.isKeyDown(Keyboard.KEY_LEFT)){
			
			if (marioAnim!=lijevo) marioAnim=lijevo;
			 if(x==0){
				 x=0;
				 marioAnim=defaultF;
			 }
				else{
					x+=5; 
		   }
			
		} 
	    
		
		if(input.isKeyPressed(Keyboard.KEY_UP)){
			
			if(!pomocna){
			if(skokTXT.contains(x)){
				jumpingShort();
				pom=0;
			}
			else{
			jumping();
			pom=0;
		}
			}
			else return; 
	  }	
		
		
		
		if(input.isKeyDown(Keyboard.KEY_DOWN)){
		//	marioAnim=crouchAnimR;
			mY=350;  
	} 
	
	}
	
	public int getID() {
		return 0;
	}
}


	

