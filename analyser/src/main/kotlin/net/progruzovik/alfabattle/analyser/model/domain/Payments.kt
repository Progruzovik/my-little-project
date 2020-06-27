package net.progruzovik.alfabattle.analyser.model.domain

import java.math.BigDecimal

data class Payments(var min: BigDecimal? = null, var max: BigDecimal? = null, var sum: BigDecimal = BigDecimal.ZERO)
