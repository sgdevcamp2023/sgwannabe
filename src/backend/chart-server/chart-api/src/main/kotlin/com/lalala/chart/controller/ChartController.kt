package com.lalala.chart.controller

import com.lalala.chart.controller.dto.ChartResponse
import com.lalala.chart.controller.dto.CreateChartRequest
import com.lalala.chart.service.ChartService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("charts")
class ChartController(
    private val chartService: ChartService,
)
{
    @GetMapping
    fun getChart(@RequestParam(required = false) timeStamp: Int?): Mono<ChartResponse> =
        chartService.getChart(timeStamp)
            .map { ChartResponse.of(it) }

    @PostMapping
    fun createChart(@RequestBody request: CreateChartRequest): Mono<ChartResponse> =
        chartService.createChart(request.timeStamp, request.records)
            .map { ChartResponse.of(it) }
}
