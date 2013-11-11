import com.twitter.algebird._
import com.twitter.algebird.Operators._
import scala.util.Random

object CanICompile {
    
  def foldMap[A, B](f: A => B)(as: Seq[A])(implicit m: Monoid[B]): B =
    as.map(f).foldLeft(m.zero)(m.plus)

  def main(args: Array[String]): Unit = {

    implicit val sgdMonoid = new SGDMonoid(SGD.constantStep(0.001), SGD.linearGradient)

    val simulatedData = (1 to 80).map { x => 
      x.toDouble -> IndexedSeq[Double](x)
    }

    val toPos = (x: (Double, IndexedSeq[Double])) => SGDPos(x)

    // foldMap(toPos)(simulatedData)

    foldMap(toPos)(simulatedData)(sgdMonoid)

  }

}