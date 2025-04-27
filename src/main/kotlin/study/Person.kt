package study

class PersonBuilder(
    private var name: String = "",
    private var company: String = "",
    private var skills: MutableList<Skill> = mutableListOf(),
    private var languages: MutableList<Language> = mutableListOf(),
) {
    fun name(name: String) {
        this.name = name
    }

    fun company(company: String) {
        this.company = company
    }

    fun skills(block: SkillsBuilder.() -> Unit) {
        SkillsBuilder(this.skills).apply(block)
    }

    fun languages(block: LanguagesBuilder.() -> Unit) {
        LanguagesBuilder(this.languages).apply(block)
    }

    fun build(): Person = Person(name, company, skills, languages)
}

class LanguagesBuilder(
    var languages: MutableList<Language>,
) {
    infix fun String.level(level: Int) {
        languages.add(Language(this, level))
    }
}

class Language(
    val name: String,
    val level: Int,
)

class SkillsBuilder(
    private var skills: MutableList<Skill>,
) {
    fun soft(soft: String) {
        skills.add(Skill(soft, "soft"))
    }

    fun hard(hard: String) {
        skills.add(Skill(hard, "hard"))
    }
}

class Skill(
    var value: String?,
    var grade: String?,
)

class Person(
    val name: String,
    val company: String,
    val skills: MutableList<Skill>,
    val languages: MutableList<Language>,
)
