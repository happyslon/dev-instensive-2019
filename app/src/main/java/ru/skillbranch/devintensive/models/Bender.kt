package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when(question){
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question

    }

    fun listenAnswer(answer:String) : Pair<String, Triple<Int,Int,Int>>{
        return if (question.answers.contains(answer)) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else if (question == Question.IDLE) {
            question.question to status.color
        } else {
            status = status.nextStatus()
            if (status == Status.NORMAL) {
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }

    fun checkCorrectAnswer(answer: String): String {
        return if (answer.isNotEmpty()) {
            when (question) {
                Question.NAME -> if (!answer[0].isUpperCase()) "Имя должно начинаться с заглавной буквы\n${question.question}" else "-1"
                Question.PROFESSION -> if (!answer[0].isLowerCase()) "Профессия должна начинаться со строчной буквы\n${question.question}" else "-1"
                Question.MATERIAL -> if (answer.contains("\\d".toRegex())) "Материал не должен содержать цифр\n${question.question}" else "-1"
                Question.BDAY -> if (answer.contains("\\D".toRegex())) "Год моего рождения должен содержать только цифры\n${question.question}" else "-1"
                Question.SERIAL -> if (answer.contains("\\D".toRegex()) || answer.length != 7) "Серийный номер содержит только цифры, и их 7\n${question.question}" else "-1"
                Question.IDLE -> "-1"
            }.toString()
        } else "-1"
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question

    }

}