
import java.util.ArrayList;
import java.util.List;

public class Snake implements ISnake {

	private final int INITSIZE = 3;

	/**
	 * Body représente le corps du serpent
	 * La tête du serpent se trouve en fin de liste 
	 * La queue du serpent se trouve en début de liste
	 */
	private List<Coordinate> body;
	private Game game;
	private Direction direction;
	private boolean alive;

	public Snake(Game game, Coordinate start) {
		alive = true;
		this.game = game;
		direction = Direction.Right;
		body = new ArrayList<>();
		for (int i = 0; i < INITSIZE; i++) {
			body.add(new Coordinate(start.getX() + i, start.getY()));
		}
	}

	/* (non-Javadoc)
	 * @see snake.ISnake#getDirection()
	 */
	@Override
	public Direction getDirection() {
		return direction;
	}

	/* (non-Javadoc)
	 * @see snake.ISnake#setDirection(snake.Direction)
	 */
	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/* (non-Javadoc)
	 * @see snake.ISnake#getBody()
	 */
	@Override
	public List<Coordinate> getBody() {
		return new ArrayList<>(body);
	}
	
	@Override
	public void move() {
		Coordinate current = body.get(body.size() - 1);
		Coordinate next = new Coordinate(current.getX() + direction.getX(), current.getY() + direction.getY());
		if(body.contains(next) || game.isOut(next))
			alive = false;
		body.add(next);
		body.remove(0);
	}

	/* (non-Javadoc)
	 * @see snake.ISnake#isAlive()
	 */
	@Override
	public boolean isAlive() {
		return alive;
	}


	


}
