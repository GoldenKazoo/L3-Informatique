public claass Var
{
    int x = 0;
}

void main()
{
    var v = new Var();
    new Prod(v).start();
    new Cons(v).start();
}