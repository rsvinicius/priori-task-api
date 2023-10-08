package com.example.prioritask.utils.validators

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import jakarta.validation.ReportAsSingleViolation
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@NotNull
@ReportAsSingleViolation
@Constraint(validatedBy = [FutureOrCurrentDateValidator::class])
annotation class FutureOrCurrentDate(
    val message: String = "Due date must be at least the current date",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class FutureOrCurrentDateValidator : ConstraintValidator<FutureOrCurrentDate, LocalDate?> {
    override fun isValid(value: LocalDate?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true  // Null values are handled by @NotBlank for 'dueDate'
        }

        val currentDate = LocalDate.now()

        return !currentDate.isAfter(value)
    }
}
