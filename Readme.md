# Paradigmas de Programação — Entrega 2: Sistema de Funções em Kotlin

## Como executar

O repositório já inclui o `main.jar` compilado. Para executar, basta ter o Java instalado:

```bash
java -jar main.jar
```

Para compilar novamente (requer o Kotlin Compiler, `kotlinc`):

```bash
kotlinc src/*.kt -include-runtime -d main.jar
```

## Estrutura

| Arquivo | Conteúdo |
| :--- | :--- |
| `Main.kt` | Executa todas as demonstrações |
| `Parte1_FuncoesComoTipos.kt` | Função como tipo e função por parâmetro |
| `Parte2_AltaOrdem.kt` | Função de alta ordem com generics, map, filter e reduce |
| `Parte3_ParciaisCurrying.kt` | Encadeamento (`let`, `compose`, `andThen`), currying e aplicação parcial |
| `Parte4_TiposAlgebricosMonadas.kt` | Tipos algébricos (`sealed`/`data class`) e mônadas (`Maybe`, `Result`) |
