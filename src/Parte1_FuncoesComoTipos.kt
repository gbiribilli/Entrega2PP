package entrega2

fun multiplicar(a: Int, b: Int): Int = a * b

fun executarOperacao(a: Int, b: Int, operacao: (Int, Int) -> Int): Int {
    return operacao(a, b)
}

fun criarMultiplicador(fator: Int): (Int) -> Int {
    return { numero -> numero * fator }
}

fun demonstrarParte1() {
    println("=== PARTE 1: FUNÇÕES COMO TIPO ===")

    println("\n--- Função como tipo ---")
    val soma: (Int, Int) -> Int = { x, y -> x + y }
    val subtracao = fun(x: Int, y: Int): Int {
        return x - y
    }
    val multiplicacaoRef: (Int, Int) -> Int = ::multiplicar

    println("soma(10, 5) = ${soma(10, 5)}")
    println("subtracao(10, 5) = ${subtracao(10, 5)}")
    println("multiplicacaoRef(10, 5) = ${multiplicacaoRef(10, 5)}")

    println("\n--- Função por parâmetro ---")
    val res1 = executarOperacao(10, 20, soma)
    val res2 = executarOperacao(10, 20, ::multiplicar)
    val res3 = executarOperacao(20, 5, { x, y -> x / y })
    val res4 = executarOperacao(2, 8) { base, expoente ->
        var total = 1
        repeat(expoente) { total *= base }
        total
    }
    println("Variável: $res1")
    println("Referência (::): $res2")
    println("Lambda: $res3")
    println("Trailing lambda (2^8): $res4")

    println("\n--- Função que retorna função ---")
    val duplicar = criarMultiplicador(2)
    val triplicar = criarMultiplicador(3)
    println("duplicar(7) = ${duplicar(7)}")
    println("triplicar(7) = ${triplicar(7)}")
}
