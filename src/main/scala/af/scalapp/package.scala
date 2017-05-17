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

    /**
      * Applies func to this object before passing it to pp
      * eg. for an RDD: rdd.ppf(_collect)   or  rdd.ppf(_.take(10))
      *
      * @param func function to be applied
      * @param labels optional labels separated by /
      * @return this object (not the result of the func). However, func may have had side effect on the object,
      *         for example triggering an action on an RDD
      */
    def ppf(func: (T => Any), labels: String): T = {
      PrettyPrint.pp(func(obj), labels)
      obj
    }


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
