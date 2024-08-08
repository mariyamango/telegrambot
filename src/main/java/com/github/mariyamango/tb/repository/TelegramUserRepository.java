package com.github.mariyamango.tb.repository;

import com.github.mariyamango.tb.repository.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@link Repository} for handling with {@link TelegramUser} entity.
 */

public interface TelegramUserRepository extends JpaRepository<TelegramUser, String> {
    List<TelegramUser> findAllByActiveTrue();
}
