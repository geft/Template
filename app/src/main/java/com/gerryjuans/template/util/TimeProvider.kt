package com.gerryjuans.template.util

import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class TimeProvider @Inject constructor() {

    fun getCurrentTime(): LocalDateTime = LocalDateTime.now()
}