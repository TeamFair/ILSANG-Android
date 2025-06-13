package com.ilsangtech.ilsang.core.util

import kotlin.math.max

object XpLevelCalculator {

    private fun getAccumulatedXp(level: Int): Int {
        return (0..level).reduce { acc, xp ->
            acc + xp * 50
        }
    }

    fun getCurrentLevel(xpPoint: Int): Int {
        var currentLevel = 0
        var currentXp = 0
        while (currentXp <= xpPoint) {
            currentLevel += 1
            currentXp += 50 * currentLevel
        }
        return max(0, currentLevel - 1)
    }

    fun getXpPointInCurrentLevel(xpPoint: Int): Int {
        val currentLevel = getCurrentLevel(xpPoint)
        return xpPoint - getAccumulatedXp(currentLevel)
    }

    fun getRequiredXpPointForNextLevel(xpPoint: Int): Int {
        val nextLevel = getCurrentLevel(xpPoint) + 1
        return getAccumulatedXp(nextLevel) - xpPoint
    }

    fun getLevelProgress(xpPoint: Int): Float {
        val nextLevelXp = getAccumulatedXp(getCurrentLevel(xpPoint) + 1)
        return xpPoint.toFloat() / nextLevelXp.toFloat()
    }
}
