package com.sode.calculator.data

import com.sode.calculator.data.SODEContext.FUNCTIONS
import com.softmoore.graphlib.GraphPoints
import com.softmoore.graphlib.Point
import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Expression

/**
 * A graph function is a function together with a color used to graph it.
 */
internal object SODECalculator {
    /**
     * Find the differential of a function using calculated current values.
     */
    internal class Differential(val function: SODEFunction, initYList: DoubleArray) {
        val argX = Argument("x")
        val argY = Argument("y")
        val argYList =
            initYList.indices.map { it -> Argument("y$it", initYList[it]) }.toTypedArray()
        val expression: Expression = Expression(function.functionStr, argX, argY, *argYList)

        internal fun dydx(x: Double, y: Double, yList: DoubleArray): Double {
            argX.argumentValue = x
            argY.argumentValue = y
            for (i in yList.indices) {
                argYList[i].argumentValue = yList[i]
            }
            val retval = expression.calculate()
            return retval
        }
    }

    /**
     * Get Screen points for simultaneous differential equations using the SODEContext.
     */
    fun getScreenPointsForSODEFunctions(): List<GraphPoints> {
        val initYList = FUNCTIONS.map { it -> it.initValStr.toDouble() }.toDoubleArray()
        val differentialFunctions = buildList {
            for (function in FUNCTIONS) {
                add(Differential(function, initYList))
            }
        }
        return rungeKutta(
            SODEContext.initX,
            initYList,
            SODEContext.finalX,
            SODEContext.increment,
            differentialFunctions
        )
    }

    /**
     * Finds point values of simultaneous differential equations.
     * initial values of differential equations in initYList at initX.
     * calculate all points till finalX and return list of line graph points
     */
    internal fun rungeKutta(
        initX: Double,
        initYList: DoubleArray,
        finalX: Double,
        h: Double,
        funList: List<Differential>
    ): List<GraphPoints> {
        val mutableLists: Array<MutableList<Point>> =
            Array(funList.size) { index -> mutableListOf() }
        var x0 = initX
        // Count number of iterations using step size
        val n = ((finalX - x0) / h).toInt()

        // Iterate for number of iterations
        val yList = initYList
        for (i in 1..n) {
            for (j in funList.indices) {
                yList[j] = rungeKutta(x0, yList[j], yList, h, funList[j])
                mutableLists[j].add(Point(x0, yList[j]))
            }
            // Update next value of x
            x0 = x0 + h
        }
        return buildList {
            funList.forEachIndexed { index, function ->
                add(GraphPoints(mutableLists[index].toList(), function.function.color))
            }
        }
    }

    // Finds value of y for a given x using step size h.
    internal fun rungeKutta(
        x: Double,
        y: Double,
        yList: DoubleArray,
        h: Double,
        differential: Differential
    ): Double {
        // Apply Runge Kutta Formulas to find next value of y
        val k1: Double = h * (differential.dydx(x, y, yList))
        val k2 = h * (differential.dydx(x + 0.5 * h, y + 0.5 * k1, yList))
        val k3: Double = h * (differential.dydx(x + 0.5 * h, y + 0.5 * k2, yList))
        val k4 = h * (differential.dydx(x + h, y + k3, yList))

        // Return next value of y
        return y + (1.0 / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4)
    }
}