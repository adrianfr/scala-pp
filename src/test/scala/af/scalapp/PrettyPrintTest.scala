package af.scalapp

import org.junit.Test

/**
 * Sanity/functional test for PrettyPrint
 */
class PrettyPrintTest {

  @Test
  def testAll() {
    // simple objects
    42.pp("my42")
    new Integer(22).pp("Integer22")
    val tup = (5, "aa", 2.2)
    tup.pp
    tup.pp("myTuple3")

    // simple lists
    List().pp("emptyList")
    List(1, 2, 3, 4).pp("x1#//")
    List(1, "s2", 3, 4).pp("x2")

    // nested collections
    val xs1 = List((1, Map((11, 12), (13, 14))), (List(111), Set(12, 14, 3), "boo"), (3, Seq(13, 14, 17, 18, 2)), (6, Seq(15))).pp
    xs1.pp("aa#/bb/cc#/dd/ee")
    val xs2 = List((1, Seq(11, 12, 13, 14)), (2, Seq(12, 14, 3)), (3, Seq(13, 14, 17, 18, 2)), (4, Seq(7, 8)), (5, Seq(15, 16)), (6, Seq(15))).pp
    ("a", 2, (2, 3, 4, 5)).pp


    // iterators
    List(1, 2, 3, 4).toIterator.pp("iterator")
    List(1, 2, 3, 4).toIterator.toSeq.pp("stream") // this has a Stream type
    //    Stream.from(1).pp   // this does not stop. take a sample by default


    // case classes
    case class CC(i: Int, s: String, list: Seq[Int])
    val cc = new CC(2, "aa", List(1, 2, 3))
    cc.pp


    // Options
    None.pp
    Some(42).pp
    Option(22).pp
    Left(25).pp
    Right(999).pp
    Some(List(1)).pp
  }

}
