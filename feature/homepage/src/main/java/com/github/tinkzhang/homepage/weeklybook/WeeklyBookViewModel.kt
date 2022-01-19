package com.github.tinkzhang.homepage.weeklybook

import androidx.lifecycle.ViewModel
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.basic.model.NYBookType

class WeeklyBookViewModel : ViewModel() {
    val fictionBooks = UserRepository.getWeeklyBooks(NYBookType.Fiction)
    val nonFictionBooks = UserRepository.getWeeklyBooks(NYBookType.NonFiction)
}