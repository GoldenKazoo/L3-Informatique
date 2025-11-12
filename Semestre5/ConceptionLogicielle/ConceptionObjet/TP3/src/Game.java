
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Game {
	
	private static final int FRUIT_PERIOD = 20;
	private static final Random RANDOM_SEED = new Random();	
	private int height;
	private int width;
	private SnakeObservable snake;
	private List<Fruit> fruits; 
	private int timer;		
		
	public Game(int width, int height, Coordinate c) {
		this.height = height;
		this.width = width;
		snake = new SnakeObservable(this, c);
		fruits = new ArrayList<>();	
		addFruit();
		timer = 0;		
	}	

	public SnakeObservable getSnake() {
		return snake;
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	 boolean isOut(Coordinate c) {
		if (c.getX() < 0 || c.getY() < 0)
			return true;
		if (c.getX() >= width || c.getY() >= height)
			return true;
		return false;
	}

	public void step() {
		boolean grow = false;
		for (Fruit fruit : fruits) {
			if (compare(fruit.getPosition(), snake.getHead()) == 0)
			{
				grow = true;
			}
			fruit.step(snake);
		}
		snake.move(grow);
		timer++;
		fruits.removeIf(fruit -> !fruit.isAlive());		
		if(timer == FRUIT_PERIOD) {
			addFruit();
			timer = 0;			
		}			
	}

	private void addFruit() {
		int x = RANDOM_SEED.nextInt(getHeight());
		int y = RANDOM_SEED.nextInt(getWidth());
		Coordinate position = new Coordinate(x, y);
		fruits.add(new Fruit(position));
	}

	public Stream<Fruit> getFruits() {
		return fruits.stream();
	}
	
	public int compare (Coordinate a, Coordinate b)
	{
		if (a.getX() == b.getX() && a.getY() == b.getY())
			return 0;
		else 
			return 1;
	}
}
