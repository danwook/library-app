package com.group.libraryapp.util

import java.lang.IllegalArgumentException

fun fail(): Nothing{
    throw IllegalArgumentException()
}