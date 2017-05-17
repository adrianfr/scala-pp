package af

/**
 * PP package object
 */
package object scalapp {

  /**
   * Implicits for "Any"
   */
  implicit class PrettyPrintAny[T](val obj: T) {

    /**
      *
      * @param labels slash(/) - separated labels for each depth
      * @return a pretty-print string
      */
    def ps(labels: String): String = PrettyPrint.ps(obj, labels)
    def ps: String = PrettyPrint.ps(obj, "")

    /**
      * Prints the ps output and return this object
      * Tap this anywhere in an operations sequence for debugging
      *
      * @param labels slash(/) - separated labels for each depth
      * @return this object
      */
    def pp(labels: String): T = PrettyPrint.pp(obj, labels)
    def pp: T = PrettyPrint.pp(obj, "")

    /**
      * Applies func to this object before passing it to pp
      * eg. for an RDD: rdd.ppf(_collect)   or  rdd.ppf(_.take(10))
      *
      * @param func function to be applied
      * @param labels optional labels separated by /
      * @return this object (not the result of the func). However, func may have had side effect on the object,
      *         for example triggering an action on an RDD
      */
    def ppf(func: (T => Any), labels: String = ""): T = {
      PrettyPrint.pp(func(obj), labels)
      obj
    }

  }

}
