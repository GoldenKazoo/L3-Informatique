class Etu(val numetu: Int)
{
    def note (e:Etu) =
    {
        Thread.sleep (2000)
        e.numetu % 21
    }
    override def equals(objet: Any): Boolean = 
    objet match
    {
        case e: Etu => this.numetu == e.numetu
        case _      => false
    }
    // override def hashCode(): Int = numetu.hashCode()
    
}

def note(e: Etu) =
{
  Thread.sleep(2000)
  e.numetu % 21
}


