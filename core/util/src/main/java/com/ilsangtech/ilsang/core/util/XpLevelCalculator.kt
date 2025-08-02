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

    fun getLevelProgress(xpPoint: Int): Float {
        val currentLevel = getCurrentLevel(xpPoint)
        val currentLevelXp = getAccumulatedXp(currentLevel)
        val nextLevelXp = getAccumulatedXp(currentLevel + 1)

        val requiredXp = nextLevelXp - xpPoint
        return 1f - (requiredXp.toFloat() / (nextLevelXp - currentLevelXp).toFloat())
    }
}
