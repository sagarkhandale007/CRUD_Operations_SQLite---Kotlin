package com.example.sqlite_demo_kotlin.Model

class Customer {
    var sCustomerId = 0
    var firstName: String? = null
    var middleName: String? = null
    var lastName: String? = null
    var contactNo: String? = null

    constructor() {

    }
    constructor(SCustomerId: Int, firstName: String?, middleName: String?, lastName: String?, contactNo: String?) {
        sCustomerId = SCustomerId
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.contactNo = contactNo
    }
}