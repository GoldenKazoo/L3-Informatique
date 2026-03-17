import scala.collection.immutable.HashMap

class Cache[T, U] private (val f: T => U, val cache: HashMap[T, U])
{
  
  def this(f: T => U) = this(f, HashMap.empty[T, U])

  def apply(x: T): (U, Cache[T, U]) =
  {
    cache.get(x) match
    {
        case Some(result) => (result, this)
        case None =>
        val result = f(x)
        val newCache = new Cache(f, cache + (x -> result))
        (result, newCache)
    }
  }
}



