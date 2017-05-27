package org.irreprimivel.montao.api.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider

object JsonUtil {
    fun objectToJsonString(expectedFields: Array<out String>?, jsonFilterName: String, pojo: Any): String {
        val except = if (expectedFields != null) SimpleBeanPropertyFilter.filterOutAllExcept(*expectedFields) else SimpleBeanPropertyFilter.serializeAll()
        val filter = SimpleFilterProvider().addFilter(jsonFilterName, except)
        val mapper = ObjectMapper()
        return mapper.writer(filter).writeValueAsString(pojo)
    }
}