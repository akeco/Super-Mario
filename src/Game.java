import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Game extends StateBasedGame {
	static int menu=0;
	static int play=1;

	public Game(String name) {
		super(name);
		this.addState(new Menu(menu));
		
	}
	
	
	/*public void run(int x, int y){
		
		initDisplay(x, y);
		
		
		while(Display.isCloseRequested()==false){
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		System.exit(0);
		
	}*/
	
	
	/*public void initDisplay(int x, int y){
		try{
		Display.setDisplayMode(new DisplayMode(x, y));
		Display.create();
		
		} catch(LWJGLException e){
			e.printStackTrace();
		}
	}*/

	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(menu).init(gc, this);
		//this.getState(play).init(gc, this);
		this.enterState(menu);
			
	}
	
	
	public static void main(String[] args) {
		AppGameContainer game;
		try{
			game= new AppGameContainer(new Game("Igrica"));
			game.setDisplayMode(640, 480, false);
			game.setTargetFrameRate(60);
			game.start();
			
		} catch(Exception e){
			e.printStackTrace();
		}
	//	Game igrica = new Game("Igrica");
	//	igrica.run(640, 480);

	}
	

	

	

}
