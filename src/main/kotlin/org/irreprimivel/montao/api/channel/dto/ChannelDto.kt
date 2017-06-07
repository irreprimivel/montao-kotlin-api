package org.irreprimivel.montao.api.channel.dto

import org.jetbrains.annotations.NotNull

data class ChannelDto(
        @NotNull
        val title: String,
        val description: String,
        @NotNull
        val communityId: Long)