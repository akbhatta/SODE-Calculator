package com.sode.calculator.data

import android.graphics.Color
import com.softmoore.graphlib.GraphPoints
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License
import org.mariuszgromada.math.mxparser.mXparser
import kotlin.math.PI

private val TOKEN_EXP: Regex = Regex("^x$|^y\\d+$")

/**
 * Stores list of SODE functions initial values and constant values currently loaded
 */
object SODEContext {
    /**
     * An array of SODE function items.
     */
    val FUNCTIONS: MutableList<SODEFunction> = ArrayList()

    /**
     * An array of SODE constant items.
     */
    val CONSTANTS: MutableList<SODEConstant> = ArrayList()

    /**
     * Generated graph points.
     */
    var graphPoints: List<GraphPoints> = listOf()

    /**
     * An array of coordinates to get from UI.
     */
    val COORDINATES: List<SODEConstant> = listOf(
        SODEConstant("X-Start", "-4.0"),
        SODEConstant("X-End", "4.0"),
        SODEConstant("Y-Start", "-4.0"),
        SODEConstant("Y-End", "4.0"),
        SODEConstant("Step-Size", "0.1"),
    )

    /**
     * Initial value of X co-ordinate.
     */
    var initX: Double = -PI
    /**
     * Final value of X co-ordinate.
     */
    var finalX: Double = PI
    /**
     * Initial value of X co-ordinate.
     */
    var initY: Double = -1.0
    /**
     * Final value of X co-ordinate.
     */
    var finalY: Double = -6.0
    /**
     * Initial value of X co-ordinate.
     */
    var increment: Double = 0.1

    /**
     * Clear the context, usually when a new file/configuration is loaded.
     * Make sure to invalidate any views using it.
     */
    fun clear() {
        FUNCTIONS.clear()
        CONSTANTS.clear()
        graphPoints = listOf()
    }

    fun updateFunctionDependencies() {
        License.iConfirmNonCommercialUse("SODE Calculator")
        mXparser.disableCanonicalRounding();
        mXparser.disableUlpRounding();
        mXparser.disableAlmostIntRounding();
        for (function in FUNCTIONS) {
            function.evaluate()
            val tokens = function.expression.copyOfInitialTokens
            val missingTokens =
                tokens.filter { it.tokenTypeId < 0 && !it.tokenStr.matches(TOKEN_EXP) }
            val currentTokens = CONSTANTS.map { it -> it.constant }.toSet()
            val unavailableTokens = missingTokens.filter { !currentTokens.contains(it.tokenStr) }
            for (token in unavailableTokens) {
                CONSTANTS.add(SODEConstant(token.tokenStr))
            }
        }
    }

    fun createGraphPoints() {
        initX = COORDINATES[0].valueStr.toDouble()
        finalX = COORDINATES[1].valueStr.toDouble()
        initY = COORDINATES[2].valueStr.toDouble()
        finalY = COORDINATES[3].valueStr.toDouble()
        increment = COORDINATES[4].valueStr.toDouble()
        graphPoints = SODECalculator.getScreenPointsForSODEFunctions()
    }
}

/**
 * A function representing a differential equation with description.
 */
data class SODEFunction(
    var functionStr: String = "",
    var initValStr: String = "0.0",
    var color: Int = Color.BLACK,
    var evaluatedExpression: String = "",
    internal val expression: Expression = Expression(functionStr)
) {
    override fun toString(): String = functionStr

    fun evaluate(): String {
        // expression.disableImpliedMultiplicationMode()
        expression.expressionString = functionStr
        evaluatedExpression = expression.canonicalExpressionString
        return evaluatedExpression
    }
}

/**
 * A function representing a differential equation constants with description.
 */
data class SODEConstant(val constant: String, var valueStr: String = "0.0") {
    override fun toString(): String = constant
}