package study

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking { // 3. 모든 자식들을 취소함.
        launch { // 2. 모든 자식들을 취소함.
            launch {
                delay(1000)
                throw Error() // 1. 예외 전파가 부모한테 감
            }
            launch {
                delay(2000)
                println("Will not be printed")
            }
            launch {
                delay(500)
                println("Will be printed")
            }
        }

        launch { // 3번에서 취소될때 다 취소됨
            delay(2000)
            println("Will not be printed")
        }
    }
}
