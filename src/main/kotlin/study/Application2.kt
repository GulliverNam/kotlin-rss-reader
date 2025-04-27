@file:Suppress("ktlint:standard:no-wildcard-imports")

package study

import kotlinx.coroutines.*

data class UserInfo(
    val name: String,
    val lastName: String,
    val id: Int,
)

lateinit var user: UserInfo

fun main() {
    runBlocking {
        asyncGetUserInfo(1)
        delay(1000)
        println("User ${user.id} is ${user.name} ${user.lastName}")
    }
}

suspend fun asyncGetUserInfo(id: Int) {
    GlobalScope.async {
        delay(1100)
        user = UserInfo("Jason", "Park", id)
    }
}

/**
 * 실행결과
 * Exception in thread "main" kotlin.UninitializedPropertyAccessException: lateinit property user has not been initialized
 * 	at coroutines.Application2Kt.getUser(Application2.kt:7)
 * 	at coroutines.Application2Kt$main$1.invokeSuspend(Application2.kt:13)
 * 	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
 * 	at kotlinx.coroutines.DispatchedTaskKt.resume(DispatchedTask.kt:231)
 * 	at kotlinx.coroutines.DispatchedTaskKt.dispatch(DispatchedTask.kt:164)
 * 	at kotlinx.coroutines.CancellableContinuationImpl.dispatchResume(CancellableContinuationImpl.kt:466)
 * 	at kotlinx.coroutines.CancellableContinuationImpl.resumeImpl(CancellableContinuationImpl.kt:500)
 * 	at kotlinx.coroutines.CancellableContinuationImpl.resumeImpl$default(CancellableContinuationImpl.kt:489)
 * 	at kotlinx.coroutines.CancellableContinuationImpl.resumeUndispatched(CancellableContinuationImpl.kt:587)
 * 	at kotlinx.coroutines.EventLoopImplBase$DelayedResumeTask.run(EventLoop.common.kt:490)
 * 	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:277)
 * 	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:95)
 * 	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:69)
 * 	at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
 * 	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking$default(Builders.kt:48)
 * 	at kotlinx.coroutines.BuildersKt.runBlocking$default(Unknown Source)
 * 	at coroutines.Application2Kt.main(Application2.kt:10)
 * 	at coroutines.Application2Kt.main(Application2.kt)
 * 	---
 * 	UserInfo 가 초기화가 1.1초 뒤에 이루어졌기 때문에 오류가 발생함
 * **/
