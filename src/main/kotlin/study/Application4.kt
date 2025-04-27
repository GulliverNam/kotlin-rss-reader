package study

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() =
    runBlocking {
        val job =
            launch {
                while (true) {
                    println("While in ${Thread.currentThread().name}")
                    delay(100L) // 2. 여기에 delay 를 넣어주면 아래 delay 를 지우지 않아도 job.cancel() 이 호출됨
                }
            }
        delay(1000L) // 1. suspend function -> 함수중단 중 다른일을 찾음 -> job 에 일을 뺐겨서 job.cancel() 이 실행되지 않음
        job.cancel()
    }
