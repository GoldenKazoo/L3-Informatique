object QUESTION6
{
    def main(args:Array[String]) =
    {
        val etu1 = new Etu(10)
        val cacheNote = new Cache(etu1.note)

        val (note1, cache1) = cacheNote(etu1)
        println("Note :" + note1)

        val (note2, cache2) = cache1(etu1)
        println("Note (depuis cache) :" + note2)
        val etu2 = new Etu(10)
        val (note3, cache3) = cache2(etu1)
        println("Note (depuis cache) :" + note2)
    }
}