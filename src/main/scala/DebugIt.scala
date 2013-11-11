import com.twitter.algebird._
import scala.util.Random

object DebugIt {

  def main(args: Array[String]): Unit = {

    def dot(x : IndexedSeq[Double], y : IndexedSeq[Double]) : Double =
        x.view.zip(y).map { case (a: Double, b: Double) => a*b }.sum

    val linearGradient : (IndexedSeq[Double], (Double, IndexedSeq[Double])) => IndexedSeq[Double] = { (w, pos) =>
        val (y, xs) = pos
        val xsPlusConst = xs :+ 1.0
        assert(w.length == xsPlusConst.length)
        val err = dot(w,xsPlusConst) - y
        // Here is the gradient
        val m = xsPlusConst.map { _ * err }
        
        // println("error", err, "m", m, "weights", w)
        m
      } 


    val sgdMonoid = new SGDMonoid(SGD.constantStep(0.001), linearGradient)
    

    // val many = (1 to 10).map { x => 
    //     x.toDouble*x -> IndexedSeq[Double](x/2)
    // }

    val many = (1 to 80).map { x => 
        x.toDouble -> IndexedSeq[Double](x)
    }

    val data = 2.0 -> IndexedSeq[Double](2)
    val data2 = 3.0 -> IndexedSeq[Double](3)
    //println(sgdMonoid)

    val weights = SGDWeights(1, Vector(1.1, 0.01))
    // val weights = SGDWeights(1, Vector(5, 5))

    var hi = sgdMonoid.plus(weights, SGDZero)
    println(hi)
    //hi = sgdMonoid.plus(hi, SGDPos(data))
    //hi = sgdMonoid.plus(hi, SGDPos(data2))

    //data.foldLeft(SGDZero, (x, y => x.plus()))


    many foreach { x => 

        hi = sgdMonoid.plus(hi.asInstanceOf[SGDWeights], SGDPos(x))
        println("new weights", hi, "new data", SGDPos(x))
        // val m = sgdMonoid.newWeights(hi.asInstanceOf[SGDWeights], x)
        // println("more weights", m)

        val ty = hi.asInstanceOf[SGDWeights].weights.view
        .zip(List(2.0, 1.0))
        .map { case (l : Double, r : Double) =>  
            // println(l, .001 * r);
            l - (.001 * r) 

        }
        .toIndexedSeq

        // println("weeee", ty)
    }


    println(hi)


    
  }

}