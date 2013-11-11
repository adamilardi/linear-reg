import com.twitter.algebird._
import com.twitter.algebird.Operators._
import scala.util.Random

object SGDFreakOut {

  def main(args: Array[String]): Unit = {

    //implicit val standardMonoid = new SGDMonoid(SGD.constantStep(0.001), SGD.linearGradient)


    implicit val sgdMonoid: Monoid[SGD[(Double, IndexedSeq[Double])]] = new SGDMonoid(SGD.constantStep(0.001), SGD.linearGradient)

    val many = (1 to 80).map { x => 
        x.toDouble -> IndexedSeq[Double](x)
    }


    val weights = SGDWeights(1, Vector(1.1, 0.1))
    var sgdTheta = sgdMonoid.plus(weights, SGDZero)

    many foreach { x => 
        sgdTheta = sgdMonoid.plus(sgdTheta, SGDPos(x))
        println("new weights", sgdTheta, "new data", SGDPos(x))
    }

    def foldMap[A, B](f: A => B)(as: Seq[A])(implicit m: Monoid[B]): B =
        as.map(f).foldLeft(m.zero)(m.plus)

    val bom = many.foldLeft(SGDWeights(1, Vector(1.1, 0.1))) { (x: SGDWeights, y) => 
        x.asInstanceOf[SGDMonoid[(Double, IndexedSeq[Double])]].plus(x.asInstanceOf[SGDWeights], SGDPos(y))
    }

    
    println(sgdTheta)
  }

}