package com.superwall.superwallkit_flutter.json

import com.superwall.sdk.models.assignment.ConfirmedAssignment
import com.superwall.sdk.models.triggers.Experiment

fun ConfirmedAssignment.toJson() =
    mapOf(
        "experimentId" to this.experimentId,
        "variant" to this.variant.toJson(),
    )

fun Experiment.Variant.toJson(): Map<String, Any?> =
    mapOf(
        "id" to this.id,
        "type" to
            when (this.type) {
                Experiment.Variant.VariantType.TREATMENT -> "TREATMENT"
                Experiment.Variant.VariantType.HOLDOUT -> "HOLDOUT"
            },
        "paywallId" to this.paywallId,
    )
