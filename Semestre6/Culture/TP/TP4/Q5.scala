object QUESTION5
  {
    def main(args:Array[String]) =
    {
        val etu = new Etu(32)

        val cacheNote = new Cache(etu.note)

        val (note1, cache1) = cacheNote(etu)
        println("Note :" + note1)

        val (note2, cache2) = cacheNote(etu)
        println("Note (depuis cache) :" + note2)
    }
}