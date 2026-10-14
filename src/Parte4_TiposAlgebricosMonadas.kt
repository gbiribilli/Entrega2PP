package entrega2

sealed interface Expressao {
    data class Numero(val valor: Double) : Expressao
    data class Soma(val esquerda: Expressao, val direita: Expressao) : Expressao
    data class Multiplicacao(val esquerda: Expressao, val direita: Expressao) : Expressao
    data class Divisao(val esquerda: Expressao, val direita: Expressao) : Expressao
}

fun avaliar(expr: Expressao): Double = when (expr) {
    is Expressao.Numero -> expr.valor
    is Expressao.Soma -> avaliar(expr.esquerda) + avaliar(expr.direita)
    is Expressao.Multiplicacao -> avaliar(expr.esquerda) * avaliar(expr.direita)
    is Expressao.Divisao -> {
        val divisor = avaliar(expr.direita)
        if (divisor == 0.0) throw ArithmeticException("Divisão por zero!")
        avaliar(expr.esquerda) / divisor
    }
}

fun formatar(expr: Expressao): String = when (expr) {
    is Expressao.Numero -> expr.valor.toString()
    is Expressao.Soma -> "(${formatar(expr.esquerda)} + ${formatar(expr.direita)})"
    is Expressao.Multiplicacao -> "(${formatar(expr.esquerda)} * ${formatar(expr.direita)})"
    is Expressao.Divisao -> "(${formatar(expr.esquerda)} / ${formatar(expr.direita)})"
}

sealed class Maybe<out T> {
    data class Just<out T>(val value: T) : Maybe<T>()

    object None : Maybe<kotlin.Nothing>() {
        override fun toString(): String = "None"
    }

    companion object {
        fun <T> unit(value: T?): Maybe<T> =
            if (value != null) Just(value) else None
    }

    fun <R> map(transform: (T) -> R): Maybe<R> = when (this) {
        is Just -> Just(transform(this.value))
        is None -> None
    }

    fun <R> flatMap(transform: (T) -> Maybe<R>): Maybe<R> = when (this) {
        is Just -> transform(this.value)
        is None -> None
    }

    fun getOrElse(default: @UnsafeVariance T): T = when (this) {
        is Just -> this.value
        is None -> default
    }
}

fun converterParaDouble(texto: String): Maybe<Double> =
    texto.toDoubleOrNull()?.let { Maybe.unit(it) } ?: Maybe.None

fun raizQuadrada(numero: Double): Maybe<Double> =
    if (numero >= 0) Maybe.unit(Math.sqrt(numero)) else Maybe.None

fun dividirPorDois(numero: Double): Maybe<Double> =
    Maybe.unit(numero / 2.0)

fun divisao(a: Int, b: Int): Result<Int> = runCatching { a / b }

fun demonstrarParte4() {
    println("=== PARTE 4: TIPOS ALGÉBRICOS E MÔNADAS ===")

    println("\n--- Tipos algébricos ---")
    val expressao = Expressao.Multiplicacao(
        esquerda = Expressao.Soma(Expressao.Numero(5.0), Expressao.Numero(3.0)),
        direita = Expressao.Divisao(Expressao.Numero(10.0), Expressao.Numero(2.0))
    )
    println("${formatar(expressao)} = ${avaliar(expressao)}")

    println("\n--- Mônada Maybe ---")
    val resultado = Maybe.unit("64.0")
        .flatMap(::converterParaDouble)
        .flatMap(::raizQuadrada)
        .flatMap(::dividirPorDois)
    println("'64.0' -> ${resultado.getOrElse(0.0)}")

    val falha = Maybe.unit("invalido")
        .flatMap(::converterParaDouble)
        .flatMap(::raizQuadrada)
        .flatMap(::dividirPorDois)
    println("'invalido' -> $falha")

    println("\n--- Mônada Result ---")
    val calculo = divisao(20, 4)
        .map { it * 2 }
        .getOrElse { -1 }
    println("20 / 4 -> $calculo")

    val calculoFalha = divisao(20, 0)
        .map { it * 2 }
        .getOrElse { -1 }
    println("20 / 0 -> $calculoFalha")
}
