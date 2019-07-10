package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        var parts: List<String>? = null
        val firstName: String?
        val lastName: String?
        val tempName = fullName?.trim()?.replace("\\s+".toRegex()," ")
        if (!tempName.isNullOrEmpty()) {
            parts = tempName?.split(" ")
        }
        firstName = parts?.getOrNull(0)
        lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var finalResult = ""
        var flag = true
        val mapOfChar: MutableMap<Char, String> = mutableMapOf()
        mapOfChar['а'] = "a"
        mapOfChar['б'] = "b"
        mapOfChar['в'] = "v"
        mapOfChar['г'] = "g"
        mapOfChar['д'] = "d"
        mapOfChar['е'] = "e"
        mapOfChar['ё'] = "e"
        mapOfChar['ж'] = "zh"
        mapOfChar['з'] = "z"
        mapOfChar['и'] = "i"
        mapOfChar['й'] = "i"
        mapOfChar['к'] = "k"
        mapOfChar['л'] = "l"
        mapOfChar['м'] = "m"
        mapOfChar['н'] = "n"
        mapOfChar['о'] = "o"
        mapOfChar['п'] = "p"
        mapOfChar['р'] = "r"
        mapOfChar['с'] = "s"
        mapOfChar['т'] = "t"
        mapOfChar['у'] = "u"
        mapOfChar['ф'] = "f"
        mapOfChar['х'] = "h"
        mapOfChar['ц'] = "c"
        mapOfChar['ч'] = "ch"
        mapOfChar['ш'] = "sh"
        mapOfChar['щ'] = "sh'"
        mapOfChar['ъ'] = ""
        mapOfChar['ы'] = "i"
        mapOfChar['ь'] = ""
        mapOfChar['э'] = "e"
        mapOfChar['ю'] = "yu"
        mapOfChar['я'] = "ya"

        for (char in payload) {
            if (char.toLowerCase() in mapOfChar) {
                if (char.isUpperCase()) {
                    val temp = mapOfChar[char.toLowerCase()]
                    if (temp!!.length > 1) {
                        finalResult += temp.substring(0, 1).toUpperCase() + temp.substring(1)
                    } else {
                        finalResult += temp.toUpperCase()
                    }
                } else {
                    finalResult += mapOfChar[char]
                }
            } else if (char == ' ') {
                finalResult += divider
            } else {
                if (char.isUpperCase()) {
                    finalResult += char.toUpperCase()
                } else finalResult += char
            }
        }
        return finalResult
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var initials: String? = ""
        if (!firstName?.trim().isNullOrEmpty()) {
            initials += firstName?.getOrNull(0)?.toUpperCase().toString()
        }
        if (!lastName?.trim().isNullOrEmpty()) {
            initials += lastName?.getOrNull(0)?.toUpperCase().toString()
        }
        if (initials.isNullOrEmpty()) {
            initials = null
        }
        return initials
    }
}