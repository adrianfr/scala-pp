package af

/**
 * PP package object
 */
package object scalapp {

  /**
   * Implicits for "Any"
   */
  implicit class PrettyPrintAny[T](val obj: T) {

    // PrettyString: recursive alternative for toString()
    def ps: String = PrettyPrint.ps(obj, "")
    def ps(labels: String): String = PrettyPrint.ps(obj, labels)

    // tap this anywhere in an operations sequence (on collections) for debugging. Returns this object
    def pp: T = PrettyPrint.pp(obj, "")
    def pp(labels: String): T = PrettyPrint.pp(obj, labels)

    def saveToFile(path: String): Unit = {
      val bw = new java.io.BufferedWriter(new java.io.FileWriter(path))
      bw.write(obj.toString)
      bw.close()
    }

    def psSaveToFile(path: String): Unit = {
      val bw = new java.io.BufferedWriter(new java.io.FileWriter(path))
      bw.write(obj.ps)
      bw.close()
    }
  }

}
