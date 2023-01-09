package org.mogoldb.weberpBackend.util

import kotlin.random.Random

class VerificationCodeUtil {
    companion object {
        private val alphabet = arrayListOf<Char>(
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'
        )

        fun genVerifyCode(): String {
            var code = ""
            while (code.length < 8) {
                val isCharRandom = Random.nextBoolean()
                code += if (isCharRandom) {
                    val charRandom = Random.nextInt(alphabet.size - 1)
                    alphabet[charRandom].toString()
                } else {
                    Random.nextInt(9).toString()
                }
            }
            return code
        }
    }
}