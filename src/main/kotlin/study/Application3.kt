package study

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val time =
            measureTimeMillis {
                val name = async { getName() }
                val lastName = async { getLastName() }
                println("Hello, ${name.await()} ${lastName.await()}")
            }
        println("Execution took $time ms")
    }
}

suspend fun getName(): String {
    delay(1000)
    return "Jason"
}

suspend fun getLastName(): String {
    delay(1000)
    return "Park"
}

/**
 * 실행 결과
 * Hello, Jason Park
 * Execution took 2017 ms
 * ---
 * Application.kt와 결과가 왜 다른가?
 * 기본적으로 코루틴은 async/await 구문을 넣어줘야 의도한대로 작동함.
 * 결과적으로 await 를 언제 호출하냐에 따라 코루틴의 실행시간이 결정됨.
 * "suspend" = 중단
 *
 * **/
