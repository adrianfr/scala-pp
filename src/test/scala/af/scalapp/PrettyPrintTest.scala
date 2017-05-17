package af.scalapp

import org.junit.Test

/**
 * Sanity/functional tests for visual inspections
 */
class PrettyPrintTest {

  @Test
  def testAll() {

    "=== simple objects".pp
    42.pp("scala Int")
    new Integer(22).pp("java Integer")
    (5, "aa", 2.2).pp("Heterogeneous Tuple")
    (5, "aa", 2.2).pp


    "=== simple lists".pp
    Nil.pp("Nil")
    List().pp("Empty List")
    Array().pp("Empty Array")
    List(1, 2, 3, 4).pp("x1#//")
    List(1, "s2", 3, 4).pp("x2")

    // nested collections
    val xs1 = List((1, Map((11, 12), (13, 14))), (List(111), Set(12, 14, 3), "boo"), (3, Seq(13, 14, 17, 18, 2)), (6, Seq(15))).pp
    xs1.pp("aa#/bb/cc#/dd/ee")
    val xs2 = List((1, Seq(11, 12, 13, 14)), (2, Seq(12, 14, 3)), (3, Seq(13, 14, 17, 18, 2)), (4, Seq(7, 8)), (5, Seq(15, 16)), (6, Seq(15))).pp
    ("a", 2, (2, 3, 4, 5)).pp


    "=== iterators".pp
    List(1, 2, 3, 4).toIterator.pp
    List(1, 2, 3, 4).toIterator.toSeq.pp("stream") // this has a Stream type
    //    Stream.from(1).pp   // this does not stop. take a sample by default


    "=== case classes".pp
    case class CaseClass1(i: Int, s: String, list: Seq[Int])
    CaseClass1(2, "aa", List(1, 2, 3)).pp


    "=== Options".pp
    None.pp
    Some(42).pp
    Option(22).pp
    Left(22).pp
    Right(999).pp
    Some(List(1)).pp
  }

}
