package spring.feed.dto.request;

import spring.feed.dto.FollowPayload;

public record FollowRequest(FollowPayload fromUser, FollowPayload toUser) {}
