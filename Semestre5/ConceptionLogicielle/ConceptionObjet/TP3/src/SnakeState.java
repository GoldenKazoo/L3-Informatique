public abstract class SnakeState
{
    protected Snake snake;
    protected SnakeState(Snake snake)
    {
        this.snake = snake;
    }
    public abstract void move();
}
