package af.scalapp

import scala.util.Random

/**
 * Describes a type that can be treated like a collection
 */
trait CollectionLike[A] {

  def asList(): List[A]

  // basic implementations (not likely to be optimal)
  def head(maxElements: Int = 10): List[A] = asList().take(maxElements)

  def sample(maxElements: Int = 10): List[A] = asList().take(maxElements)   // todo implement real sampling

  def headSample(pattern: String = "5;5"): List[A] = {
    val counts = pattern.split(';')
    assert(counts.length == 2)
    counts.foreach(a => assert(a.forall(_.isDigit)))
    val intCounts = counts.map(_.toInt)
    var (headCount, randCount) = (intCounts(0), intCounts(1))
    val list = asList()
    val len = list.length
    if (len <= headCount + randCount) {
      headCount += randCount
      randCount = 0
    }

    val head = list.take(headCount)
    if (randCount > 0) {
      val random = Random.shuffle(list.slice(headCount, len)).take(randCount)
      head ::: random
    } else
      head
  }

  def headSampleTail(pattern: String = "5;5;5"): List[A] = {
    val counts = pattern.split(';')
    assert(counts.length == 3)
    counts.foreach(a => assert(a.forall(_.isDigit)))
    val intCounts = counts.map(_.toInt)
    var (headCount, randCount, tailCount) = (intCounts(0), intCounts(1), intCounts(2))
    val list = asList()
    val len = list.length
    if (len <= headCount + randCount + tailCount) {
      headCount += randCount + tailCount
      randCount = 0
    }

    val head = list.take(headCount)
    if (randCount > 0) {
      val random = Random.shuffle(list.slice(headCount, len - tailCount)).take(randCount)
      val tail = list.drop(len - tailCount)
      head ::: random ::: tail
    } else
      head
  }
}
