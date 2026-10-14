package entrega2

infix fun <A, B, C> ((B) -> C).compose(g: (A) -> B): (A) -> C = { a -> this(g(a)) }

infix fun <A, B, C> ((A) -> B).andThen(g: (B) -> C): (A) -> C = { a -> g(this(a)) }

fun <A, B, R> ((A, B) -> R).curried(): (A) -> (B) -> R = { a ->
    { b -> this(a, b) }
}

fun <A, B, C, R> ((A, B, C) -> R).curried(): (A) -> (B) -> (C) -> R = { a ->
    { b -> { c -> this(a, b, c) } }
}

fun <A, B, R> ((A, B) -> R).partial(a: A): (B) -> R = { b -> this(a, b) }

class PartialFunction<in In, out Out>(
    val isDefinedAt: (In) -> Boolean,
    private val f: (In) -> Out
) : (In) -> Out? {
    override fun invoke(x: In): Out? = if (isDefinedAt(x)) f(x) else null
}

fun aplicarDesconto(desconto: Double, valor: Double): Double = valor * (1 - desconto / 100.0)

fun notificar(msg: String, canal: String = "EMAIL", prioridade: String = "NORMAL") {
    println("[$canal | $prioridade] $msg")
}

fun demonstrarParte3() {
    println("=== PARTE 3: ENCADEAMENTO, CURRYING E FUNÇÕES PARCIAIS ===")

    println("\n--- Encadeamento ---")
    val resultado = "   kotlin com paradigmas funcionais   "
        .let { it.trim() }
        .let { it.replace(" ", "_") }
        .let { it.uppercase() }
    println("let: '$resultado'")

    val removerEspacos: (String) -> String = { it.trim() }
    val adicionarPrefixo: (String) -> String = { "PROJETO: $it" }
    val caixaAlta: (String) -> String = { it.uppercase() }
    val formatarTexto = removerEspacos andThen adicionarPrefixo andThen caixaAlta
    println("andThen: '${formatarTexto("  linguagens  ")}'")

    val duplicar = { x: Int -> x * 2 }
    val somarTres = { x: Int -> x + 3 }
    val somaDepoisDuplica = duplicar compose somarTres
    println("compose: ${somaDepoisDuplica(5)}")

    println("\n--- Currying ---")
    val somar = { a: Int, b: Int -> a + b }
    val somarCurried = somar.curried()
    println("somar.curried()(2)(3) = ${somarCurried(2)(3)}")

    val calcularFrete = { pesoKg: Double, distanciaKm: Double, taxaPorKm: Double ->
        (pesoKg * 0.5) + (distanciaKm * taxaPorKm)
    }
    val freteCurried = calcularFrete.curried()
    val freteComPeso = freteCurried(10.0)
    val freteComDistancia = freteComPeso(150.0)
    val total = freteComDistancia(2.5)
    println("Frete passo a passo: $total")

    println("\n--- Aplicação parcial ---")
    val calcularImposto = { aliquota: Double, valor: Double -> valor * (aliquota / 100.0) }
    val calcularIcmsSp = calcularImposto.partial(18.0)
    println("partial (ICMS 18% de 1000): ${calcularIcmsSp(1000.0)}")

    val descontoVip = { valor: Double -> aplicarDesconto(15.0, valor) }
    println("lambda (VIP 15% de 500): ${descontoVip(500.0)}")

    notificar("Alerta!")
    notificar("Falha crítica!", canal = "SMS", prioridade = "URGENTE")

    println("\n--- Função parcial ---")
    val divisaoSegura = PartialFunction<Pair<Double, Double>, Double>(
        isDefinedAt = { (_, divisor) -> divisor != 0.0 },
        f = { (dividendo, divisor) -> dividendo / divisor }
    )
    println("10 / 2 = ${divisaoSegura(Pair(10.0, 2.0))}")
    println("10 / 0 = ${divisaoSegura(Pair(10.0, 0.0))}")
}
