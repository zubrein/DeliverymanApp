package xit.zubrein.gogocarrier.model

data class ModelDashboard(
    var status_code: Int,
    var completed_order: Int,
    var pending_order: Int,
    var total_earn: Int,
    var total_deposit: Int
)