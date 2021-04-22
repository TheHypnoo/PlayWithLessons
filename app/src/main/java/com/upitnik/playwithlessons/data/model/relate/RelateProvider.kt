package com.upitnik.playwithlessons.data.model.relate

class RelateProvider {
    companion object {
        fun getQuestions(): List<RelateData> {

            return listOf(
                RelateData(
                    getAnswers()
                ),
            )
        }

        fun getAnswers(): List<RelateAnswers> {
            return listOf(
                RelateAnswers(
                    answerLeft = listOf(
                        RelateAnswerLeft(title = "Casa", correct = "Jardin"),
                        RelateAnswerLeft(title = "Coche", correct = "Volante"),
                        RelateAnswerLeft(title = "Internet", correct = "Ordenador"),
                        RelateAnswerLeft(title = "Movil", correct = "Llamada")
                    ),
                    answerRight = listOf(
                        RelateAnswerRight(title = "Jardin", correct = "Casa"),
                        RelateAnswerRight(title = "Volante", correct = "Coche"),
                        RelateAnswerRight(title = "Ordenador", correct = "Internet"),
                        RelateAnswerRight(title = "Llamada", correct = "Movil")
                    )
                ),
                RelateAnswers(
                    answerLeft = listOf(
                        RelateAnswerLeft(title = "Casa", correct = "Jardin"),
                        RelateAnswerLeft(title = "Coche", correct = "Volante"),
                        RelateAnswerLeft(title = "Internet", correct = "Ordenador"),
                        RelateAnswerLeft(title = "Movil", correct = "Llamada")
                    ),
                    answerRight = listOf(
                        RelateAnswerRight(title = "Jardin", correct = "Casa"),
                        RelateAnswerRight(title = "Volante", correct = "Coche"),
                        RelateAnswerRight(title = "Ordenador", correct = "Internet"),
                        RelateAnswerRight(title = "Llamada", correct = "Movil")
                    )
                )
            )
        }
    }
}