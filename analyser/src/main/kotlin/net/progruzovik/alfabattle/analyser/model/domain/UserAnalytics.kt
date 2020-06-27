package net.progruzovik.alfabattle.analyser.model.domain

import java.math.BigDecimal

data class UserAnalytics(val userId: String, val totalSum: BigDecimal, val analyticInfo: Map<String, Payments>)
