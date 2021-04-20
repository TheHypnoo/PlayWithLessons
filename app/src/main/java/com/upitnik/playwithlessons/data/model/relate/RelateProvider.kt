package com.upitnik.playwithlessons.data.model.relate

class RelateProvider {
    companion object {
        fun getQuestions(): List<RelateData> {

            return listOf(
                RelateData(
                    title = "Usamos la palabra reservada var para...",
                    answers = listOf(
                        RelateAnswerData(title = "Casa", correct = "Casa"),
                        RelateAnswerData(title = "Ca234sa", correct = "Cas234a"),
                        RelateAnswerData(title = "324", correct = "Ca3242sa"),
                        RelateAnswerData(title = "Valor constante", correct = "Variable")
                    )
                ),
                RelateData(
                    title = "Esto es una prueba",
                    answers = listOf(
                        RelateAnswerData(title = "Variable", correct = "PEPE"),
                        RelateAnswerData(title = "Valor constante", correct = "Volante"),
                        RelateAnswerData(title = "Compilar la app", correct = "Volante")
                    )
                ),
                RelateData(
                    title = "Esto es una prueba",
                    answers = listOf(
                        RelateAnswerData(title = "adw", correct = "Volante"),
                        RelateAnswerData(title = "adwd constante", correct = "Volante"),
                        RelateAnswerData(title = "Compilar dawd app", correct = "Volante")
                    )
                ),
                RelateData(
                    title = "adw...",
                    answers = listOf(
                        RelateAnswerData(title = "Variable", correct = "Volante"),
                        RelateAnswerData(title = "Valor constante", correct = "Volante"),
                        RelateAnswerData(title = "Crear una función", correct = "Volante"),
                        RelateAnswerData(title = "Compilar la app", correct = "Volante")
                    )
                ),
                RelateData(
                    title = "Esto es dqwdba",
                    answers = listOf(
                        RelateAnswerData(title = "Variable", correct = "Volante"),
                        RelateAnswerData(title = "Valor constante", correct = "Volante"),
                        RelateAnswerData(title = "Crear una función", correct = "Volante"),
                        RelateAnswerData(title = "Compilar la app", correct = "Volante")
                    )
                ),
                RelateData(
                    title = "Esto es unqwdeueba",
                    answers = listOf(
                        RelateAnswerData(title = "adw", correct = "Volante"),
                        RelateAnswerData(title = "adwd constante", correct = "Volante"),
                        RelateAnswerData(title = "dwad una función", correct = "Volante"),
                        RelateAnswerData(title = "Compilar dawd app", correct = "Volante")
                    )
                )
            )
        }
    }
}