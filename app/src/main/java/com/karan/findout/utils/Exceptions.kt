package com.karan.findout.utils

class UnAuthorisedException : Exception()
class ApiException(message: String? = null) : Exception(message)