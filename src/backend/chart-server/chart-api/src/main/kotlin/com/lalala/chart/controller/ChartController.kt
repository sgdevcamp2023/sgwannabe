package com.lalala.chart.controller

import com.lalala.chart.controller.dto.ChartResponse
import com.lalala.chart.controller.dto.CreateChartRequest
import com.lalala.chart.service.ChartService
import com.lalala.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("charts")
@Tag(name = "Chart", description = "Chart API 문서")
class ChartController(
    private val chartService: ChartService,
) {
    @GetMapping
    @Operation(summary = "차트 조회", description = "차트 조회 API")
    suspend fun getChart(
        @RequestParam(required = false) timeStamp: Int?,
    ): BaseResponse<ChartResponse> {
        val chart = chartService.getChart(timeStamp)
        return BaseResponse.from(
            HttpStatus.OK.value(),
            "차트를 조회했습니다.",
            ChartResponse.of(chart),
        )
    }

    @PostMapping
    @Operation(summary = "차트 생성", description = "차트 생성 API")
    suspend fun createChart(
        @RequestBody request: CreateChartRequest,
    ): BaseResponse<ChartResponse> {
        val chart = chartService.createChart(request.timeStamp, request.records)
        return BaseResponse.from(
            HttpStatus.OK.value(),
            "차트를 생성했습니다.",
            ChartResponse.of(chart),
        )
    }
}
