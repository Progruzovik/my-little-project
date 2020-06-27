package net.progruzovik.alfabattle.analyser.model.domain

data class UserAnalytics(val userId: String, val totalSum: Double, val analyticInfo: Map<String, Payments>)
