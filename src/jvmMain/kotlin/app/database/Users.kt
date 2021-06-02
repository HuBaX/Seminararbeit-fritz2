package app.database

import app.model.User
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object UsersTable: LongIdTable() {
    val username = varchar("username", 255)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
}

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UsersTable)

    var username by UsersTable.username
    var email by UsersTable.email
    var password by UsersTable.password

    fun toUser() = User(this.username, this.email, this.password)
}

object UserDB {
    fun add(user: User): User = database {
        UserEntity.new {
            username = user.username
            email = user.email
            password = user.password
        }.toUser()
    }

    fun find(username: String): UserEntity? = database {
        UserEntity.find { UsersTable.username eq username }.firstOrNull()
    }
}

fun <T> database(statement: Transaction.() -> T): T {
    return transaction {
        addLogger(StdOutSqlLogger)
        statement()
    }
}
