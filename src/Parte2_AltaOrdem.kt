package entrega2

data class Produto(val nome: String, val preco: Double, val categoria: String)

fun <T, R> filtrarETransformar(
    itens: List<T>,
    predicado: (T) -> Boolean,
    transformador: (T) -> R
): List<R> {
    val resultado = mutableListOf<R>()
    for (item in itens) {
        if (predicado(item)) {
            resultado.add(transformador(item))
        }
    }
    return resultado
}

fun demonstrarParte2() {
    println("=== PARTE 2: FUNÇÕES DE ALTA ORDEM ===")

    println("\n--- Alta ordem com polimorfismo ---")
    val numeros = listOf(1, 2, 3, 4, 5, 6)
    val paresFormatados = filtrarETransformar(
        itens = numeros,
        predicado = { it % 2 == 0 },
        transformador = { "Número Par: $it" }
    )
    println("Int -> String: $paresFormatados")

    val catalogo = listOf(
        Produto("Notebook", 4500.0, "Eletrônicos"),
        Produto("Mouse", 150.0, "Eletrônicos"),
        Produto("Cafeteira", 320.0, "Eletrodomésticos")
    )
    val precosEletronicosComDesconto = filtrarETransformar(
        itens = catalogo,
        predicado = { it.categoria == "Eletrônicos" },
        transformador = { it.preco * 0.90 }
    )
    println("Produto -> Double: $precosEletronicosComDesconto")

    println("\n--- Filter, Map e Reduce ---")
    val eletronicos = catalogo.filter { it.categoria == "Eletrônicos" }
    println("Filter: ${eletronicos.map { it.nome }}")

    val nomesEmMaiusculo: List<String> = catalogo.map { it.nome.uppercase() }
    println("Map: $nomesEmMaiusculo")

    val precos = catalogo.map { it.preco }
    val totalPrecos = precos.reduce { acumulador, precoAtual -> acumulador + precoAtual }
    println("Reduce: R$ $totalPrecos")

    val totalEletronicosCaros = catalogo
        .filter { it.categoria == "Eletrônicos" && it.preco > 200.0 }
        .map { it.preco }
        .reduce { acc, preco -> acc + preco }
    println("Filter -> Map -> Reduce: R$ $totalEletronicosCaros")
}
