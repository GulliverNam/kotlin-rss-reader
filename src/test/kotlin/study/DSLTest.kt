package study

import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DSLTest {
    @ValueSource(strings = ["남기원", "김철수"])
    @ParameterizedTest
    fun name(name: String) {
        val person =
            introduce {
                name(name) // 최상위 함수로 name 이 존재하거나 this 가 원래 있어야하는데 생략이 되었음 -> this 는 introduce 에서 정의를 해줘야함
            }
        person.name shouldBe name
    }

    @Test
    fun company() {
        val person =
            introduce {
                name("남기원")
                company("다음")
            }
        person.name shouldBe "남기원"
        person.company shouldBe "다음"
    }

    @Test
    fun skills() {
        val person =
            introduce {
                name("박재성")
                company("우아한형제들")
                skills {
                    soft("A passion for problem solving")
                    soft("Good communication skills")
                    hard("Kotlin")
                }
            }
        person.name shouldBe "박재성"
        person.company shouldBe "우아한형제들"
        person.skills[0] shouldBeEqualToComparingFields Skill("A passion for problem solving", "soft")
        person.skills[1] shouldBeEqualToComparingFields Skill("Good communication skills", "soft")
        person.skills[2] shouldBeEqualToComparingFields Skill("Kotlin", "hard")
    }

    @Test
    fun languages() {
        val person =
            introduce {
                name("박재성")
                company("우아한형제들")
                skills {
                    soft("A passion for problem solving")
                    soft("Good communication skills")
                    hard("Kotlin")
                }
                languages {
                    "Korean" level 5
                    "English" level 3
                }
            }
        person.name shouldBe "박재성"
        person.company shouldBe "우아한형제들"
        person.skills[0] shouldBeEqualToComparingFields Skill("A passion for problem solving", "soft")
        person.skills[1] shouldBeEqualToComparingFields Skill("Good communication skills", "soft")
        person.skills[2] shouldBeEqualToComparingFields Skill("Kotlin", "hard")
        person.languages[0] shouldBeEqualToComparingFields Language("Korean", 5)
        person.languages[1] shouldBeEqualToComparingFields Language("English", 3)
    }
}

// parameter 타입이 function
// 입력: function: () => 아무 함수나 올 수 있음
//       function: Person.() => Person 안에서 함수를 찾음
// 출력: Person
private fun introduce(block: PersonBuilder.() -> Unit): Person = PersonBuilder().apply(block).build()
