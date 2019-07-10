package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    val temp = this.trim()
    return if (temp.length - 1 <= length) {
        temp
    } else {
        val finalString = temp.substring(0, length + 1)
        if (finalString.endsWith(" ")) {
            finalString.trim().plus("...")
        } else {
            finalString.plus("...")
        }
    }
}

fun String.stripHtml(): String {
    return this.replace("<[^>]*>|&[^а-яА-ЯёЁ][#0-9]+?;|&\\w+?;".toRegex(), "")
            .replace("\\h+".toRegex(), " ")

}