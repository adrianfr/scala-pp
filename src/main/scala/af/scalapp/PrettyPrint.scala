package af.scalapp

import scala.collection.GenTraversableOnce

/**
  * Utilities for pretty-printing nested collections
  */
object PrettyPrint {

  val nl = System.getProperty("line.separator")
  val objStyles = Map(
    "seqStyle" ->("[", "]"),
    "setStyle" ->("{", "}"),
    "mapStyle" ->("<", ">"),
    "tupleStyle" ->("(", ")"),
    "objStyle" ->("{", "}"),
    "iteratorStyle" ->("[", "]")
  )

  //

  /**
    * map and pp
    * apply m to each element on the first level (e.g. _.size())
    * @param obj
    * @param m
    * @tparam T
    * @return
    */
  def mpp[T](obj: T, m: (T) => String): T = {
    // todo m andThen pp
    ???
  }

  def pp[T](obj: T): T = pp(obj, "")

  /**
    * Pretty-print the object argument.
    * Multiple arguments are printed as a tuple. ex: pp("myLabel", obj)
    * @param obj Any object (tuple, or nested collection containing any traversable, array or tuple)
    * @param labelSeq A slash-separated sequence of labels for each level. If a label ends with #, the label will be followed by the index of the element
    *                 e.g. "aa/bb#/cc"
    * @tparam T object Type, aslo used as return type
    * @return the original parameter object
    */
  def pp[T](obj: T, labelSeq: String): T = {
    val str = ps(obj, labelSeq)
    System.out.synchronized {
      print(str)
    }
    obj
  }

  def ps[T](obj: T): String = ps(obj, "")

  /**
    * Pretty-print recursively an object (or more than one - as a tuple) composed of any number of nested collections, arrays, and Tuples. Collections on the last level are printed on single line
    * Multiple arguments are printed as a tuple. ex: pp("myLabel", obj)
    * @param obj The object to be pretty-printed. Since we handle tuples, you can pass more than one parameter
    * @param labelSeq A slash-separated sequence of labels for each level. If a label ends with #, the label will be followed by the index of the element
    *                 e.g. "aa/bb#/cc"
    * @return The pretty-string
    */
  def ps[T](obj: T, labelSeq: String): String = {
    val sb = new StringBuilder
    val labelsMap = labelSeq.split("/") // todo: make this a tree. now it's first of each level only
      .map(_.trim)
      .zipWithIndex
      .map {
        case (label, index) if label.endsWith("#") => (index, (label.init, true))
        case (label, index) => (index, (label, false))
      }
      .toMap.withDefaultValue(("", false)) // unspecified labels are empty by default, with no index printing

    ppIndent(obj, sb, 0, 0)

    // recursively print
    def ppIndent(obj: Any, sb: StringBuilder, depth: Int, index: Int): Unit = {
      val (listOrObj, style) = inspect(obj)
      val (left, right) = objStyles(style)
      val indent = "  " * depth
      val indexStr = if (labelsMap(depth)._2) " #" + index else ""
      val label = if (labelsMap(depth)._1.isEmpty) "" else labelsMap(depth)._1 + indexStr + ": "

      listOrObj match {
        case list: List[_] if isOneLiner(list) => sb ++= indent + left + label + list.mkString(",") + right + nl // single line for small collections
        case list: List[_] => // one element per line
          sb ++= indent + left + label + nl
          list.zipWithIndex.foreach(a => ppIndent(a._1, sb, depth + 1, a._2))
          sb ++= indent + right + nl
        case _ => sb ++= indent + label + obj.toString + nl // no brackets for non-traversables
      }
    }

    // returns (obj, style)
    def inspect(obj: Any): (Any, String) = {
      obj match {
        case arr: Array[_] => (arr.toList, "seqStyle")
        case seq: Seq[_] => (seq.toList, "seqStyle") // it could be a Stream, so we call toList to materialize it
        case set: Set[_] => (set.toList, "setStyle")
        case map: Map[_, _] => (map.toList, "mapStyle")
        case trav: GenTraversableOnce[_] => (trav.toList, "iteratorStyle") // materialize it because we need to access it more than once
        case p: Product => (p.productIterator.toList, "tupleStyle") // includes all tuples, case classes, Option, Either
        case coll: CollectionLike[_] => (coll.asList(), "seqStyle") // any other object
        case _ => (obj, "objStyle") // any other object
      }
    }

    def isOneLiner(list: List[_]): Boolean = {
      !list.exists(isNonEmptyTraversableLike) // with no subCollections, it's compact, regardless of size
      //      list.map(inspect).map(_._2).sum < 10
    }

    // return true if you want these types treated as collections (i.e. displayed one element per line)
    def isNonEmptyTraversableLike(obj: Any): Boolean = {
      obj match {
        case trav: GenTraversableOnce[_] if trav.size > 1 => true
        case arr: Array[_] if arr.length > 1 => true
        case _: Product => true // includes all tuples, case classes, Option, Either
        case _: AnyRef => false // AnyRef is not a traversable
        case _ => false // AnyVal is not traversable  BUT, may contain traversable members...todo?
      }
    }

    sb.toString()
  }
}
