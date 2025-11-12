

public class Fruit {
	
	static final int TIME_OUT = 25;	
	private int countdown;
	private Coordinate position;
	private boolean alive;
	
	public Fruit(Coordinate position) {
		countdown = TIME_OUT;
		this.position = position;
		alive = true;
	}
		
	public void step(ISnake snake) {
		if (alive)
		{
			countdown--;
			if(countdown == 0 || snake.getHead().equals(position)){
				alive = false; 
			} 
		}
		
	}

	public boolean isAlive() {		
		return alive;
	}

	public Coordinate getPosition() {
		return position;
	}

}
