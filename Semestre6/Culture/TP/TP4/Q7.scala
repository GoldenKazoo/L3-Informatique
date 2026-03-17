object QUESTION7
{
    def composition(c:Cache[U,V]) =
    {
        val(dedans, pasdedans) = c.partition
        {
            (_,y) => c.contains(y)
        }
    }

    def main(args:Array[String]) =
    {

    }

    
}

