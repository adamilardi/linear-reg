import com.twitter.algebird._
import com.twitter.algebird.Operators._
import scala.util.Random

object SGDFreakOut {

  def main(args: Array[String]): Unit = {

    val sgdMonoid = new SGDMonoid(SGD.constantStep(0.001), SGD.linearGradient)

    val simulatedData = (1 to 80).map { x => 
      x.toDouble -> IndexedSeq[Double](x)
    }

    // Java Style
    val weights = SGDWeights(1, Vector(1.1, 0.1))
    var coefJavaStyle = sgdMonoid.plus(weights, SGDZero)

    // Look for iteration 78 forward and the algorithm freaks out after it converges
    simulatedData foreach { x =>
      coefJavaStyle = sgdMonoid.plus(coefJavaStyle, SGDPos(x))
      println("current coefficients: ", coefJavaStyle, "new observation: ", SGDPos(x))
    }

    // Scala style
    val coefsScalaStyle = simulatedData.foldLeft(SGDWeights(1, Vector(1.1, 0.1)): SGD[(Double, IndexedSeq[Double])]) { (x: SGD[(Double, IndexedSeq[Double])], y) => 
      sgdMonoid.plus(x, SGDPos(y))
    }

    println(coefJavaStyle, coefsScalaStyle)
  }

}