import breeze.linalg._
import breeze.numerics._
import breeze.stats.mean

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.{break, breakable}

import java.io.File


object Main {
  def main(args: Array[String]): Unit = {
    val file = read_csv("Boston.csv")
    val data = DenseMatrix(file.toArray.map(_.toArray): _*)


    var X_test = data(0 until (data.rows * 0.15).toInt, 0 until data.cols - 1)
    var X_train = data((data.rows * 0.15).toInt + 1 until data(::, 0).length, 0 until data.cols - 1)
    X_train = DenseMatrix.horzcat(X_train, DenseMatrix(DenseVector.fill(X_train.rows) {
      1.0
    }).t)
    X_test = DenseMatrix.horzcat(X_test, DenseMatrix(DenseVector.fill(X_test.rows) {
      1.0
    }).t)


    val y_test = data(0 until (data.rows * 0.15).toInt, data.cols - 1)
    val y_train = data((data.rows * 0.15).toInt + 1 until data.rows, data.cols - 1)


    var model = new LinearRegression(X_train, y_train)
    model.fit()
    val y_pred = model.predict(X_test)
    val mse = sum((y_test - y_pred).map(x => pow(x, 2)))
    val p = sum((y_test - mean(y_test)).map(x => pow(x, 2)))
    println("r2 = " + (1 - mse / p))

    csvwrite(new File("res.csv"), Matrix(y_pred))
  }


  def read_csv(path: String): ListBuffer[Array[Double]] = {
    val bufferedSource = io.Source.fromFile(path)
    val rows = ListBuffer[Array[Double]]()
    for ((line, count) <- bufferedSource.getLines.zipWithIndex) {
      breakable {
        if (count == 0) break
        val row = line.split(",").map(_.trim)
        if (row.contains("")) {
          break
        } else {
          rows += (row.slice(1, row.length).map(x => x.toDouble))
        }
      }
    }
    bufferedSource.close
    rows
  }

}

class LinearRegression(X_train: DenseMatrix[Double], y_train: DenseVector[Double]) {
  var w = DenseVector.zeros[Double](X_train.cols)

  def predict(X: DenseMatrix[Double]): DenseVector[Double] = X * w

  def fit(): Unit = {
    w = inv(X_train.t * X_train + 0.05 * DenseMatrix.eye[Double](X_train.cols)) * X_train.t * y_train
  }
}








