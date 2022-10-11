package com.example.domain.executor

interface SchedulerProvider {
    fun io(): Scheduler

    fun ui(): Scheduler
}