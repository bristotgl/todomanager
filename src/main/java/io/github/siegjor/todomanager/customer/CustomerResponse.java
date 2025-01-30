package io.github.siegjor.todomanager.customer;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CustomerResponse(UUID customerId, String username, String email, OffsetDateTime createdAt) {
}
