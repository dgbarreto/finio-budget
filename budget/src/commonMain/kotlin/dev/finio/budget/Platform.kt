package dev.finio.budget

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform