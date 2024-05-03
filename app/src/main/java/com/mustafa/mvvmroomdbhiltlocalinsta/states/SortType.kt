package com.mustafa.mvvmroomdbhiltlocalinsta.states

enum class SortType(val sortName: String) {
    ID_DESCENDING("Yeniye Göre Sırala"),

    ID_ASCENDING("Eskiye Göre Sırala"),

    LIKES_DESCENDING("Beğeniye Göre Azalan Sırala"),

    LIKES_ASCENDING("Beğeniye Göre Artan Sırala"),

    DESCRIPTION_LENGTH_DESCENDING("Uzun Açıklamaya Göre Sırala"),

    DESCRIPTION_LENGTH_ASCENDING("Kısa Açıklamaya Göre Sırala"),

    OBFUSCATED_DATE("Düzenlenenleri Tarihe Göre Sırala")
}