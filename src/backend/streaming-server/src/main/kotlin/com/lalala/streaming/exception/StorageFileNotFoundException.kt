package com.lalala.streaming.exception

class StorageFileNotFoundException : RuntimeException {
    constructor(message: String, ex: Exception) : super(message, ex) {}
    constructor(message: String) : super(message) {}
    constructor(ex: Exception): super(ex) {}
}
