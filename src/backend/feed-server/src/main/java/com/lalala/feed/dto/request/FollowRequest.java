package com.lalala.feed.dto.request;

import com.lalala.feed.dto.FollowPayload;

public record FollowRequest(FollowPayload fromUser, FollowPayload toUser) {}
